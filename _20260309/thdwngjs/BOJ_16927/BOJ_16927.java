package _20260309.thdwngjs.BOJ_16927;

import java.io.*;
import java.util.*;

class Main {
    private static int N, M, R;

    private static int[] dx = {0, 1, 0, -1};
    private static int[] dy = {1, 0, -1, 0};
    private static int[][] map;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++)
                map[i][j] = Integer.parseInt(st.nextToken());
        }

        int n = N, m = M;

        for(int i = 0; i < Math.min(M, N) / 2; i++){
            rotate(i, 2*n + 2*m - 4);
            n -= 2;
            m -= 2;
        }

        StringBuilder sb = new StringBuilder();
        for(int[] arr: map){
            for(int i: arr)
                sb.append(i + " ");
            sb.append('\n');
        }
        System.out.print(sb);
    }

    private static void rotate(int shell, int len){
        int cir = R % len;

        for(int t = 0; t < cir; t++){
            int first = map[shell][shell];
    		
    		int x = shell;
			int y = shell;
			
    		for(int idx = 0; idx < 4; ) {
    			int nx = x + dx[idx];
    			int ny = y + dy[idx];
    			
    			if(nx >= shell && ny >= shell && nx < N-shell && ny < M-shell) {
    				map[x][y] = map[nx][ny];
    				x = nx;
    				y = ny;
    			} else {
    				idx++;
    			}
    		}
    		map[shell+1][shell] = first;
        }
    }
}