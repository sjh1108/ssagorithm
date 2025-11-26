package week;// 실4

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

/**
 * 문제 이해
 * - 괄호가 잘 열리고 닫힌 것을 판단해서 YES/NO 대답
 * 
 * 전략
 * - 스택
 *
 * 후기
 * - 왜이렇게 헷갈리게 풀어지는 거지
 * - 어떤 기준을 잡고 조건문을 써야할지 혼란스럽다
 */
public class Main_9012_괄호 {

	static int T;
	static ArrayDeque<Character> stack;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		T = Integer.parseInt(br.readLine());
		
		
		a: for(int tc = 1; tc <= T; tc++) {
			stack = new ArrayDeque<>();
			
			String input = br.readLine();
			char[] arr = input.toCharArray();
			
			for(char c : arr) {
				if(c == '(') {
					stack.push(c);
				}else if(c == ')'){ // 헷갈려서 if넣음
					if(stack.size() == 0) {
						sb.append("NO").append("\n");
						continue a;
					}
					stack.pop();
					
				}
			}
			
			if(stack.size() == 0) {
				sb.append("YES").append("\n");	
			}else {
							
				sb.append("NO").append("\n");
			}

		}
		
		System.out.println(sb.toString());
	}
}
