/**
 * BOJ 2159 - 케익 배달 (골드 3)
 *
 * [풀이] DP
 * 각 고객은 원래 위치에서 상하좌우 1칸 또는 제자리에 머물 수 있다 (5가지).
 * dp[i][d] = i번째 고객이 d방향으로 이동했을 때, 1번부터 i번까지의 최소 이동 거리
 *
 * 전이: dp[i][cur] = min(dp[i-1][prev] + (i번째의 cur위치와 i-1번째의 prev위치 간 맨해튼 거리))
 * 출발지(0번)는 이동하지 않으므로, dp[1][d]는 출발지에서 1번 고객의 d방향 위치까지 거리로 초기화.
 */
package _20260330.thdwngjs.BOJ_2159;

import java.io.*;
import java.util.*;

class Main {
    static final int DIR_COUNT = 5; // 제자리 + 상하좌우
    static final int[] DX = {0, 0, 1, 0, -1};
    static final int[] DY = {0, 1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] pos = new int[n + 1][2];            // pos[0]: 출발지, pos[1~n]: 고객 위치
        long[][] dp = new long[n + 1][DIR_COUNT];   // dp[i][d]: i번째 고객이 d방향일 때 최소 거리

        for (int i = 0; i <= n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            pos[i][0] = Integer.parseInt(st.nextToken());
            pos[i][1] = Integer.parseInt(st.nextToken());
            Arrays.fill(dp[i], Long.MAX_VALUE);
        }

        int startX = pos[0][0];
        int startY = pos[0][1];

        // 초기화: 출발지 → 1번 고객의 각 이동 방향까지의 맨해튼 거리
        for (int d = 0; d < DIR_COUNT; d++) {
            dp[1][d] = Math.abs(pos[1][0] + DX[d] - startX)
                     + Math.abs(pos[1][1] + DY[d] - startY);
        }

        // DP 전이: 이전 고객의 모든 방향에서 현재 고객의 모든 방향으로 최솟값 갱신
        for (int i = 2; i <= n; i++) {
            for (int cur = 0; cur < DIR_COUNT; cur++) {
                for (int prev = 0; prev < DIR_COUNT; prev++) {
                    long dist = Math.abs((pos[i][0] + DX[cur]) - (pos[i - 1][0] + DX[prev]))
                              + Math.abs((pos[i][1] + DY[cur]) - (pos[i - 1][1] + DY[prev]));
                    dp[i][cur] = Math.min(dp[i][cur], dp[i - 1][prev] + dist);
                }
            }
        }

        // n번째 고객의 5방향 중 최솟값이 정답
        long answer = Long.MAX_VALUE;
        for (int d = 0; d < DIR_COUNT; d++) {
            answer = Math.min(answer, dp[n][d]);
        }
        System.out.println(answer);
    }
}