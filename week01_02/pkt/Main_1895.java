package week01_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 필터 실버4
public class Main_1895 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int R = Integer.parseInt(st.nextToken());
		int C = Integer.parseInt(st.nextToken());

		int[][] a = new int[R][C];
		for (int i = 0; i < R; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < C; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		int T = Integer.parseInt(br.readLine());

		int[] box = new int[9];
		int cnt = 0;

		// 필터 범위를 이동시키면서
		for (int i = 0; i <= R-3; i++) {
			for (int j = 0; j <= C-3; j++) {
				// 중앙값 필터링 해주기.
				int idx = 0;
				for (int x = i; x < i+3; x++) {
					for (int y = j; y < j+3; y++) {
						box[idx++] = a[x][y];
					}
				}
				
				Arrays.sort(box);
				int med = box[4];
				if(med >= T) cnt++;
				
			}
		}
		
		System.out.println(cnt);

	}

}
