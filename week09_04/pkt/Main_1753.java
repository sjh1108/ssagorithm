package week_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1753 {
    static class Node implements Comparable<Node> {
        int v, w; 
        
        Node(int v, int w) {
            this.v = v;
            this.w = w;
        }
        public int compareTo(Node o) {
            return this.w - o.w; // 가중치 오름차순
        }
    }

    static final int INF = Integer.MAX_VALUE;
    static int V, E, start;      
    static List<Node>[] graph;    
    static int[] dist;            

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        
        V = Integer.parseInt(st.nextToken()); // 정점의 갯수
        E = Integer.parseInt(st.nextToken()); // 간선의 갯수
        start = Integer.parseInt(br.readLine());

        
        graph = new ArrayList[V+1];
        for (int i = 1; i <= V; i++) graph[i] = new ArrayList<>();

        
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            // 방향 그래프
            graph[u].add(new Node(v, w));
        }

     
        dist = new int[V+1];
        Arrays.fill(dist, INF); 

        
        dijkstra(start);

        
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= V; i++) {
            sb.append(dist[i] == INF ? "INF" : dist[i]).append("\n");
        }
        System.out.print(sb);
    }

   
    static void dijkstra(int s) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        dist[s] = 0; // 시작점까지 거리는 0으로 설정 - 정답 배열 세팅, 위 아래 역할이 다름!                  
        pq.add(new Node(s, 0)); // 시작 정점을 큐에 넣음 - 탐색 시작 트리거     

        while (!pq.isEmpty()) {
            Node cur = pq.poll(); // 가장 거리가 짧은 정점 꺼내기, 왜? 우선순위 큐니까,
            if (cur.w > dist[cur.v]) continue; // 이미 처리된 거리보다 크면 스킵..

            // dist[next.v] 기준이 되며 계속 갱신되는 녀석
            for (Node next : graph[cur.v]) {
                // 더 짧은 경로 발견 시 업데이트
                if (dist[next.v] > dist[cur.v] + next.w) {
                    dist[next.v] = dist[cur.v] + next.w;
                    pq.add(new Node(next.v, dist[next.v])); // 갱신된 거리 큐에 추가
                }
            }
        }
    }
}
