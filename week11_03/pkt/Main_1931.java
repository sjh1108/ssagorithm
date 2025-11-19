package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1931 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int[][] arr = new int[N][2];

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken()); 
			arr[i][1] = Integer.parseInt(st.nextToken()); 

		}

		Arrays.sort(arr, (a,b) -> {
			if(a[1] == b[1]) return a[0] - b[0]; // 끝나는 시간 같으면 시작시간
			return a[1] - b[1]; // 끝나는 기준 오름차순
		});

//		for (int i = 0; i < N; i++) {
//				System.out.print(arr[i][0] +" "+ arr[i][1]);
//				System.out.println();
//		}
		
		
		// 끝나는 시간이 빠른 회의부터 보면서 이전에 잡은 회의가 끝난 이후에 시작할 수 있으면 계속 채텍
		
		int end = 0;
		int cnt = 0;
		for (int i = 0; i < N; i++) {
			int start = arr[i][0];
			int finish = arr[i][1];
			
			if(start >= end) { // 이전에 잡은 회의가 끝난 이후에 시작할 수 있다면
				cnt++;
				end = finish; // 채택
			}
		}
		
		System.out.println(cnt);

	}
}
