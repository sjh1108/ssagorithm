package week01_02.thdwngjs.BOJ_1231;

import java.util.*;
import java.io.*;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken());
        int D = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] stock = new int[C+1][D+1];

        for(int i = 1; i <= C; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 1; j <= D; j++) stock[i][j] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[500_001];
        for(int j = 2; j <= D; j++){
            Arrays.fill(dp, 0);

            for(int i = 1; i <= C; i++){
                int prev = stock[i][j-1];
                int benefit = stock[i][j] - prev;

                for(int k = prev; k <= M; k++){
                    dp[k] = Math.max(dp[k], dp[k-prev] + benefit);
                }
            }

            M += dp[M];
        }

        System.out.println(M);
    }
}