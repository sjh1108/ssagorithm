package _20261019.jun.OJ_92;

import java.io.*;
import java.util.*;

// OJ_92 가장 가까운 드론 두 대
// 실버, 기하학, 브루트포스
public class Main {

    public static void main(String[] args) throws IOException {
        int N;
        int[] x, y;
        long output = Long.MAX_VALUE; // 출력하는 값은 32비트 정수 범위를 넘을 수 있다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        x = new int[N];
        y = new int[N];

        // 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            x[i] = Integer.parseInt(st.nextToken());
            y[i] = Integer.parseInt(st.nextToken());
        }

        // 거리 탐색
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                long dx = x[i] - x[j];
                long dy = y[i] - y[j];
                long dist = (dx * dx) + (dy * dy); // 드론 사이의 거리의 제곱

                // 서로 다른 두 드론 사이의 거리의 제곱 중 가장 작은 값을 출력
                if (output > dist) {
                    output = dist;
                }
            }
        }

        System.out.println(output);
    }
}
