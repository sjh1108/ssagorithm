package algo.baek.b1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1012_이용호 {
/*
 * 흰지렁이가 배추 보호해주고 인접한 다른 배추로 이동가능
 * 이동은 상하좌우
 * bfs로 상하좌우 탐색한거 카운트 하면 될듯
 */
	public static int[] dx = {-1,1,0,0};
	public static int[] dy = {0,0,-1,1};
			
	public static int[][] map;
	public static boolean[][] visited;
	public static int W,H;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			map = new int[H][W];
			visited = new boolean[H][W];
			for(int i = 0; i < K; i++) { //배추 심기
				st = new StringTokenizer(br.readLine());
				int y = Integer.parseInt(st.nextToken());
				int x = Integer.parseInt(st.nextToken());
//				System.out.println(x+" "+y);
				map[x][y] = 1;
			}
			
			int cnt = 0;
			for(int i = 0; i < H; i++) {
				for(int j = 0; j < W; j++) {
					if(visited[i][j] || map[i][j] == 0) continue; 
					visited[i][j] = true;
					bfs(i,j);
//					System.out.println();
					cnt++;

						
				}
			}
			System.out.println(cnt);
		}
		
	}
//	dfs 버전
	public static void dfs(int x, int y) {
		visited[x][y] = true;
		for(int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if(nx < 0 || ny < 0 || nx >= H || ny >= W) continue;
			if(map[nx][ny] == 0 || visited[nx][ny]) continue;
			dfs(nx,ny);
		}
	}
	public static void bfs(int x, int y) { 
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {x,y});
		while(!q.isEmpty()){
			int[] now = q.poll();
			for(int i = 0; i < 4; i++) {
				int nx = now[0]; int ny = now[1];
				nx = nx + dx[i];
				ny = ny + dy[i];
//				System.out.println(nx + " "+ ny);
				if(nx < 0 || ny < 0 || nx >= H || ny >= W) continue;
				//배추 없거나 이미 흰지렁이 있으면 패스
				if(map[nx][ny] == 0 || visited[nx][ny]) continue;
				q.add(new int[] {nx,ny});
				visited[nx][ny] = true; //흰지렁이 방문 ^^
			}
		}
	}

}
