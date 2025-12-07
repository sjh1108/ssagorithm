package week12_02.Minsang.BOJ_2839;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class BOJ_2839 {
	static int N;
	// 구하고자 하는 것 : 배달하는 봉지의 최소 개수 출력
	// 그리디 라네요 .. 하하
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		N = Integer.parseInt(br.readLine());
		
		int count = 0;
		
		while(N >= 0) {
			
			if(N % 5 == 0) {
				count += N/5;
				System.out.println(count);
				return;
				
			} else {
				N -= 3;
				count++;
			}
			
			if(N < 0) {
				System.out.println(-1);
			}
			
		}
		
	}

}
