package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_20310_타노스_S3 {

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		String S = br.readLine();
		int n = S.length();
		int cnt0 = 0;
		int cnt1 = 0;

		for (int i = 0; i < n; i++) {
			char ch = S.charAt(i);
			if (ch == '0') cnt0++;
			else cnt1++;
		}

		int del0 = cnt0 / 2; // 뒤에서부터 제거할 0 개수
		int del1 = cnt1 / 2; // 앞에서부터 제거할 1 개수

		boolean[] removed = new boolean[n];

		for (int i = 0; i < n && del1 > 0; i++) {
			if (S.charAt(i) == '1') {
				removed[i] = true;
				del1--;
			}
		}

		for (int i = n - 1; i >= 0 && del0 > 0; i--) {
			if (S.charAt(i) == '0') {
				removed[i] = true;
				del0--;
			}
		}


		for (int i = 0; i < n; i++) {
			if (!removed[i]) sb.append(S.charAt(i));
		}

		System.out.println(sb.toString());
	}
}
