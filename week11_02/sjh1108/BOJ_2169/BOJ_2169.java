package week11_02.sjh1108.BOJ_2169;

import java.io.*;
import java.util.*;

// 백준 2169 - 로봇 조종하기 (DP)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N: 행, M: 열
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        // map[i][j]: (i, j) 칸의 가치
        int[][] map = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // dp[i][j]: (i, j)에 도달했을 때의 '최대 가치'
        int[][] dp = new int[N][M];
        
        // --- 1. 첫 번째 행(0행) 초기화 ---
        dp[0][0] = map[0][0]; // 시작점 (0, 0)
        for(int i = 1; i < M; i++){
            // 0행은 위에서 내려올 수 없으므로, 무조건 왼쪽(i-1)에서 와야 함
            dp[0][i] = map[0][i] + dp[0][i-1];
        }

        // [핵심 로직]
        // 1행부터 N-1행까지 계산
        // 로봇은 (i, j)에서 왼쪽, 오른쪽, 아래로 이동 가능 (위로 X, 방문했던 곳 X)
        // (i-1)행 -> (i)행으로 이동은 '아래'로만 가능
        // (i)행에 도착한 후, 왼쪽 <-> 오른쪽으로 자유롭게 이동 가능 (단, 왔던 길 되돌아가기 X)
        
        // (i, j)에 도달하는 경로는 2가지로 나눌 수 있음
        // 1. (i-1)행 -> (i)행 -> ... -> (i, j-1) -> (i, j)  [왼쪽에서 오른쪽으로 옴]
        // 2. (i-1)행 -> (i)행 -> ... -> (i, j+1) -> (i, j)  [오른쪽에서 왼쪽으로 옴]
        // 이 두 가지를 따로 계산해야 함
        
        // temp[0][j]: 1번 경우 (왼쪽 -> 오른쪽) + (위 -> 아래)
        // temp[1][j]: 2번 경우 (오른쪽 -> 왼쪽) + (위 -> 아래)
        int[][] temp = new int[2][M];
        
        for(int i=1;i<N;i++){ // 1행부터 N-1행까지
            
            // --- 1-1. (왼쪽 -> 오른쪽) 또는 (위 -> 아래) 경우 계산 (temp[0]) ---
            // (i, 0) (맨 왼쪽 칸)은 왼쪽에서 올 수 없으므로, 무조건 위(i-1, 0)에서만 옴
            temp[0][0] = dp[i-1][0] + map[i][0];
            
            for(int j=1;j<M;j++){
                // (i, j)에 올 수 있는 경로:
                // 1) (i, j-1)에서 오른쪽으로 옴 (temp[0][j-1])
                // 2) (i-1, j)에서 아래쪽으로 옴 (dp[i-1][j])
                // 이 중 최대값 + 현재 칸 가치
                temp[0][j] = Math.max(temp[0][j-1], dp[i-1][j]) + map[i][j];
            }

            // --- 1-2. (오른쪽 -> 왼쪽) 또는 (위 -> 아래) 경우 계산 (temp[1]) ---
            // (i, M-1) (맨 오른쪽 칸)은 오른쪽에서 올 수 없으므로, 무조건 위(i-1, M-1)에서만 옴
            temp[1][M-1] = dp[i-1][M-1] + map[i][M-1];
            
            for(int j=M-2;j>=0;j--){ // 오른쪽에서 왼쪽으로 거꾸로 계산
                // (i, j)에 올 수 있는 경로:
                // 1) (i, j+1)에서 왼쪽으로 옴 (temp[1][j+1])
                // 2) (i-1, j)에서 아래쪽으로 옴 (dp[i-1][j])
                // 이 중 최대값 + 현재 칸 가치
                temp[1][j] = Math.max(temp[1][j+1],dp[i-1][j])+map[i][j];
            }

            // --- 2. 두 경우(temp[0], temp[1]) 중 최대값으로 dp[i]행 갱신 ---
            for(int j=0;j<M;j++){
                // (i, j)의 최종 최대 가치는
                // (왼쪽/위에서 온 최대 가치)와 (오른쪽/위에서 온 최대 가치) 중 더 큰 값
                dp[i][j] = Math.max(temp[0][j],temp[1][j]);
            }
        }
        
        // 최종 목적지 (N-1, M-1)의 최대 가치 출력
        System.out.println(dp[N-1][M-1]);
    }
}