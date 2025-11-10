package week11_02.sjh1108.BOJ_11049;

import java.util.*;
import java.io.*;

// 백준 11049 - 행렬 곱셈 순서 (DP)
class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 행렬의 개수 N
        int N = Integer.parseInt(br.readLine());
        
        // map[i][0]: i번째 행렬의 행(row) 크기
        // map[i][1]: i번째 행렬의 열(column) 크기
        int[][] map = new int[N+1][2];
        
        // dp[i][j]: i번째 행렬부터 j번째 행렬까지 곱하는 데 필요한 '최소 연산 횟수'
        // dp[i][i]는 0 (자기 자신은 곱셈 횟수 0)
        int[][] dp = new int[N+1][N+1];
        
        StringTokenizer st;
        // 1번 행렬부터 N번 행렬까지의 크기 입력 (1-based index)
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());

            map[i][0] = Integer.parseInt(st.nextToken()); // r (행)
            map[i][1] = Integer.parseInt(st.nextToken()); // c (열)
        }

        // [핵심 DP 로직]
        // i: 계산할 행렬 체인의 '길이' (간격)
        // i=1: 2개씩 묶어서 계산 (예: (1,2), (2,3), ...)
        // i=N-1: N개 묶어서 계산 (예: (1,N))
        for(int i = 1; i < N; i++){
            
            // j: 행렬 체인의 '시작' 인덱스
            // k: 행렬 체인의 '끝' 인덱스 (k = j + i)
            for(int j = 1; j <= N - i; j++){
                int k = i + j; // 끝 인덱스 계산
                
                // dp[j][k] (j부터 k까지의 최소 비용)을 "무한대"로 초기화
                dp[j][k] = Integer.MAX_VALUE;

                // l: (j...k) 체인을 두 그룹으로 나누는 '분할 지점'
                // (j...l) 그룹과 (l+1...k) 그룹으로 나눔
                for(int l = j; l < k; l++){
                    
                    // dp[j][k] = min(
                    //      현재까지의 dp[j][k] 값,
                    //      (j~l)까지의 최소 비용 + (l+1~k)까지의 최소 비용
                    //      + (j~l 결과 행렬)과 (l+1~k 결과 행렬)을 곱하는 비용
                    // )
                    
                    // (j...l)의 결과 행렬 크기: map[j][0] x map[l][1]
                    // (l+1...k)의 결과 행렬 크기: map[l+1][0] x map[k][1]
                    // (행렬 곱셈 조건상 map[l][1] == map[l+1][0] 이어야 함)
                    
                    // 두 그룹을 곱하는 최종 비용: (앞 그룹 행) * (앞 그룹 열) * (뒷 그룹 열)
                    // = map[j][0] * map[l][1] * map[k][1]
                    
                    dp[j][k] = Math.min(dp[j][k], dp[j][l] + dp[l + 1][k] + map[j][0] * map[l][1] * map[k][1]);
                }
            }
        }

        // 1번 행렬부터 N번 행렬까지 곱하는 데 필요한 총 최소 연산 횟수
        System.out.println(dp[1][N]);
    }
}