package week12_01.Minsang.BOJ_2738;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2738 {
	static int N, M;
	static int[][] arr1;
	static int[][] arr2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 행렬 A, 행렬 B => 초기화
		arr1 = new int[N][M];
		arr2 = new int[N][M];
		
		// 행렬 A 입력받기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				arr1[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 행렬 B 입력받기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				arr2[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 행렬 A + 행렬 B 더한 행렬 출력
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				System.out.print(arr1[i][j] + arr2[i][j] + " ");
				
				if(j == M-1) {
					System.out.println();
				}
			}
		}

	}

}

