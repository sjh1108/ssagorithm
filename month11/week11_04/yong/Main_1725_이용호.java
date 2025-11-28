package algostudy.baek.week11_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Stack;

public class Main_1725_이용호 {
/*
 * N : 1 ~ 100000
 * 높이는 최대 10억 -> N으로 탐색해야함
 * 히스토그램 높아지면 계속 진행, 이후 낮아지면 그전 것들 최대 높이 계산
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] heights = new int[N+2]; 
		
		int ans = 0;
		for(int i = 1; i <= N; i++) { // 1based
			int h = Integer.parseInt(br.readLine());
			heights[i] = h;
		}
		heights[N+1] = 0;
		
		ArrayDeque<Integer> dq = new ArrayDeque<>();
		dq.addLast(0);
		for(int i = 1; i <= N + 1; i++) {
			// 큐가 비었거나 
			// 큐에 넣으려는 값이 큐앞부분 히스토그램 보다 작으면 first ~ i까지 최대 직사각형 계산
			while(!dq.isEmpty() && heights[dq.peekLast()] > heights[i]) {
				int last = dq.pollLast();
				ans = Math.max(ans, heights[last] * (i - dq.peekLast() - 1));
				/*
				 * 회인이가 말했던 4 2 4의 경우 
				 * 마지막 i = 4, 높이 0에 의해 처리될때 스택에는 0, 2(높이2), 3(높이4)이있고, i는 4인 상태
				 * 3 -> 4 (이건 안다고 했었고)
				 * 2 -> i ~ peekLast - 1까지니까 4 - 0 -1 = 3(밑변) * 높이(2) 해서 6 나옴  
				 */
			}
			dq.addLast(i);
		}
		System.out.println(ans);

	}

}
