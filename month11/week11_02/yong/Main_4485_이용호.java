package algostudy.baek.week11_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_4485_이용호 {
	public static int[][] map, cost;
	public static int[] dx = {-1,1,0,0};
	public static int[] dy = {0,0,-1,1};
	public static int N;
	
	public static class Node implements Comparable<Node>{
		int x; int y; int c;
		public Node(int x, int y, int c) {
			this.x = x;
			this.y = y;
			this.c = c;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.c, o.c);
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = 0;
		while(true) {
			N = Integer.parseInt(br.readLine());
			if(N == 0) break;
			
			map = new int[N][N];
			cost = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			for(int i = 0; i < N; i++) {
				Arrays.fill(cost[i], Integer.MAX_VALUE);
			}
			
			dij(0,0);
			System.out.println("Problem " + ++T + ": " + cost[N-1][N-1]);
			
		}
	}
	public static void dij(int r, int c) {
		Queue<Node> q = new PriorityQueue<>();
		q.offer(new Node(r,c,map[r][c]));
		cost[r][c] = map[r][c];
		
		while(!q.isEmpty()) {
			Node now = q.poll();
//			System.out.println(now.x + " "+now.y);
			if(now.c > cost[now.x][now.y]) continue;
			for(int i = 0; i < 4; i++) {
				
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				// 다음 위치 비용 갱신 할필요 없으면 패스
				if(now.c + map[nx][ny] >= cost[nx][ny]) continue;
				cost[nx][ny] = now.c + map[nx][ny];
				q.offer(new Node(nx,ny,cost[nx][ny]));
			}
		}
	}
}
