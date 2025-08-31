package algorithm.d0829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_1992_쿼드트리 {
	
	static int[][] map;
	static StringBuilder sb =new StringBuilder();
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		int n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		for(int i=0; i<n; i++) {
			String st = br.readLine();
			for(int j=0; j<n; j++) {
				map[i][j] = st.charAt(j)-'0';
			}
		}
		
		divide(0,0,n);
		System.out.println(sb);
		
	}
	
	static boolean isSplit(int r, int c, int size) {
		int first = map[r][c];
		for(int i=r; i<r+size; i++) {
			for(int j=c; j<c+size; j++) {
				if(map[i][j]!=first) {
					return false;
				}
			}
		}
		return true;
		
	}
	
	static void divide(int r, int c, int size) {
		if(isSplit(r,c,size)) {
			if(map[r][c]==1) {
				sb.append("1");
			}else if(map[r][c]==0) {
				sb.append("0");
			}
		}else {
			sb.append("(");
			size = size/2;
			divide(r,c,size);
			divide(r,c+size,size);
			divide(r+size,c,size);
			divide(r+size,c+size,size);
			sb.append(")");
			}
	}
	

}
