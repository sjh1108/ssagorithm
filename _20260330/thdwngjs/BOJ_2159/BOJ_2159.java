package _20260330.thdwngjs.BOJ_2159;

import java.io.*;
import java.util.*;

class Main {
    static final int DIR_COUNT = 5;
    static final int[] DX = {0, 0, 1, 0, -1};
    static final int[] DY = {0, 1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] pos = new int[n + 1][2];
        long[][] dp = new long[n + 1][DIR_COUNT];

        for (int i = 0; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pos[i][0] = Integer.parseInt(st.nextToken());
            pos[i][1] = Integer.parseInt(st.nextToken());
            Arrays.fill(dp[i], Long.MAX_VALUE);
        }

        int startX = pos[0][0];
        int startY = pos[0][1];

        for (int d = 0; d < DIR_COUNT; d++) {
            dp[1][d] = Math.abs(pos[1][0] + DX[d] - startX)
                     + Math.abs(pos[1][1] + DY[d] - startY);
        }

        for (int i = 2; i <= n; i++) {
            for (int cur = 0; cur < DIR_COUNT; cur++) {
                for (int prev = 0; prev < DIR_COUNT; prev++) {
                    long dist = Math.abs((pos[i][0] + DX[cur]) - (pos[i - 1][0] + DX[prev]))
                              + Math.abs((pos[i][1] + DY[cur]) - (pos[i - 1][1] + DY[prev]));
                    dp[i][cur] = Math.min(dp[i][cur], dp[i - 1][prev] + dist);
                }
            }
        }

        long answer = Long.MAX_VALUE;
        for (int d = 0; d < DIR_COUNT; d++) {
            answer = Math.min(answer, dp[n][d]);
        }
        System.out.println(answer);
    }
}