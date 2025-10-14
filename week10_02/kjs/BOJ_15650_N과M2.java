package algorithm.d1012;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class BOJ_15650_N과M2 {

	static int n, m;
	static int[] numbers;
	static boolean[ ] isSelected;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); 
		m = Integer.parseInt(st.nextToken()); 	
		numbers = new int[m];
		isSelected = new boolean[n+1];
		
		combination(0,1);
	}
	
	private static void combination(int cnt, int start) {
		if(cnt==m) {
			for(int num :numbers) {
				System.out.print(num+ " ");
			}
			System.out.println();
			return;
		}
		
		for(int i=start; i<=n; i++) {
			numbers[cnt]=i;
			combination(cnt+1, i+1);		
		}
		
	}

}
