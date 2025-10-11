package week09_03.sjh1108.BOJ_1719;

import java.io.*;
import java.util.*;

public class Main_1719_송주헌 {
    static class Edge  implements Comparable<Edge>{
        int to;
        int cost;

        public Edge(int to, int cost) {
            this.to = to;
            this.cost = cost;
        }
        
        @Override
        public int compareTo(Edge e){
            return Integer.compare(this.cost, e.cost);
        }
    }
    private static final int INF = 10_000_000;

    private static int N, M;
    private static int[][] map;
    private static List<List<Edge>> edgeList;

    private static void dijkstra(int start){
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        int[] dist = new int[N+1];
        boolean[] visited = new boolean[N+1];

        Arrays.fill(dist, INF);
        dist[start] = 0;

        // 각 정점에 대해서 시작점 초기화
        pq.add(new Edge(start, 0));
        while(!pq.isEmpty()){
            Edge cur = pq.poll();

            // 방문한 곳이라면 검사하지 않음
            if(dist[cur.to] < cur.cost) continue;
            // 방문 체크
            if(!visited[cur.to]) visited[cur.to] = true;

            // 도착지로부터 출발하는 간선 검사
            for(Edge e : edgeList.get(cur.to)){
                if(visited[e.to]) continue;
                if(cur.cost + e.cost >= dist[e.to]) continue;

                dist[e.to] = cur.cost + e.cost;
                map[e.to][start] = cur.to;

                pq.add(new Edge(e.to, dist[e.to]));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // init
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        edgeList = new ArrayList<>();
        for(int i = 0; i <= N; i++){
            edgeList.add(new ArrayList<>());
        }

        map = new int[N+1][N+1];
        for(int i = 1; i <= N; i++){
            Arrays.fill(map[i], INF);
            map[i][i] = 0;
        }

        // add Edges
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            edgeList.get(f).add(new Edge(t, c));
            edgeList.get(t).add(new Edge(f, c));
        }

        // dijkstra algorithm for every start points
        for(int i = 1; i <= N; i++){
            dijkstra(i);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= N; i++){
            for(int j = 1; j <= N; j++){
                if(i == j) sb.append('-');
                else sb.append(map[i][j]);
                sb.append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb);
    }
}