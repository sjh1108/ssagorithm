package week09_04.sjh1108.BOJ_13911;
import java.io.*;
import java.util.*;

/*
 * B형 기출 문제 풀이를 위해 다시 풀어본 문제
 * B형 직후 ai를 활용하여 미리 풀었었으나, 내 힘으로 풀기 위해 다시 풀어봄
 */
class Main {
    // 간선 정보를 저장하는 클래스
    static class Edge implements Comparable<Edge>{
        int to, cost;
        // 기본 Edge 클래스와의 차이점
        // 스타벅스에서 출발했는지, 맥도날드에서 출발했는지에 대한 정보를 저장함
        boolean flag;

        public Edge(int to, int cost){
            this.to = to;
            this.cost = cost;
        }
        public Edge(int to, int cost, boolean flag){
            this(to, cost);
            this.flag = flag;
        }
        @Override
        public int compareTo(Edge e){
            return Integer.compare(this.cost, e.cost);
        }
    }
    public static void main(String[] args) throws IOException {
        // 입력파트
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int V = Integer.parseInt(st.nextToken());
        int E = Integer.parseInt(st.nextToken());

        List<List<Edge>> list = new ArrayList<>();
        for(int i = 0; i <= V; i++){
            list.add(new ArrayList<>());
        }

        while(E-- > 0){
            st = new StringTokenizer(br.readLine());

            int f = Integer.parseInt(st.nextToken());
            int t = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());

            list.get(f).add(new Edge(t, c));
            list.get(t).add(new Edge(f, c));
        }

        // 다익스트라를 위한 PQ 선언
        PriorityQueue<Edge> pq = new PriorityQueue<>();

        // 맥도날드의 수 m, 맥세권 조건 x
        st = new StringTokenizer(br.readLine());
        int m = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        
        // 맥도날드와의 cost 저장하는 dist 배열 선언
        int[] distM = new int[V+1];
        int INF = 1_000_000_000;
        Arrays.fill(distM, INF);

        // 맥도날드 정보 입력
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < m; i++){
            int tmp = Integer.parseInt(st.nextToken());
            pq.add(new Edge(tmp, 0, false));
        }

        // 스타벅스의 수 s, 스세권 조건 y
        st = new StringTokenizer(br.readLine());
        int s = Integer.parseInt(st.nextToken());
        int y = Integer.parseInt(st.nextToken());
        
        // 스타벅스와의 cost 저장하는 dist 배열 선언
        int[] distS = new int[V+1];
        Arrays.fill(distS, INF);
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < s; i++){
            int tmp = Integer.parseInt(st.nextToken());
            pq.add(new Edge(tmp, 0, true));
        }

        int min = INF;
        while(!pq.isEmpty()){
            Edge cur = pq.poll();

            // 간선이 스타벅스에서 출발한 경우
            if(cur.flag){
                // 이미 방문한 위치면 탐색하지 않음
                if(distS[cur.to] != INF) continue;

                int curCost = cur.cost;
                distS[cur.to] = curCost;

                // 맥세권이라면 min 값 초기화
                //
                // 이 부분에서 실수 1회
                // 맥세권인지 맥도날드인지 확인하지 않았음
                if(distM[cur.to] != INF && distM[cur.to] > 0) min = Math.min(min, distM[cur.to] + curCost);

                for(Edge nxt: list.get(cur.to)){
                    int nxtCost = curCost + nxt.cost;
                    if(nxtCost > y) continue;
                    pq.add(new Edge(nxt.to, nxtCost, cur.flag));
                }
            } 
            // 간선이 맥도날드에서 출발한 경우
            else{
                if(distM[cur.to] != INF) continue;

                int curCost = cur.cost;
                distM[cur.to] = curCost;

                if(distS[cur.to] != INF && distS[cur.to] > 0) min = Math.min(min, distS[cur.to] + curCost);

                for(Edge nxt: list.get(cur.to)){
                    int nxtCost = curCost + nxt.cost;
                    if(nxtCost > x) continue;
                    pq.add(new Edge(nxt.to, nxtCost, cur.flag));
                }
            }
        }

        // min 값이 초기화되지 않았다면, 원하는 집이 존재하지 않았던 것임
        System.out.println(min == INF ? -1 : min);
    }
}