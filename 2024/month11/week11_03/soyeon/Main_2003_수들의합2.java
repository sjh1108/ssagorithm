package week; // 실버4

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - N개의 수로 된 수열 
 * - i번째 수부터 j번째 수까지의 합이 M이 되는 경우의 수를 구하라 
 * 
 * 입력
 * - N (1~10,000)
 * - M (1~300,000,000) 3억?????
 * - 각 수열값은 30,000을 넘지 않음
 * 
 * 전략
 * - 쌩으로 다하면 시초날 것 같음
 * - 누적합, 투포인터
 * 
 * - 누적합 배열 만들고...
 * -  ix(i-1) ... 을 N번 반복... 이면 시간복잡도가 얼마임? 일단 해보겠음 
 *
 */
public class Main_2003_수들의합2 {

	static int N, M;
	static int[] arr, dp;
	static int cnt;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 수열 1부터 시작 
		arr = new int[N+1];
		dp = new int[N+1];
		
		st = new StringTokenizer(br.readLine());
		for(int i = 1; i < N+1; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			dp[i] = dp[i-1] + arr[i];
		} // 입력끝 + 누적합 배열
		
		for(int i = 1; i < N+1; i++) {
			for(int j = 0; j < i; j++) { //0부터시작( 자기자신 포함해야함)
				if(dp[i] - dp[j] == M) cnt++; 
			}
		}
		
		System.out.println(cnt);
	}
}
