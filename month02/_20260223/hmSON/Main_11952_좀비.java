import java.io.*;
import java.util.*;

public class Main_11952_좀비 {

    /*
     * BOJ_11952 : 좀비 (Gold_2)
     * 자료구조 및 알고리즘 : BFS, 다익스트라
     *
     * [문제 요약]
     * - N개의 도시(정점)와 M개의 양방향 도로(간선)가 주어진다.
     * - 좀비에게 점령된 도시(occupied)는 들어갈 수 없다.
     * - 점령 도시에 가까운(거리 S 이내) 도시는 '위험(danger)' 도시가 되어 숙박 비용이 더 비싸다.
     * - 안전 도시(safety)는 비용 P, 위험 도시(danger)는 비용 Q를 지불하며 이동한다.
     * - 1번 도시에서 N번 도시까지 이동할 때 드는 최소 비용을 구한다.
     *
     * [핵심 아이디어]
     * - 이동 비용이 "도시의 상태(안전/위험/점령)"에 따라 달라지므로, 먼저 각 도시의 상태를 확정해야 한다.
     * - 1) 멀티 소스 BFS로 점령 도시들을 시작점으로 확산하며,
     *    거리 S 이내의 도시를 danger로 마킹한다. (occupied는 그대로 유지)
     * - 2) 그 다음, 도시 상태에 따라 간선 완화 비용을 계산하며 다익스트라로 최소 비용 경로를 구한다.
     *
     * [구현 메모]
     * - state 배열:
     *   - 'o' : occupied (진입 불가)
     *   - 'd' : danger (비용 Q)
     *   - 's' : safety (비용 P)
     * - BFS:
     *   - 큐(pollution)에 모든 occupied 도시를 (거리 0)으로 넣고 시작(멀티 소스).
     *   - 안전 도시('s')만 danger로 바꾸며,
     *   - 현재 거리 + 1 < range 일 때만 큐에 넣어 다음 레벨로 확산한다.
     *     (즉, 거리 1..range 범위 내까지 danger 처리)
     * - 다익스트라:
     *   - dist[v] = 1번(0번 인덱스) 도시에서 v까지 최소 비용.
     *   - next가 occupied면 skip.
     *   - next가 도착 도시(city-1)면 "도착지는 숙박비를 내지 않는다"는 조건을 반영하기 위해
     *     cur.cost를 바로 반환한다. (도착 도시 비용을 더하지 않음)
     *   - 그 외 도시는 상태에 따라 safety(P) 또는 danger(Q)를 더해 완화한다.
     *
     * [시간 복잡도]
     * - BFS: O(N + M)
     * - 다익스트라: O((N + M) log N)
     * - 총: O((N + M) log N)
     */

    static int city, road, occupied, range, safety, danger;
    static char[] state; // o(occupied), d(danger), s(safety);
    static Queue<Node> pollution = new ArrayDeque<>();
    static List<Integer>[] graph;

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

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        city = Integer.parseInt(st.nextToken());
        road = Integer.parseInt(st.nextToken());
        occupied = Integer.parseInt(st.nextToken());
        range = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        safety = Integer.parseInt(st.nextToken());
        danger = Integer.parseInt(st.nextToken());

        state = new char[city];
        graph = new ArrayList[city];
        Arrays.fill(state, 's');
        for(int i=0; i<city; i++) {
            graph[i] = new ArrayList<>();
        }

        // 점령 도시 입력 + 멀티 소스 BFS 시작점으로 큐에 삽입
        for(int i=0; i<occupied; i++) {
            int idx = Integer.parseInt(br.readLine()) - 1;
            state[idx] = 'o';
            pollution.add(new Node(idx, 0)); // cost 필드를 BFS에서는 "거리"로 사용
        }

        for(int i=0; i<road; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            graph[a].add(b);
            graph[b].add(a);
        }
    }

    // 점령 도시로부터 range 거리 내 도시를 danger로 마킹 (멀티 소스 BFS)
    static void bfs() {
        while(!pollution.isEmpty()) {
            Node cur = pollution.poll();

            for(int next : graph[cur.id]) {
                // 이미 occupied거나 danger로 처리된 도시는 스킵 (안전 도시만 danger로 바꾼다)
                if(state[next] != 's') continue;

                state[next] = 'd';
                // 거리 확산: range 범위까지만 전파
                if(cur.cost + 1 < range) {
                    pollution.add(new Node(next, cur.cost + 1));
                }
            }
        }
    }

    static long dijkstra() {
        long[] dist = new long[city];
        Arrays.fill(dist, Long.MAX_VALUE);
        PriorityQueue<Node> q = new PriorityQueue<>();
        q.add(new Node(0, 0));
        dist[0] = 0;

        while(!q.isEmpty()) {
            Node cur = q.poll();
            if(cur.cost > dist[cur.id]) continue;

            for(int next : graph[cur.id]) {
                // 점령 도시는 진입 불가
                if(state[next] == 'o') continue;

                // 도착 도시에는 비용을 더하지 않고 종료 (문제 조건 반영)
                if(next == city-1) return cur.cost;

                long newCost = cur.cost + (state[next] == 's' ? safety : danger);
                if(newCost >= dist[next]) continue;

                dist[next] = newCost;
                q.add(new Node(next, newCost));
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        bfs();
        System.out.println(dijkstra());
    }

}