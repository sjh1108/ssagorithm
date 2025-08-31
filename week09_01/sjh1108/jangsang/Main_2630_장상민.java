package algo2025_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2630_장상민 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N;
    static int[][] A;
    static int white = 0, blue = 0;

    static void cut(int r, int c, int size) {
        if (isUniform(r, c, size)) {
            if (A[r][c] == 0) white++;
            else blue++;
            return;
        }
        int half = size / 2;
        cut(r, c, half); //좌상단
        cut(r, c + half, half); //우상단
        cut(r + half, c, half); //좌하단
        cut(r + half, c + half, half); //우하단
    }

    static boolean isUniform(int r, int c, int size) {
        int color = A[r][c]; //좌상단 기준으로 색선정
        for (int i = r; i < r + size; i++) {
            for (int j = c; j < c + size; j++) {
                if (A[i][j] != color) return false;
            }
        }
        return true;
    }
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(br.readLine().trim());
        A = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) A[i][j] = Integer.parseInt(st.nextToken());
        }
        
        cut(0, 0, N);
        
        StringBuilder sb = new StringBuilder();
        sb.append(white).append('\n').append(blue);
        System.out.print(sb.toString());
    }
}