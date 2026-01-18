package algo.baek.b4963;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_4963_이용호 {
/*
 * 섬의 개수 세는 프로그램
 * 자기 기준 팔방은 걸어갈수 있음(이어져있음)
 * bfs나 dfs로 섬 개수 세면 될듯
 */
	public static int[][] map;
	public static boolean[][] visited;
	public static int[] dx = {-1,1,0,0,-1,-1,1,1};
	public static int[] dy = {0,0,-1,1,-1,1,1,-1};
	public static int w, h;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while(true) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			w = Integer.parseInt(st.nextToken());
			h = Integer.parseInt(st.nextToken());
			
			if(w == 0 && h == 0) break; //종료조건 체크
			map = new int[h][w];
			visited = new boolean[h][w];
			
			for(int i = 0; i < h; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < w; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			
			int cnt = 0;
			
			for(int i = 0; i < h; i++) {
				for(int j = 0; j < w; j++) {
					if(map[i][j] == 0 || visited[i][j]) continue;
					visited[i][j] = true;
					dfs(i,j);
					cnt++;
				}
			}
			System.out.println(cnt);
		}
		
	}
	public static void bfs(int x, int y) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {x,y});
		while(!q.isEmpty()) {
			int[] now = q.poll();
			
			for(int i = 0; i < 8; i++) {
				int nx = now[0] + dx[i];
				int ny = now[1] + dy[i];
				//맵 밖이면 패스
				if(nx < 0 || ny < 0 || nx >= h || ny >= w) continue;
				//바다거나 이미 방문한곳이면 패스
				if(map[nx][ny] == 0 || visited[nx][ny]) continue;
				visited[nx][ny] = true; //현재 섬 방문 ^^
				q.add(new int[] {nx,ny}); //현재 위치 큐에 추가 ^^
	
			}	
		}
	}
	public static void dfs(int x,int y) {
		visited[x][y] = true; // 현재위치 방문 ^^
		for(int i = 0; i < 8; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			//맵 밖이면 패스
			if(nx < 0 || ny < 0 || nx >= h || ny >= w) continue;
			//바다거나 이미 방문한곳이면 패스
			if(map[nx][ny] == 0 || visited[nx][ny]) continue;
			dfs(nx, ny); //다음위치 재귀 ^^
		}
	}

}
