package _20260303.sjh1108.BOJ_1999;

import java.io.*;
import java.util.*;

class Main {
	private static final int MIN = 0;
	private static final int MAX = 1 << 8;

    private static int N, B, K;

    private static int[][] map;
    // 행별 B구간의 최대/최소를 미리 계산
    private static int[][] max, min;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        map = new int[N + 1][N + 1];

		for (int i = 1; i <= N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 1; j <= N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		max = new int[N + 1][N + 1];
		min = new int[N + 1][N + 1];

        // 각 행에서 길이 B 구간의 최대/최소 전처리
		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N - B + 1; j++) {
				int maxV = MIN;
				int minV = MAX;

				for (int k = 0; k < B; k++) {
					if (map[i][j + k] > maxV) maxV = map[i][j + k];
					if (map[i][j + k] < minV) minV = map[i][j + k];
				}

				max[i][j] = maxV;
				min[i][j] = minV;
			}
		}

		StringBuilder sb = new StringBuilder();

		while (K-- > 0) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());

			int maxVal = MIN;
			int minVal = MAX;

            // BxB 영역에서 행 전처리 값을 이용해 최댓값/최솟값 계산
			for (int i = 0; i < B; i++) {
				maxVal = Math.max(maxVal, max[r + i][c]);
				minVal = Math.min(minVal, min[r + i][c]);
			}

			sb.append(maxVal - minVal).append('\n');
		}

		System.out.println(sb);
    }
}
