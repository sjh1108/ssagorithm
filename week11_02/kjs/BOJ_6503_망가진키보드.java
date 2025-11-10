package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_6503_망가진키보드 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			int m = Integer.parseInt(br.readLine());
			if (m == 0) break; // 종료

			String str = br.readLine();
			int n = str.length();

			int[] freq = new int[256]; // ASCII code 수 128, 256 ???
			int distinct = 0; // 현재 구간 안의 문자 종류 수
			int left = 0, maxLen = 0;

			for (int right = 0; right < n; right++) {
				char rc = str.charAt(right);

				// 새 문자라면 종류 +1
				if (freq[rc] == 0)
					distinct++;
				freq[rc]++;

				// 문자 종류가 m 초과 → left를 당겨서 줄이기
				while (distinct > m) {
					char lc = str.charAt(left);
					freq[lc]--;
					if (freq[lc] == 0)
						distinct--; 
					left++;
				}

				// 현재 길이 갱신
				maxLen = Math.max(maxLen, right - left + 1);
			}

			System.out.println(maxLen);
		}
	}
}
