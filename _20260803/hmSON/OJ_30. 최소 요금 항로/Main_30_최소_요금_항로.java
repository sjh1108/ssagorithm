import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 다익스트라
     * 출발점과 도착점이 1 to N으로 고정되어 있다
     * 1 to N의 최소 요금을 구하고, 동일한 요금을 내는 방법이 여러 가지인 경우 이동한 항로 수가 가장 적은 방법을 선택한다
     * 따라서 우선순위 큐에 이 정렬 순서를 적용한 뒤 다익스트라를 활용하여 최단 경로를 탐색한다
     *
     * 요금의 합은 int 범위를 초과할 수 있으며, N번 섬에 도달하지 못할 수 있다
     * 같은 두 섬을 잇는 항로가 여러 개일 수 있고, 요금도 서로 다를 수 있다
     */

    // 최단 경로 탐색에 적용할 Node 클래스
    // 정렬 순서 : 1 - 비용(cost), 2 - 이동 횟수(cnt)
    static class Node implements Comparable<Node> {
        int id, cnt;
        long cost;

        public Node(int id, long cost, int cnt) {
            this.id = id;
            this.cost = cost;
            this.cnt = cnt;
        }

        @Override
        public int compareTo(Node o) {
            if(this.cost == o.cost) return Integer.compare(this.cnt, o.cnt);
            return Long.compare(this.cost, o.cost);
        }
    }

    static int v;
    // 각 섬별 최단 경로 이동시의 비용
    static long[] dist;
    // 각 섬별 최단 경로를 기록할 때 이동 횟수를 이 배열에 기록한다
    static int[] moveCnt;
    // 인접 리스트. 각 리스트 내 배열은 인접한 다른 섬의 번호와 항로 비용
    static List<int[]>[] graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        graph = new ArrayList[v];
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
        }
        dist = new long[v];
        Arrays.fill(dist, Long.MAX_VALUE);
        moveCnt = new int[v];
        Arrays.fill(moveCnt, Integer.MAX_VALUE);

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            int cost = Integer.parseInt(st.nextToken());
            graph[v1].add(new int[]{v2, cost});
            graph[v2].add(new int[]{v1, cost});
        }

        dijkstra();
    }

    static void dijkstra() {
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(0, 0, 0));
        dist[0] = 0;
        moveCnt[0] = 0;

        while(!q.isEmpty()) {
            Node cur = q.poll();

            // id가 동일한 노드의 중복 탐색 방지
            if(cur.cost > dist[cur.id]) continue;
            // 현재 위치가 도착점인 경우
            if(cur.id == v-1) {
                System.out.println(cur.cost + " " + cur.cnt);
                return;
            }

            for(int[] next : graph[cur.id]) {
                long newCost = cur.cost + next[1];
                // 현재 경로의 비용과 기존에 기록된 비용이 같더라도 현재 경로의 이동 횟수가 더 적다면 갱신해야 함
                if(dist[next[0]] < newCost || (dist[next[0]] == newCost && moveCnt[next[0]] <= cur.cnt + 1)) continue;

                dist[next[0]] = newCost;
                moveCnt[next[0]] = cur.cnt + 1;
                q.add(new Node(next[0], newCost, cur.cnt + 1));
            }
        }

        // Node 객체 전부 사용 -> N번 섬으로 도착할 수 없음
        System.out.println("-1");
    }

}