package algorithm.d0825;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_10828_스택 {
	
	static Stack<Integer> stack = new Stack<>();
	
	private static void push(int x) {
		stack.add(x);
	}
	
	private static void pop() {
		if(stack.isEmpty()) {
			System.out.println("-1");
		}else {
			System.out.println(stack.pop());
		}
	}
	
	private static void size() {
		System.out.println(stack.size());
	}
	
	private static void empty() {
		if(stack.isEmpty()) {
			System.out.println("1");
		}else {
			System.out.println("0");
		}
	}
	
	private static void top() {
		if(stack.isEmpty()) {
			System.out.println("-1");
		}else {
			System.out.println(stack.peek());
		}
	}
	

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		for(int i=0; i<N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String cmd = st.nextToken();	
			if(cmd.equals("push")) {
				int num = Integer.parseInt(st.nextToken());
				push(num);
			}else if (cmd.equals("pop")) {
				pop();
			}else if (cmd.equals("size")) {
				size();
			}else if (cmd.equals("empty")) {
				empty();
			}else if (cmd.equals("top")) {
				top();
			}
		}
	}
}
