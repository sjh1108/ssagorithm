package algo2025_09_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_12712_파리퇴치3 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int T, N, M;
	static int[][] map;
	
	//상하좌우
	static int[] drJ = {-1,1,0,0};
	static int[] dcJ = {0,0,-1,1};
	//좌상 우상 좌하 우하
	static int[] drD = {-1,-1,1,1};
	static int[] dcD = {-1,1,-1,1};
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		T = Integer.parseInt(input.readLine());
		for (int tc = 0; tc < T; tc++) {
			tokens = new StringTokenizer(input.readLine());
			N = Integer.parseInt(tokens.nextToken());
			M = Integer.parseInt(tokens.nextToken());
			map = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				tokens = new StringTokenizer(input.readLine());
				for (int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(tokens.nextToken());
				}
			}
			int res = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < N; j++) {
					int jikjin = dir(i,j,drJ,dcJ);
					int daegak = dir(i,j,drD,dcD);
					res = Math.max(res, Math.max(daegak, jikjin));
				}
			}
			
			System.out.println("#"+(tc+1)+" "+res);
		}
	}
	static int dir(int r, int c, int[]dr, int[]dc) {
		int sum = map[r][c];
		for (int i = 0; i < 4; i++) {
			for (int j = 1; j < M; j++) {
				int nr = r + dr[i] * j;
				int nc = c + dc[i] * j;
				if(!isIn(nr, nc)) {
					break;
				}
				sum += map[nr][nc];
			}
		}
		return sum;
	}
	static boolean isIn(int nr, int nc) {
		if(nr >= 0 && nr < N && nc >= 0 && nc < N) {
			return true;
		}else {
			return false;
		}
		
	}
}
