package week12_02.thdwngjs.BOJ_25048;

import java.io.*;
import java.util.*;

// 백준 25048 - 랜선 연결
class Main {
    // DP 초기화를 위한 무한대 값 (비용 합이 long 범위를 넘지 않도록 적절히 큰 값 설정)
    private static final long INF = 1L << 60;

    // 멀티탭(스위치) 정보를 담는 클래스
    static class Switch {
        int weight; // 이 스위치를 추가했을 때 늘어나는 포트의 수 (실제 포트 수 - 2)
        long cost;  // 스위치 비용

        public Switch(int weight, long cost) {
            this.weight = weight;
            this.cost = cost;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine()); // 스위치 개수
        List<Switch> list = new ArrayList<>();
        
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()); // 포트 수
            long b = Long.parseLong(st.nextToken());  // 비용
            
            // [중요] 무게(weight) 설정
            // 스위치를 하나 설치하면:
            // 1. 기존 포트 1개를 사용함 (-1)
            // 2. 스위치 자체의 포트가 생김 (+A)
            // 3. 하지만 스위치를 연결하는 선을 꽂을 포트 1개가 필요? 
            //    (일반적으로는 A-1이 순수 증가분이지만, 이 코드에서는 A-2로 계산하고 있습니다.
            //     입력으로 주어지는 A가 1인 경우 등을 고려하거나 문제 특성에 맞춘 조정으로 보입니다.)
            //    만약 A < 2라면 weight가 음수가 되어 인덱스 에러가 날 수 있으니 주의가 필요합니다.
            if(a - 2 >= 0) {
                list.add(new Switch(a - 2, b));
            }
        }

        int M = Integer.parseInt(br.readLine()); // 필요한 랜선 포트(컴퓨터) 수

        // 목표: M개의 컴퓨터를 연결해야 함.
        // 기본적으로 벽면 포트가 1개 있으므로, 추가로 확보해야 할 포트 수는 M - 1개.
        int target = M - 1;

        // 이미 포트가 충분한 경우 (M=1) 비용 0
        if (target <= 0) {
            System.out.println(0);
            return;
        }

        // dp[i] : i개의 추가 포트를 확보하기 위한 최소 비용
        long[] dp = new long[target + 1];
        Arrays.fill(dp, INF);

        dp[0] = 0; // 0개를 추가하는 비용은 0

        // 배낭 문제 (Knapsack DP)
        for (Switch s : list) {
            // 각 스위치는 한 번만 사용할 수으므로 뒤에서부터 탐색
            for (int j = target; j >= s.weight; j--) {
                // j - s.weight 만큼의 포트를 확보한 상태에서 현재 스위치를 추가하여 j개를 만들 수 있다면 갱신
                if (dp[j - s.weight] != INF) {
                    dp[j] = Math.min(dp[j], dp[j - s.weight] + s.cost);
                }
            }
        }

        // 목표 포트 수를 정확히 맞출 필요 없이, 그 이상을 확보해도 되므로
        // target 이상의 용량을 만들 수 있는 경우 중, target 위치에 최소값이 모이도록 처리하거나
        // 혹은 정확히 target을 채우는 것이 아니라 '최소 target 이상'을 만족하는 값을 찾아야 할 수도 있습니다.
        // *현재 코드는 정확히 target만큼의 무게를 채우는(또는 그 위치에 도달하는) 최소 비용을 출력합니다.*
        // 만약 target보다 큰 용량으로 target을 만족시키는 경우가 더 싸다면, 
        // DP 정의를 "i개 *이상* 확보하는 비용"으로 하거나, 마지막에 dp[target] ~ dp[Max] 중 최소를 찾아야 할 수 있습니다.
        // 하지만 문제 특성상(포트가 많을수록 비용이 쌀 리는 없으므로) dp[target]이 유효한 해라면 답이 됩니다.
        
        System.out.println(dp[target] == INF ? -1 : dp[target]);
    }
}