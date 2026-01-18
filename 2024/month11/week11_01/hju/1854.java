import java.io.*;
import java.util.*;

class Main {

    static final int INF = 987654321;

    static int N, M, K;
    static PriorityQueue<Integer> dist[];
    static ArrayList<Edge> edges[];

    static class Edge implements Comparable<Edge> {
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.cost = cost;
            this.to = to;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        dist = new PriorityQueue[N + 1];
        edges = new ArrayList[N + 1];
        for(int i = 1; i <= N; i++) {
            edges[i] = new ArrayList<>();
            // 가장 큰 값이 앞으로 오도록
            dist[i] = new PriorityQueue<>(Collections.reverseOrder());
        }

        for(int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edges[a].add(new Edge(b, c));
        }

        dijkstra();
        
        for(int i = 1; i <= N; i++) {
            if(dist[i].size() < K)   System.out.println(-1);
            else                     System.out.println(dist[i].peek());
        }

    }

    static void dijkstra() {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        
        dist[1].add(0);
        pq.add(new Edge(1, 0));

        while(!pq.isEmpty()) {
            Edge cur = pq.poll();

            for(Edge nx : edges[cur.to]) {
                int newCost = cur.cost + nx.cost;

                if(dist[nx.to].size() < K) {
                    dist[nx.to].add(newCost);
                    pq.add(new Edge(nx.to, newCost));
                }
                else if(dist[nx.to].size() == K) {
                    if(newCost < dist[nx.to].peek()) {
                        dist[nx.to].add(newCost);
                        pq.add(new Edge(nx.to, newCost));
                    }
                }

                if(dist[nx.to].size() > K)  dist[nx.to].poll();
            }
        }
    }
}