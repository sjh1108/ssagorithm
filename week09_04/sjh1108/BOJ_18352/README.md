import java.io.*;
import java.util.*;

class Main {
    static class Edge implements Comparable<Edge>{
        int to, cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge e){
            return Integer.compare(this.cost, e.cost);
        }
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int X = Integer.parseInt(st.nextToken());

        List<List<Edge>> list = new ArrayList<>();
        for(int i = 0; i <= N; i++) list.add(new ArrayList<>());

        while(M-- > 0){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(new Edge(b, 1));
        }

        int[] dist = new int[N+1];

        int INF = 3_000_000;
        Arrays.fill(dist, INF);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(X, 0));

        List<Integer> ans = new ArrayList<>();

        while(!pq.isEmpty()){
            Edge cur = pq.poll();

            if(dist[cur.to] != INF) continue;
            if(cur.cost == K) ans.add(cur.to);
            dist[cur.to] = cur.cost;

            for(Edge nxt: list.get(cur.to)){
                pq.add(new Edge(nxt.to, cur.cost+1));
            }
        }

        Collections.sort(ans);

        if(ans.size() == 0){
            System.out.println(-1);
            return;
        }
        for(int a: ans){
            if(a == X) continue;
            System.out.println(a);
        }
    }
}