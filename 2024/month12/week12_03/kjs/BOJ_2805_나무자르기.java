package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2805_나무자르기 {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		long M = Long.parseLong(st.nextToken());

		int[] trees = new int[N];
		st = new StringTokenizer(br.readLine());

		int left = 0;
		int right = 0;

		for (int i = 0; i < N; i++) {
			trees[i] = Integer.parseInt(st.nextToken());
			right = Math.max(right, trees[i]);
		}

		while (left <= right) {

			int mid = (left + right) / 2;

			long sum = 0;

            for (int i = 0; i < N; i++) {
                if (trees[i] > mid) {
                    sum += trees[i] - mid;
                }
            }

			if (sum >= M) {
				left = mid + 1;
			} else {
				right = mid - 1;
			}

		}

		System.out.println(right);
	}
}
