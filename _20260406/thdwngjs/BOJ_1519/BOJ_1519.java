/**
 * BOJ 1519 - 부분 문자열 뽑기 게임 (골드 2)
 *
 * [풀이] 게임 DP
 * 현재 수 n에서 자기 자신을 제외한 부분 문자열(0으로 시작하지 않음) 하나를 골라
 * n에서 그 값을 빼는 방식으로 턴을 진행하는 게임.
 * dp[n] = n에서 시작하는 현재 플레이어가 이기기 위해 선택해야 할 "최소 부분 문자열 값".
 *         0이면 이길 수 있는 수가 없는 패배 상태.
 *
 * 전이: 부분 문자열 tmp 중, dp[n - tmp] == 0 (상대가 패배 상태) 인 것들의 최솟값이 dp[n].
 * 한 자리 수(1~9)는 뽑을 수 있는 부분 문자열이 자기 자신뿐이라 항상 패배 → N < 10이면 -1.
 */
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 한 자리 수는 선택할 수 있는 진짜 부분 문자열이 없으므로 즉시 패배
        if(N < 10){
            System.out.println(-1);
            return;
        }

        int[] dp = new int[N + 1]; // dp[n]: n에서 시작하는 플레이어의 최소 승리 수 (0이면 패배)

        for (int n = 10; n <= N; n++) {
            String str = String.valueOf(n);
            Set<String> set = new HashSet<>(); // 중복 제거용 부분 문자열 집합

            // n의 모든 부분 문자열을 추출 (0으로 시작하는 것, 자기 자신은 제외)
            for (int start = 0; start < str.length(); start++) {
                if (str.charAt(start) == '0') {
                    continue;
                }

                String res = "";
                for (int i = start; i < str.length(); i++) {
                    res += str.charAt(i);

                    if (!res.equals(str)) {
                        set.add(res);
                    }
                }
            }

            Iterator<String> it = set.iterator();

            // 상대를 패배 상태(dp 값 0)로 만드는 tmp 중 최솟값 탐색
            int min = Integer.MAX_VALUE;
            while (it.hasNext()) {
                int num = Integer.parseInt(str);
                int tmp = Integer.parseInt(it.next());

                if (dp[num - tmp] == 0) {
                    min = Math.min(min, tmp);
                }
            }

            if (min != Integer.MAX_VALUE) {
                dp[n] = min; // 승리 상태: 최소 선택 수 기록
            }
        }

        // dp[N]이 0이면 승리 수 없음(-1), 아니면 첫 번째로 뽑을 최소 부분 문자열 값
        System.out.println(dp[N] == 0 ? -1 : dp[N]);
    }
}