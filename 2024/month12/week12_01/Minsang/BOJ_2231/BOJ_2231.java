package week12_01.Minsang.BOJ_2231;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2231 {
	static int N, M;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 자연수 M의 분해합이 N
		N = Integer.parseInt(br.readLine());
		
		int answer = 0;
		
		// N까지의 숫자까지 다 돌아봅니다.
		for(int i = 1; i <= N; i++) {
			int sum = i;
			int tmp = i;
			
			// 숫자 쪼갤건데 0보다 커야하고
			while(tmp > 0) {
				// 숫자 하나씩 쪼개기
				sum += tmp%10;
				tmp /= 10;
			}
			
			// sum == N -> answer = i;
			if(sum == N) {
				answer = i;
				break;
			}
		}
		
		System.out.println(answer);
	}

}
 {
    
}
