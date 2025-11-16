package baek_week11_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2169_이용호 {
/*
 * 왼쪽, 오른쪽, 아래 이동가능
 * 갔던곳 방문 x, 한쪽으로 진행하면 그대로 가야함
 * 왼쪽 진행(누적), 오른쪽 진행 나누고 최댓값으로 갱신
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		
		int[][] map = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int[][] dp = new int[N][M];
		
		// 첫칸 초기화
		dp[0][0] = map[0][0];
		// 첫행은 오른쪽만 진행 가능
		for(int j = 1; j < M; j++) {
			dp[0][j] = dp[0][j-1] + map[0][j];
		}
		for(int i = 1; i < N; i++) {
			int[] leftToRight = new int[M];
			int[] rightToLeft = new int[M];
			
			// 왼쪽 -> 오른쪽 진행
			// 첫열은 위에서 내려온거만 있음
			leftToRight[0] = dp[i-1][0] + map[i][0];
			for(int j = 1; j < M; j++) {
				// 위에서온거 vs 왼쪽에서 온거
				int bigger = Math.max(leftToRight[j-1], dp[i-1][j]);
				leftToRight[j] = bigger + map[i][j];
			}
			
			// 오른쪽 -> 왼쪽 진행
			rightToLeft[M-1] = dp[i-1][M-1] + map[i][M-1];
			for(int j = M - 2; j >= 0 ; j--) {
				// 위에서 온거 vs 오른쪽에서 온거
				int bigger = Math.max(rightToLeft[j+1], dp[i-1][j]);
				rightToLeft[j] = bigger + map[i][j];
			}
			
			// 왼쪽진행 vs 오른쪽진행
			for(int j = 0; j < M; j++) {
				dp[i][j] = Math.max(leftToRight[j], rightToLeft[j]);
			}
		}
		System.out.println(dp[N-1][M-1]);
		
	}

}
