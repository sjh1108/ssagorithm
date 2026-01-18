import java.io.*;
import java.util.*;

class Main {

    static final long INF = Long.MAX_VALUE;

    static int N, M, K;
    static ArrayList<Edge> edges[];
    static long[][] dist;       // 걸리는 시간이 1,000,000 이하임을 주의하자

    static class Edge implements Comparable<Edge>{
        int to;
        long cost;
        int cnt;    // 남은 포장 횟수

        public Edge(int to, long cost) {
            this.cost = cost;
            this.to = to;
        }

        public Edge(int to, long cost, int cnt) {
            this.cnt = cnt;
            this.cost = cost;
            this.to = to;
        }

        @Override
        public int compareTo(Edge o) {
            // 비용이 같은 경우 남은 포장 횟수가 많은 경우가 우선
            if(this.cost == o.cost)
                return Integer.compare(o.cnt, this.cnt);
            return Long.compare(this.cost, o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dist = new long[N + 1][K + 1];
        for(int i = 1; i <= N; i++) 
            for(int j = 0; j <= K; j++)
                dist[i][j] = INF;
        
        edges = new ArrayList[N + 1];
        for(int i = 1; i <= N; i++)
            edges[i] = new ArrayList<>();
        
        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int cost = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(b, cost));
            edges[b].add(new Edge(a, cost));
        }

        dijkstra();

        long ans = INF;
        for(int k = 0; k <= K; k++)
            ans = Math.min(ans, dist[N][k]);
        
        System.out.println(ans);
    }

    static void dijkstra() {
        
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for(int k = 0; k <= K; k++)
            dist[1][k] = 0;
        pq.add(new Edge(1, 0, K));

        while(!pq.isEmpty()) {
            Edge cur = pq.poll();

            if(cur.cost > dist[cur.to][cur.cnt])    continue;

            for(Edge nx : edges[cur.to]) {

                // 2가지 경우를 생각해야함.
                // 현재 포장횟수가 남아있는 경우(cnt > 0) 다음 길을 포장
                if(cur.cnt > 0) {
                    // 포장하면 걸리는 시간이 0이 됩니다.
                    long newCost = dist[cur.to][cur.cnt] + 0;

                    if(newCost < dist[nx.to][cur.cnt - 1]) {
                        dist[nx.to][cur.cnt - 1] = newCost;
                        pq.add(new Edge(nx.to, newCost, cur.cnt - 1));
                    }
                }

                // 다음 길을 포장안하고 가는 경우
                long newCost = dist[cur.to][cur.cnt] + nx.cost;

                if(newCost < dist[nx.to][cur.cnt]) {
                    dist[nx.to][cur.cnt] = newCost;
                    pq.add(new Edge(nx.to, newCost, cur.cnt));
                }
            }
        }
    }
}