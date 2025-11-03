package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_24061_알고리즘_병합정렬2 {
	static int N;
	static long K;
	static int[] A, tmp, after;
	static long count = 0;
	static boolean find = false;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		K = Long.parseLong(st.nextToken());

		A = new int[N];
		tmp = new int[N];

		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			A[i] = Integer.parseInt(st.nextToken());
		}

		mergeSort(0, N - 1);

		if (!find) {
			System.out.println(-1);
		} else {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < N; i++) {
				if (i > 0) {
					sb.append(' ');
				}
				sb.append(after[i]);
			}
			System.out.println(sb.toString());
		}
	}

	static void mergeSort(int p, int r) {
		if (find) {
			return;
		}
		if (p < r) {
			int q = (p + r) >>> 1; // (p + r) / 2
			mergeSort(p, q);
			mergeSort(q + 1, r);
			merge(p, q, r);
		}
	}

	static void merge(int p, int q, int r) {
		if (find) {
			return;
		}
		int i = p;
		int j = q + 1;
		int t = 0;

		while (i <= q && j <= r) {
			if (A[i] <= A[j]) {
				tmp[t++] = A[i++];
			} else {
				tmp[t++] = A[j++];
			}
		}
		while (i <= q) {
			tmp[t++] = A[i++];
		}
		while (j <= r) {
			tmp[t++] = A[j++];
		}

		i = p;
		t = 0;
		while (i <= r) {
			A[i] = tmp[t];
			count++;
			if (!find && count == K) {
				after = A.clone();
				find = true;
				return;
			}
			i++;
			t++;
		}
	}
}
