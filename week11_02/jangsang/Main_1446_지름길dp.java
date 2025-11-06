package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1446_지름길dp {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N,D,A,B,C;
	static int[][] road;
	static int[] dp;
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		D = Integer.parseInt(tokens.nextToken());
		road = new int[N][3];
		dp = new int[D+1];
		
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			for (int j = 0; j < 3; j++) {
				road[i][j] = Integer.parseInt(tokens.nextToken());
			}
		}
		
//		for (int i = 0; i <= D; i++) {
//			dp[i] = i;
//		}
		for (int i = 0; i <= D; i++) {
			int min = Math.min(dp[i], dp[i-1] + 1);
		}
		
	}
}
