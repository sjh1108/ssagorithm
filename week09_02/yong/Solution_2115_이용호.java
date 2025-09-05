package swea;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_2115_이용호 {
	static int[][] map;
	static int[][] dp;
	static boolean[] used;
	static int N, M, C;
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++) {
			StringTokenizer  st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			
			map = new int[N][N];
			
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0; j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			dp = new int[N][N-M+1];
			for(int i = 0; i < N; i ++) {
				for(int j = 0; j <= N-M; j++) {
					
					dp[i][j] = partprofit(i,j); //i,j ~ i,j+M 까지 부분집합중 C가 넘지 않으면서 합이 가장큰 것
				}
			}
			
			int maxhoney = work();
			System.out.println("#" + tc + " "+ maxhoney);
			
		}
	}
	//구간 M에서 최대 이익 반환
	static int partprofit(int x, int y) {
		int[] honey = new int[M]; // (x, y)~(x, y+M)까지 꿀
		for(int j = 0; j < M; j++) {
			honey[j] = map[x][y+j];
		}
		return subset(honey,0,0,0);
	}
	//구간 M에서 C를 넘지 않는 최대 부분집합 찾기
	static int subset(int[] honey,int idx, int sum, int profit) {
		if(sum > C) return 0; //용량 초과 -> res = 0
		if(idx == honey.length) return profit; 
		
		int res1 = subset(honey, idx+1, sum, profit); // 선택 x
		int res2 = subset(honey, idx+1, sum + honey[idx], profit + honey[idx] * honey[idx]);
		
		return Math.max(res1, res2);
	}
	
	static int work() {
		int max = 0;
		
		for(int i1 = 0; i1 < N; i1++) {
			for(int j1 = 0; j1 <= N - M; j1++) {
				for(int i2 = 0; i2 < N; i2++) {
					for(int j2 = 0; j2 <= N-M; j2++) {
						if(i1 == i2 && !(j1+M <= j2)) continue; //같은 행일때 겹치면 패스
						
						int sum = dp[i1][j1] + dp[i2][j2];
						max = Math.max(max, sum);
					}
				}
			}
		}
		return max;
	}
	
}
