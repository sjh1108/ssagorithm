package _20261116.Taeyum;

// N행 M열 격자, 정상(1) / 고장(0)
// 정상 패널만으로 이루어진 직사각형 구역 하나 고르기
// 넓이는 구역 안에 있는 칸 수 -> 가장 넓은 구역의 넓이 출력 (정상 패널 하나도 없으면 0)
// 직사각형 전부 잡고 안에 칸까지 다 확인하면 100^6 -> 터짐
// 각 열마다 위로 1이 몇 개 연속인지(높이) 세두고
// 행 하나를 바닥으로 잡은 다음, left ~ right 구간에서 제일 낮은 높이 * 가로 길이 -> 넓이
// 100 * 100 * 100 = 1,000,000 -> 가능

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class OJ_119 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        char[][] map = new char[N][M];

        for (int i = 0; i < N; i++) {
            map[i] = br.readLine().toCharArray();
        }

        // 지금 보는 행 기준으로 j열에서 위로 연속된 1의 개수
        int[] height = new int[M];
        int ans = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                // 정상이면 위에서 이어지니까 1 고장이면 끊기니까 0
                if (map[i][j] == '1') {
                    height[j]++;
                } else {
                    height[j] = 0;
                }
            }

            // 직사각형 left 고정하고 right 늘려감
            for (int left = 0; left < M; left++) {
                int min = height[left];

                for (int right = left; right < M; right++) {
                    // 구간 안에서 제일 낮은 높이까지만 직사각형이 됨
                    min = Math.min(min, height[right]);

                    // 고장 칸이 끼면 더 늘려봐야 의미 없음
                    if (min == 0) {
                        break;
                    }

                    // 가로 길이 = right - left + 1
                    ans = Math.max(ans, min * (right - left + 1));
                }
            }
        }

        System.out.println(ans);
    }
}
