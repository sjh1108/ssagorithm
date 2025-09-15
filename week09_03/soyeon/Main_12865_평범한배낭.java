package com.codingtest.bk;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 0/1 Knapsack
 * 
 * 전략 
 * - 가자.
 */
public class Main_12865_평범한배낭 {

	static int N, K;
	static int[] value, weight;
	static int[][] dp;
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		value = new int[N+1]; // 물건이 1번부터 시작
		weight = new int[N+1];
		dp = new int[N+1][K+1];
		
		for(int i = 1; i < N+1; i++) {
			st = new StringTokenizer(br.readLine());
			weight[i] = Integer.parseInt(st.nextToken());
			value[i] = Integer.parseInt(st.nextToken());	
		}// 입력끝
		
		for(int i = 1; i < N+1; i++) {
			for(int w = 1; w < K+1; w++) {
				int curw = weight[i];
				int curv = value[i];
				
				if(curw > w) dp[i][w] = dp[i-1][w];
				else dp[i][w] = Math.max(dp[i-1][w], dp[i-1][w-curw] + curv);
			}
		}

		System.out.println(dp[N][K]);
		
		
		
	}
}

