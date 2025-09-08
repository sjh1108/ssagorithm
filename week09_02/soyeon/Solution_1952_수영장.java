package com.codingtest.swea;

import java.util.Scanner;

// dfs, dp 둘다 가능
public class Solution_1952_수영장 {

	static int[] price;
	static int[] plan;
	static int ans;
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int T =sc.nextInt();
		
		for(int tc = 1; tc <= T; tc++) {
			
			price = new int[4];
			plan = new int[12+1];// 1인덱스가 1월
			ans = Integer.MAX_VALUE; // 최대구하니까 최소로 초기화
			
			
			for(int i = 0; i < price.length; i++) {
				price[i] = sc.nextInt();
			}
			
			// 1인덱스가 1월
			for(int i = 1; i < plan.length; i++) {
				plan[i] = sc.nextInt();
			} // 입력 끝
			
			dfs(1, 0);
			
			System.out.println("#" + tc + " " + ans);
		}
	}
	
	// 그러니까. dfs가.
	// 한 선택에서 다음 선택을 할 때
	// 경우의 수를 다 dfs() 호출로 돌리는 거다?? 
	
	// 일단 무엇을 현재(cur, pos 등등.. )으로 볼지를 생각하고
	// 그에 따라 무슨 값이 변하는 지를 떠올려보자. 
	private static void dfs(int cur, int sum) {
		
//		cur++;
		
		if(cur > 12) {
//			System.out.println("sum : " + sum);
			ans = Math.min(ans, sum);
			return;
		}

		// 1일 선택
		dfs(cur + 1, sum + plan[cur] * price[0]);
		// 1달 선택
		dfs(cur + 1, sum + price[1]);
		// 3달 선택
//		if(cur < 11) { //오답노트. 이게 없어도 된다.  
			dfs(cur + 3, sum + price[2]);
//		}
		
		// 1년 선택
//		if(cur == 1) { //오답노트. 이게 없어도 된다. 
			dfs(cur + 12, price[3]);
//		}
		
		
	}
}
