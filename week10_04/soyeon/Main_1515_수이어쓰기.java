package bk.silver; //2

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 후기
 * - 포인터를 이용하는 문제풀이 자체를 떠올리지 못함
 * - 아직도 이런 발상이 익숙하지 않아 비슷한 문제를 풀어봐야할 것 같음
 * - String을 분해하거나 int를 String으로 만들어서 각 자리를 비교하는 방법을 배움
 * 
 */
public class Main_1515_수이어쓰기 {
	

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String input = br.readLine();
		
		int inputIdx = 0; // 포인터
		int num = 0; // 1~N까지 증가 
		
		while(true) {
			num++;
			// 현재 num의 값을 각 자리수로 분해
			String cur = String.valueOf(num);
			// 각 자리수와 input에서 포인터가 가리키는 곳을 비교
			for(int i = 0; i < cur.length(); i++) {
				if(cur.charAt(i) == input.charAt(inputIdx)) {
					// num이 해당 숫자의 원본임을 알았으니 포인터 옮김 
					inputIdx++;
					
					// 종료조건
					// inputIdx가 input을 벗어나면 종료 
					if(inputIdx == input.length()) {
						System.out.println(num);
						return;
					}
				}
			}
		}
	}
}