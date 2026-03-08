package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2573_이용호 {
/*
 * 빙산이 1년마다 녹는다.(네 방향으로 붙어있는 0 개수 만큼)
 * 빙산이 두 덩어리 이상으로 분리 되는 최초 시간
 * 
 * for문 돌면서 bfs로 빙산 개수 체크 + 녹는 정도 저장
 * 두 덩어리 미만이면 빙산 녹이기
 */
	static int[][] map;
	static boolean[][] visited;
	static int N, M;
	static ArrayList<Ice> ices;
	static int[][] delta = {{-1,0},{1,0},{0,-1},{0,1}};
	public static class Ice{
		int r; int c; int sea;
		public Ice(int r, int c, int sea) {
			this.r = r;
			this.c = c;
			this.sea = sea;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		int years = 0;
		int iceCnt = 0;
		while(true) { // 빙산 두 덩어리 될때까지 반복
			visited = new boolean[N][M];
			iceCnt = 0; // 빙산 덩어리 개수
			ices = new ArrayList<>(); // 빙산 리스트
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < M; j++) {
					if(map[i][j] == 0 || visited[i][j]) continue;
					iceCnt++;
					bfs(i, j); // 빙산 체크
				}
			}
			if(iceCnt >= 2 || iceCnt == 0) {
				break;
			}
			// 빙산 녹이기
			for(Ice i : ices) {
				map[i.r][i.c] -= i.sea;
				if(map[i.r][i.c] < 0) { // 0보다 작아지면 0으로 수정
					map[i.r][i.c] = 0;
				}
			}
			years++;
		}
		System.out.println(iceCnt == 0 ? 0 : years);
		
	}

	private static void bfs(int r, int c) {
		Queue<Ice> q = new LinkedList<>();
		int sc = seaCount(r,c);
//		System.out.println(sc);
		q.add(new Ice(r,c,sc));
		visited[r][c] = true;
		ices.add(new Ice(r,c,sc));
		
		while(!q.isEmpty()) {
			Ice cur = q.poll();
			for(int d = 0; d < 4; d++) {
				int nr = cur.r + delta[d][0];
				int nc = cur.c + delta[d][1];
				
				if(nr < 0 || nc < 0 || nr >= N || nc >= M) continue;
				if(map[nr][nc] == 0 || visited[nr][nc]) continue;
				
				q.add(new Ice(nr,nc,seaCount(nr,nc)));
				ices.add(new Ice(nr,nc,seaCount(nr,nc)));
				visited[nr][nc] = true;
			}
		}
		
	}
	
	private static int seaCount(int r, int c) {
		int seaCount = 0;
		for(int d = 0; d < 4; d++) {
			int nr = r + delta[d][0];
			int nc = c + delta[d][1];
			if(nr < 0 || nc < 0 || nr >= N || nc >= M) continue;
			if(map[nr][nc] == 0) seaCount++;
		}
		return seaCount;
	}
}
