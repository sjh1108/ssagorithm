package practice;

import java.io.*;
import java.util.*;

public class Main_7569 {

    static int M, N, H;
    static int[][][] map;
    static int[] dx = {-1, 1, 0, 0, 0, 0}; // 상하좌우앞뒤
    static int[] dy = {0, 0, -1, 1, 0, 0};
    static int[] dz = {0, 0, 0, 0, -1, 1};

    static class Point { // class 기입 까먹지 말기.
        int z, x, y;
        Point(int z, int x, int y) {
            this.z = z; this.x = x; this.y = y;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 열
        M = Integer.parseInt(st.nextToken()); // 행
        H = Integer.parseInt(st.nextToken()); // 높이
        map = new int[H][M][N]; // 3차원 가즈아!!

        Queue<Point> q = new ArrayDeque<>();

        for (int z = 0; z < H; z++) {
            for (int x = 0; x < M; x++) {
                st = new StringTokenizer(br.readLine());
                for (int y = 0; y < N; y++) {
                    map[z][x][y] = Integer.parseInt(st.nextToken());
                    if (map[z][x][y] == 1) q.offer(new Point(z, x, y)); // 익은 토마토
                    // 2차원은 int[]{i,j} 3차원은 클래스 생성해서 ㄱㄱ
                }
            }
        }

        bfs(q);

        int days = 0;
        for (int z = 0; z < H; z++) {
            for (int x = 0; x < M; x++) {
                for (int y = 0; y < N; y++) {
                    if (map[z][x][y] == 0) {
                        System.out.println(-1);
                        return;
                    }
                    days = Math.max(days, map[z][x][y]);
                }
            }
        }

        System.out.println(days - 1); // 처음 익은 토마토가 1이므로 하루 빼줌
    }

    private static void bfs(Queue<Point> q) {
        while (!q.isEmpty()) {
            Point cur = q.poll();

            for (int d = 0; d < 6; d++) {
                int nz = cur.z + dz[d]; // 생성자 친구들 활용
                int nx = cur.x + dx[d];
                int ny = cur.y + dy[d];

                if (nz < 0 || nx < 0 || ny < 0 || nz >= H || nx >= M || ny >= N) continue;
                if (map[nz][nx][ny] == 0) {
                    map[nz][nx][ny] = map[cur.z][cur.x][cur.y] + 1;
                    q.offer(new Point(nz, nx, ny));
                }
            }
        }
    }
}