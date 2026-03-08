package _20260309.thdwngjs.BOJ_18430;

import java.io.*;
import java.util.*;

class Main {
    private static int N, M, res;

    private static int[][] map;
    private static boolean[][] visited;
    private static final int[][] BOOMERANGS = {
        {1, 0, 0, -1},   // down + left
        {-1, 0, 0, -1},  // up + left
        {-1, 0, 0, 1},   // up + right
        {1, 0, 0, 1}     // down + right
    };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];
        res = 0;

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0);
        
        System.out.println(res);
    }

    private static void dfs(int idx, int sum) {
        if (idx == N * M) {
            res = Math.max(res, sum);
            return;
        }

        int r = idx / M;
        int c = idx % M;

        if (!visited[r][c]) {
            visited[r][c] = true;
            for (int[] shape : BOOMERANGS) {
                int arm1R = r + shape[0];
                int arm1C = c + shape[1];
                int arm2R = r + shape[2];
                int arm2C = c + shape[3];

                if (!canPlace(arm1R, arm1C, arm2R, arm2C)) {
                    continue;
                }

                visited[arm1R][arm1C] = true;
                visited[arm2R][arm2C] = true;

                int gain = map[r][c] * 2 + map[arm1R][arm1C] + map[arm2R][arm2C];
                dfs(idx + 1, sum + gain);

                visited[arm1R][arm1C] = false;
                visited[arm2R][arm2C] = false;
            }
            visited[r][c] = false;
        }

        dfs(idx + 1, sum);
    }

    private static boolean canPlace(int arm1R, int arm1C, int arm2R, int arm2C) {
        return isIn(arm1R, arm1C)
                && isIn(arm2R, arm2C)
                && !visited[arm1R][arm1C]
                && !visited[arm2R][arm2C];
    }

    private static boolean isIn(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }
}
