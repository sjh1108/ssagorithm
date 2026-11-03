package _20261102.jun.OJ_108;

import java.io.*;
import java.util.*;

// OJ_108 모래 섞인 빙판
// 실버, 격자, bfs
public class Main {

    static int H, W;
    static char[][] board;
    static int[] penguin = new int[2]; // 'S'
    static int[][] count; // 해당 칸에 멈추기 위해 필요한 최소 미끄러짐 횟수

    // bfs 상, 하, 좌, 우
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        board = new char[H][W]; // (행, 열)을 0부터 센 좌표로 사용
        int[] endPoint = new int[2]; // 'E'

        // 입력
        for (int i = 0; i < H; i++) {
            board[i] = br.readLine().toCharArray();
            for (int j = 0; j < W; j++) {
                if (board[i][j] == 'S') {
                    penguin[0] = i;
                    penguin[1] = j;
                    board[i][j] = '.';
                }
                if (board[i][j] == 'E') {
                    endPoint[0] = i;
                    endPoint[1] = j;
                    board[i][j] = '.';
                }
            }
        }

        // 탐색
        bfs();

        // 출력
        System.out.println(count[endPoint[0]][endPoint[1]]);
    }

    static void bfs() {
        Queue<int[]> queue = new ArrayDeque<>();
        count = new int[H][W];
        for (int i = 0; i < H; i++) {
            Arrays.fill(count[i], -1); // 칸에 도착할 수 없으면 -1 을 출력
        }

        int r = penguin[0];
        int c = penguin[1];
        queue.offer(new int[] {penguin[0], penguin[1]}); // 시작점
        count[r][c] = 0; // 방문 처리

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            r = cur[0];
            c = cur[1];

            // 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int cr = r;
                int cc = c;

                // 벽 까지 이동
                while (true) {
                    int nr = cr + dr[i];
                    int nc = cc + dc[i];

                    if (nr < 0 || nr >= H || nc < 0 || nc >= W) {
                        break;
                    }

                    if (board[nr][nc] == '#') {
                        break;
                    }

                    cr = nr;
                    cc = nc;

                    // 모래 칸 도착
                    if (board[cr][cc] == 'O') {
                        break;
                    }
                }

                if (count[cr][cc] == -1) {
                    count[cr][cc] = count[r][c] + 1;
                    queue.offer(new int[] {cr, cc});
                }
            }
        }
    }
}
