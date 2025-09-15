package d0910;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class BOJ_15439_베라의패션 {
	
	static int n, r=2;
	static int [] numbers;
	static boolean[] selected;
	static int count=0;

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		
		// 장선이형 코드 슈킹 ~~~
		
		numbers = new int[r];
		selected = new boolean[n+1]; // 숫자 맞추기 위해

		permutation(0);
		System.out.println(count);
	}

	private static void permutation(int cnt) {
		
		if(cnt == r) {
			count++;
			return;
		}
		
		for(int i=1; i<=n; i++) {
			if(selected[i]) continue;
			numbers[cnt] = i;
			selected[i] = true;
			permutation(cnt+1);
			selected[i] = false; 
		}
		
	}
	
}
