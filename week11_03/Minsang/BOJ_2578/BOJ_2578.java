package week11_03.Minsang.BOJ_2578;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BOJ_2578 {
	static int[][] board;
	static int num; // 사회자가 부르는 수 카운트
	static int cnt; // 빙고 줄

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 빙고 판 초기화
		board = new int[5][5];
		
		// 빙고 칸 수에 따라 숫자 입력받기
		for(int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			
			for(int j = 0; j < 5; j++) {
				board[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 사회자가 부르는 수
		for(int i = 0; i < 5; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < 5; j++) {
				num++;
				int call = Integer.parseInt(st.nextToken());
				
				// 부르는 수에 따른 빙고판 체크
				makeBoard(call);
				
				// 3빙고 -> return 
				int cnt = bingoCheck();
				if(cnt >= 3) {
					System.out.println(num);
					return;
				}
			}
		}
		
	}

	// 사회자가 부른 수 -> -1로 변환 (x 체크)
	private static void makeBoard(int call) {
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 5; j++) {
				if(board[i][j] == call) {
					board[i][j] = -1;
					return;
				}
			}
		}
		
	}

	// 1 빙고 만들기
	private static int bingoCheck() {
		cnt = 0;

		// col 행
		for(int i = 0; i < 5; i++) {
			int col = 0;
			for(int j = 0; j < 5; j++) {
				if(board[i][j] == -1) 
					col++;
				}
				if(col == 5) 
					cnt++;
		}
		
		// row 행
		for(int j = 0; j < 5; j++) {
			int row = 0;
			for(int i = 0; i < 5; i++) {
				if(board[i][j] == -1) 
					row++;
				}
				if(row == 5)
					cnt++;
		}
		
		// xy 행
		int xy = 0;
		for(int i = 0; i < 5; i++) {
			if(board[i][i] == -1)
					xy++;
			}
				if(xy == 5)
					cnt++;
		
		// yx 행
		int yx = 0;
		for(int i = 0; i < 5; i++) {
			if(board[i][4-i] == -1) 
					yx++;
			}
				if(yx == 5) 
					cnt++;
		return cnt;
	}
}