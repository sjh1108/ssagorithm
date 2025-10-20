package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4883_삼각그래프 {
	
	private static final int INF = 1_000_000; // 고정 상수

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N = Integer.parseInt(br.readLine()); 계속 적혀야 하니까, 이 자리 아님. 

		int idx = 0;

		while(true) {

			int N = Integer.parseInt(br.readLine());

			if(N == 0) break; //  return 때리면 안됨. while끝나고, 출력을 위함. 

			int[][] map = new int[N][3];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < 3; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			int[][] dp = new int[N][3];

			// DP[0] 출발선 초기값 세팅
			dp[0][0] = INF; // 비용 제곱은 1000000보다 작다 활용
			dp[0][1] = map[0][1]; // 시작위치
			dp[0][2] = map[0][1] + map[0][2]; // 시작위치에서 시작하므로, 확정적으로 0행에서 두개 더하더라도,,해주기.


			// DP 실행
			for (int i = 1; i < N; i++) { // 구간 제대로 확인 하기. 
				// 실수 i를 N으로 씀.. 후,,
				dp[i][0] = map[i][0] + Math.min(dp[i-1][0], dp[i-1][1]); // 기존(map[i][0] map[i-1][0](x)) + 넘어온 친구
				dp[i][1] = map[i][1] + min(dp[i][0], dp[i-1][0], dp[i-1][1], dp[i-1][2]);
				dp[i][2] = map[i][2] + min(dp[i][1], dp[i-1][1], dp[i-1][2]);
				//  디버깅 코드...
//				System.out.println();
//				System.out.print(dp[i][0] + " ");
//				System.out.print(dp[i][1] + " ");
//				System.out.print(dp[i][2] + " ");
				
			}

			// StringBuilder 활용 출력
			sb.append(++idx).append(". ").append(dp[N-1][1]).append("\n");
		}

		System.out.println(sb);
	}

	private static int min(int... dps) { // 가변인자 활용
		int minValue = INF; // 애 처리 안해서 계속 틀렸음. 
		for (int dpValue : dps) {
			minValue = Math.min(minValue, dpValue);
			// 실수 minValue = Math.min(1001, dpValue); 
		}
		return minValue;
	}
}

