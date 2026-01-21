package Rank5.gold_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10942 {
	
	// 알고리즘 분류 : DP
	// 구상 : 2차원 배열에 각 구역별로 팰린드롬 완성 여부를 저장하는 배열 생성
	// dp[i][j] : j부터 i까지의 숫자는 팰린드롬인가?
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		int[] arr = new int[n];
		// 팰린드롬 완성 여부만 저장하면 되므로 boolean 타입 배열로 선언
		boolean[][] dp = new boolean[n][n];
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
			
			// 길이가 1인 부분수열
			// 자기 자신은 그 자체로 하나의 팰린드롬임
			dp[i][i] = true;
			
			// 길이가 3 이상인 부분 수열
			// 어떤 구역을 지정했을 때 양 끝단의 숫자가 같은지, 그리고 그 사이의 숫자열은 팰린드롬인지 검증
			// 두 조건이 모두 맞아야 현재 구역의 숫자열도 팰린드롬임
			for(int j=0; j<i-1; j++) {
				dp[i][j] = (arr[j] == arr[i]) && (dp[i-1][j+1]);
			}
			
			// 길이가 2인 부분수열
			// 마지막 2개 숫자가 동일한지 비교
			if(i >= 1) dp[i][i-1] = arr[i] == arr[i-1];
		}
		
		int query = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<query; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken()) - 1;
			int e = Integer.parseInt(st.nextToken()) - 1;
			sb.append(dp[e][s] ? 1 : 0).append("\n");
		}
		
		System.out.println(sb);
	}

}