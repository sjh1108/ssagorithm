package algorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BOJ10773_제로 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int K = Integer.parseInt(br.readLine());
		Stack<Integer> stack = new Stack<>();
		for(int i=0; i<K; i++) {
			int N = Integer.parseInt(br.readLine());
			if(N==0) {
				stack.pop();
				continue;
			}
			stack.push(N);
		}
		int sum =0;
//		System.out.println(stack.size());
//		sum+=stack.pop();
//		System.out.println("sum" +sum);
////		System.out.println(stack.pop());
//		System.out.println(sum +=stack.pop());
		int size = stack.size();
		for(int i=0; i<size; i++) {
			sum +=stack.pop();
//			System.out.println(sum);
		}
		System.out.println(sum);
		
		
		
		

	}

}
