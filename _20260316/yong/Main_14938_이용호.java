package _20260316.yong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_14938_이용호 {
    /*
     * 어떤 지점에서 부터 수색범위 m 이하의 모든 지역의 아이템 수집
     * 최대 얻을 수 있는 아이템 수 구하기
     * 각 지점에서 다익스트라로 최대 얻을 수 있는 아이템 수 구하기
     */
    public static int N, M, R;
    public static int[] item;
    public static List<Node>[] graph;
    public static int INF = 1000000000;

    public static class Node implements Comparable<Node>{
        int to, dist;
        public Node(int to, int dist){
            this.to = to;
            this.dist = dist;
        }
        @Override
        public int compareTo(Node o){
            return this.dist - o.dist;
        }

    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 지역개수 n, 수색범위 m, 길의 개수r
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        item = new int[N+1];
        st = new StringTokenizer(br.readLine());
        // 각 지역의 아이템 수 N개
        for(int i = 1; i <= N; i++) {
            item[i] = Integer.parseInt(st.nextToken());
        }
        graph = new ArrayList[N+1];
        // 양끝 지역 번호, 거리(길이) R개
        for(int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        for(int i = 1; i <= R; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            graph[a].add(new Node(b,l));
            graph[b].add(new Node(a,l));
        }

        int answer = 0;
        for(int i = 1; i <= N; i++) {
//			System.out.println(i);
            int sum = dij(i);
            answer = Math.max(answer, sum);
        }
        System.out.println(answer);

    }
    private static int dij(int start) {

        PriorityQueue<Node> pq = new PriorityQueue<>();
        int[] dist = new int[N+1];
        Arrays.fill(dist, INF);
        pq.add(new Node(start,0));
        dist[start] = 0;

        while(!pq.isEmpty()) {
            Node cur = pq.poll();

            // 이미 더 작은 값으로 갱신된게 있으면 가지치기
            if(cur.dist > dist[cur.to]) continue;

            // 현재 노드에서 갈수 있는곳 확인
            for(Node next : graph[cur.to]) {
                // next까지 비용 계산
                int nextDist = cur.dist + next.dist;
                // 이전 계산값 보다 현재 계산값이 작으면 next 비용 갱신
                if(dist[next.to] > nextDist) {
                    dist[next.to] = nextDist;
                    pq.add(new Node(next.to, nextDist));
                }
            }
        }
        // 수색 범위 내 인지 확인 후 아이템 수 합산(dist가 m 이하인지 확인)
        int sum = 0;
        for(int i = 1; i <= N; i++) {
            if(dist[i] <= M) {
                sum += item[i];
            }
        }
        return sum;
    }

}
