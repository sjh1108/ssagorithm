package algo2025_09_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution_3499_퍼펙트셔플 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int T, N, mid;
	static String[] cards, temp;
	static String[] c1, c2;
	public static void main(String[] args) throws NumberFormatException, IOException {
		T = Integer.parseInt(input.readLine());
		for (int tc = 0; tc < T; tc++) {
			N = Integer.parseInt(input.readLine());
			cards = new String[N];
			temp = new String[N];
			tokens = new StringTokenizer(input.readLine());
			for (int i = 0; i < N; i++) {
				cards[i] = tokens.nextToken();
			}
			mid = (N + 1) / 2;
			c1 = new String[mid];
			c2 = new String[mid];
			if(N % 2 == 0) {
				for (int i = 0; i < N; i++) {
					if(i < mid) {
						c1[i] = cards[i];
					} else {
						c2[i-mid] = cards[i];
					}
				}
			}else if(N % 2 != 0) {
				for (int i = 0; i < N; i++) {
					if(i < mid) {
						c1[i] = cards[i];
					} else {
						c2[i-mid] = cards[i];
					}
				}
			}
			System.out.println(Arrays.toString(c1)+" "+Arrays.toString(c2));
			System.out.print("#"+(tc+1)+" ");
			for (int i = 0; i < mid; i++) {
				System.out.print(c1[i]+" ");
				if (i < c2.length) {
					if(c2[i] == null) break;
					System.out.print(c2[i]+" ");
				}
			}
			System.out.println();

		}// for
		
   }// Main
}//class
