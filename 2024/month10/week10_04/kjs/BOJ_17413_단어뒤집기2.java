package Test;

import java.io.*;
import java.util.*;

public class BOJ_17413_단어뒤집기2 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String s = br.readLine();

		StringBuilder result = new StringBuilder(s.length());
		StringBuilder temp = new StringBuilder();
		boolean isTag = false;

		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);

			if (c == '<') {
				// 단어 버퍼 비우며 뒤집어 출력
				for (int j = temp.length() - 1; j >= 0; j--)
					result.append(temp.charAt(j));
				temp.setLength(0); // 초기화
				isTag = true;
				result.append(c);
			} else if (c == '>') {
				isTag = false;
				result.append(c);
			} else if (isTag) {
				result.append(c);
			} else {
				if (c == ' ') {
					// 공백 만나면 지금까지의 단어를 뒤집어 출력
					for (int j = temp.length() - 1; j >= 0; j--)
						result.append(temp.charAt(j));
					temp.setLength(0);
					result.append(' ');
				} else {
					// 단어 구성 문자 누적
					temp.append(c);
				}
			}
		}

		// 마지막 단어
		for (int j = temp.length() - 1; j >= 0; j--)
			result.append(temp.charAt(j));

		System.out.println(result.toString());
	}
}
