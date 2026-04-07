package gold_2;

import java.io.*;
import java.util.*;

public class Main_1519_부분_문자열_뽑기_게임 {

    /*
     * BOJ_1519 : 부분 문자열 뽑기 게임 (Gold_2)
     * 자료구조 및 알고리즘 : DP, 게임 이론
     *
     * [문제 요약]
     * - 숫자 N이 주어졌을 때, 현재 숫자에서 "0이 아닌 부분 문자열 수"를 하나 골라 빼는 게임을 한다.
     * - 단, 자기 자신 전체를 그대로 빼는 경우는 허용되지 않는다.
     * - 더 이상 이동할 수 없는 사람이 패배한다.
     * - N에서 시작했을 때, 선공이 이길 수 있다면 이기게 만드는 부분 문자열 수 중 하나를,
     *   이길 수 없다면 -1을 출력한다.
     *
     * [핵심 아이디어]
     * - 작은 수부터 차례대로 "이 수가 이기는 상태인지 / 지는 상태인지"를 판별하는 DP 문제다.
     * - 어떤 수 i에서 만들 수 있는 다음 상태(i - target)들 중
     *   하나라도 "지는 상태"로 보낼 수 있으면 현재 i는 이기는 상태다.
     * - 반대로 가능한 모든 다음 상태가 이기는 상태라면 현재 i는 지는 상태(킬러 넘버)다.
     * - 이 코드에서는
     *   - dp[i] == INF : i가 지는 상태
     *   - dp[i] != INF : i가 이기는 상태이며, 그때 선택할 수 있는 부분 문자열 수 중 하나
     *   로 해석할 수 있다.
     *
     * [구현 메모]
     * - 한 자리 수는 자기 자신 전체를 빼는 것밖에 안 되므로 시작부터 지는 상태다.
     *   그래서 dp는 INF로 채워 두고, 실제 탐색은 i=10부터 시작한다.
     * - 각 i에 대해,
     *   숫자의 각 접미 부분 문자열 값을 target으로 만들어 보며 가능한 수를 확인한다.
     * - target이 0이면 뺄 수 없으므로 제외.
     * - target == i 이면 전체 숫자를 그대로 빼는 경우이므로 제외.
     * - target >= dp[i] 조건은 이미 더 작은 답을 찾은 경우 불필요한 갱신을 막기 위한 가지치기다.
     * - 핵심 판정:
     *   - dp[i - target] == INF 이면, 상대를 지는 상태로 보내는 수가 존재한다는 뜻
     *   - 따라서 현재 i는 이길 수 있고, 그 수로 target을 기록한다.
     *
     * [시간 복잡도]
     * - 각 i마다 자릿수 기준으로 가능한 부분 문자열을 확인한다.
     * - 전체적으로 O(N * (자릿수)^2) 정도로 볼 수 있다.
     */

    static final int INF = 9_999_999;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] dp = new int[n+1];
        Arrays.fill(dp, INF);

        for(int i=10; i<=n; i++) {
            int num = i;
            while(num > 0) {
                int p = 10;
                while(true) {
                    int target = num % p;

                    // 자기 자신 전체를 빼는 경우는 제외하고,
                    // 이미 더 작은 정답을 찾았다면 그보다 큰 target도 볼 필요가 없다.
                    if(target == i || target >= dp[i]) break;

                    // 0은 부분 문자열 수로 선택할 수 없음
                    if(target == 0) {
                        p *= 10; continue;
                    }

                    // i-target이 지는 상태라면 현재 i는 이기는 상태
                    if(dp[i - target] == INF) dp[i] = target;
                    p *= 10;

                    // 현재 num 전체를 이미 본 경우 종료
                    if(target == num) break;
                }

                num /= 10;
            }
        }

        System.out.println(dp[n] == INF ? -1 : dp[n]);
    }

}