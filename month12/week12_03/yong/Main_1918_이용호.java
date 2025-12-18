package algostudy.baek.week12_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_1918_이용호 {
/*
 * 피연산자는 바로 스택에
 * 자신보다 우선순위가 낮은 연산자를 만날때까지 스택에 넣기
 * 자신보다 우선순위 높은 연산자 만나면 pop 
 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		Stack<Character> operator = new Stack<>();
		String line = br.readLine();
		StringBuilder sb = new StringBuilder();
		
		for(char now : line.toCharArray()) {
			if(now >= 'A' && now <= 'Z') {
				sb.append(now);
			}
			else if(now == '(') {
				operator.push(now);
			}
			// '(' 만나기 전까지 pop
			else if(now == ')') {
				while(!(operator.peek() == '(')) {
					sb.append(operator.pop());
				}
				// '(' pop
				operator.pop();
			}
			// 나머지 연산자들
			else {
				// 현재 연산자가 우선순위 더 낮으면 '('도 포함
				while(!operator.isEmpty() && priority(operator.peek()) >= priority(now)) {
					sb.append(operator.pop());
				}
				// 현재 연산자가 우선순위 더 높으면 push
				operator.push(now);
			}
			
		}
		while(!operator.isEmpty()) {
			sb.append(operator.pop());
		}
		System.out.println(sb);
			
	}
	// 연산자 우선순위 리턴
	public static int priority(char c) {
		if(c == '+' || c == '-') {
			return 1;
		}else if(c == '*' || c == '/') {
			return 2;
		}
		else return 0;
	}

}
