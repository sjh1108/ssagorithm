package week; //실4

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * 문제 이해
 * - N개의 카드 (1~N)
 * - 1번카드가 제일 위.
 * 
 * 규칙
 * - 제일 위 카드를 바닥에 버림
 * - 제일 위에 있는 카드를 제일 아래에 있는 카드 밑으로 옮긴다 
 * 
 * 출력
 * - 마지막에 남게되는 카드구하기
 * 
 * 전략
 * - 큐 offer / poll
 * 
 *
 */
public class Main_2164_카드2 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N =  sc.nextInt();
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		for(int i = 1; i <= N; i++) {
			q.offer(i);
		}// 입력끝
		
		while(q.size() > 1) {
			
			int a = q.poll();
			
			if(q.size() == 1) {
				System.out.println(q.poll());
				return;
			}
			
			int next = q.poll();
			
			q.offer(next);
		}
		System.out.println(q.poll());
		
		
	}
}

