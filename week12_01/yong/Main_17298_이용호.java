package baek_week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_17298_이용호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Stack<Integer> s = new Stack<>();
		int[] nums = new int[N];
		int[] ans = new int[N];
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N;i++) {
			nums[i] = Integer.parseInt(st.nextToken());
			
		}
		
		for(int i = nums.length - 1; i >= 0; i--) {
			// 현재수가 오큰수가 되니까 작은수는 이후에 나올일 없음
			// 현재 수보다 작은 수들 스택에서 빼기
			while(!s.isEmpty() && s.peek() <= nums[i]) {
				s.pop();
			}
			// 스택에 아무것도 없으면 -1 있으면 peek
			if(s.isEmpty()) {
				ans[i] = -1;
			}else {
				ans[i] = s.peek();
			}
			s.push(nums[i]);
			
		}
		for(int i = 0; i < N; i++) {
			sb.append(ans[i] + " ");
		}
		System.out.println(sb);

	}

}
