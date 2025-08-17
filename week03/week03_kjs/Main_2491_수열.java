package ssagorithm.week03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Boj2491_수열 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine().trim()); // 원소 N개 받기
		int[] arr = new int[N]; // 해당 원소 만큼 배열 생성

		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken()); // 입력 받고 배열에 배치
		}

		if (N == 1) {
			System.out.println(1);
			return;
		}

		int inc = 1; // 증가 체크용
		int dec = 1; // 감소 체크용
		int temp = 1; // 저장용

		for (int i = 1; i < N; i++) {
			// 증가 체크
			if (arr[i - 1] <= arr[i])
				inc++;
			else
				inc = 1;

			// 감소 체크 
			if (arr[i - 1] >= arr[i])
				dec++;
			else
				dec = 1;

			// 최대 값 찾기
			temp = Math.max(temp, Math.max(inc, dec));
		}

		System.out.println(temp);
	}
}
