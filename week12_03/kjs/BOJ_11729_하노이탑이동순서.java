package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_11729_하노이탑이동순서 {

	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());

		long k = 1;
		for (int i = 0; i < n; i++) {
			k = k * 2;

		}

		k -= 1;

		sb.append(k).append("\n");
		re(n, 1, 2, 3);

		System.out.print(sb.toString());

	}

	static void re(int n, int start, int mid, int to) {
		if (n == 1) {
			sb.append(start).append(" ").append(to).append("\n");
			return;
		}

		re(n - 1, start, to, mid);
		sb.append(start).append(" ").append(to).append("\n");

		re(n - 1, mid, start, to);
	}

}
