package week09_03.sjh1108.BOJ_1238;

import java.util.*;
import java.io.*;

public class Main_1238_송주헌{
    private static int N, M, X;

    private static int[][] map;
    private static List<List<Edge>> graph;

    private static class Edge implements Comparable<Edge>{
        int from, to, weight;
        Edge(int from, int to, int weight){
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    private static void dijkstra(Queue<Edge> edges){
        PriorityQueue<Edge> pq = new PriorityQueue<>(edges);

        while(!pq.isEmpty()){
            Edge edge = pq.poll();

            if(edge.weight > map[edge.from][edge.to]) continue;

            for(Edge next : graph.get(edge.to)){
                if(map[edge.from][next.to] > map[edge.from][edge.to] + next.weight){
                    map[edge.from][next.to] = map[edge.from][edge.to] + next.weight;
                    pq.offer(new Edge(edge.from, next.to, map[edge.from][next.to]));
                }
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());

        map = new int[N+1][N+1];
        for(int i = 1; i <= N; i++){
            Arrays.fill(map[i], Integer.MAX_VALUE);
            map[i][i] = 0;
        }
        graph = new ArrayList<>();
        for(int i = 0; i <= N; i++) graph.add(new ArrayList<>());

        Queue<Edge> edges = new ArrayDeque<>();
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            int weight = Integer.parseInt(st.nextToken());

            map[from][to] = weight;

            Edge edge = new Edge(from, to, weight);
            graph.get(from).add(edge);
            edges.offer(edge);
        }

        dijkstra(edges);

        int max = 0;
        for(int i = 1; i <= N; i++){
            max = Math.max(max, map[i][X] + map[X][i]);
        }

        System.out.println(max);
    }
}