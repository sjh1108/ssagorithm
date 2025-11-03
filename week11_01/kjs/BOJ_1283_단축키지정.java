package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;


public class BOJ_1283_단축키지정 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		boolean[] alphabet = new boolean[26];
		for (int i = 0; i < n; i++) { 
			String option = br.readLine(); 
			String[] separation = option.split(" "); // 띄어쓰기를 통한 단어 구분
			int separationIdx = -1; // 몇 번째 단어, 기본 값 -1로 설정
			int wordIdx = -1; // 몇 번쨰 알파벳, 기본값 -1
			for (int j = 0; j < separation.length; j++) { // 모든 단어에 대한 첫 번째 알파벳 검사
				String temp = separation[j];
				temp = temp.toLowerCase();
				if (!alphabet[temp.charAt(0) - 'a']) { 
					alphabet[temp.charAt(0) - 'a'] = true;
					separationIdx = j;
					wordIdx = 0;
					break; // 저장 후 반복문 탈출
				}
			}
			if (separationIdx == -1) { // 아직 체크된게 없다면
				boolean found = false;
				for (int j = 0; j < separation.length && !found; j++) {
			        for (int k = 1; k < separation[j].length(); k++) {
			            String temp = separation[j].toLowerCase();
			            if (!alphabet[temp.charAt(k) - 'a']) { // 알파벳이 단축키로 쓰였는지 확인
			            	alphabet[temp.charAt(k) - 'a'] = true;
			                separationIdx = j;
			                wordIdx = k;
			                found = true; 
			                break; // 찾으면 루프 탈출
			            }
					}
				}
			}
			if (separationIdx == -1) { // 체크된 적이 없다면
				System.out.println(option);
				continue;
			}
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < separation.length; j++) {
				if (separationIdx == j) {
					for (int k = 0; k < separation[j].length(); k++) {
						if (k != wordIdx) {
							sb.append(separation[j].charAt(k));
							continue;
						}
						sb.append("["); // 체크된 부분에 대해서만 괄호를 통해 감싸줌
						sb.append(separation[j].charAt(k));
						sb.append("]");
					}
					sb.append(" ");
					continue;
				}
				sb.append(separation[j] + " ");
			}
			System.out.println(sb);
		}

	}
}