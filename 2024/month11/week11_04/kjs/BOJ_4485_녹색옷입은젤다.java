package samsung01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_4485_녹색옷입은젤다 {

	static int[][] map;
	static int[][] dist;
	static int result, n;
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int pNum = 1;
		while (true) {
			n = Integer.parseInt(br.readLine());
			if (n == 0)
				break;

			map = new int[n][n];
			dist = new int[n][n];

			for (int i = 0; i < n; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for (int j = 0; j < n; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					dist[i][j] = Integer.MAX_VALUE;
				}
			}
			
			int result = dijkstra();
			 System.out.println("Problem " + pNum++ + ": " + result);
			
		}
	}

	static class Node implements Comparable<Node> {
		int r, c, cost;

		Node(int r, int c, int cost) {
			this.r = r;
			this.c = c;
			this.cost = cost;

		}

		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}

	static int dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		// 시작
		dist[0][0] = map[0][0];
		pq.offer(new Node(0,0, map[0][0]));
		
		while(!pq.isEmpty()) {
			
			Node cur = pq.poll();
			
			if(cur.cost > dist[cur.r][cur.c]) continue;
			
			for(int d=0; d<4; d++) {
				
				int nr = cur.r + dr[d];
				int nc = cur.c + dc[d];
				
				if (nr < 0 || nr >= n || nc < 0 || nc >= n) continue;
				
				int nCost = cur.cost + map[nr][nc];
				
				if(nCost < dist[nr][nc]) {
					dist[nr][nc] = nCost;
					pq.offer(new Node(nr, nc, nCost));
				}
				
			}
			
			
		}
		
		return dist[n-1][n-1];
		
		
	}

}
