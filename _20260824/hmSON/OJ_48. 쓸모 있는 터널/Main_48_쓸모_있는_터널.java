import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 다익스트라
     * 시작점 - 도착점에 대한 최속 경로가 여러 개일 수 있다
     * 최속 경로의 소요 시간과, 최속 경로에 속한 모든 터널의 수를 중복 없이 카운트해야 한다
     * 여러 최속 경로 중 어느 하나라도 포함되는 터널이라면 "쓸모 있는 터널"로 분류하여 카운트한다
     *
     * 1. 시작점을 원점으로 하는 다익스트라와, 도착점을 원점으로 하는 다익스트라를 각각 진행
     * 2. 각 터널마다 최속 경로에 속하는 지 검증
     * -> 다익스트라를 통해 최속 경로의 소요 시간을 알 수 있음
     * -> 이를 이용해 각 터널마다 최속 경로에 속하는 지 검증할 수 있음
     * -> (시작점 to 현재 터널의 한 입구) + (현재 터널) + (현재 터널의 다른 입구 to 도착점의 합) == (최속 경로의 소요 시간)
     * -> 위 조건을 충족하면 현재 터널은 최속 경로에 속한다
     */

    // 현 위치에서 다음 정점으로의 이동 정보를 관리하는 클래스
    // 목표 정점의 번호와 이동 시간 관리, 시간 오름차순으로 정렬 기준 재정의
    static class Node implements Comparable<Node> {
        int id;
        long cost;

        public Node(int id, long cost) {
            this.id = id;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            return Long.compare(this.cost, o.cost);
        }
    }

    static PriorityQueue<Node> q = new PriorityQueue<>(); // 다익스트라 우선순위 큐(Node의 정렬 기준 적용)
    static List<Node>[] graph; // 인접 리스트

    // 다익스트라 수행 메서드(매개변수 : 원점, 최단 시간 배열)
    static void dijkstra(int first, long[] dist) {
        q.clear();
        q.add(new Node(first, 0));
        dist[first] = 0;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            // 이미 더 짧은 시간을 소모하여 방문한 정점 무시
            if (cur.cost > dist[cur.id]) continue;

            for (Node next : graph[cur.id]) {
                // 현재까지의 이동 시간 + 다음 정점까지의 이동 시간
                long newCost = cur.cost + next.cost;
                if (dist[next.id] <= newCost) continue;

                dist[next.id] = newCost;
                q.add(new Node(next.id, newCost));
            }
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 시작점, 도착점으로부터 각 정점까지의 최소 이동 시간 초기화
        long[] distByStart = new long[v], distByEnd = new long[v];
        Arrays.fill(distByStart, Long.MAX_VALUE);
        Arrays.fill(distByEnd, Long.MAX_VALUE);

        graph = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            graph[i] = new ArrayList<>();
        }

        st = new StringTokenizer(br.readLine());
        int start = Integer.parseInt(st.nextToken()) - 1;
        int end = Integer.parseInt(st.nextToken()) - 1;

        // 다익스트라 이후 각 터널의 검증을 위해 인접 리스트와 별개로 터널 정보 저장
        int[] startIdx = new int[e], endIdx = new int[e];
        long[] nodeCost = new long[e];
        for (int i = 0; i < e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            long cost = Long.parseLong(st.nextToken());

            startIdx[i] = v1;
            endIdx[i] = v2;
            nodeCost[i] = cost;
            graph[v1].add(new Node(v2, cost));
            graph[v2].add(new Node(v1, cost));
        }

        // 시작점, 도착점을 기준으로 각각 다익스트라 수행
        // 최속 경로는 다익스트라를 통해 확정
        // 단, 시작점 - 도착점 간 이동 불가능한 입력값이 주어질 수 있음
        dijkstra(start, distByStart);
        dijkstra(end, distByEnd);
        long minCost = distByStart[end];
        if(minCost == Long.MAX_VALUE) {
            System.out.println("-1");
            return;
        }

        // 터널 검증 수행 및 카운트
        // 각 터널의 두 입구를 a, b라 할 때
        // (start -> a) + (a -> b) + (b -> end) == minCost
        // (start -> b) + (b -> a) + (a -> end) == minCost
        // 둘 중 하나라도 성립하면 해당 터널은 최속 경로에 속함
        int cnt = 0;
        for(int i=0; i<e; i++) {
            long needCost = minCost - nodeCost[i];
            if(distByStart[startIdx[i]] + distByEnd[endIdx[i]] == needCost
                    || distByEnd[startIdx[i]] + distByStart[endIdx[i]] == needCost) cnt++;
        }
        System.out.println(minCost + " " + cnt);
    }

}