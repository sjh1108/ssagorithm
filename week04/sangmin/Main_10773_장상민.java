package java_home_work.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_10773_장상민 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static int N;
	public static void main(String[] args) throws IOException{
		Stack<Integer> stack = new Stack<>();
		N = Integer.parseInt(input.readLine());
		
		for (int i = 0; i < N; i++) {
			int num = Integer.parseInt(input.readLine());
			if(num != 0) {
				stack.push(num);
			} else if (num == 0) {
				stack.pop();
			}
		}
		
		int res = 0;
		int size = stack.size();
		
		//이렇게 쓰면 pop하는순간 stack.size가 1로 줄어버려서 for문이 타지 않아
		//마지막 요소를 더해줄 수 없음
//		for (int i = 0; i < stack.size(); i++) {
//			res += stack.pop();
//		}
		for (int i = 0; i < size; i++) {
			res += stack.pop();
		}
		// 아래처럼 이렇게 쓰거나 stack.size()를 고정해놔야함
//		while(!stack.isEmpty()) {
//			res += stack.pop();
//		}
		System.out.println(res);
		
	}
}
