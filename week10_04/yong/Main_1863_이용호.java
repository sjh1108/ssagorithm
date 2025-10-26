package algostudy.baek.week10_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;
/*
 * 백준 1863 : 스카이라인 쉬운거
 */
public class Main_1863_이용호 {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		Stack<Integer> s = new Stack<>();
		int BuildingCnt = 0;
		for(int i = 0; i < N; i ++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			
			// 높이 낮아지면 pop, count++
			while(!s.isEmpty() && s.peek() > y) {
				s.pop();
				BuildingCnt++;
			}
			
			// 더 높으면 push
			if(s.isEmpty() || s.peek() < y) {
				s.push(y);
			}
			
		}
		while(!s.isEmpty()) {
			int y = s.pop();
			if(y == 0) continue; // 0은 왜들어가는데;;
			BuildingCnt++;
		}
		System.out.println(BuildingCnt);

	}
}

