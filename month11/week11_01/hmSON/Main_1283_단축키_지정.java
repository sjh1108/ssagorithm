import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_1283_단축키_지정 {
	
	/*
	 * BOJ_1283 : 단축키 지정(Silver_1)
	 * 자료구조 및 알고리즘 : 구현, 문자열
	 * 
	 * 단축키 지정 규칙
	 * 0. 단축키는 알파벳의 대소문자를 구분하지 않는다.
	 * 1. 왼쪽에서 오른쪽 순서로 단어를 순회하며 단어의 첫 글자가 아직 단축키로 지정되지 않은 상태라면 그 알파벳을 단축키로 지정한다.
	 * 2. 1번에 해당하는 알파벳이 없는 경우 각 단어의 첫 글자를 제외한 모든 글자를 순회하며 아직 단축키로 지정되지 않은 알파벳을 단축키로 지정한다.
	 * 3. 2번에 해당하는 알파벳도 없는 경우 단축키를 지정하지 않는다.
	 * 
	 * 단축키 지정 이후 각 옵션을 출력할 때, 각 옵션별로 단축키의 기원이 된 위치의 알파벳에 [ ] 괄호를 씌워 출력한다.
	 */
	
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 옵션 수
		int n = Integer.parseInt(br.readLine());
		// 단축키 등록 여부를 확인하기 위한 방문 처리 배열
		boolean[] regist = new boolean[26];
		for(int i=0; i<n; i++) {
			String[] words = br.readLine().trim().split(" ");
			boolean flag = false;
			
			// 규칙 1 : 각 단어의 첫 글자가 아직 단축키로 지정되지 않은 경우
			for(int j=0; j<words.length; j++) {
				char first = words[j].charAt(0);
				// 규칙 0 : 단축키는 대소문자를 구분하지 않는다.
				// 단어의 첫 글자가 아직 단축키로 등록되지 않은 경우 즉시 추가한다.
				char upper = Character.toUpperCase(first);
				if(!regist[upper - 'A']) {
					flag = true;
					regist[upper - 'A'] = true;
					words[j] = "[" + first + "]" + words[j].substring(1);
					break;
				}
			}
			
			if(flag) {
				registWord(words);
				continue;
			}
			
			// 규칙 2 : 각 단어의 첫 글자가 이미 단축키로 지정된 경우
			for(int j=0; j<words.length; j++) {
				String word = words[j];
				
				// 각 단어의 첫 글자를 제외한 나머지 글자 순회
				for(int k=1; k<word.length(); k++) {
					char next = word.charAt(k);
					// 규칙 0 : 단축키는 대소문자를 구분하지 않는다.
					// 각 글자가 아직 단축키로 등록되지 않은 경우 즉시 추가한다.
					char upper = Character.toUpperCase(next);
					if(!regist[upper - 'A']) {
						flag = true;
						regist[upper - 'A'] = true;
						words[j] = word.substring(0, k) + "[" + next + "]" + word.substring(k+1);
						break;
					}
				}
				if(flag) break;
			}
			
			// 괄호를 적용한 옵션 문자열 등록
			// 규칙 3에 의해 단축키 지정이 안된 옵션도 그냥 등록한다.
			registWord(words);
		}
		
		System.out.println(sb);
	}
	
	// 단축키 지정이 포함된 옵션 문자열 등록 메서드
	static void registWord(String[] words) {
		for(String str : words) {
			sb.append(str + " ");
		}
		sb.append("\n");
	}
	
}
