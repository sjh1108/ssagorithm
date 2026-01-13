package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Arrays;

public class BOJ_1895_필터_S4 {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());

		int[][] img = new int[R][C];

		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				img[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int T = Integer.parseInt(br.readLine());

		int count = 0;
		int[] filter = new int[9];

		for (int i = 0; i <= R - 3; i++) {
			for (int j = 0; j <= C - 3; j++) {

				int idx = 0;

				for (int a = i; a < i + 3; a++) {
					for (int b = j; b < j + 3; b++) {
						filter[idx] = img[a][b];
						idx++;
					}
				}

				Arrays.sort(filter); // sort

				if (filter[4] >= T) {
					count++;
				}
			}
		}

		System.out.println(count);
	}
}