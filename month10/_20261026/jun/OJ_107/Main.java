package _20261026.jun.OJ_107;

import java.io.*;
import java.util.*;

// OJ_107 기울기 판 위의 두 펭귄
// 격자, 시뮬레이션
public class Main {

    static int H, W, T;
    static char[][] iceBoard;
    static int[][] penguin = new int[2][2];

    // 상,하,좌,우
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    public static void main(String args[]) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st =  new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        // 입력 && 펭귄 좌표 기록.
        iceBoard = new char[H + 1][W + 1];
        for (int i = 1; i <= H; i++) {
            String line = br.readLine();
            for (int j = 1; j <= W; j++) {
                iceBoard[i][j] = line.charAt(j - 1);
                if (iceBoard[i][j] == '1') {
                    penguin[0][0] = i;
                    penguin[0][1] = j;
                }
                if (iceBoard[i][j] == '2') {
                    penguin[1][0] = i;
                    penguin[1][1] = j;
                }
            }
        }

        String line = br.readLine();
        for (int i = 0; i < T; i++) {
            char command = line.charAt(i);
            simulation(command);
        }

        System.out.println(penguin[0][0] + " " + penguin[0][1]);
        System.out.println(penguin[1][0] + " " + penguin[1][1]);
    }

    // RDLU 커멘드 적용.
    static void simulation(char command) {
        int first, second, direction;

        // 두 펭귄은 동시에 이동.
        // 이동 방향에 따라 시뮬레이션에서 먼저 움직여야 하는 펭귄 구분.
        switch (command) {
            case 'U':
                first = penguin[0][0] <= penguin[1][0] ? 0 : 1;
                second = 1 - first;
                direction = 0;
                move(first, second, direction);
                break;
            case 'D':
                first = penguin[0][0] >= penguin[1][0] ? 0 : 1;
                second = 1 - first;
                direction = 1;
                move(first, second, direction);
                break;
            case 'L':
                first = penguin[0][1] <= penguin[1][1] ? 0 : 1;
                second = 1 - first;
                direction = 2;
                move(first, second, direction);
                break;
            case 'R':
                first = penguin[0][1] >= penguin[1][1] ? 0 : 1;
                second = 1 - first;
                direction = 3;
                move(first, second, direction);
                break;
            default:
                break;
        }
    }

    // 펭귄 이동.
    static void move(int first, int second, int direction) {
        int[] nums = {first, second};
        char[] next = new char[2];

        do {
            for (int i = 0; i < 2; i++) {
                int num = nums[i];
                int r = penguin[num][0];
                int c = penguin[num][1];
                int nr = r + dr[direction];
                int nc = c + dc[direction];

                // 보드 현황 갱신.
                // 펭귄 좌표 갱신.
                next[num] = iceBoard[nr][nc];
                if (next[num] == '.') {
                    iceBoard[nr][nc] = iceBoard[r][c];
                    iceBoard[r][c] = '.';
                    penguin[num][0] += dr[direction];
                    penguin[num][1] += dc[direction];
                }
            }
        } while (next[0] == '.' || next[1] == '.');
    }
}
