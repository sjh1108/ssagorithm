import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DP, Knapsack
     * 각 동전을 1회씩 사용할 수 있고 이를 조합하여 1 ~ S 사이의 가치를 가진 조합의 수와 그 합을 구해야 함
     * 단, 동일 금액인 조합이 여러 개라면 1번으로 취급(중복 허용하지 않음)
     *
     * 이전 문제와 동일하게 동일 금액이 조합에 대해서는 중복을 허용하지 않으므로 boolean 타입 배열로도 DP 가능
     * 고려해야 하는 경우는 다음과 같음.
     * 1. 이미 다른 동전을 이용해 해당 값만큼의 조합을 만들 수 있는 경우
     * 2. 다른 조합에 현재 동전을 추가해서 만들 수 있는 경우
     * 3. 현재 동전만 사용하는 경우
     * 세 조건 중 하나라도 충족하면 특정 값을 구성할 수 있게 됨
     *
     * 단, 3번의 경우 2번 조건을 이용하면 점화식에 조건문을 추가할 필요가 없어짐
     * dp[0] = true로 초기화해두면 이후 2번 조건에 의해 "공집합에 현재 동전을 추가하는 경우"로 재정의할 수 있음
     * 이후 조합의 수 카운트 및 값의 합산 과정에서 i=0인 경우만 제외하면 결과에 이상 없음
     * 현재 동전의 가치가 v일 때 이를 점화식으로 작성하면
     * dp[x] = dp[x] || dp[x-v]
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int max = Integer.parseInt(st.nextToken());

        // 같은 가치의 조합은 중복을 허용하지 않으므로 boolean 배열로도 해결할 수 있음
        boolean[] dp = new boolean[max+1];
        // 공집합이므로 결과에서는 취급하지 않으나, 3번 조건을 처리를 위해 true로 초기화
        dp[0] = true;

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            int val = Integer.parseInt(st.nextToken());

            for(int j=max; j>=val; j--) {
                dp[j] = dp[j] || dp[j-val];
            }
        }

        // 조합의 수와 그 합을 모두 요구함
        // 합은 문제 조건 상 int 범위 초과할 수 있음
        // 가치 합이 1인 경우부터 확인하므로 i=0인 경우는 무시
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