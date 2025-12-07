package week12_02.Minsang.BOJ_10989;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class BOJ_10989 {
	static int N;
//	static int[] arr;
	// 메모리 초과 ---> arrays.sort
	// 이번엔 list -> collection.sort 로 안통하다니 ..
	// arrays.sort로 바꿔서 풀었더니 가능 !
	// 하지만,,, 나는 counting sort로도 풀어봐야지 ~

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(br.readLine());
		
		int[] count = new int[10001];
		
		for(int i = 0; i < N; i++) {
			count[Integer.parseInt(br.readLine())]++;
		}
		
		for(int i = 1; i <= 10000; i++) {
			for(int j = 0; j < count[i]; j++) {
				sb.append(i).append("\n");
			}
		}
		
		System.out.println(sb);

//		arr = new int[N];
//		
//		for(int i =0 ; i < N; i++) {
//			arr[i] = Integer.parseInt(br.readLine());
//		}
//		
//		Arrays.sort(arr);;
//		
//		for(int i = 0; i < N; i++) {
//			sb.append(arr[i]).append("\n");
//		}
//		
//		System.out.println(sb);
		
		
	}

}

