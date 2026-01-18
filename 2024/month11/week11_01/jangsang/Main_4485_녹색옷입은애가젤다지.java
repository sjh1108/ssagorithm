package algo2025_10_05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_4485_녹색옷입은애가젤다지 {
	static BufferedReader input = new  BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int[][] map, dist;
	static int N;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static final int INF = Integer.MAX_VALUE;
	
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
		int cnt = 0;
		while(true) {
			N = Integer.parseInt(input.readLine());
			if(N == 0) break;

			map = new int[N][N];
			dist = new int[N][N];
			cnt++;
			for (int i = 0; i < N; i++) {
				tokens = new StringTokenizer(input.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(tokens.nextToken());
					dist[i][j] = INF;
				}
			}
			System.out.println("Problem" + " " +cnt+":" + " " + dijkstra());
		}
		
	} // main
	static int dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[0][0] = map[0][0]; // 시작 정점도 포함
		pq.offer(new Node(0,0, map[0][0]));
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			
			// 현재 노드에서 뽑자마자 dist 배열에서 큰지 비교 크면 바로 컷
			// for문 밖에 있어야함
			if (cur.cost > dist[cur.x][cur.y]) continue; 
			
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				// 범위ㅏ확인 잘하자..
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				
				int min = cur.cost + map[nx][ny];
				if (min < dist[nx][ny]) {
					dist[nx][ny] = min;
					pq.offer(new Node(nx, ny, min));
				}
			}
		}
		return dist[N-1][N-1];
	}
} //class
