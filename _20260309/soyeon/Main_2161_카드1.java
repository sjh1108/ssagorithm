import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

/**
 * 문제 이해
 * - 1~N 번호가 붙은 카드
 * - 1번카드가 제일 위, N번 카드가 제일 아래 순
 * 
 * - 다음 동작을 한 장 남을 때까지 반복
 * 1. 제일 위에 있는 카드를 바닥에 버림
 * 2. 제일 위에 있는 카드를 제일 아래에 있는 카드 밑으로 옮김
 * 
 * - 버린 카드를 순서대로 출력 + 마지막 남게 되는 카드 출력
 * 
 * 전략
 * - 큐 쓰면 되는 거 아님?
 *
 */
public class Main_2161_카드1 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		StringBuilder sb = new StringBuilder();
		
		Queue<Integer> q = new ArrayDeque<Integer>();
		
		for(int i = 1; i <= N; i++) {
			q.add(i);
		}
		
		for(int i = 0; i < N; i++) {
			
			if(q.size() == 1) {
				sb.append(q.poll());
				break;
			}
			
			// 맨앞 빼기
			int n = q.poll();
			sb.append(n).append(" ");
			
			// 그 다음 맨 앞 빼고 다시넢음
			q.add(q.poll());
		}
		
		System.out.println(sb.toString());
		
	}
}
