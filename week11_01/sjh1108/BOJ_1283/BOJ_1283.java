package week11_01.sjh1108.BOJ_1283;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        // 단축키로 '등록된' 문자를 저장 (대소문자 모두 저장)
        HashSet<Character> registered = new HashSet<>();
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < N; i++){
            String option = br.readLine();
            String[] split = option.split(" ");

            // 단축키가 적용될 단어의 인덱스
            int wordIdx = -1; 
            // 단축키가 적용될 문자의 인덱스 (해당 단어 내)
            int charIdx = -1;
            boolean found = false;

            // 1. 단어의 첫 글자가 단축키로 지정되었는지 확인한다.
            for (int j = 0; j < split.length; j++) {
                // 빈 문자열은 건너뜀 (e.g., "File  New" -> "File", "", "New")
                if (split[j].length() == 0) continue; 
                
                char firstChar = split[j].charAt(0);
                if (!registered.contains(firstChar)) {
                    // 단축키로 등록 (대소문자 둘 다)
                    registered.add(Character.toUpperCase(firstChar));
                    registered.add(Character.toLowerCase(firstChar));
                    
                    wordIdx = j; // j번째 단어
                    charIdx = 0; // 0번째 문자
                    found = true;
                    break;
                }
            }

            // 2. 모든 단어의 첫 글자가 이미 지정되어 있다면, 왼쪽에서부터 차례대로 본다.
            if (!found) {
                for (int j = 0; j < split.length; j++) {
                    if (split[j].length() == 0) continue;
                    
                    for (int k = 0; k < split[j].length(); k++) {
                        char c = split[j].charAt(k);
                        if (!registered.contains(c)) {
                            // 단축키로 등록 (대소문자 둘 다)
                            registered.add(Character.toUpperCase(c));
                            registered.add(Character.toLowerCase(c));
                            
                            wordIdx = j; // j번째 단어
                            charIdx = k; // k번째 문자
                            found = true;
                            break;
                        }
                    }
                    if (found) break; // 찾았으면 단어 루프 탈출
                }
            }

            // 3. 찾은 단축키를 바탕으로 출력 문자열(sb)을 조립한다.
            for (int j = 0; j < split.length; j++) {
                if (j == wordIdx) { // 단축키가 적용될 단어라면
                    String word = split[j];
                    // 괄호 앞부분
                    sb.append(word.substring(0, charIdx)); 
                    // [괄호]
                    sb.append('[').append(word.charAt(charIdx)).append(']'); 
                    // 괄호 뒷부분
                    sb.append(word.substring(charIdx + 1)); 
                } else {
                    // 일반 단어는 그냥 추가
                    sb.append(split[j]);
                }
                
                // 마지막 단어가 아니면 공백 추가
                if (j < split.length - 1) {
                    sb.append(" ");
                }
            }
            sb.append('\n'); // 각 옵션 입력이 끝난 후 줄바꿈
        }

        System.out.println(sb); // N개의 줄을 처리한 후 한 번에 출력
    }
}