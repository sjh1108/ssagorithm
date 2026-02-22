package baek_week02_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2829_이용호 {
/*
 * 주 대각선 성분의 합 A, 다른 대각선 성분의 합 B
 * 행렬의 아름다움 = A - B
 * 아름다운 정도가 가장 큰 부분 행렬
 * 행렬 크기 N(2 <= N <= 400)
 * 부분 행렬 크기 P (2~N x 2~N)
 * 부분행렬 위치(1 ~ N - P + 1) x (1 ~ N - P + 1)
 * 누적합 구해 두고 부분행렬 위치마다 A - B 계산
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine().trim());
		int[][] matrix = new int[N+1][N+1];
		int[][][] prefix = new int[N+1][N+2][2]; // 0 주 대각(좌상향), 1 반대 대각(우상향)
		int result = Integer.MIN_VALUE;
		// 행렬 입력
		for(int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 1; j <= N; j++) {
				matrix[i][j] = Integer.parseInt(st.nextToken());
				if(i == 1 || j == 1) {
					// 주 대각선 누적합 초기값
					prefix[i][j][0] = matrix[i][j];
				}
				if(i == 1 || j == N) {
					// 반대 대각선 누적합 초기값
					prefix[i][j][1] = matrix[i][j];
				}
			}
		}
		// 누적합 구해두기 
		for(int i = 2; i <= N; i++) {
			for(int j = 1; j <= N; j++) {				
				// 주 대각 없을때
				if(j == 1) {
					prefix[i][j][1] = prefix[i-1][j+1][1] + matrix[i][j];
				}
				// 반대 대각 없을때
				if(j == N) {
					prefix[i][j][0] = prefix[i-1][j-1][0] + matrix[i][j];
				}
				else {
					prefix[i][j][1] = prefix[i-1][j+1][1] + matrix[i][j];
					prefix[i][j][0] = prefix[i-1][j-1][0] + matrix[i][j];
				}
			}
		}
		// 부분행렬 크기 
		for(int p = 2; p <= N; p++) {
			// 부분행렬 계산 시작 위치(행렬위 가장 왼쪽위)
			for(int i = 1; i <= N - p + 1; i++) {
				for(int j = 1; j <= N - p + 1; j++) {
					// 주 대각선 합 계산(부분 행렬 내 가장 오른쪽 아래 - 부분행렬 내 왼쪽위의 왼쪽위)
					int A = prefix[i+p-1][j+p-1][0] - prefix[i-1][j-1][0]; 
					// 반대 대각선 합 계산(부분행렬 내 가장 왼쪽 아래 - 부분행렬 내 오른쪽위의 오른쪽 위)
					int B = prefix[i+p-1][j][1] - prefix[i-1][j+p][1]; // 반대 대각선 합 계산
					result = Math.max(result,A-B);
				}
			}
		}
		System.out.println(result);
	}

}
