package week09_04.sjh1108.BOJ_18352;

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

        // 간선 추가
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.get(a).add(new Edge(b, 1));
        }

        // 방문 체크를 위한 dist 배열 초기화
        int[] dist = new int[N+1];
        int INF = 3_000_000;
        Arrays.fill(dist, INF);

        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(X, 0));

        List<Integer> ans = new ArrayList<>();

        while(!pq.isEmpty()){
            Edge cur = pq.poll();

            // 비용이 K보다 크면 더 이상 탐색할 필요 없음
            if(cur.cost > K) break;
            // 비용이 INF가 아니면 이미 탐색한 경로임
            // -> 탐색할 필요 없음
            if(dist[cur.to] != INF) continue;
            // 비용이 K임 -> ans list에 추가
            if(cur.cost == K) ans.add(cur.to);

            // 방문 체크 및 비용 업데이트
            dist[cur.to] = cur.cost;

            // 목적지에서 뻗어 나가는 간선 추가
            for(Edge nxt: list.get(cur.to)){
                pq.add(new Edge(nxt.to, cur.cost+1));
            }
        }

        // 문제의 "오름차순" 조건을 만족하기 위한 정렬
        Collections.sort(ans);

        // 존재하지 않는 경우 -1 출력
        if(ans.size() == 0){
            System.out.println(-1);
            return;
        }

        // 정답 출력
        for(int a: ans){
            if(a == X) continue;
            System.out.println(a);
        }
    }
}
/*
 * 회고
 * 
 * ans 리스트를 리스트가 아닌 priorityqueue를 사용해도 괜찮았을 것 같네요
 * 다익스트라 알고리즘에 대한 최적화는 조금 부족한 것 같습니다.
 */