package bk.silver; // 실버 1

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - n이 주어질 때, 줄어들지 않는 n자리 수의 개수를 구하라
 * - 줄어들지 않는 수 : 1234, 0000, 0001
 * 
 * 전략
 * - n이 최대 64면... 64자리수 미쳤나
 * - 다 구하면 터질것같은뎁쇼. DP가보겠습니다.
 * 
 * 실패이유
 * - dp는 미리 계산해놓는건데 자꾸 새로 만듦
 * - dp 배열을 뭘로 짜야할지 감을 못잡았습니다.
 * - n = 64이므로 int -> long주의
 *
 */
public class _Main_2688_줄어들지않아 {

	
	static int T;
	static long[][] dp;
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		T = Integer.parseInt(br.readLine());
		
		
		// 최대 n이 64이므로 미리 계산해두는 것이 효율적
        // dp[자릿수][끝나는숫자]
        dp = new long[65][10];

        // 초기값 설정: 1자리 수일 때
        for (int j = 0; j <= 9; j++) {
            dp[1][j] = 1;
        }

        // DP 테이블 채우기
        for (int i = 2; i <= 64; i++) {
            for (int j = 0; j <= 9; j++) {
                // j로 끝나는 i자리 수는, i-1자리에서 0~j로 끝나는 수들의 합
                for (int k = 0; k <= j; k++) {
                    dp[i][j] += dp[i - 1][k];
                }
            }
        }
		
		for(int tc = 1; tc <= T; tc++) {
			
			int n = Integer.parseInt(br.readLine());
            long sum = 0;
            for (int j = 0; j <= 9; j++) {
                sum += dp[n][j];
            }
            sb.append(sum).append("\n");
			
			
			
			
		}
		
		System.out.println(sb.toString());
	}
}
