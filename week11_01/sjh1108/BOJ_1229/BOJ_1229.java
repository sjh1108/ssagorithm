package week11_01.sjh1108.BOJ_1229;

import java.io.*;
import java.util.*;

// 백준 1229 - 육각수 (DP, 라그랑주 네 제곱수 정리와 유사)
class Main {
    // 이 상수는 DP 테이블을 초기화할 "무한대" 값으로 사용됩니다.
    // (문제의 답은 최대 6(육각수 정리)이므로 7 이상의 값이면 충분합니다)
    // 변수 이름이 N의 최대값(MAX_N)처럼 보이지만, 실제로는 INF(무한대) 값입니다.
    private static final int MAX_N = 146858;
    
    // six_angle[i] = i번째 육각수 (H_i = i * (2*i - 1))
    // (이 배열은 매우 크게 선언되었지만, 실제로는 H_i <= N 인 범위까지만 사용됨)
    private static int[] six_angle = new int[MAX_N + 1];
    
    // dp[i] = 숫자 i를 만드는 데 필요한 육각수의 "최소 개수"
    private static int[] dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 목표 숫자 N (N <= 1,000,000)
        int N = Integer.parseInt(br.readLine());
        
        // 육각수들을 미리 계산 (최대 N까지만 필요하지만, 이 코드는 MAX_N까지 계산)
        prefix();

        // DP 테이블(dp[0] ~ dp[N]) 초기화
        dp = new int[N + 1];
        // 모든 값을 "무한대" (MAX_N)로 채움
        Arrays.fill(dp, MAX_N);
        
        // --- DP Base Cases ---
        // 0을 만드는 데는 0개의 육각수가 필요
        dp[0] = 0;
        // 1을 만드는 데는 1개 (H_1 = 1)의 육각수가 필요
        dp[1] = 1;
        // ---------------------

        // DP 점화식 실행 (i = 2부터 N까지)
        for (int i = 2; i <= N; i++) {
            // 1번째 육각수(H_1)부터 순서대로 사용해봄
            for (int j = 1; j <= i; j++) {
                // j번째 육각수 H_j
                int current_hex = six_angle[j];
                
                // [최적화]
                // 만약 j번째 육각수(current_hex)가 현재 만들려는 수(i)보다 크다면,
                // 그 뒤의 (j+1)번째, (j+2)번째 육각수들은 더 크므로 볼 필요가 없음
                if (current_hex > i) break;
                
                // [핵심 점화식]
                // dp[i] = min(dp[i], dp[i - H_j] + 1)
                // (현재 i를 만드는 최소 개수) 와
                // ( (i - H_j)를 만드는 최소 개수 + 1 (H_j를 사용했으므로) ) 중
                // 더 작은 값으로 dp[i]를 갱신
                if (dp[i] > dp[i - current_hex] + 1) {
                    dp[i] = dp[i - current_hex] + 1;
                }
            }
        }
        
        // N을 만드는 데 필요한 최소 개수 출력
        System.out.println(dp[N]);
    }

    /**
     * 육각수(H_i = i * (2*i - 1))를 계산하여 six_angle 배열에 채웁니다.
     * (육각수의 점화식: H_i = H_{i-1} + (4*i - 3))
     */
    private static void prefix() {
        // H_1 = 1
        six_angle[1] = 1;
        
        // H_2 부터 H_{MAX_N} 까지 계산
        // (참고: N <= 1,000,000 이므로 H_708(약 1,001,478) 까지만 계산해도 충분합니다.
        //  i가 약 46,341을 넘어가면 int 범위를 오버플로우하지만,
        //  N <= 1,000,000 범위에서는 그 전에 DP loop가 break 되므로 문제 없습니다.)
        for (int i = 2; i <= MAX_N; i++) {
            // H_i = H_{i-1} + (4*i - 3) 
            // 아래 식은 (i-1)*6 - (2*i - 3) = 6i - 6 - 2i + 3 = 4i - 3 으로 위 점화식과 동일
            six_angle[i] = (i - 1) * 6 + six_angle[i - 1] - (2 * (i - 1) - 1);
        }
    }
}