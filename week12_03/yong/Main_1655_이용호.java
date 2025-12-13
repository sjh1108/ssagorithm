package algostudy.baek.week12_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.PriorityQueue;

public class Main_1655_이용호 {
/*
 * left는 top보다 작은 값들을 저장
 * right은 top보다 큰값들을 저장
 * right 값의 개수보다 left 값의 개수를 많게 해준다 -> 중간값을 left top으로 고정
 * left나 right에 값 하나씩 추가해주고, left와 right 균형 맞춰준 후 peek로 중간값(left top값) 확인
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		// 3 2 1(내림 차순)
		PriorityQueue<Integer> left = new PriorityQueue<>(Collections.reverseOrder());
		// 4 5(오름 차순)
		PriorityQueue<Integer> right = new PriorityQueue<>();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < N; i++) {
			int now = Integer.parseInt(br.readLine());
			// left top 보다 크면 right으로
			if(!left.isEmpty() && left.peek() < now) {
				right.add(now);
			}
			else {
				left.add(now);
			}
			
			// right가 많아지면 하나 빼서 left에 추가
			if(right.size() > left.size()) {
				left.add(right.poll());
			}
			// left가 두개 더 많으면 left top을 하나 빼서 right에 추가
			else if(left.size() > right.size()+1) {
				right.add(left.poll());
			}
//			System.out.println("left: " + left.toString() + "| right: " + right.toString());
			sb.append(left.peek() + "\n");
		}
		System.out.println(sb);

	}

}
