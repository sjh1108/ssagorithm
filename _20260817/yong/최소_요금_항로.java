/*
섬 N개, 양방향로 M개, 각 항로 요금 w
1번 섬 -> N 번 섬 까지 최소 요금(요금의 합은 int 넘을수 있음)
최소 요금, 요금 같으면 항로 개수 적은것
다익스트라
*/
import java.io.*;
import java.util.*;
public class 최소_요금_항로 {
    static int N, M;
    static ArrayList<Node>[] graph;

    // from 섬 -> to 섬 까지 비용 클래스
    public static class Node implements Comparable<Node>{
        int from; int to;
        long w;

        public Node(int from, int to, long w){
            this.from = from;
            this.to = to;
            this.w = w;
        }

        @Override
        public int compareTo(Node o){
            // 오름차순 정렬
            return Long.compare(this.w, o.w);
        }

    }
    // 다익스트라
    public static void dij(int start, int N){

        // 최소 거리 담을 배열
        long[] dist = new long[N + 1];
        Arrays.fill(dist, Long.MAX_VALUE); // 거리 합은 32비트 이상일수도 있으니 long 타입

        // 1번섬 출발이므로 1번섬 비용은 0
        dist[start] = 0;

        // 사용한 항로 개수
        int[] cnt = new int[N + 1];
        Arrays.fill(cnt, Integer.MAX_VALUE);
        cnt[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1,1,0));

        while(!pq.isEmpty()){
            Node now = pq.poll();

            if(dist[now.to] < now.w) continue;

            // 현재 섬에서 연결된 항로들 확인
            for(Node next : graph[now.to]){

                // 비용 갱신 : 현재 섬 까지 비용 + 다음 섬 까지 항로 비용
                long nextCost = dist[now.to] + next.w;

                int nextCnt = cnt[now.to] + 1;

                if(dist[next.to] > nextCost){
                    dist[next.to] = nextCost; 
                    cnt[next.to] = nextCnt;   
                    pq.offer(new Node(now.to, next.to, nextCost));                
                }
                
                // 비용 같은데 항로개수 더 적으면 갱신
                else if(dist[next.to] == nextCost && cnt[next.to] > nextCnt){
                    cnt[next.to] = nextCnt;
                    pq.offer(new Node(now.to, next.to, nextCost)); 
                }
                
            }
        }
        if(dist[N] == Long.MAX_VALUE){
            System.out.println(-1);
        }
        else{
            System.out.println(dist[N] +" "+ cnt[N]);
        }
        
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 섬, 항로 개수 입력
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 간선 그래프 초기화
        graph = new ArrayList[N + 1];

        for(int i = 0; i <= N; i ++){
            graph[i] = new ArrayList<>();
        }
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            long w = Long.parseLong(st.nextToken());

            graph[from].add(new Node(from,to,w));
            graph[to].add(new Node(to,from,w));
        }

        dij(1, N);
    }
}
