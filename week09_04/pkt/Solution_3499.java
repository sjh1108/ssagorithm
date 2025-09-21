package week_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution_3499 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			
			int N = Integer.parseInt(br.readLine());
			String[] str = br.readLine().split(" ");
			
//			for (int i = 0; i < N; i++) {
//				System.out.print(str[i] + " ");
//			}
			
			
			// mid를 기준으로 나누고 -> N+1해주면 짝수 인덱스 문제도, 홀수 문제도 처리해줌.
			int mid = (N + 1) / 2;
			
			String[] left = new String[mid];
			String[] right = new String[N - mid];
			
			for (int i = 0; i < mid; i++) {
				left[i] = str[i];
			}
			
			for (int i = mid; i < N; i++) {
				right[i-mid] = str[i];
			}
						
			sb.append("#").append(tc).append(" ");
			for (int i = 0; i < right.length; i++) {
				sb.append(left[i]).append(" ").append(right[i]).append(" ");
			}
			if(N % 2 == 1) sb.append(left[mid - 1]);
			sb.append("\n");
			
		}
		System.out.println(sb);
	}
}
