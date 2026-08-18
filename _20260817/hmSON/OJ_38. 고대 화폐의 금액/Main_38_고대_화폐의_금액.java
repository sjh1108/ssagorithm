import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DP, Knapsack
     * 각 거푸집을 무한으로 사용할 수 있고 이를 이용해 1 ~ S 사이의 가치를 가진 조합의 수와 그 합을 구해야 함
     * 단, 동일 금액인 조합이 여러 개라면 1번으로 취급(중복 허용하지 않음)
     *
     * 일단 조합의 수는 중요하지 않고, 동전들의 합으로 특정 값을 만들 수 있는지만 중요하므로, boolean 타입 배열로도 DP 가능
     * 고려해야 하는 경우는 다음과 같음.
     * 1. 이미 다른 동전을 이용해 해당 값만큼의 조합을 만들 수 있는 경우
     * 2. 현재 다루는 거푸집만 써서 만들 수 있는 경우, 즉 배수인 경우
     * 3. 다른 조합에 현재 동전을 원하는 만큼 추가해서 만들 수 있는 경우
     * 세 조건 중 하나라도 충족하면 특정 값을 구성할 수 있게 됨
     *
     * 현재 동전의 가치가 v일 때 이를 점화식으로 작성하면
     * dp[x] = dp[x] || x % v == 0 || dp[x-v]
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int max = Integer.parseInt(st.nextToken());

        // 같은 가치의 조합은 중복을 허용하지 않으므로 boolean 배열로도 해결할 수 있음
        boolean[] dp = new boolean[max+1];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            int val = Integer.parseInt(st.nextToken());

            for(int j=val; j<=max; j++) {
                // A or B or C. 세 조건 중 하나라도 충족하면 됨
                dp[j] = dp[j] || j % val == 0 || dp[j-val];
            }
        }

        // 조합의 수와 그 합을 모두 요구함
        // 합은 문제 조건 상 int 범위 초과할 수 있음
        int cnt = 0;
        long totalVal = 0L;
        for(int i=1; i<=max; i++) {
            if(dp[i]) {
                cnt++;
                totalVal += i;
            }
        }

        System.out.println(cnt + " " + totalVal);
    }

}