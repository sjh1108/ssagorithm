import java.util.*;
import java.io.*;

class Solution {
    public class Node implements Comparable<Node>
    {
        int x;
        int y;
        int dist;
        int horizon;

        Node(int x,int y, int dist)
        {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node o){
            return this.dist - o.dist;
        }
    }

    int[][][] dist;
    int[][] Board;
    int[][] delta = {{1,0},{0,1},{-1,0},{0,-1}};
    int n;

    public int solution(int[][] board) {
        int answer = 0;

        n = board.length;

        dist = new int[n][n][2];
        Board = new int[n][n];

        Board = board;

        for(int i=0; i<n; i++)
        {
            for(int j=0; j<n; j++)
            {
                Arrays.fill(dist[i][j],Integer.MAX_VALUE);
            }
        }

        Dijkstra();

        answer = Math.min(dist[n-1][n-1][1],dist[n-1][n-1][0]);

        return answer;
    }

    public void Dijkstra()
    {
        PriorityQueue<Node> pq = new PriorityQueue<Node>();

        dist[0][0][0] = 0;
        dist[0][0][1] = 0;

        if(Board[0][1] == 0)
        {
            dist[0][1][1] = 100;

            Node n = new Node(0,1,100);
            n.horizon = 1;
            pq.add(n);
        }

        if(Board[1][0] == 0)
        {
            dist[1][0][0] = 100;

            Node n = new Node(1,0,100);
            n.horizon = 0;
            pq.add(n);
        }

        while(!pq.isEmpty())
        {
            Node node = pq.poll();

            if(dist[node.x][node.y][node.horizon] < node.dist) continue;

            for(int i=0; i<4; i++)
            {
                int dx = node.x + delta[i][0];
                int dy = node.y + delta[i][1];
                int horizon = node.horizon;

                if(!(dx>=0 && dx <n && dy >= 0 && dy < n)) continue;
                if(Board[dx][dy] == 1) continue;

                int d = 100;
                if(!(horizon == 1 &&  node.x == dx|| horizon == 0 && node.y == dy)) {
                    d += 500;

                    if(horizon == 1) horizon = 0;
                    else horizon = 1;
                }

                if(dist[dx][dy][horizon] < node.dist + d) continue;

                dist[dx][dy][horizon] = node.dist + d;
                Node n = new Node(dx,dy,dist[dx][dy][horizon]);
                n.horizon = horizon;

                pq.add(n);
            }
        }
    }
}
