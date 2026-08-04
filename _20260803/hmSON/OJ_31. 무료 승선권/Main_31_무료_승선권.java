import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 다익스트라
     * 출력 : 최소 비용 경로의 소모 비용과 무료 승선권 사용 횟수
     * Node 객체가 소모 비용과 승선권 사용 횟수를 모두 관리해야 함
     * 정렬 순서 : 1 - 비용, 2 - 승선권 소모 횟수
     *
     * 본토와 이어진 항구 섬이 출발점이며 여러 곳이 주어질 수 있기 때문에 해당 섬을 모두 출발점으로 하는 다익스트라가 요구됨
     * 도착점은 N번 섬으로 고정. 본토와 이어진 항구 섬 중 도착점도 포함될 수 있음 -> 0 0 출력 후 종료
     *
     * 무료 승선권 - 사용 시 항로 이동 비용을 반감하며 소수점은 버린다
     * 각 섬마다 승선권 사용 횟수에 따른 최소 비용을 기록해야 함
     */

    // 다익스트라에 사용할 Node 클래스
    // 각 섬의 번호, 해당 섬까지의 소모 비용, 승선권 사용 횟수 관리
    static class Node implements Comparable<Node> {
        int id, used;
        long cost;

        public Node(int id, long cost, int used) {
            this.id = id;
            this.cost = cost;
            this.used = used;
        }

        @Override
        public int compareTo(Node o) {
            if(this.cost == o.cost) return Integer.compare(this.used, o.used);
            return Long.compare(this.cost, o.cost);
        }
    }

    static int v, k;
    // 각 섬마다 승선권 사용 횟수별 최소 비용 저장
    static long[][] dist;
    // 인접 리스트. 리스트 내 각 배열은 { 인접한 섬의 번호, 이동 비용 }
    static List<int[]>[] graph;
    static PriorityQueue<Node> q = new PriorityQueue<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        // 본토와 이어진 항구 섬(출발점) 수
        int port = Integer.parseInt(st.nextToken());

        graph = new ArrayList[v];
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
        }

        dist = new long[v][k+1];
        for(int i=0; i<v; i++) {
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        // 출발점 입력
        // 이 과정에서 도착점 == 출발점인 경우가 하나라도 존재하면 즉시 "0 0" 출력 후 종료
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<port; i++) {
            int p = Integer.parseInt(st.nextToken()) - 1;
            if(p == v-1) {
                System.out.println("0 0");
                return;
            }

            // 해당 섬을 출발점으로 등록하고 최소 비용 기록
            q.add(new Node(p, 0, 0));
            Arrays.fill(dist[p], 0);
        }

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
        while(!q.isEmpty()) {
            Node cur = q.poll();
            // 동일한 섬을 중복 탐색하는 경우에 대한 예외 처리
            if(cur.cost > dist[cur.id][cur.used]) continue;

            // 현재 위치가 도착점인 경우가 곧 최소 비용이면서 승선권 최소 사용인 상황임
            if(cur.id == v-1) {
                System.out.println(cur.cost + " " + cur.used);
                return;
            }

            for(int[] next : graph[cur.id]) {
                // 무료 승선권을 사용하는 경우 : 다음 섬으로 이동하는 비용을 반감
                // 단, 현재까지 사용한 승선권 수가 K개라면 추가 사용 불가
                long discountCost = cur.cost + (next[1] / 2);
                if(cur.used < k && dist[next[0]][cur.used + 1] > discountCost) {
                    dist[next[0]][cur.used + 1] = discountCost;
                    q.add(new Node(next[0], discountCost, cur.used + 1));
                }

                // 무료 승선권을 사용하지 않는 경우
                long newCost = cur.cost + next[1];
                if(dist[next[0]][cur.used] > newCost) {
                    dist[next[0]][cur.used] = newCost;
                    q.add(new Node(next[0], newCost, cur.used));
                }
            }
        }

        // Node 객체 전부 소모 : N번 섬으로 이동할 수 없음
        System.out.println("-1");
    }

}