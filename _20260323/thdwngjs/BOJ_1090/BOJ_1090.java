package _20260323.thdwngjs.BOJ_1090;

import java.io.*;
import java.util.*;

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

        // x좌표 정렬 → 고정된 x에 대해 |x - Xk| 를 정렬 없이 구할 수 있음
        int[] sortedX = X.clone();
        int[] sortedY = Y.clone();
        Arrays.sort(sortedX);
        Arrays.sort(sortedY);

        int[] ans = new int[N + 1];
        Arrays.fill(ans, Integer.MAX_VALUE);

        // 각 후보 지점에서의 거리를 담을 배열 (재사용)
        int[] dist = new int[N];

        // x비용 전처리: costX[i][j] = |sortedX[i] - X[j]|
        // 매번 계산하는 대신 캐싱
        int[][] costX = new int[N][N];
        int[][] costY = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int k = 0; k < N; k++) {
                costX[i][k] = Math.abs(sortedX[i] - X[k]);
                costY[i][k] = Math.abs(sortedY[i] - Y[k]);
            }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                // 후보 지점 (sortedX[i], sortedY[j])
                for (int k = 0; k < N; k++)
                    dist[k] = costX[i][k] + costY[j][k];

                // counting sort: 거리 최대 2*10^6 이므로 가능하지만
                // N≤1000이면 Arrays.sort가 충분
                Arrays.sort(dist);

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