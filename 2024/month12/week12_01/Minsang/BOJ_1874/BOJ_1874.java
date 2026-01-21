package week12_01.Minsang.BOJ_1874;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class BOJ_1874 {
	// 구해야하는 것 : push -> + , pop -> -
	static int[] A;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력할 수열 개수
		int n = Integer.parseInt(br.readLine());		
		A = new int[n];
		
		// 배열 형태로 수열 입력받기
		for(int i = 0; i < n; i++) {
			A[i] = Integer.parseInt(br.readLine());
		}
		
		// 오름차순이니까
		int num = 1;

		Stack<Integer> stack = new Stack<>();
		StringBuilder sb = new StringBuilder();

		// 수열 개수까지 su 하나씩 넣고 비교
		for(int i = 0; i < A.length; i++) {
			int su = A[i];
			
			// 비교할 수가 num보다 크면 숫자를 계속 넣어줘야해요
			while(num <= su) {		
				stack.push(num++);
				sb.append("+\n");
			}
			
			// 비교할 수가 같으면 빼야하고
			if(stack.peek() == su) {
				stack.pop();
				sb.append("-\n");
			} else {
				// 불가능한 경우 NO 출력
				System.out.println("NO");
				return;
			}
		}
		System.out.println(sb);

	}

}
 {
    
}
