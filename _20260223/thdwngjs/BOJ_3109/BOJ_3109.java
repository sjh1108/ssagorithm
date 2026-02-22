package _20260223.thdwngjs.BOJ_3109;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 백준 3109 - 빵집 (Greedy + DFS)
class Main {
    private static int r;
    private static int c;
    private static int cnt;
    
    // 건물이 있거나 이미 파이프를 설치(또는 설치 시도 후 실패)한 곳을 표시
    // 실패한 경로를 다시 방문하지 않게 하는 것이 시간 초과를 막는 핵심(Memoization 역할)
    private static boolean[][] blocked;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken()); // 행
        c = Integer.parseInt(st.nextToken()); // 열

        blocked = new boolean[r][c];
        for (int i = 0; i < r; i++) {
            String line = br.readLine();
            for (int j = 0; j < c; j++) {
                // 'x'인 경우 건물이 있어 갈 수 없음 (true)
                blocked[i][j] = line.charAt(j) == 'x';
            }
        }

        // 맨 윗행부터 차례대로 파이프 연결 시도 (Greedy)
        for (int i = 0; i < r; i++) {
            // 연결에 성공하면 카운트 증가
            if (dfs(i, 0)) {
                cnt++;
            }
        }

        System.out.print(cnt);
    }

    private static boolean dfs(int x, int y) {
        // 현재 위치 방문 처리 (성공하든 실패하든 다시 올 필요 없음)
        blocked[x][y] = true;

        // 마지막 열 바로 앞(c - 2)에 도달했다면 
        // 다음 칸은 무조건 도착점(c - 1)이므로 성공으로 간주
        if (y == c - 2) {
            return true;
        }

        // [Greedy의 핵심] 
        // 최대한 많은 파이프를 연결하려면 위쪽으로 바짝 붙여서 연결해야 함.
        // 따라서 이동 순서는 항상 1. 우상단(↗) -> 2. 우(→) -> 3. 우하단(↘) 순서로 탐색
        int ny = y + 1;
        
        // 1. 우상단 탐색 (x - 1, y + 1)
        int nx = x - 1;
        if (nx >= 0 && !blocked[nx][ny] && dfs(nx, ny)) {
            return true;
        }
        
        // 2. 우측 탐색 (x, y + 1)
        if (!blocked[x][ny] && dfs(x, ny)) {
            return true;
        }
        
        // 3. 우하단 탐색 (x + 1, y + 1)
        nx = x + 1;
        // 마지막 옵션이므로 바로 리턴
        return nx < r && !blocked[nx][ny] && dfs(nx, ny);
    }
}