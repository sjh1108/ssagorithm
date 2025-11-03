package week10_03.sjh1108.BOJ_4883;

import java.io.*;
import java.util.*;

class Main {
    private static int min(int... data){
        int minimum = data[0];
        for (int i = 1; i < data.length; i++) {
            if (data[i] < minimum) {
                minimum = data[i];
            }
        }
        return minimum;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int idx = 0;
        StringBuilder sb = new StringBuilder();
        
        while(true){
            int N = Integer.parseInt(br.readLine());
            if (N == 0) {
                break;
            }

            int[][] map = new int[N][3];
            for(int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                map[i][0] = Integer.parseInt(st.nextToken());
                map[i][1] = Integer.parseInt(st.nextToken());
                map[i][2] = Integer.parseInt(st.nextToken());
            }

            int[][] dp = new int[N][3];

            dp[0][1] = map[0][1];
            dp[0][0] = 1_000_000_000; 
            dp[0][2] = map[0][1] + map[0][2];

            for(int i = 1; i < N; i++){
                dp[i][0] = map[i][0] + min(dp[i-1][0], dp[i-1][1]);
                dp[i][1] = map[i][1] + min(dp[i-1][0], dp[i-1][1], dp[i-1][2], dp[i][0]);
                dp[i][2] = map[i][2] + min(dp[i-1][1], dp[i-1][2], dp[i][1]);
            }
            
            sb.append(++idx).append(". ").append(dp[N - 1][1]).append("\n");
        }
        System.out.print(sb);
    }
}