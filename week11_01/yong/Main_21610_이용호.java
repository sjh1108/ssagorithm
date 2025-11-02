package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_21610_이용호 {
	/*
	 * 마법상어
	 * A[r][c] -> 바구니에 저장되어 있는 물의양
	 * 1번행과 N번행을 연결, 1번 열과 N번 열을 연결
	 * 비바라기 범위 (N, 1),(N, 2), (N-1, 1), (N-1, 2)
	 */
	public static Queue<Cloud> cq;
	public static int[] dx = {0,0, -1, -1, -1, 0, 1, 1, 1}; //1-based 좌 ~ 상 ~ 우 ~ 하 ~
	public static int[] dy = {0,-1, -1, 0, 1, 1, 1, 0, -1};
	public static int[][] map;
	public static int N;
	
	public static class Cloud{
		int x; int y;
		public Cloud(int x, int y){
			this.x = x;
			this.y = y;
		}
		
		public void move(int dir) {
			int nx = this.x + dx[dir];
			int ny = this.y + dy[dir];
			// 여기 다른방법 아시는분?
			// 1번행과 N번행 연결 처리
			if(nx == -1) {
				nx = N-1;
			}
			else if(nx == N) {
				nx = 0;
			}
			// 1번열과 N번열 연결 처리
			if(ny == -1) {
				ny = N-1;
			}
			else if(ny == N) {
				ny = 0;
			}		
			this.x = nx;
			this.y = ny;
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		cq = new LinkedList<>();
		// 구름 생성
		cq.offer(new Cloud(N-1,0));
		cq.offer(new Cloud(N-2,0));
		cq.offer(new Cloud(N-2,1));
		cq.offer(new Cloud(N-1,1));
		
		// 이동 명령 di, si
		for(int c = 0; c < M; c++) {
			st = new StringTokenizer(br.readLine());
			int di = Integer.parseInt(st.nextToken());
			int si = Integer.parseInt(st.nextToken());
			simulate(di,si);
		}
		int totalWater = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] == 0) continue;
				totalWater += map[i][j];
			}
		}
		System.out.println(totalWater);
		
	}
	public static void simulate(int d, int s) {
		boolean[][] nonCloud = new boolean[N][N];
		// 구름 이동
		for(int i = 0; i < s; i++) { // 여기 모듈러 쓰면 한번에 가능할듯
			for(Cloud c : cq) {
				c.move(d);
			}
		}
		// 비가 와, 구름이 사라져
		ArrayList<int[]> rained = new ArrayList<>();
		while(!cq.isEmpty()) {
			Cloud c = cq.poll();
			map[c.x][c.y]++;
			nonCloud[c.x][c.y] = true;
			rained.add(new int[] {c.x,c.y});
		}
		// 물 복사 <- 이거 실수 (비가 온 뒤 복사 해줘야하는데 위 wilhe문에서 복사했었음
		for(int[] pos: rained) {
			int basketCnt = 0;
			for(int i = 1; i <= 4; i++) {
				int nx = pos[0] + dx[i * 2]; // 대각 2, 4, 6 ,8
				int ny = pos[1] + dy[i * 2];
				if(nx < 0 || ny < 0 || nx >= N || ny >= N || map[nx][ny] == 0) continue;
				basketCnt++;
			}
			map[pos[0]][pos[1]] += basketCnt;
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if (map[i][j] < 2 || nonCloud[i][j]) continue;
				map[i][j] -= 2;
				cq.offer(new Cloud(i,j));
			}
		}
	}
	

}
