import java.io.*;
import java.util.*;

public class Solution {
    public class Node implements Comparable<Node>
    {
        int id;
        int dist;

        Node(int id, int dist)
        {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o) {
            return this.dist - o.dist;
        }
    }

    int N;
    ArrayList<Node>[] roads;
    int[] dist;

    public int solution(int N, int[][] road, int K) {
        int answer = 0;
        this.N = N;

        roads = new ArrayList[N+1];

        for(int i=0; i<=N; i++)
        {
            roads[i] = new ArrayList<Node>();
        }

        for(int i=0; i<road.length; i++)
        {
            int a = road[i][0];
            int b = road[i][1];
            int c = road[i][2];

            roads[a].add(new Node(b,c));
            roads[b].add(new Node(a,c));
        }
        dist = new int[N+1];
        Dijkstra();

        for(int i=1; i<=N; i++)
        {
            if(dist[i] <= K)
            {
                answer++;
            }
        }

        // [실행] 버튼을 누르면 출력 값을 볼 수 있습니다.
        //System.out.println("Hello Java");

        return answer;
    }

    public void Dijkstra()
    {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        Arrays.fill(dist, Integer.MAX_VALUE);

        dist[1] = 0;
        pq.add(new Node(1,0));

        while(!pq.isEmpty())
        {
            Node node = pq.poll();

            if(node.dist > dist[node.id]) continue;

            for(Node curNode : roads[node.id])
            {
                if(node.dist + curNode.dist >= dist[curNode.id]) continue;

                dist[curNode.id] = node.dist + curNode.dist;
                pq.add(new Node(curNode.id, dist[curNode.id]));
            }
        }
    }
}
