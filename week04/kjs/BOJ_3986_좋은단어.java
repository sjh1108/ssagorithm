package algorithm.d0826;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BOJ_3986_좋은단어_0826 {

	public static void main(String[] args) throws NumberFormatException, IOException {


		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int count = 0;
		for (int i = 0; i < N; i++) {
			Stack<Character> stack = new Stack<>();
			String row = br.readLine();
			for (int j = 0; j < row.length(); j++) {
//				System.out.println();
				char ch = row.charAt(j);
//				System.out.print("ch"+i+ " "+ch +" ");

				if (!stack.isEmpty() && stack.peek() == ch) {
					stack.pop();
				} else {
					stack.push(ch);
				}

			}
			if (stack.isEmpty()) {
				count++;
			}
		}
		System.out.println(count);
	}
}
