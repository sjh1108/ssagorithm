package algo2025_10_05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Solution_1249_보급로re {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int[][] map, dist;
	static final int INF = Integer.MAX_VALUE;
	static int N, tc;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Node implements Comparable<Node>{
		int x, y, cost;

		public Node(int x, int y, int cost) {
			super();
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		tc = Integer.parseInt(input.readLine());
		for (int i = 1; i <= tc; i++) {
			N = Integer.parseInt(input.readLine());
			map = new int[N][N];
			dist = new int[N][N];
			
			for (int j = 0; j < N; j++) {
				String line = input.readLine();
				for (int k = 0; k < N; k++) {
					map[j][k] = line.charAt(k) -'0';
					dist[j][k] = INF;
				}
			}
			
			System.out.println("#" + i + " " + dijkstra());
		}
	}
	static int dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[0][0] = map[0][0];
		pq.offer(new Node(0, 0, map[0][0]));
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			if(cur.cost > dist[cur.x][cur.y]) continue;
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				
				int min = cur.cost + map[nx][ny];
				if (min < dist[nx][ny]) {
					dist[nx][ny] = min;
					pq.offer(new Node(nx, ny, min));
				}
			}
			
		}
		
		return dist[N-1][N-1];
		
	}
}
