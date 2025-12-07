package week; // 실4

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

/**
 * ArrayDeque를 덱으로 써보는 건 처음이네요.
 * - 메서드가 뭐지? 
 * - 앞에 넣기 push
 * - 앞에 빼기 pop, poll -> pollFirst
 * - 뒤에 넣기 offer
 * - 뒤에 빼기 rear ?? -> pollLast
 *
 * 아주 헷갈림
 * 각 자료구조 메서드 바로바로 나오게  기억하자. 
 *
 */
public class Main_28279_덱2 {

	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int N = Integer.parseInt(br.readLine());
		
		ArrayDeque<Integer> deque = new ArrayDeque<Integer>();
		
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int order = Integer.parseInt(st.nextToken());
			
			switch (order) {
			case 1:
				int x = Integer.parseInt(st.nextToken());
				deque.push(x);
				break;
			case 2:
				int y = Integer.parseInt(st.nextToken());
				deque.offer(y);
				break;
			case 3:
				if(deque.size()>0) {
					int n = deque.pop();
					System.out.println(n);
				}else {
					System.out.println(-1);
				}
				break;
			case 4:
				if(deque.size()>0) {
					int n = deque.pollLast();
					System.out.println(n);
				}else {
					System.out.println(-1);
				}
				break;
			case 5:
				System.out.println(deque.size());
				break;
			case 6:
				if(deque.size() == 0) {
					System.out.println(1);
				}else {
					System.out.println(0);
				}
				break;
			case 7:
				if(deque.size() > 0) {
					System.out.println(deque.peekFirst());
				}else {
					System.out.println(-1);
				}
				break;
			case 8:
				if(deque.size() > 0) {
					System.out.println(deque.peekLast());
				}else {
					System.out.println(-1);
				}
				break;
			default:
				break;
			}
			
		}
	}
}
