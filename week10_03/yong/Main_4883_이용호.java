package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4883_이용호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int cnt = 0;
		while(true) {
			int N = Integer.parseInt(br.readLine());
			if(N == 0) break;
			cnt++;
			
			int[][] dp = new int[N][3];
			int[][] map = new int[N][3];
			for(int i = 0; i < N; i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				map[i][0] = Integer.parseInt(st.nextToken());
				map[i][1] = Integer.parseInt(st.nextToken());
				map[i][2] = Integer.parseInt(st.nextToken());
			}
			dp[0][0] = Integer.MAX_VALUE;
			dp[0][1] = map[0][1];
			dp[0][2] = map[0][1] + map[0][2];
			
			dp[1][0] = map[1][0] + dp[0][1];
			dp[1][1] = map[1][1] + Math.min(dp[1][0], Math.min(dp[0][1], dp[0][2]));
			dp[1][2] = map[1][2] + Math.min(dp[1][1], Math.min(dp[0][1], dp[0][2]));
			
			for (int i = 2; i < N; i++) {
				dp[i][0] = map[i][0] + Math.min(dp[i-1][0], dp[i-1][1]);
				dp[i][1] = map[i][1] + Math.min(Math.min(Math.min(dp[i-1][0], dp[i-1][1]), dp[i-1][2]), dp[i][0]);
				dp[i][2] = map[i][2] + Math.min(Math.min(dp[i-1][1], dp[i-1][2]), dp[i][1]);
			}
			
			
			System.out.println(cnt + ". " + dp[N-1][1]);
		}
		
	}

}
