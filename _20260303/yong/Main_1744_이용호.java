package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_1744_이용호 {
/*
 * 수 묶기
 * 길이 N 수열의 합(두 수를 묶어서 곱해줄수 있음)
 * 음수는 가장작은 순서대로 묶기
 * 양수는 가장 큰 순서대로 묶기
 * 
 * 음수 개수가 홀수고 0이 있으면 가장 큰 음수는 0과 곱한 합을 더하기
 * 음수 개수 홀수인데 0 없으면 그냥 더하기
 * 양수는 1이 있을경우 1은 따로 더해줘야함(1과 다른양수 곱하면 최댓값이 아니게 됨)
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		int negative = 0;
		boolean zero = false;
		int result = 0;
		for(int i = 0; i < N; i++) {
			// 0 있는지 체크 + 음수 몇개인지 체크
			arr[i] = Integer.parseInt(br.readLine());
			if(arr[i] < 0) negative++;
			if(arr[i] == 0) zero = true;
		}
		Arrays.sort(arr);
		
		// 음수 있으면서 짝수개 일때
		if(negative > 0 && negative % 2 == 0) {
			for(int i = 0; i < negative; i += 2) {
				result += arr[i] * arr[i+1];
			}
		}
		// 음수가 있으면서 홀수개 일때
		else if(negative > 0 && negative % 2 == 1) {
			// 0이 있으면 
			if(zero) { 
				for(int i = 0; i < negative-1; i += 2) { // negative-1: 제일 큰 음수는 계산 안함
					result += arr[i] * arr[i+1];
				}
			}
			// 0이 없으면
			else {
				for(int i = 0; i < negative-1; i += 2) {
					result += arr[i] * arr[i+1];
				}
				result += arr[negative-1]; // 제일 큰 음수 합산
			}
		}
		// 양수
		for(int i = arr.length - 1; i >= negative; i-= 2) {
			// 앞 인덱스가 -1 or 앞 수가 0 or 앞 수가 음수
			if(i - 1 == -1 || arr[i-1] <= 0) {
				result += arr[i];
			}
			// 앞 수가 자연수면 서로 곱한 후 더함
			else {
				if(arr[i-1] == 1) { // 앞 수가 1이면 1더하기 처리
					result++;
				}
				result += arr[i] * arr[i-1];
			}
			
		}
		System.out.println(result);
	}

}
