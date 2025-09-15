package week0911;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2805 {
	static int N;
	static int M;
	static int[] arr;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		arr = new int[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		// !-- 이분탐색으로 잘린 거 합산해 나아가보자. --
		
		// 최솟값과 최댓값 설정하기.
		int low = 0;
		int high = 0;
		
		for (int h : arr) {
			high = Math.max(high, h);
		}
		
		
		int ans = 0;
		
		while(low <= high) {
			int mid = (low + high) / 2;
			
			long sum = 0;
			for (int h : arr) {
				if(h > mid) {
					sum += (h - mid); // mid보다 큰 나무들만 잘려서 양수
				}
			}

			if(sum >= M) {
				ans = mid;
				low = mid + 1;
			} else {
				high = mid - 1;			
			}		
		}
		System.out.println(ans);
	}
}
