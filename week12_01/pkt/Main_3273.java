package week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_3273 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int N = Integer.parseInt(br.readLine());
		
		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(arr); // 투포인터에서는 정렬 필수
		
		int target = Integer.parseInt(br.readLine());
		
		int left = 0;
		int right = N-1;
		int cnt = 0;
		
		while(left < right) {
			int sum = arr[left] + arr[right]; // 합을 구해야 하는데,
			
			if(sum < target) { // 목표값보다 작으면 합을 키워줘야 하니까, 왼쪽값을 늘리고, 
				left++;
			}
			
			else if(sum > target) { // 목표값보다 크면 합을 줄여줘야 하니까, 오른쪽 값을 줄이고, 
				right--;
			}
			
			else { // 목표값 일치하면 둘다 옮기고, 카운트 한 번 해주기. 
				left++;
				right--;
				cnt++;
			}
			
		}
		
		System.out.println(cnt);
		
	}
}
