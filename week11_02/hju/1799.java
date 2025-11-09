import java.io.*;
import java.util.*;

class Main {

    static int N; // 1 <= N <= 10
    static int[][] board;
    static boolean[] slashDiag; // (y + x)
    static boolean[] backslashDiag; // (y - x + N-1), 음수 인덱스 방지
    static int maxBlack = 0, maxWhite = 0;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        board = new int[N][N];
        slashDiag = new boolean[2 * N - 1];
        backslashDiag = new boolean[2 * N - 1];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++)
                board[i][j] = Integer.parseInt(st.nextToken());
        }

        // 검은 네모위의 비숍과 하얀 네모 위의 비숍은 만날 수 없다.
        slash(0, 0, 0, 0);
        slash(0, 1, 0, 1);

        System.out.println(maxBlack + maxWhite);
    }

    static void slash(int y, int x, int cnt, int color) {

        if (y == N) {
            if (color == 0)
                maxBlack = Math.max(maxBlack, cnt);
            else
                maxWhite = Math.max(maxWhite, cnt);
            return;
        }

        // 다음 탐색할 좌표
        int nextY = y;
        int nextX = x + 2;
        if (nextX >= N) {
            nextY++;
            // 다음 행의 시작 x좌표
            nextX = (nextY + color) % 2;
        }

        // 1. 현재 위치에 비숍을 놓는 경우
        int slashIdx = y + x;
        int backslashIdx = y - x + (N - 1);

        // 비숍을 놓을 수 있는 위치이고,
        if (board[y][x] == 1 && !slashDiag[slashIdx] && !backslashDiag[backslashIdx]) {
            // X 방향으로 다른 비숍이 존재하면 안됨

            slashDiag[slashIdx] = true;
            backslashDiag[backslashIdx] = true;

            slash(nextY, nextX, cnt + 1, color);

            slashDiag[slashIdx] = false;
            backslashDiag[backslashIdx] = false;
        }

        // 2. 현재 위치에 비숍을 놓지 않는 경우
        slash(nextY, nextX, cnt, color);
    }

}
