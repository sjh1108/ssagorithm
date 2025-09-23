package week_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1182 {
	
	static int N, S;
	static int[] arr;
	static int count = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		dfs(0,0);
		
		// 크기가 양수인 부분수열이라는 조건 존재함, 공집합은 제외해야 하므로 S==0일 때 -1 처리
	    if (S == 0) count--;
	    System.out.println(count);
		
	}
	
	
	private static void dfs(int index, int sum) {
		// index 탐색 완료- 같은 것만 세주기.
		if(index == N) {
			if(sum == S) {
				count++;
			}
			return;
		}
		
		// 현재 원소 포함
		dfs(index + 1, sum + arr[index]);

		// 현재 원소 미포함
		dfs(index + 1, sum);
	}
	
}
