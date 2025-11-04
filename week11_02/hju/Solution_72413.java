import java.util.*;

class Solution {
    
    static class Edge implements Comparable<Edge> {
        int to;
        int cost;
        
        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
        
        public int compareTo(Edge o) {
            return Integer.compare(this.cost, o.cost);
        }
    }
    
    static final int INF = 987654321;
    static int N;
    static ArrayList<Edge> edges[];
    static int dist[][];
    
    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = INF;
        
        // 결론부터 말하자면 다익스트라 3번 할건데,
        // 3개의 함수를 따로 만들 순 없으니까 dist 배열을 2차원으로 만들어서 사용합시다.
        N = n;
        edges = new ArrayList[N + 1];
        dist = new int[N + 1][3]; // 0 : s, 1 : a, 2 : b
        
        for(int i = 0; i <= N; i++)
            edges[i] = new ArrayList<>();
        
        for(int[] fare : fares) {
            int q = fare[0];
            int w = fare[1];
            int e = fare[2];
            
            edges[q].add(new Edge(w, e));
            edges[w].add(new Edge(q, e));
        }
        for(int i = 0; i <= N; i++)
            Arrays.fill(dist[i], INF);
        
        dijkstra(s, 0);
        dijkstra(a, 1);
        dijkstra(b, 2);
        
        for(int i = 1; i <= N; i++) {
            // dist 배열에 INF로 남아있으면 갈 수 없는 곳
            if(dist[i][0] == INF || dist[i][1] == INF || dist[i][2] == INF)    continue;
            answer = Math.min(answer, dist[i][0] + dist[i][1] + dist[i][2]);
        }

        // dist[i][0] + dist[i][1] + dist[i][2] 이 수식의 의미
        // (s -> i) + (i -> a) + (i -> b) 입니다.
        // 어 왜 s -> i 는 출발지를 s로 하는 다익스트라로 하는데, i -> a, i -> b 는 어떻게 나오나요??
        // 단방향이 아닌 양방향 간선이기 때문에 i -> a 는 출발지로 a로 하는 다익스트라를 실행하고 나온 a -> i 와 같습니다. 
        
        return answer;
    }
    
    // 평범한 다익스트라
    static void dijkstra(int start, int flag) {
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        dist[start][flag] = 0;
        pq.add(new Edge(start, 0));
        
        while(!pq.isEmpty()) {
            Edge cur = pq.poll();
            
            if(dist[cur.to][flag] < cur.cost)   continue;
            
            for(Edge nx : edges[cur.to]) {
                int newCost = dist[cur.to][flag] + nx.cost;
                if(newCost < dist[nx.to][flag]) {
                    dist[nx.to][flag] = newCost;
                    pq.add(new Edge(nx.to, newCost));
                }
            }
        }
        
    }
}