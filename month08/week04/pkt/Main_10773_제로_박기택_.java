package month08.week04.pkt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main_10773_제로_박기택_ {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		
		Stack<Integer> stack = new Stack<Integer>();
		
		int N = Integer.parseInt(br.readLine());
		
		
		for (int i =0; i < N; i++) {
			int input = Integer.parseInt(br.readLine());
			
//			// input문제. 조건을 확실히 걸어주기. 차라리 else를 넣어야 함. 
//			if (input == 0) {
//				stack.pop();
//			} 
//			// 여기에 else를 쓰거나.
//			stack.push(input);
			
			
			if(input != 0) {
				stack.push(input);
			} else if (input == 0) {
				stack.pop();
			}
			
		}
		
		int sum = 0;
		while(!stack.isEmpty()) {
			sum += stack.pop();
		}
		
		System.out.println(sum);
		
	}

}
