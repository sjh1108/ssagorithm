package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_1018_체스판다시칠하기 {


	static int n, m;
	static char[][] map;
	static int[] dr = { -1, 1, 0, 0 };
	static int[] dc = { 0, 0, -1, 1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		map = new char[n][m];

		for (int i = 0; i < n; i++) {
			String s = br.readLine();
			for (int j = 0; j < m; j++) {
				map[i][j] = s.charAt(j);
			}
		}
		
		int answer = Integer.MAX_VALUE;
		

		for (int i = 0; i <= n - 8; i++) {
			for (int j = 0; j <= m - 8; j++) {
				int count = check(i,j);
				if(count < answer) {
					answer = count;
				}
			}
		}
		
		System.out.println(answer);

	}

	static int check(int startR, int startC) {
		int checkedB = 0;
		int checkedW = 0;
		
		for(int r=0; r<8; r++) {
			for(int c=0; c<8; c++) {
				char cur = map[startR+r][startC+c];
				
				if((r+c)%2 == 0 ) { // 짝수면 시작 색과 같음
					if(cur != 'W') {
						checkedW++;
					}
					if(cur != 'B') {
						checkedB++;
					}
				}else { // 홀수면 시작색과 다름
					if(cur != 'W') checkedB++;
					if(cur != 'B') checkedW++;
				}
				
			}
		}
		
		return Math.min(checkedW, checkedB);
		
		
	}


}
