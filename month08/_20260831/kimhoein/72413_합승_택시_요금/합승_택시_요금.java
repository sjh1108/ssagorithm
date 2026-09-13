import java.io.*;
import java.util.*;

class Solution {
    public class Node implements Comparable<Node>
    {
        int dist;
        int id;

        Node(int id, int dist)
        {
            this.id = id;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o){
            return this.dist - o.dist;
        }
    }

    ArrayList<Node>[] arr;
    int[] distA;
    int[] distB;
    int[] distcost;

    int N;
    int S,A,B;

    public int solution(int n, int s, int a, int b, int[][] fares) {
        int answer = Integer.MAX_VALUE;
        N = n;
        arr = new ArrayList[n+1];
        distA = new int[n+1];
        distB = new int[n+1];
        distcost = new int[n+1];

        S = s;
        A = a;
        B = b;

        for(int i=0; i<=n; i++)
        {
            arr[i] = new ArrayList<Node>();
        }

        for(int i=0; i<fares.length; i++)
        {
            int start = fares[i][0];
            int end = fares[i][1];
            int c = fares[i][2];

            arr[start].add(new Node(end,c));
            arr[end].add(new Node(start,c));
        }

        distA = Dijkstra(a);
        distB = Dijkstra(b);
        distcost = Dijkstra(s);

        for(int i=1; i<=N; i++)
        {
            answer = Math.min(answer, distA[i] + distB[i] + distcost[i]);
        }

        return answer;
    }

    public int[] Dijkstra(int start)
    {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();
        int[] dist = new int[N+1];
        Arrays.fill(dist,Integer.MAX_VALUE);

        dist[start] = 0;
        pq.add(new Node(start,0));

        while(!pq.isEmpty())
        {
            Node node = pq.poll();

            if(dist[node.id] < node.dist) continue;

            for(Node curNode : arr[node.id])
            {
                if(node.dist + curNode.dist >= dist[curNode.id]) continue;

                dist[curNode.id] = node.dist + curNode.dist;
                pq.add(new Node(curNode.id, dist[curNode.id]));
            }
        }

        return dist;
    }
}
