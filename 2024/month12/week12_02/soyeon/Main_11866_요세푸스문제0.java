package week; //실4

import java.util.ArrayDeque;
import java.util.Scanner;

/**
 * 문제이해 
 * - 원형으로 연결된 사람들의 +K번째를 순서대로 제거 해나감  
 * - 큐로 계속 뱅뱅 돌려주면될듯 
 * - offer/poll
 */
public class Main_11866_요세푸스문제0 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		StringBuilder sb = new StringBuilder();
		int N = sc.nextInt(); // 사람수 
		int K = sc.nextInt(); // 양의정수,<=N
		
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		
		for(int i = 1; i <= N; i++) {
			q.offer(i);
		}// 입력 끝
		
		sb.append("<");
		while(q.size() > 1) {
			for(int i = 1; i <= K; i ++) {
				int num = q.poll();
				if(i != K) {
					q.offer(num);
				}else {
					sb.append(num).append(", ");
				}
			}
		}
		sb.append(q.poll() + ">");
		
		System.out.println(sb.toString());
	}
}
