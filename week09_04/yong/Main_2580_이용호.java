package algostudy.baek.m9w4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2580_이용호 {
    static int[][] map = new int[9][9];
    static StringBuilder sb = new StringBuilder();
    static boolean solved = false;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        // 입력
        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        // 풀이 시작
        solve(0, 0);
        
    }

    static void solve(int row, int col) {
        if (col == 9) {
            solve(row + 1, 0);
            return;
        }
        if (row == 9) {
            // 다 채운 경우 출력
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sb.append(map[i][j]).append(" ");
                }
                sb.append("\n");
            }
            System.out.print(sb);
            solved = true;
            return;
        }
        
        if (map[row][col] == 0) {
        	// 들어갈수 있는 숫자 찾기
            for (int num = 1; num <= 9; num++) {
                if (isPossible(row, col, num)) {
                    map[row][col] = num;
                    solve(row, col + 1);
                    if (solved) return;
                    map[row][col] = 0; // 백트래킹(다시 빈칸으로)
                }
            }
        } else {
            solve(row, col + 1);
        }
    }

    static boolean isPossible(int row, int col, int num) {
        // 가로 검사
        for (int i = 0; i < 9; i++) {
            if (map[row][i] == num) return false;
        }
        // 세로 검사
        for (int i = 0; i < 9; i++) {
            if (map[i][col] == num) return false;
        }
        // 3x3 박스 검사
        // 012 = 0, 345 = 1, 678 = 2 -> 0,3,6
        int startRow = (row / 3) * 3; 
        int startCol = (col / 3) * 3;
        for (int i = startRow; i < startRow + 3; i++) {
            for (int j = startCol; j < startCol + 3; j++) {
                if (map[i][j] == num) return false;
            }
        }
        return true;
    }
}
