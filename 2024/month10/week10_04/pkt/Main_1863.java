package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

// 스카이라인_1863
public class Main_1863 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());

		// 왼쪽에서 스카이라인을 보아 갈 때 스카이라인의 고도가 바뀌는 지점의 x,y좌표
		Stack<Integer> stack = new Stack<>();

		// 윤곽선의 개수
		int count = 0;

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());


			// 스택이 비어있지 않고, 현재 높이가 스택의 top보다 낮다면, 스카이라인이 꺾이는 지점이다.
			while(!stack.isEmpty() && stack.peek() > y) {
				stack.pop(); // 내려가니 빼줌. 
				count++;
			}

			// 스택이 비어있거나
			// 높이가 이전보다 높을때만 넣어줌. 
			// 같은 높이는 push할 필요없다.
			// 끝 선만 세면 됨. 
			if(stack.isEmpty() || stack.peek() < y) {
				stack.push(y);
			}

		}


		// 모든 입력이 끝난 뒤, 남은 높이들은 스카이라인 조각으로 계산
		while(!stack.isEmpty()) {
			if(stack.pop() > 0) count++;
		}

		System.out.println(count);
	}
}


