package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_14510_나무높이 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int T, N;
	static int[] tree;
	public static void main(String[] args) throws NumberFormatException, IOException {
		T = Integer.parseInt(input.readLine());
		
		for (int tc = 0; tc < T; tc++) {
			N = Integer.parseInt(input.readLine());
			tree = new int[N];
			
			tokens = new StringTokenizer(input.readLine());
			
			int max = 0;
			for (int i = 0; i < N; i++) {
				tree[i] = Integer.parseInt(tokens.nextToken());
				max = Math.max(max, tree[i]);
			}
			
			int twoDaysNeed = 0;
			int oneDaysNeed = 0;
			for (int i = 0; i < N; i++) {
				int diff = max - tree[i];
				twoDaysNeed += diff / 2;
				oneDaysNeed += diff % 2;
			}
			
			int day = 0;
			while(oneDaysNeed>0 || twoDaysNeed >0) {
				day++;
				if(day % 2 == 1) {
					if(oneDaysNeed > 0) {
						oneDaysNeed--;
					}else if(oneDaysNeed == 0 && twoDaysNeed > 1) {
						oneDaysNeed++;
						twoDaysNeed--;
					}
				} else {
					if(twoDaysNeed > 0) {
						twoDaysNeed--;
					}
				}
			}
			System.out.println("#"+(tc+1)+" "+day);
			
		}
	}
}
