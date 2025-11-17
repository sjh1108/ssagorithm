package week; // 실버 1

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - NxN 맵
 * - (x1,y1)부터 (x2,y2)까지의 합을 구하라
 * - 구역의 좌상, 우하 좌표를 통해 네모난 구간의 합을 구하는 것
 * 
 * 전략
 * - 누적합
 * - 해당 좌표까지의 누적합을 구한 배열을 만들어놓고
 * - 그걸 열심히 조합해서 구해보자~~ 
 * 
 */
public class Main_11660_구간합구하기5 {

	static int N, M;
	static int[][] arr, dp; // 좌상부터 해당 좌표까지의 누적합 2차배열 
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		
		// 좌표가 1부터 시작함 -> N+1로 셋팅  
		// 누적합 dp할 때 인덱스가 -1이 나오지 않게 하기 위함이기도 함
		arr = new int[N+1][N+1];
		dp = new int[N+1][N+1];
				
		
		for(int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= N; j++) {
				arr[i][j] = Integer.parseInt(st.nextToken());
				dp[i][j] = arr[i][j] + dp[i-1][j] + dp[i][j-1]
						- dp[i-1][j-1]; // 교집합 뺴기 
			}
		}//입력끝 + 입력받으면서 누적합 배열 만들기 
		
		StringBuilder sb = new StringBuilder();

		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			
			int startX = Integer.parseInt(st.nextToken());
			int startY = Integer.parseInt(st.nextToken());
			
			int endX = Integer.parseInt(st.nextToken());
			int endY = Integer.parseInt(st.nextToken());
			
			int sum = dp[endX][endY] 
					- dp[startX - 1][endY] - dp[endX][startY - 1]
							+ dp[startX - 1][startY - 1];
			sb.append(sum).append("\n");
		}
		
		System.out.println(sb.toString());
				
	}
	
}
