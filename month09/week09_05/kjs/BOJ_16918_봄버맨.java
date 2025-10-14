package Test;

import java.io.*;
import java.util.*;

public class BOJ_16918_봄버맨 {
    static int R, C, N;
    static final int[] dr = { -1, 1, 0, 0 };
    static final int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());


        char[][] init = new char[R][C];
        for (int i = 0; i < R; i++) {
            init[i] = br.readLine().toCharArray();
        }
        if (N == 1) {
            print(init);
            return;
        }
        
        
        if (N % 2 == 0) {
            print(fillAllBombs());
            return;
        }


        
        char[][] once = bomb(init);
        if (N % 4 == 3) {
            print(once);
            return;
        }

        char[][] twice = bomb(once);
        print(twice);
    }


    
    static char[][] fillAllBombs() {
        char[][] full = new char[R][C];
        for (int i = 0; i < R; i++) {
            Arrays.fill(full[i], 'O');
        }
        return full;
    }
    
    
    
    
    static char[][] bomb(char[][] src) {
        char[][] next = fillAllBombs(); 
        boolean[][] clear = new boolean[R][C];

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (src[r][c] == 'O') {
                    clear[r][c] = true; // 자기 자신
                    for (int k = 0; k < 4; k++) {
                        int nr = r + dr[k], nc = c + dc[k];
                        if (0 <= nr && nr < R && 0 <= nc && nc < C) {
                            clear[nr][nc] = true; // 인접 칸
                        }
                    }
                }
            }
        }

        for (int r = 0; r < R; r++) {
            for (int c = 0; c < C; c++) {
                if (clear[r][c]) next[r][c] = '.';
            }
        }
        return next;
    }

    static void print(char[][] g) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) {
            sb.append(g[i]).append('\n');
        }
        System.out.print(sb);
    }
}
