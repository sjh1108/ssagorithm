package week; // 실버3

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - 정수 n이 주어졌을 때, n을 1,2,3의 합으로 나타내는 방법의 수
 * 
 * 입력
 * - 테케 T
 * - 정수 (양수, 1~10)
 * 
 * 전략
 * - 왜 아무것도 떠오르지 않지 
 * - 이딴문제 못풀겠다
 * - 이거 애 이렇게 풀리는지 아시는분
 * 
 * 오답노트
 * - dp 규칙 못찾음
 * - N이 3보다 작을 때 배열 크기 조정 문제
 */
public class Main_9095_123더하기 {
	
	static int T;
	static int N;
	static int[] dp;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		T = Integer.parseInt(br.readLine());
		

		
		for (int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			if(N < 4) {
				dp = new int[4];
			}else {
				dp = new int[N+1]; // 1부터 N까지 필요 
			}
			
			
			dp[1] = 1; // 1
			dp[2] = 2; // 1+1 2
			dp[3] = 4; // 1+1+1 1+2 2+1 3
			
			for(int i = 4; i <= N; i++) {
				dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
			}
			
			System.out.println(dp[N]);
			
		}
	}
}
