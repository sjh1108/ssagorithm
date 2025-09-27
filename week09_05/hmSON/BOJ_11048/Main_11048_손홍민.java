import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11048_손홍민 {
	
	// 알고리즘 분류 : DP
	// 구상 : DP 2차원 배열의 각 구역은 현재 위치까지 이동했을 때 얻을 수 있는 최대 사탕 개수
	// 즉 하나의 구역에 대해 위에서 내려올 때, 대각선 우측 아래로 내려올 때, 왼쪽 구역에서 오른쪽으로 이동할 때 중 사탕 개수가 제일 높은 값을 선택
	// 또, 음수 항이 없으므로 대각선 이동을 고르는 것은 의미가 없음. 최대한 많은 구역을 탐색해서 사탕 수를 늘려야 하므로 대각선은 취급하지 않음.
	
	// 1차원 배열만으로 충분히 최대값을 갱신할 수 있을 것으로 예상
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		// 각 구역까지 이동하면서 획득할 수 있는 사탕의 최대 개수
		int[] dp = new int[c+1];
		
		for(int i=1; i<=r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=1; j<=c; j++) {
				int x = Integer.parseInt(st.nextToken());
				
				// dp[j] : 현재 위치 , dp[j-1]과 dp[j] : 각각 현재 위치의 왼쪽과 위쪽의 사탕 최대 개수
				dp[j] = Math.max(dp[j-1], dp[j]) + x;
			}
		}
		
		System.out.println(dp[c]);
	}
	
}
