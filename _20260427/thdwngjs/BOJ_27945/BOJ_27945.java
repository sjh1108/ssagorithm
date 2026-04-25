package _20260427.thdwngjs.BOJ_27945;

import java.io.*;
import java.util.*;

class Main {
    // 간선 클래스 (가중치 기준 오름차순 정렬)
    static class Edge implements Comparable<Edge>{
        int u, v, w; // u, v: 정점, w: 가중치(날짜)

        Edge(int u, int v, int w){
            this.u = u;
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge e){
            return Integer.compare(this.w, e.w);
        }
    }

    private static int[] parent;       // Union-Find 부모 배열 (음수면 루트, 절대값은 트리 크기)
    private static Queue<Edge> pq;     // 가중치 오름차순 우선순위 큐

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 정점 수
        int M = Integer.parseInt(st.nextToken()); // 간선 수

        parent = new int[N + 1];
        pq = new PriorityQueue<>();

        // 간선 입력
        int u, v, w;
        while (M-- > 0) {
            st = new StringTokenizer(br.readLine());

            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            pq.offer(new Edge(u, v, w));
        }

        System.out.println(kruskal(N));
    }

    // 크루스칼 알고리즘 변형: 날짜가 연속으로 이어지는 간선만 선택
    private static int kruskal(int N) {
        Arrays.fill(parent, -1); // 모든 정점을 독립 집합으로 초기화
        int day = 0; // 현재까지 연속으로 이은 날짜 수

        while (!pq.isEmpty() && day < N - 1) {
            Edge e = pq.poll();

            if (!union(e.u, e.v)) continue; // 이미 같은 집합이면 스킵 (사이클 방지)

            // 현재 날짜(day+1)보다 간선의 날짜가 크면 연속이 끊기므로 종료
            if (day + 1 < e.w) break;
            day++;
        }

        return day + 1; // 1일째부터 시작하므로 +1
    }

    // Union-Find: 두 집합 합치기 (랭크 기반)
    private static boolean union(int u, int v) {
        int r1 = find(u);
        int r2 = find(v);

        if (r1 == r2) return false; // 이미 같은 집합

        // 크기가 큰 트리에 작은 트리를 붙임
        if (parent[r1] < parent[r2]) {
            parent[r1] += parent[r2];
            parent[r2] = r1;
        } else {
            parent[r2] += parent[r1];
            parent[r1] = r2;
        }

        return true;
    }

    // Union-Find: 루트 찾기 (경로 압축)
    private static int find(int u) {
        if (parent[u] < 0) return u; // 음수면 루트

        return parent[u] = find(parent[u]); // 경로 압축
    }
}
