package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_1337_이용호 {

	/*
	 * 백준 1337번: 올바른 배열
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		
		for(int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		
		Arrays.sort(arr);
		int answer = 4; // 양쪽 포인터 차이는 최대 4
		
		for(int i = 0; i < N; i++) {
			int end = i;
			/* i 부터  연속될수 있는 최대 길이 계산
			 * 최대 길이 -> 인덱스 i ~ end까지 
			 * 인덱스 end 로직 : N미만, end의 수와 i의 수 차이는 4 미만이어야함(1, 2, 3, 4, 5) -> 5 - 1 = 4
			 */
			while(end < N && arr[end] - arr[i] <= 4) { //end 포인터 한칸씩 뒤로
				end++;
			}
			int len = end - i;
			answer = Math.min(answer, 5 - len);
		}
		
		System.out.println(answer);
	}

}
