package baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_11866_이용호 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		Queue<Integer> q = new LinkedList<>();
		for(int i = 1; i <= N; i++) {
			q.add(i);
		}
		
		sb.append("<");
		while(!q.isEmpty()) {
			for(int i = 1; i < K; i++) { //K번째 전까지 앞에 수를 뒤에 추가
				int front = q.poll();
				q.add(front);
			}
			if(q.size() == 1) {
				sb.append(q.poll()).append(">");
			}
			else {
				sb.append(q.poll()).append(", ");
			}		
		}
		System.out.println(sb);
	}
	

}
