package week01_04.sjh1108.BOJ_10942;

import java.util.*;
import java.io.*;

// 백준 10942 - 팰린드롬? (DP)
class Main{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int N, M;
    static int[] arr;
    static boolean[][] dp; // dp[i][j]: i번 인덱스부터 j번 인덱스까지가 팰린드롬인지 여부

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine());

        arr = new int[N+1];
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // DP 테이블 채우기
        DP();
        
        M = Integer.parseInt(br.readLine());
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());
            // 문제의 S, E는 1-based index이므로 0-based로 변환
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

        // 1. 길이가 1인 경우와 2인 경우 초기화
        for(int i = 0; i < N; i++){
            dp[i][i] = true; // 길이 1: 무조건 팰린드롬

            // 길이 2: 두 수가 같으면 팰린드롬
            // i+1 < N 조건을 추가하여 배열 범위 초과 방지 권장 (현재 arr 크기가 N+1이라 에러는 안남)
            if(i < N - 1 && arr[i] == arr[i+1]){
                dp[i][i+1] = true;
            }
        }
        
        // 2. 길이가 3 이상인 경우 (i는 간격/길이-1)
        // 바텀업 방식으로 간격(i)을 늘려가며 확인
        for(int i = 1; i < N; i++){
            for(int j = 0; j <= N-i; j++){
                // j: 시작 인덱스, j+i: 끝 인덱스
                // 시작값(arr[j])과 끝값(arr[j+i])이 같고, 
                // 그 사이(dp[j+1][j+i-1])가 팰린드롬이라면 전체도 팰린드롬
                if(arr[j] == arr[j+i] && dp[j+1][j+i-1]){
                    dp[j][j+i] = true;
                }
            }
        }
    }
}