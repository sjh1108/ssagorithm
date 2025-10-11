import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2293_이용호 {
/*
 * k원이 되는 경우의 수
 */
	public static void main(String[] args) throws NumberFormatException, IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int k = Integer.parseInt(st.nextToken());
		int[] coins = new int[n];
		for(int i = 0; i < n; i++) {
			coins[i] = Integer.parseInt(br.readLine());
		}
		
		int[] dp = new int[k+1];
		dp[0] = 1; //0원 만드는 경우의 수
		
		for(int coin : coins) {
			for(int j = 1; j <= k; j++) {
				if(j-coin < 0) continue;
				dp[j] += dp[j - coin];
			}
				
		}
		System.out.println(dp[k]);
	}
}
