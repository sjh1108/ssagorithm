package month08.week04.kjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_1874_스택수열_0826 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		Stack<Integer> stack = new Stack<>();
		int[] rArray = new int[n];
		for(int i=0; i<n; i++) {
			rArray[i] = Integer.parseInt(br.readLine());
		}
		int num=0;
		int sNum=0;
		
		for(int i=0; i<n; i++) {
			sNum=rArray[i];
			while(num < sNum) {
				stack.push(++num);
				sb.append("+\n");
			}
			if(stack.peek() != sNum) {
				System.out.println("NO");
				return;
			}
			stack.pop();
			sb.append("-\n");
		}
		System.out.print(sb);
			
	}
}
