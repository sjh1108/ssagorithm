/*
P섬 시작, 다익스트라, 승선권 배열 추가
*/

import java.io.*;
import java.util.*;

public class 무료_승선권 {
    public static class Node implements Comparable<Node>{
        int to; long cost; int cnt;

        public Node(int to, long cost, int cnt){
            this.to = to;
            this.cost = cost;
            this.cnt = cnt; // 티켓 사용 개수
        }

        @Override
        // 오름차순 정렬
        public int compareTo(Node o){
            return Long.compare(this.cost,o.cost);
        }
    }

    public static void dij(){

        // dist[i][k] 승선권 k 장 써서 i 까지 갔을때 최소 비용
        long[][] dist = new long[N + 1][K + 1];

        for(int i = 1; i <= N ;i++){
            Arrays.fill(dist[i], Long.MAX_VALUE);
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 출발 섬 비용 0
        for(int p : ports){
            dist[p][0] = 0;
            pq.offer(new Node(p,0,0));
        }

        while(!pq.isEmpty()){
            Node now = pq.poll();
            // 더 최적인게 이미 들어가 있으면 패스
            if(dist[now.to][now.cnt] < now.cost) continue;
            
            for(Node next : graph[now.to]){
                // 승선권 사용 x
                long nextCost = dist[now.to][now.cnt] + next.cost;

                // 최적 이면 갱신
                if(dist[next.to][now.cnt] > nextCost){
                    dist[next.to][now.cnt] = nextCost;
                    pq.offer(new Node(next.to, nextCost, now.cnt));
                }

                // 승선권 사용 o
                if(now.cnt < K){
                    nextCost = dist[now.to][now.cnt] + next.cost / 2;
                    if(dist[next.to][now.cnt + 1] > nextCost){
                        dist[next.to][now.cnt + 1] = nextCost;
                        pq.offer(new Node(next.to, nextCost, now.cnt + 1));
                    }
                }
                

            }

        }
        long minCost = Long.MAX_VALUE;
        int minCnt = 0;

        for(int i = 0; i <= K; i++){
            if(dist[N][i] < minCost){
                minCost = dist[N][i];
                minCnt = i;
            }
        }

        if(minCost == Long.MAX_VALUE){
            System.out.println(-1);
        }

        else{
            System.out.println(minCost + " " + minCnt);
        }
    }
    static int N, M, P, K;
    static int[] ports;
    static ArrayList<Node>[] graph;
    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());
        P = Integer.parseInt(st.nextToken());

        // 항구 번호
        st = new StringTokenizer(br.readLine());
        ports = new int[P];
        for(int i = 0; i < P; i++){
            ports[i] = Integer.parseInt(st.nextToken());
        }

        // 항로 초기화
        graph = new ArrayList[N + 1];

        for(int i = 0; i <= N; i++){
            graph[i] = new ArrayList<>();
        }

        // 항로 입력
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());        
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            long cost = Long.parseLong(st.nextToken());

            graph[from].add(new Node(to, cost, 0));
            graph[to]. add(new Node(from, cost, 0));
        }

        dij();

    }
}
