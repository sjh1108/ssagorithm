import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1106_손홍민 {
	
	// 알고리즘 분류 : DP, Knapsack
	// 풀이 순서 : 
	// 1. 목표 인원수를 크기르 가지는 DP 배열 생성
	// 2. 한 도시마다 돌면서 1부터 goal까지 특정 인원수를 채우기 위해 어느 정도의 비용을 소모해야 하는 지 기록
	// 3. 기록한 값과 이미 DP 배열에 기록한 값을 비교하여 최소값 갱신
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int goal = Integer.parseInt(st.nextToken());
		int city = Integer.parseInt(st.nextToken());
		
		// 목표 인원수를 최대값으로 하는 DP 배열
		int[] dp = new int[goal+1];
		for(int i=0; i<city; i++) {
			st = new StringTokenizer(br.readLine());
			
			// 각 도시마다 홍보 비용과 그에 따른 확보 가능한 고객 수가 존재
			int cost = Integer.parseInt(st.nextToken());
			int value = Integer.parseInt(st.nextToken());
			
			// 첫 도시인 경우 필요한 비용을 그대로 입력
			if(i == 0) {
				for(int j=1; j<=goal; j++) {
					if(j < value) dp[j] = cost;
					else dp[j] = dp[j-value] + cost;
				}
			}
			
			// 첫 도시가 아닌 경우 이전 기록과 비교하여 최소값 갱신
			else {
				for(int j=1; j<=goal; j++) {
					if(j < value) dp[j] = Math.min(cost, dp[j]);
					else dp[j] = Math.min(dp[j-value] + cost, dp[j]);
				}
			}
		}
		
		System.out.println(dp[goal]);
	}

}