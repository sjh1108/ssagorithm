package week12_01.Minsang.BOJ_11729;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_11729 {
	static int count = 0;
	static StringBuilder sb = new StringBuilder(); 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int K = Integer.parseInt(br.readLine());
		
		// 하노이 원리
		// 장대 3개가 있을때 -- (1, 2, 3)
		// 원판이 2개일때를 가정해보자
		// 1번 : N-1, start, mid, end
		// 2번 : N , start -> end
		// 3번 : N-1, mid, end, start
		
		hanoi(K, 1, 3, 2);
		
		System.out.println(count);
		System.out.println(sb.toString());
		
	}
	
	static void hanoi(int K, int start, int end, int mid) {
		
		if(K == 0) {
			return;
		}
		
		hanoi(K-1, start, mid, end);
		count++;
		sb.append(start).append(" ").append(end).append("\n");
		
		
		hanoi(K-1, mid, end, start);
		
	}

}

