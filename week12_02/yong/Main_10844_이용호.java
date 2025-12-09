package algostudy.baek.week12_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_10844_이용호 {
/*
 * 계단수: 인접 수 차이 1
 * N번째 자리수가 0인 경우 -> 1, 9인 경우 -> 8
 * 10 -> 101, 19 -> 198
 * dp[n][i], 
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] dp = new int[N+1][10];
		
		for(int i = 1; i < 10; i++) { // 0시작은 계단수 아님
			dp[1][i] = 1;
		}
		
		for(int n = 2; n <= N; n++) {
			for(int i = 0; i < 10; i++) {
				if(i == 0) {
					dp[n][i] = dp[n-1][1] % 1000000000;
				}
				else if(i == 9) {
					dp[n][i] = dp[n-1][8] % 1000000000;
				}
				else {
					dp[n][i] = (dp[n-1][i-1] + dp[n-1][i+1]) % 1000000000;
				}
			}
		}
		int sum = 0;
		for(int i = 0;i < 10; i++) {
			sum = (sum + dp[N][i]) % 1000000000;
//			System.out.println("i : " + i + " | sum: " + sum);
		}
		System.out.println(sum);
	}

}
