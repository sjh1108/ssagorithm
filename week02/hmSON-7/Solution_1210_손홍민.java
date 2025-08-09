package d4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_1210_손홍민 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	static int y, x;
	static char[][] board = new char[100][100];
	
	public static void main(String[] args) throws Exception {
		for(int i=0; i<10; i++) {
			int t = Integer.parseInt(br.readLine());
			init(); solve(t);
		}
		System.out.println(sb);
	}
	
	public static void init() throws Exception {
		for(int i=0; i<100; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<100; j++) {
				char c = st.nextToken().charAt(0);
				board[i][j] = c;
				if(c == '2') {
					y = i; x = j;
				}
			}
		}
	}
	
	public static void solve(int idx) {
		sb.append("#").append(idx).append(" ");
		while(y > 0) {
			if(x > 0 && board[y][x-1] == '1') {
				while(x > 0 && board[y][x-1] == '1') {
					board[y][x] = '0';
					x--;
				}
			} else if(x < 99 && board[y][x+1] == '1') {
				while(x < 99 && board[y][x+1] == '1') {
					board[y][x] = '0';
					x++;
				}
			}
			y--;
		}
		
		sb.append(x).append("\n");
	}
}
