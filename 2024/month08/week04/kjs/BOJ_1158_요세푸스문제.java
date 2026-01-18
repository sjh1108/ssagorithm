package month08.week04.kjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class BOJ_1158_요세푸스문제 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		StringBuilder sb = new StringBuilder();
		
		Deque<Integer> dq = new ArrayDeque<>();
		
		for (int i = 1; i <=N; i++) {
			dq.add(i);
		}
		
		while(!dq.isEmpty()) {

			for(int i=0; i<K-1; i++) {
				dq.add(dq.pollFirst());
			}
			if(dq.size()==1) {
				sb.append(dq.pollFirst());
			}else {
				sb.append(dq.pollFirst()+", ");
			}
			if(dq.isEmpty()) break;
		}
		System.out.println("<"+sb+">");

	}

}
