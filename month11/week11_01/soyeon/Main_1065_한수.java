package week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - 양의 정수 X
 * - 각 자리가 등차수열을 이룬다면 -> 한수
 * - 1~N 사이의 값중에 한수의 갯를 출력하시오
 * 
 * 전략
 * - 일단 전부해봅시다
 * - 총 4자리의 배열을 만들어서 한자리씩 등차수열인지 확인
 * - 배열에 왼쪽 공백은 배제 (0)
 * 
 * 입력
 * - N (1000 이하 자연수)
 * 
 * 출력
 * - 한수의 개수 
 *
 */
public class Main_1065_한수 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int cnt = 0;
		
		// 1~2 자리 숫자의 경우 1~N의 모든 숫자가 성립함
		if(N%10 == N || N%100 == N) {
			System.out.println(N);
			return;
		// 3자리 숫자 
		}else if(N%1000  == N) {
			cnt = 99; // 2자리 숫자까지의 모든 숫자 카운트
			
			// 100부터 N까지의 숫자 판별 
			for(int i = 100; i <= N; i++) {
				// 각 자리수로 숫자 나눔 
				int[] num = {i/100, i%100/10, i%10};
				
				// gap 비교
				int preGap = 0;
				for(int j = 0; j < 2; j++) {
					if(j == 0) {
						preGap = num[0] - num[1];
						continue;
					}
					
					int curGap = num[j] - num[j+1];
					if(preGap == curGap) {
						cnt++;
					}
				}
			}
		// 4자리 숫자
		}else if(N%10000 == N) {
			cnt = 144; // 3자리 숫자까지의 모든 숫자 카운트 // 예제가 1000넣으면 144래요
			
			// 1000부터 N까지의 숫자 판별 
			for(int i = 1000; i <= N; i++) {
				// 각 자리수로 숫자 나눔 
				int[] num = {i/1000, i%1000/100, i%100/10, i%10};
				
				// gap 비교
				int preGap = 0;
				for(int j = 0; j < 3; j++) {
					if(j == 0) {
						preGap = num[0] - num[1];
						continue;
					}
					
					int curGap = num[j] - num[j+1];
					if(preGap == curGap) {
						cnt++;
					}
				}
			}
		}
		
		System.out.println(cnt);
		
	}
}
