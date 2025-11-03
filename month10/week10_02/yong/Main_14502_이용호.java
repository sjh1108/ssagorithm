package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14502_이용호 {
	/*
	 * 1. 빈칸 중 3개 조합으로 벽 세우기
	 * 2. 바이러스 퍼뜨리기
	 * 3. 안전구역 세고 max안전구역 갱신
	 */
	public static int maxSafe;
	public static int[] dx = {-1,1,0,0};
	public static int[] dy = {0,0,-1,1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int[][] lab = new int[N][M];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				lab[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		maxSafe = 0;
		makeWall(lab, N, M, 0); //벽세우기
		System.out.println(maxSafe);

	}
	public static void makeWall(int[][] lab, int N, int M, int count) {
		if(count == 3) { //벽 3개 세웠으면 바이러스 퍼뜨리기
			virus(N, M, lab);
			return;
		}
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				if(lab[i][j] != 0) continue; //빈칸 아니면 패스
				lab[i][j] = 1;//벽세우기
				makeWall(lab, N, M, count+1);
				lab[i][j] = 0; //백트래킹(원상복구)
			}
		}
	}
	public static void virus(int N, int M, int[][] lab) {
		int[][] copy = new int[N][M];
		for(int i = 0; i < N; i++) {
			copy[i] = Arrays.copyOf(lab[i], M);
		}
		
		//바이러스 좌표 저장
		Queue<int[]> q = new LinkedList<>();
		for(int i = 0; i < N; i ++) {
			for(int j = 0; j < M; j++) {
				if(copy[i][j] == 2) q.add(new int[] {i,j});
			}
		}
		
		while(!q.isEmpty()) {
			int[] now = q.poll();
			int x = now[0];
			int y = now[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue; // 경계 넘어가면 패스
				if(copy[nx][ny] == 0) {
					copy[nx][ny] = 2;
					q.add(new int[] {nx,ny});
				}
			}			
		}
		//안전구역 계산
		countSafe(copy,N,M);
	}
	public static void countSafe(int[][] copy, int N, int M) {
		int safe = 0;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++){
				if(copy[i][j] == 0) safe++;
			}
		}
		maxSafe = Math.max(maxSafe, safe);
	}

}
