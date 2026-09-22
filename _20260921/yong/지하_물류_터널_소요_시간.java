import java.io.*;
import java.util.*;
public class 지하_물류_터널_소요_시간 {

    // 도착 지점과 도착 지점까지 드는 비용을 의미하는 클래스
    public static class Node implements Comparable<Node>{
        int idx;
        long cost;

        public Node(int idx, long cost){
            this.idx = idx;
            this.cost = cost;
        }

        @Override 
        public int compareTo(Node o){
            return Long.compare(this.cost, o.cost);
        }
    }

    public static void dij(int start){
        Arrays.fill(dist, Long.MAX_VALUE);

        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 시작점
        dist[start] = 0;
        pq.offer(new Node(start, 0));

        while(!pq.isEmpty()){
            Node cur = pq.poll();

            int nowV = cur.idx;
            Long curCost = cur.cost;

            // 이미 더 짧은 경로가 있으면 무시
            if(dist[nowV] < curCost){
                continue;
            }
            // 현재 정점과 연결된 정점 확인
            for(Node next : graph[nowV]){
                int nextV = next.idx;
                Long nextCost = curCost + next.cost;

                // 현재 정점을 거치는게 더 짧으면 갱신
                if(dist[nextV] > nextCost){
                    dist[nextV] = nextCost;
                    pq.offer(new Node(nextV, nextCost));
                }
            }
               
        }

    }

    static List<Node>[] graph;
    static int N, M;
    static long[] dist;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int S = Integer.parseInt(br.readLine());

        // 인접 리스트, 시작 지점 부터 최소비용 초기화
        graph = new ArrayList[N + 1];
        dist = new long[N + 1];

        for(int i = 0; i <= N; i++){
            graph[i] = new ArrayList<>();
        }

        // 터널 입력
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            Long cost = Long.parseLong(st.nextToken());
            // 양방향 터널 추가
            graph[from].add(new Node(to,cost));
            graph[to].add(new Node(from,cost));
        }

        dij(S);

        StringBuilder sb = new StringBuilder();
        for(int i = 1; i <= N; i++){
            if(dist[i] == Long.MAX_VALUE){
                sb.append("-1").append("\n");
            }
            else{
                sb.append(dist[i]).append("\n");
            }
        }
        System.out.println(sb);
    }
}

