package algostudy.baek.week12_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1912_이용호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] dp = new int[N+1];
		int ans = Integer.MIN_VALUE;
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i <= N; i++) {
			int a = Integer.parseInt(st.nextToken());
			// 누적 vs 현재 값 비교
			dp[i] = Math.max(a, dp[i-1] + a);
			ans = Math.max(dp[i], ans);
//			System.out.println(dp[i]);
		}
		System.out.println(ans);
	}

}
