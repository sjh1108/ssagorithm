import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 다익스트라
     * 무방향 그래프에서 임의의 출발점이 지정되고 다른 정점으로 이동할 때의 최소 비용을 각각 반환
     * 일부 정점에 도달할 수 없는 조건이 주어질 수 있음
     * 최소 비용이 int 범위를 벗어날 수 있음
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

    static long[] dist; // 각 정점까지의 최소 이동 시간 저장
    static PriorityQueue<Node> q = new PriorityQueue<>(); // 다익스트라 우선순위 큐(Node의 정렬 기준 적용)
    static List<Node>[] graph; // 인접 리스트

    // 다익스트라 수행 메서드
    static void dijkstra() {
        while(!q.isEmpty()) {
            Node cur = q.poll();
            // 이미 더 짧은 시간을 소모하여 방문한 정점 무시
            if(cur.cost > dist[cur.id]) continue;

            for(Node next : graph[cur.id]) {
                // 현재까지의 이동 시간 + 다음 정점까지의 이동 시간
                long newCost = cur.cost + next.cost;
                if(dist[next.id] <= newCost) continue;

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

        // 각 정점까지의 최소 이동 시간 초기화
        dist = new long[v];
        Arrays.fill(dist, Long.MAX_VALUE);

        graph = new ArrayList[v];
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
        }

        // 출발점 상태 초기화
        int s = Integer.parseInt(br.readLine()) - 1;
        q.add(new Node(s, 0));
        dist[s] = 0;

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            long cost = Long.parseLong(st.nextToken());

            graph[v1].add(new Node(v2, cost));
            graph[v2].add(new Node(v1, cost));
        }

        dijkstra();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<v; i++) {
            // 다익스트라 이후에도 dist[i]가 초기값이면 도달 불가능 -> -1 출력
            sb.append(dist[i] == Long.MAX_VALUE ? -1 : dist[i]).append("\n");
        }
        System.out.println(sb);
    }

}