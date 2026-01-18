package week01_04.sjh1108.BOJ_10942;

import java.util.*;
import java.io.*;

class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int N, M;
    static int[] arr;
    static boolean[][] dp;

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());

        arr = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        DP();
        
        M = Integer.parseInt(br.readLine());
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken())-1;
            int E = Integer.parseInt(st.nextToken())-1;

            if(dp[S][E]){
                sb.append(1).append("\n");
            }else{
                sb.append(0).append("\n");
            }
        }

        System.out.println(sb);
	}

    static void DP(){
        dp = new boolean[N+1][N+1];

        for(int i = 0; i < N; i++){
            dp[i][i] = true;

            if(i < N && arr[i] == arr[i+1]){
                dp[i][i+1] = true;
            }
        }
        
        for(int i = 1; i < N; i++){
            for(int j = 0; j <= N-i; j++){
                if(arr[j] == arr[j+i] && dp[j+1][j+i-1]){
                    dp[j][j+i] = true;
                }
            }
        }
    }
}