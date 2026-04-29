package baek.d20260413;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_2565_이용호 {
/*
 * 전체 전깃줄 수 - 서로 교차하지 않게 남길수 있는 최대 전깃줄 수
 * 전깃줄이 교차하지 않으려면 A가 증가할때 B도 증가해야 한다.
 * LIS
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[][] wire = new int[N][2];
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			
			wire[i][0] = A;
			wire[i][1] = B;
		}
		// 입력이 순서대로 주어지는게 아니라서 A를 기준으로 정렬해야함
		Arrays.sort(wire,(o1, o2) -> {
			return Integer.compare(o1[0], o2[0]);
		});
		// LIS
		int[] dp = new int[N];
		int lis = 0;
		
		for(int i = 0; i < N;i++) { 
			dp[i] = 1; // 자기 자신(기본)
			for(int j = 0; j < i; j++) {
				// 0 ~ i까지 i번째 전기줄보다 j번째 전기줄이 작으면 교차 x -> 길이++
				 if (wire[j][1] < wire[i][1]) {
					 // i번째 전깃줄을 마지막으로 할 때 만들 수 있는 LIS 길이
					 dp[i] = Math.max(dp[i], dp[j] + 1);
				 }
			}
			lis = Math.max(lis, dp[i]);
		}
//		System.out.println(wire[0][0]);
		System.out.println(N - lis);
	}

}
