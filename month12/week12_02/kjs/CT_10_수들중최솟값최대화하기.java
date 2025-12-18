package codeTree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class CT_10_수들중최솟값최대화하기 {
	
	static int n;
	static int[][] map;
	static boolean[] used;
	
	static int[] colPick;
	static int answer=0;

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		n = Integer.parseInt(br.readLine());
		
		map = new int[n][n];
		used = new boolean[n];
		colPick = new int[n];
		
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
		
        

	}
	
	
	static void dfs(int row) {
		
		if(row == n) {
			int minValue = Integer.MAX_VALUE;
			
			for(int i=0; i<n; i++) {
				int c = colPick[i];
				minValue = Math.min(minValue, map[i][c]);
			}
			
			answer = Math.max(answer, minValue);
			return;
		}
		
		for(int c = 0; c<n; c++) {
			if(used[c]) continue;
			
			used[c] = true;
			
			colPick[row] = c;
			
			dfs(row+1);
			
			used[c] = false;
		}
		
	}
	

}
