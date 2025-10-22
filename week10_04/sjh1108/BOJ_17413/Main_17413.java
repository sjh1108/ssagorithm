package week10_04.sjh1108.BOJ_17413;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String args[]) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String str = br.readLine(); // 입력 문자열
        StringBuilder sb = new StringBuilder(); // 최종 결과를 저장할 StringBuilder
        
        // 태그('<'와 '>') 안에 있는지 여부를 판별하는 플래그
        // true이면 태그 안, false이면 태그 밖(일반 단어)
        boolean flag = false; 
        
        // 태그 밖의 단어를 뒤집기 위해 사용할 스택
        Stack <Character> stack = new Stack < > ();
        
        // 문자열을 첫 글자부터 끝까지 순회
        for (int i = 0; i < str.length(); i++) {
            
            // 1. 열린 태그('<')를 만난 경우
            if (str.charAt(i) == '<') {
                // 이전에 스택에 쌓인 단어(태그 밖의 단어)가 있다면
                // 모두 pop하여 sb에 추가 (단어를 뒤집어서 추가)
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
                // 태그 안으로 진입했음을 표시
                flag = true;
                // '<' 문자를 sb에 추가
                // (sb.append(str.charAt(i));가 'if (flag == true)' 블록에서 처리됨)
            }
            
            // 2. 닫힌 태그('>')를 만난 경우
            else if (str.charAt(i) == '>') {
                // 태그 밖으로 나왔음을 표시
                flag = false;
                // '>' 문자를 sb에 추가
                sb.append(str.charAt(i));
                // 다음 문자로 넘어가기 위해 continue
                continue;
            }

            // 3. 태그 내부인 경우 (flag == true)
            if (flag == true) {
                // 태그 안의 문자는 그대로 sb에 추가
                sb.append(str.charAt(i));
            }
            
            // 4. 태그 외부인 경우 (flag == false)
            else if (flag == false) {
                
                // 4-1. 공백을 만난 경우
                if (str.charAt(i) == ' ') {
                    // 이전에 스택에 쌓인 단어(공백 앞의 단어)가 있다면
                    // 모두 pop하여 sb에 추가 (단어를 뒤집어서 추가)
                    while (!stack.isEmpty()) {
                        sb.append(stack.pop());
                    }
                    // 공백 문자를 sb에 추가
                    sb.append(' ');
                    // 다음 문자로 넘어가기 위해 continue
                    continue;
                } 
                // 4-2. 일반 문자를 만난 경우 (단어의 일부)
                else {
                    // 스택에 문자를 push (나중에 뒤집기 위함)
                    stack.push(str.charAt(i));
                }
            }

            // 5. 문자열의 마지막 문자에 도달한 경우
            if (i == str.length() - 1) {
                // (마지막 문자가 태그 밖의 단어일 경우)
                // 스택에 남아있는 마지막 단어를 모두 pop하여 sb에 추가
                while (!stack.isEmpty()) {
                    sb.append(stack.pop());
                }
            }
        } // for문 종료

        // 최종 결과 출력
        System.out.println(sb);
    }
}