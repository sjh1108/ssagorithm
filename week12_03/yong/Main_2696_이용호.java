package algostudy.baek.week12_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/*
 * 홀수번째 수 읽을때 마다 중앙값 출력
 * 중앙값: 내림차순 pq, 오름차순 pq 하나씩 두고
 * max top이 중앙값이 되도록 유지 (max)3 2 1 | (min) 4 5 6
 */
public class Main_2696_이용호 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int tc = 1; tc <= T; tc++) {
			int M = Integer.parseInt(br.readLine());
			PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
			PriorityQueue<Integer> min = new PriorityQueue<>();
			// 출력 총 개수
			int ansCnt = (M+1)/2;
			sb.append(ansCnt + "\n");
			// 출력에 추가한 개수
			int cnt = 0;
			
			// 한줄에 열개씩 입력 받기
			while(M > 0) {
				int rest = 0;
				if(M > 10) {
					rest = 10;
				}
				else {
					rest = M % 10;
				}
				M -= 10;
//				System.out.println("rest : "+rest);
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int i = 1; i <= rest; i++) {
					// max size >= min size 유지
					int newNum = Integer.parseInt(st.nextToken());
					
					// 새 값이 max top 보다 크면 min으로
					if(!max.isEmpty() && newNum > max.peek()) {
						min.add(newNum);
					}
					// max가 비었거나, max top 보다 작으면 max로
					else {
						max.add(newNum);
					}
					
					// 균형 유지 해주기
					// min 개수가 더 많으면 하나 빼서 max로
					if(min.size() > max.size()) {
						max.add(min.poll());
					}
					// max가 두개 더 많으면 하나 빼서 min에 주기
					else if(max.size() > min.size() + 1) {
						min.add(max.poll());
					}
					if(i % 2 == 1) {
						sb.append(max.peek() + " ");
						cnt++;
						if(cnt % 10 == 0 || cnt == ansCnt) {sb.append("\n");}
					}	
				}
			}
		}
		System.out.println(sb);

	}

}
