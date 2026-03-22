package _20260323.thdwngjs.BOJ_1090;

import java.io.*;
import java.util.*;

/**
 * BOJ 1090 - 체커
 * 알고리즘: 브루트포스 + 정렬
 *
 * N개의 체커를 한 점으로 모을 때, k개(1~N)의 체커를 모으는 최소 이동 비용을 각각 구하는 문제.
 * 이동 비용 = 맨해튼 거리 (|x1 - x2| + |y1 - y2|)
 *
 * 핵심 관찰: 최적의 모임 지점은 기존 체커들의 x좌표, y좌표 조합 중 하나이다.
 * → 후보 지점 O(N^2)개를 모두 탐색하고, 각 지점에서 가까운 k개의 거리합을 비교.
 */
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[] X = new int[N], Y = new int[N];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            X[i] = Integer.parseInt(st.nextToken());
            Y[i] = Integer.parseInt(st.nextToken());
        }

        // 후보 x, y좌표: 기존 체커 좌표들만 고려하면 됨
        int[] sortedX = X.clone();
        int[] sortedY = Y.clone();
        Arrays.sort(sortedX);
        Arrays.sort(sortedY);

        // ans[k] = k개의 체커를 한 점에 모으는 최소 비용
        int[] ans = new int[N + 1];
        Arrays.fill(ans, Integer.MAX_VALUE);

        // 각 후보 지점에서의 거리를 담을 배열 (재사용)
        int[] dist = new int[N];

        // 맨해튼 거리 x, y 성분 전처리 (캐싱)
        // costX[i][k] = |sortedX[i] - X[k]|, costY[j][k] = |sortedY[j] - Y[k]|
        int[][] costX = new int[N][N];
        int[][] costY = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int k = 0; k < N; k++) {
                costX[i][k] = Math.abs(sortedX[i] - X[k]);
                costY[i][k] = Math.abs(sortedY[i] - Y[k]);
            }

        // 모든 후보 지점 (sortedX[i], sortedY[j])에 대해 탐색
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 각 체커까지의 맨해튼 거리 계산
                for (int k = 0; k < N; k++)
                    dist[k] = costX[i][k] + costY[j][k];

                // 거리순 정렬 → 가까운 k개의 합이 최소가 됨
                Arrays.sort(dist);

                // 누적합으로 k개 모으는 비용 갱신
                int sum = 0;
                for (int k = 0; k < N; k++) {
                    sum += dist[k];
                    if (sum < ans[k + 1]) ans[k + 1] = sum;
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++)
            sb.append(ans[i]).append(' ');
        System.out.println(sb.toString().trim());
    }
}