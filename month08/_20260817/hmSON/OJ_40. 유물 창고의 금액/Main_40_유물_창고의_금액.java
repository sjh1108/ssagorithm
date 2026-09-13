import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DP, Knapsack, 이진 분할
     * 동일 가치 조합의 중복 제거 조건은 동일
     * 각 동전 별 개수가 정해져 있으며 동일한 가치의 동전이 여러 번 주어질 수 있음
     *
     * 각 동전의 개수가 최대 10^9개까지 주어질 수 있으므로 낱개로 분리하는 행위는 비효율적
     * 이 수를 최대한 압축하기 위해 이진 분할로 해결
     * 각 동전의 개수를 {1, 2, 4, ..., 나머지 값} 의 묶음으로 분류
     * 각 동전 묶음 x 동전 가치를 새 동전의 가치라고 판단하고 DP 수행
     *
     * 점화식 : dp[i] = dp[i] || dp[i - b*v]; (b: 묶음, v: 가치)
     *
     * 입력으로 주어지는 동전 개수를 전부 활용할 필요는 없음
     * 만약 동전 가치 * 개수가 만들어야 할 최대 가치인 max보다 크면 쓸모 없는 동전이 존재함
     * 필요없는 만큼 각 동전의 카운트 차감
     */

    static int max;
    static boolean[] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        max = Integer.parseInt(st.nextToken());

        // 동일 가치 조합은 중복되어도 1번으로 취급
        // boolean 타입 배열로도 처리 가능
        dp = new boolean[max+1];
        dp[0] = true;

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            int val = Integer.parseInt(st.nextToken());
            int cnt = Integer.parseInt(st.nextToken());

            // 현재 동전을 모두 사용했을 때의 가치가 max보다 높다면 쓸데없는 동전을 덜어내야 함
            if(cnt > max / val) cnt = max / val;

            // 이진 분할
            // 2의 제곱수 단위로 동전을 묶고 더 이상 묶을 수 없을 때 남은 값도 하나의 묶음으로 취급
            // 각 묶음의 동전 수 x 동전의 가치 결과값을 하나의 새 동전이라고 취급하고 DP 점화식에 적용
            int p = 1;
            while(p <= cnt) {
                applyDP(p * val);
                cnt -= p;
                p <<= 1;
            }

            if(cnt > 0) applyDP(cnt * val);
        }

        int totalCnt = 0;
        long totalVal = 0L;
        for(int i=1; i<=max; i++) {
            if(dp[i]) {
                totalCnt++;
                totalVal += i;
            }
        }

        System.out.println(totalCnt + " " + totalVal);
    }

    // DP 점화식 메서드
    static void applyDP(int v) {
        for(int j=max; j>=v; j--) {
            dp[j] = dp[j] || dp[j-v];
        }
    }

}