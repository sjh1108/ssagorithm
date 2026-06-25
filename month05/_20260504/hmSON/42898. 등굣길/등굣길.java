import java.util.*;

class Solution {

    // 자료구조 및 알고리즘 : DP

    static final int MOD = 1_000_000_007;

    public int solution(int c, int r, int[][] sink) {
        int[][] dp = new int[r+1][c+1];
        // 초기 위치(1, 1)는 반드시 1
        dp[1][1] = 1;
        // 물에 잠긴 지역은 예외 처리를 위해 미리 -1로 초기화
        for(int i=0; i<sink.length; i++) {
            dp[sink[i][1]][sink[i][0]] = -1;
        }

        for(int i=1; i<=r; i++) {
            for(int j=1; j<=c; j++) {
                // 물에 잠긴 지역은 합산 없이 0으로 변경
                if(dp[i][j] == -1) {
                    dp[i][j] = 0; continue;
                }

                // 초기 위치도 계산에서 예외
                if(i == 1 && j == 1) continue;

                // 오른쪽과 아래로만 이동 가능하므로 위쪽, 왼쪽 지점까지의 경우의 수 합산
                // INT 범위 초과 방지를 위해 매 계산마다 MOD 적용
                dp[i][j] = (dp[i-1][j] + dp[i][j-1]) % MOD;
            }
        }

        return dp[r][c];
    }

}