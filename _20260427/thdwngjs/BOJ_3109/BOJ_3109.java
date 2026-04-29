package _20260427.thdwngjs.BOJ_3109;

import java.io.*;
import java.util.*;

class Main {
    private static int r;             // 행 수
    private static int c;             // 열 수
    private static int cnt;           // 연결 가능한 파이프 수
    private static boolean[][] blocked; // 건물('x') 또는 이미 파이프가 지나간 칸

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        // 격자 입력: 'x'인 칸은 blocked 처리
        blocked = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            String line = br.readLine();
            for (int j = 0; j < c; j++) {
                blocked[i][j] = line.charAt(j) == 'x';
            }
        }

        // 각 행의 첫 번째 열에서 출발하여 그리디 DFS로 파이프 연결 시도
        for (int i = 0; i < r; i++) {
            if (dfs(i, 0)) {
                cnt++;
            }
        }

        System.out.print(cnt);
    }

    // 그리디 DFS: 오른쪽 위 → 오른쪽 → 오른쪽 아래 순서로 탐색
    // 한 번 방문한 칸은 blocked 처리하여 다른 파이프가 지나가지 못하게 함
    private static boolean dfs(int x, int y) {
        blocked[x][y] = true; // 현재 칸을 사용한 것으로 표시

        // 마지막에서 두 번째 열에 도달하면 빵집(마지막 열)에 연결 성공
        if (y == c - 2) {
            return true;
        }

        int ny = y + 1; // 항상 오른쪽으로 한 칸 이동

        // 오른쪽 위 대각선 시도
        int nx = x - 1;
        if (nx >= 0 && !blocked[nx][ny] && dfs(nx, ny)) {
            return true;
        }
        // 오른쪽 직진 시도
        if (!blocked[x][ny] && dfs(x, ny)) {
            return true;
        }
        // 오른쪽 아래 대각선 시도
        nx = x + 1;
        return nx < r && !blocked[nx][ny] && dfs(nx, ny);
    }
}
