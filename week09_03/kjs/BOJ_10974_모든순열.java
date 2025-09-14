package d0910;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class BOJ_10974_모든순열 {
	
	static int n;
	static int[] numbers;
	static boolean[] visited;
	static int count;
	static StringBuilder sb = new StringBuilder();
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine());
		
		numbers = new int[n]; // 원래는 r
		visited = new boolean[n+1]; 
		
		permutation(0);
		System.out.println(sb);
	}
	
	static void permutation(int cnt) {
		
		if(cnt == n) {
//			System.out.println(Arrays.toString(numbers));
//			완성된 배열 for문으로 sb에 넣기
			for(int i=0; i<n; i++) {
				sb.append(numbers[i]).append(' ');
			}
			sb.append('\n');
			return;
		}
		

		for(int i=1; i<=n; i++) {
			if(visited[i]) continue;
			numbers[cnt] = i; // numbers 배열에 cnt 위치에 i (넣어야할 값);
			visited[i] = true; // 그 값을 체크
			permutation(cnt+1);
			visited[i] = false;
			

		}
		
	}
	
}
