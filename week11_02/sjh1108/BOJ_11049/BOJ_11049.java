package week11_02.sjh1108.BOJ_11049;

import java.util.*;
import java.io.*;

class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        int[][] map = new int[N+1][2];
        int[][] dp = new int[N+1][N+1];
        
        StringTokenizer st;
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());

            map[i][0] = Integer.parseInt(st.nextToken());
            map[i][1] = Integer.parseInt(st.nextToken());
        }

        for(int i = 1; i < N; i++){
            for(int j = 1; j <= N - i; j++){
                int k = i + j;
                dp[j][k] = Integer.MAX_VALUE;

                for(int l = j; l < k; l++){
                    dp[j][k] = Math.min(dp[j][k], dp[j][l] + dp[l + 1][k] + map[j][0] * map[l][1] * map[k][1]);
                }
            }
        }

        System.out.println(dp[1][N]);
    }
}