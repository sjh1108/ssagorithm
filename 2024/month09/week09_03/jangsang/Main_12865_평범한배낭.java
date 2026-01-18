package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_12865_평범한배낭 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N,W;
	static int[] weights, values;
	static int[][] dp;
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		W = Integer.parseInt(tokens.nextToken());
		
		weights = new int[N];
		values = new int[N];
		dp = new int[N+1][W+1];
		
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			weights[i] = Integer.parseInt(tokens.nextToken());
			values[i] = Integer.parseInt(tokens.nextToken());
		}
		
		for (int i = 1; i <= N; i++) {
			int cWeight = weights[i-1];
			int cValue = values[i-1];
			for (int j = 1; j <= W; j++) {
				System.out.println(cWeight);
				if(cWeight > j) {
					dp[i][j] = dp[i-1][j];
				}else {
					dp[i][j] = Math.max(cValue + dp[i-1][j - cWeight], dp[i-1][j]);
				}
			}
		}
		
		for (int[] i : dp) {
			System.out.println(Arrays.toString(i));
		}
		
		System.out.println(dp[N][W]);
	}
}
