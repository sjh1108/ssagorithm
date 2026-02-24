package _20260223.thdwngjs.BOJ_11952;

import java.io.*;
import java.util.*;

// 백준 11952 - 좀비 (Multi-source BFS + Dijkstra)
class Main {
    private static final long INF = 1L << 60; // 충분히 큰 무한대 값
    private static final int SHIFT = 17;      // 도시 번호(최대 100,000)를 저장하기 위한 17비트 시프트
    private static final long MASK = (1L << SHIFT) - 1; // 하위 17비트 마스크 (도시 번호 추출용)

    private static int N, M, K, S;
    private static int p, q;

    private static boolean[] infested;      // 좀비가 점령한 도시 여부
    private static boolean[] dangerCities;  // 좀비로부터 거리 S 이하인 위험한 도시 여부

    // 인접 리스트 구현용 배열 (객체 생성 오버헤드 최소화)
    private static int[] head;
    private static int[] to;
    private static int[] nextEdge;
    private static int edgeCount;

    // BFS 큐를 위한 배열 선언 (속도 향상)
    private static int[] danger;
    private static int dangerFront, dangerBack;
    private static int[] distFromZombie; // 좀비 도시로부터의 거리

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 도시의 수
        M = Integer.parseInt(st.nextToken()); // 도로의 수
        K = Integer.parseInt(st.nextToken()); // 좀비 점령 도시 수
        S = Integer.parseInt(st.nextToken()); // 위험 거리 범위

        st = new StringTokenizer(br.readLine());
        p = Integer.parseInt(st.nextToken()); // 안전한 도시 숙박비
        q = Integer.parseInt(st.nextToken()); // 위험한 도시 숙박비

        infested = new boolean[N + 1];
        for (int i = 0; i < K; i++) infested[Integer.parseInt(br.readLine())] = true;

        // 간선 연결 리스트 초기화
        head = new int[N + 1];
        Arrays.fill(head, -1);
        to = new int[M * 2];
        nextEdge = new int[M * 2];
        edgeCount = 0;

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            addEdge(a, b);
            addEdge(b, a);
        }

        checkDangerCites(); // 1단계: 좀비 점령지 주변 위험 도시 탐색 (BFS)
        System.out.println(dijkstra()); // 2단계: 최단 경로 탐색 (다익스트라)
    }

    private static void addEdge(int a, int b) {
        to[edgeCount] = b;
        nextEdge[edgeCount] = head[a];
        head[a] = edgeCount++;
    }

    // 다중 출발지 BFS(Multi-source BFS)를 통해 위험한 도시(S 이내)를 확인
    private static void checkDangerCites() {
        dangerCities = new boolean[N + 1];
        distFromZombie = new int[N + 1];
        Arrays.fill(distFromZombie, -1);

        danger = new int[N + 5];
        dangerFront = 0;
        dangerBack = 0;

        // 모든 좀비 점령 도시를 큐의 초기 상태로 삽입
        for (int city = 1; city <= N; city++) {
            if (!infested[city]) continue;
            distFromZombie[city] = 0;
            danger[dangerBack++] = city;
        }

        getDangerCites();
    }

    private static void getDangerCites() {
        while (dangerFront < dangerBack) {
            int cur = danger[dangerFront++];
            
            // 거리가 S에 도달하면 더 이상 확산하지 않음
            if (distFromZombie[cur] == S) continue;

            int nd = distFromZombie[cur] + 1;
            for (int e = head[cur]; e != -1; e = nextEdge[e]) {
                int near = to[e];
                if (distFromZombie[near] != -1) continue;

                distFromZombie[near] = nd;
                danger[dangerBack++] = near;
                
                // 전염된 도시가 아니라면, 위험한 도시로 마킹
                if (!infested[near]) dangerCities[near] = true;
            }
        }
    }

    // 1번 도시부터 N번 도시까지 최소 숙박비 계산
    private static long dijkstra() {
        long[] cost = new long[N + 1];
        Arrays.fill(cost, INF);
        cost[1] = 0;

        // 우선순위 큐를 사용하여 가장 적은 비용의 경로 탐색
        PriorityQueue<Long> pq = new PriorityQueue<>();
        pq.add(pack(0, 1)); // (비용, 도시 번호)

        while (!pq.isEmpty()) {
            long state = pq.poll();
            int t = (int) (state & MASK); // 하위 17비트(도시 번호) 추출
            long c = state >>> SHIFT;     // 상위 비트(누적 비용) 추출

            if (c != cost[t]) continue;
            if (t == N) return c; // 목적지 도착 시 비용 반환

            for (int e = head[t]; e != -1; e = nextEdge[e]) {
                int next = to[e];
                
                // 좀비가 점령한 도시는 들어갈 수 없음
                if (infested[next]) continue;

                // N번 도시는 숙박비가 0, 그 외 도시는 안전/위험 여부에 따라 비용 부과
                long add = (next == N) ? 0 : (dangerCities[next] ? q : p);
                long nCst = c + add;
                
                if (nCst < cost[next]) {
                    cost[next] = nCst;
                    pq.add(pack(nCst, next));
                }
            }
        }

        return cost[N];
    }

    // 객체(Node) 생성을 피하고 64비트 정수(long)에 비용과 도시 번호를 함께 담는 비트마스킹 최적화
    private static long pack(long c, int city) {
        return (c << SHIFT) | city;
    }
}