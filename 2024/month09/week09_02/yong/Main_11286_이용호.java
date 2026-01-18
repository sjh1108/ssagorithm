package baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

public class Main_11286_이용호 {
/*
 * 배열에 정수 x(x != 0)
 * 절대값 힙
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
	     // 람다 표현식으로 정렬 규칙을 지정하는 경우
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            int abs_a = Math.abs(a);
            int abs_b = Math.abs(b);
            if (abs_a != abs_b) {
                return abs_a - abs_b; // 절댓값이 작은 순으로
            } else {
                return a - b; // 절댓값이 같으면 더 작은 수로
            }
        });
		

		for(int i = 0; i < N; i++) {
			int x = Integer.parseInt(br.readLine());
			if(x != 0) {
				pq.offer(x);
			}
			else { //큐에서 뺄거 없으면 0 있으면 poll
				System.out.println(pq.peek() == null ? 0 : pq.poll());
			}
			
		}
		
	}

}
