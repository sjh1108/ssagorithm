package week01_02.thdwngjs.BOJ_1231;

import java.util.*;
import java.io.*;

// 백준 1231 - 주식왕 동호 (DP, 배낭 문제)
class Main{
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken()); // 주식의 종목 수
        int D = Integer.parseInt(st.nextToken()); // 기간 (일 수)
        int M = Integer.parseInt(st.nextToken()); // 초기 자금

        // stock[i][j]: i번째 주식의 j번째 날 가격
        int[][] stock = new int[C+1][D+1];

        for(int i = 1; i <= C; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= D; j++) stock[i][j] = Integer.parseInt(st.nextToken());
        }

        // DP 배열: dp[k]는 현재 자금 k원으로 얻을 수 있는 '최대 수익'
        // 자금이 늘어날 수 있으므로 넉넉하게 잡거나 문제의 최대 자금 한도를 고려해야 함
        // (문제 조건 상 최대 자금이 500,000을 넘지 않는다고 가정된 코드입니다)
        int[] dp = new int[500_001];

        // 매일매일의 변동을 통해 수익을 누적해 나감 (그리디 + DP)
        // j-1일에 사서 j일에 파는 행위를 반복한다고 가정
        for(int j = 2; j <= D; j++){
            // 매일 DP 테이블 초기화 (새로운 날의 수익률로 계산해야 하므로)
            Arrays.fill(dp, 0);

            // 모든 주식 종목에 대해 반복 (배낭의 물건 종류)
            for(int i = 1; i <= C; i++){
                int prev = stock[i][j-1]; // 어제 가격 (비용/무게)
                int benefit = stock[i][j] - prev; // 오늘 팔았을 때 차익 (가치)

                // 수익이 나는 주식인 경우에만 고려 (benefit > 0)
                // (코드 상으로는 benefit이 음수면 dp가 갱신되지 않으므로 조건문 없어도 무방)
                
                // 배낭 문제 (Unbounded Knapsack)
                // 주식은 여러 주 살 수 있으므로 정방향 순회
                for(int k = prev; k <= M; k++){
                    // dp[k]: 예산 k로 얻을 수 있는 최대 이익
                    // dp[k-prev] + benefit: (현재 주식을 하나 샀을 때의 이익)
                    dp[k] = Math.max(dp[k], dp[k-prev] + benefit);
                }
            }

            // 그 날 얻은 최대 수익을 현재 자금에 더함
            M += dp[M];
        }

        System.out.println(M);
    }
}