package _20261102.jun.OJ_113;

import java.io.*;
import java.util.*;

// OJ_113 해도 위의 섬 세기
// 실버, 격자, 그래프탐색, dfs
public class Main {

    static int R, C;
    static char[][] grid;
    static boolean[][] visited;
    static int count = 0; // 섬 개수
    static int maxSize = 0; // 섬 최대 크기

    // 좌상, 상, 우상, 좌, 우, 좌하, 하, 우하
    static final int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1};
    static final int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        grid = new char[R][C];
        visited = new boolean[R][C];

        // 입력
        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                grid[i][j] = line.charAt(j);
            }
        }

        // 탐색
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                int size = 0;
                if (grid[i][j] == '#' && !visited[i][j]) { // 섬 탐색 & 갯수 세기
                    count++;
                    size = dfs(i, j, size);
                }
                if (size > maxSize) {
                    maxSize = size;
                }
            }
        }

        //출력
        System.out.println(count + " " + maxSize);
    }

    // 섬 크기 탐색
    static int dfs(int r, int c, int size) {
        visited[r][c] = true;
        size++; // 섬의 최소 크기 == 1

        for (int i = 0; i < 8; i++) {
            int nr = r + dr[i];
            int nc = c + dc[i];

            if (nr < 0 || nr >= R || nc < 0 || nc >= C) {
                continue;
            }

            if (grid[nr][nc] == '#' && !visited[nr][nc]) {
                size = dfs(nr, nc, size); // 섬 크기 누적
            }
        }

        return size;
    }
}
