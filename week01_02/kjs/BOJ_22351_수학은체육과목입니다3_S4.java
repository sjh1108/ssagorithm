package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_22351_수학은체육과목입니다3_S4 { 

	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String S = br.readLine();

		int len = S.length();

		for (int A = 1; A <= 999; A++) {

			int where = 0;     
			int cur = A; 

			while (true) {

				// 문자열 끝이면 멈춤
				if (where == len) {
					int B = cur - 1;
					System.out.println(A + " " + B);
					return;
				}

				if (cur > 999) break;

				String num = String.valueOf(cur);
				int length = num.length();

				// 남은 문자열이 부족 실패
				if (where + length > len) break;

				boolean match = true;

				for (int k = 0; k < length; k++) {
					if (S.charAt(where + k) != num.charAt(k)) {
						match = false;
						break;
					}
				}

				if (!match) break;

				where += length;
				cur++;
			}
		}
	}
}