package algo2025_11_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1261_알고스팟 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static char[][] map;
    static int n, m;

    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    static class Node {
        int x, y, cost;
        Node(int x, int y, int cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
        // pq에서 lambda 대신 Comparable implements받아서 Node class에서 정렬해도됨
    }

    public static void main(String[] args) throws IOException {
        tokens = new StringTokenizer(input.readLine());
        m = Integer.parseInt(tokens.nextToken()); 
        n = Integer.parseInt(tokens.nextToken()); 

        map = new char[n][m];

        for (int i = 0; i < n; i++) {
            String line = input.readLine();
            for (int j = 0; j < m; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        System.out.println(dijkstra());
    }

    static int dijkstra() {
    	PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
    	
        int[][] dist = new int[n][m];
        for (int i = 0; i < n; i++) {
        	Arrays.fill(dist[i], Integer.MAX_VALUE);
        } 

        pq.offer(new Node(0, 0, 0));
        dist[0][0] = 0;

        while (!pq.isEmpty()) {
            Node cur = pq.poll();

            if (dist[cur.x][cur.y] < cur.cost) continue;

            for (int d = 0; d < 4; d++) {
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];
                if (nx < 0 || ny < 0 || nx >= n || ny >= m) continue;

                int nextCost = cur.cost;

                if (map[nx][ny] == '1') {
                    nextCost++;
                }

                if (dist[nx][ny] > nextCost) {
                    dist[nx][ny] = nextCost;
                    pq.offer(new Node(nx, ny, nextCost));
                }
            }
        }

        return dist[n-1][m-1];
    }
}
