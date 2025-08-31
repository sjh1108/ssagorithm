package algo2025_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1992_장상민 {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder out = new StringBuilder();
    static int N;
    static int[][] A;

    static void quad(int r, int c, int size) {
        if (isUniform(r, c, size)) {
            out.append(A[r][c]);
            return;
        }
        int half = size / 2;
        out.append('(');
        quad(r, c, half);                 // 좌상
        quad(r, c + half, half);          // 우상
        quad(r + half, c, half);          // 좌하
        quad(r + half, c + half, half);   // 우하
        out.append(')');
    }

    static boolean isUniform(int r, int c, int size) {
        int v = A[r][c];
        for (int i = r; i < r + size; i++) {
            for (int j = c; j < c + size; j++) {
                if (A[i][j] != v) return false;
            }
        }
        return true;
    }
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(br.readLine().trim());
        A = new int[N][N];
        
        for (int i = 0; i < N; i++) {
            String s = br.readLine().trim();
            for (int j = 0; j < N; j++) A[i][j] = s.charAt(j) - '0';
        }
        quad(0, 0, N);
        System.out.print(out.toString());
    }
}