package month08.week04.kjs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class BOJ_2161_카드1 {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine());
		Queue<Integer> que = new LinkedList<>();
		
		for(int i=1; i<=n; i++) {
			que.offer(i);

		}
		while(que.size()>=1) {
			if(que.size()>=2) {
				System.out.print(que.peek()+ " ");
				que.poll();
				que.offer(que.poll());
			}
			if(que.size()==1) {
				System.out.print(que.peek()+ " ");
				break;
			}
		}
		
	}

}
