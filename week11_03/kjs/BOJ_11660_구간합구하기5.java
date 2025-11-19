package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class BOJ_11660_구간합구하기5 {

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
	
		int[][] before = new int[N + 1][N + 1];

	
		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				int val = Integer.parseInt(st.nextToken());
				before[i][j] = before[i - 1][j] + before[i][j - 1] - before[i - 1][j - 1] + val;
			}
		}

		for (int q = 0; q < M; q++) {
			st = new StringTokenizer(br.readLine());
			int x1 = Integer.parseInt(st.nextToken());
			int y1 = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());

			int result = before[x2][y2] - before[x1 - 1][y2] - before[x2][y1 - 1] + before[x1 - 1][y1 - 1];

			sb.append(result).append('\n');
		}

		System.out.print(sb.toString());
	}
}
