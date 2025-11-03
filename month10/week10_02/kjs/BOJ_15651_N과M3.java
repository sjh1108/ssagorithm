package algorithm.d1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_15651_N과M3 {

	static int n,m;
	static int[] numbers;
	static StringBuilder sb; 

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		sb = new StringBuilder();
		n = Integer.parseInt(st.nextToken()); 
		m = Integer.parseInt(st.nextToken());
		
		numbers = new int[m];
		
		permutationR(0);
		System.out.println(sb);

	}
	
	private static void permutationR(int cnt) {
		
		if(cnt == m) {
			for(int num :numbers) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}
		
		for(int i=1; i<=n; i++) {
			numbers[cnt] = i;
			permutationR(cnt+1);
		}
	}

}
