package _20260526.thdwngjs;

import java.util.*;
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        char[][] grid = new char[N][M];
        int sr = 0, sc = 0, er = 0, ec = 0;
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                grid[i][j] = line.charAt(j);
                if (grid[i][j] == 'S') { sr = i; sc = j; }
                else if (grid[i][j] == 'E') { er = i; ec = j; }
            }
        }

        int[][] dist = new int[N][M];
        for (int[] row : dist) Arrays.fill(row, -1);

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sr, sc});
        dist[sr][sc] = 0;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nr = cur[0] + dr[d];
                int nc = cur[1] + dc[d];
                if (nr < 0 || nr >= N || nc < 0 || nc >= M) continue;
                if (grid[nr][nc] == '#') continue;
                if (dist[nr][nc] != -1) continue;
                dist[nr][nc] = dist[cur[0]][cur[1]] + 1;
                q.add(new int[]{nr, nc});
            }
        }

        System.out.println(dist[er][ec]);
    }
}