package _20261109.jun.OJ_119;

import java.io.*;
import java.util.*;

// OJ_119 태양광 단지의 가장 넓은 정상 구역
// 격자, 브루트포스
public class Main {
    public static void main(String[] args) throws IOException {
        int N, M;
        int[][] grid;
        int maxSize = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        grid = new int[N][M];

        // 입력
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                grid[i][j] = line.charAt(j) - '0';
            }
        }

        // 열 단위 높이 측정
        int[][] heights = new int[N][M]; // 현재 칸에서 위로 연속된 정상 패널 수
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if ((heights[i][j] == 0) && (grid[i][j]  == 1)) {
                    heights[i][j] = 1;
                }

                if ((i + 1 < N) && (heights[i][j] != 0) && (grid[i + 1][j] == 1)) {
                    heights[i + 1][j] = heights[i][j] + 1;
                }
            }
        }

        // 현재 칸을 기준으로 오른쪽으로 구역 너비 증가 =>  구역 넓이 계산 및 비교
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (heights[i][j] == 0) {
                    continue;
                }
                int height = heights[i][j]; // 현재 행이 마지막 행인 직사각형 구역 높이

                for (int k = j; k < M; k++) {
                    if (heights[i][k] == 0) {
                        break;
                    }
                    if (heights[i][k] < height) {
                        height = heights[i][k]; // 구역을 오른쪽으로 넓혀가며 직사각형 높이 계산
                    }

                    int width = k - j + 1; // 구역 너비
                    int size = height * width; // 구역 넓이

                    if (size > maxSize) {
                        maxSize = size;
                    }
                }
            }
        }

        System.out.println(maxSize);
    }
}
