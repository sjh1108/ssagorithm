import java.util.*;
import java.io.*;
public class 배달 {
    // 도착 지점과, 도착 지점 까지 드는 비용
    public static class Node implements Comparable<Node>{
        int cost;
        int v;

        public Node(int v, int cost){
            this.v = v;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o){
            return Integer.compare(this.cost,o.cost);
        }
    }
    public static int solution(int N, int[][] road, int K){
        int answer = 0;
        
        int[] dist = new int[N + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);

        ArrayList<Node>[] graph = new ArrayList[N + 1];

        for(int i = 0; i <= N; i++){
            graph[i] = new ArrayList<>();
        }
        for(int[] r : road){
            int from = r[0];
            int to = r[1];
            int cost = r[2];

            graph[from].add(new Node(to,cost));
            graph[to].add(new Node(from,cost));
        }

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(1,0));
        dist[1] = 0;

        while(!pq.isEmpty()){
            Node cur = pq.poll();

            if(cur.cost > dist[cur.v]){
                continue;
            }

            for(Node next : graph[cur.v]){
                int nextv = next.v;
                int nextCost = dist[cur.v]+ next.cost;
                if(dist[nextv] > nextCost){
                    dist[nextv] = nextCost;
                    pq.offer(new Node(nextv,nextCost));
                }
            }
        }

        for(int i = 1; i <= N; i++){
            if(dist[i] <= K){
                System.out.println(dist[i]);
                answer++;
            }
        }
        return answer;
    }
    public static void main(String[] args){
        int result = solution(
            5,
            new int[][]{
                {1, 2, 1},
                {2, 3, 3},
                {5, 2, 2},
                {1, 4, 2},
                {5, 3, 1},
                {5, 4, 2}
            },
            3
        );
        System.out.println(result);
    }

}
