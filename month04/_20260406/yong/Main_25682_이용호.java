package _20260406.yong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_25682_이용호 {
/*
 * 첫칸이 B인 배열, W인 배열 각각 두고
 * 다시 칠해야 하는 개수를 누적 ((1,1)부터 해당 행열을 포함한 사각형)
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		char[][] map = new char[N][M];
		// 체스판 입력
		for(int i = 0; i < N; i++) {
			String line = br.readLine();
			map[i] = line.toCharArray();
		}
		
		int[][] preB = new int[N+1][M+1];
		int[][] preW = new int[N+1][M+1];
		
		// 틀린칸 체크
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= M; j++) {
				char now = map[i-1][j-1];
				// B 시작 체스판 기준
				char expectB = ((i+j) % 2 == 0) ? 'B' : 'W';
				int notB = (now != expectB) ? 1 : 0; // 칠해야 하면 1
				
				char expectW = ((i+j) % 2 == 0) ? 'W' : 'B';
				int notW = (now != expectW) ? 1: 0; // 칠해야 하면 1
				
				// 현재  + 위 + 왼쪽 - 왼쪽위
				preB[i][j] = notB + preB[i-1][j] + preB[i][j-1] - preB[i-1][j-1];
				preW[i][j] = notW + preW[i-1][j] + preW[i][j-1] - preW[i-1][j-1];		
			}
		}
		int res = Integer.MAX_VALUE;
		// KxK 영역 검사
		// i, j 를 오른쪽 아래로 둔 KxK 영역
		for(int i = K; i <= N; i++) { 
			for(int j = K; j <= M; j++) {
				// B 시작 기준 틀린 칸 개수
				int b = preB[i][j] - preB[i][j - K] - preB[i-K][j] + preB[i-K][j-K];
				int w = preW[i][j] - preW[i][j - K] - preW[i-K][j] + preW[i-K][j-K];
				res = Math.min(Math.min(b, w), res);
			}
		}
		
		System.out.println(res);
//		System.out.println(map[N-1][M-1]);
	}

}
