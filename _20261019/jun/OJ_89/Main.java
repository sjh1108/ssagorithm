package _20261019.jun.OJ_89;

import java.io.*;
import java.util.*;

// 89 가스에서 가장 먼 갱도
// 실버, bfs
public class Main {

    static int H, W;
    static int[][] graph; // 갱도
    static int[][] depth; // 분출구까지의 거리

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        graph = new int[H][W];
        depth = new int[H][W];

        for (int i = 0; i < H; i++) {
            String line = br.readLine();

            for (int j = 0; j < W; j++) {
                char c = line.charAt(j);

                // 빈 갱도 칸(.): 0, 막힌 칸(#): 1, 분출구 칸(*): 2
                if (c == '#') {
                    graph[i][j] = 1;
                }
                if (c == '*') {
                    graph[i][j] = 2;
                }
            }

            // 방문(false) == -1
            Arrays.fill(depth[i], -1);
        }

        bfs();

        int maxDepth = -1; // 후보가 되는 칸이 하나도 없으면 -1 만 출력
        int x = -1;
        int y = -1 ;


        // 후보가 되는 칸이 여럿이면 행 번호가 가장 작은 칸을,
        // 행 번호까지 같으면 열 번호가 가장 작은 칸을 출력
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (depth[i][j] > maxDepth && graph[i][j] == 0) {
                    maxDepth = depth[i][j];
                    x = i;
                    y = j;
                }
            }
        }

        System.out.println(maxDepth);

        // 대피소 후보가 되는 칸이 있을 때만
        if (maxDepth != -1) {
            // 격자의 가장 위쪽 줄이 1행, 가장 왼쪽 열이 1열이다.
            System.out.println((x + 1) + " " + (y + 1));
        }
    }

    // bfs
    // 분출구 칸이 시작점. 분출구에서 부터 깊이 탐색.
    static void bfs() {
        Queue<int[]> queue = new ArrayDeque<>();

        // 분출구 방문 처리
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (graph[i][j] == 2) {
                    // 방문(true) == 0
                    depth[i][j] = 0;
                    queue.offer(new int[]{i, j});
                }
            }
        }

        // 상, 하, 좌, 우 탐색
        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int cx = cur[0];
            int cy = cur[1];

            for (int i = 0; i < 4; i++) {
                int nx = cx + dx[i];
                int ny = cy + dy[i];

                if (nx < 0 || nx >= H || ny < 0 || ny >= W) {
                    continue;
                }

                if (depth[nx][ny] != -1 || graph[nx][ny] == 1) {
                    continue;
                }

                depth[nx][ny] = depth[cx][cy] + 1;
                queue.offer(new int[]{nx, ny});
            }
        }
    }
}