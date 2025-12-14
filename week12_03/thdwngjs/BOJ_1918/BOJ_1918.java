package week12_03.thdwngjs.BOJ_1918;

import java.io.*;
import java.util.*;

// 백준 1918 - 후위 표기식 (Stack)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder(); // 결과 문자열(후위 표기식)을 저장할 빌더

        String input = br.readLine(); // 중위 표기식 입력

        // 연산자를 저장할 스택
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);

            switch (cur){
                case '+':
                case '-':
                case '*':
                case '/':
                    // 현재 연산자(cur)보다 우선순위가 높거나 같은 연산자가 스택 top에 있다면
                    // 그 연산자들을 먼저 수행(출력)해야 하므로 pop하여 결과에 추가
                    while (!stack.isEmpty() && priority(stack.peek()) >= priority(cur)) {
                        sb.append(stack.pop());
                    }
                    // 현재 연산자를 스택에 push
                    stack.push(cur);
                    break;
                case '(':
                    // 여는 괄호는 무조건 push (우선순위가 가장 낮게 취급되어 나중에 닫는 괄호 나올 때까지 유지됨)
                    stack.push(cur);
                    break;
                case ')':
                    // 닫는 괄호가 나오면, 여는 괄호 '('가 나올 때까지 스택의 연산자들을 pop하여 출력
                    while(!stack.isEmpty() && stack.peek() != '('){
                        sb.append(stack.pop());
                    }
                    stack.pop(); // 여는 괄호 '(' 제거
                    break;
                default:
                    // 피연산자(알파벳)는 바로 출력
                    sb.append(cur);
            }
        }

        // 스택에 남은 모든 연산자를 pop하여 출력
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        System.out.println(sb);
    }

    // 연산자 우선순위 반환 함수
    public static int priority(char op){
        if(op == '(' || op == ')'){
            return 0; // 스택 안에서는 가장 낮은 우선순위 (꺼내지지 않도록)
        } else if (op == '+' || op == '-') {
            return 1;
        } else if (op == '*' || op == '/') {
            return 2;
        }
        return -1;
    }
}