package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_24060_알고리즘_병합정렬1 {
	static int N;
	static long K;
	static int[] A, tmp;
	static long count = 0;
	static int answer = -1;
	static boolean found = false;

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

		System.out.println(answer);
	}

	static void mergeSort(int p, int r) {
		if (found) {
			return;
		}
		if (p < r) {
			int q = (p + r) >>> 1;
			mergeSort(p, q);
			mergeSort(q + 1, r);
			merge(p, q, r);
		}
	}

	static void merge(int p, int q, int r) {
		if (found) {
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
			if (count == K) {
				answer = tmp[t];
				found = true;
				return;
			}
			i++;
			t++;
		}
	}
}
