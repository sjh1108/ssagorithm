package algo2025_10_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_17413_단어뒤집기2 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {

        String input = br.readLine();
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        boolean reverseWord = false; // 태그 안에 있는지 여부

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            if (c == '<') { // 태그 만났을때 stack에 삽입
                while (!stack.isEmpty()) {
                    result.append(stack.pop());
                }
                reverseWord = true;
                result.append(c); // StringBuilder에 현재 괄호 < 붙여서 괄호는 순서대로
            } else if (c == '>') {
            	reverseWord = false;
                result.append(c);
              //여기까지 태그 안에 글자 반대로 출력
            }else if (reverseWord) { // <> <>안에 단어 그대로 출력
                result.append(c);
            }
            // <> 없을때
            else {
                if (c == ' ') { // 공백만났을때 처리
                    while (!stack.isEmpty()) {
                        result.append(stack.pop());
                    }
                    result.append(' ');
                } else {
                    stack.push(c);
                }
            }
        }
        // 나머지 처리
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        System.out.println(result);
    }
}
