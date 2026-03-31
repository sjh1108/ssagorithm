import java.io.*;
import java.util.*;

/**
 * BOJ 1937 - 욕심쟁이 판다
 * 알고리즘: DFS + DP (메모이제이션)
 *
 * 대나무 숲에서 판다가 상하좌우로 이동하되,
 * 현재 칸보다 대나무가 더 많은 칸으로만 이동할 수 있을 때
 * 최대 이동 가능 칸 수를 구하는 문제.
 *
 * dp[x][y] = (x, y)에서 출발했을 때 최대 이동 가능 칸 수
 */
class Main {
    private static int N;

    // 상, 우, 하, 좌 방향 벡터
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, 1, 0, -1};

    private static int[][] map, dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        dp = new int[N][N];

        // 대나무 숲 정보 입력
        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 모든 칸에서 DFS 수행 (메모이제이션으로 중복 계산 방지)
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) dfs(i, j);
        }

        // dp 배열에서 최댓값 탐색
        int max = 0;
        for(int[] arr: dp){
            for(int i: arr) max = Math.max(max, i);
        }

        System.out.println(max);
    }

    /**
     * (x, y)에서 출발하여 갈 수 있는 최대 칸 수를 반환
     * 이미 계산된 경우 메모이제이션된 값을 반환
     */
    private static int dfs(int x, int y){
        // 이미 방문하여 계산된 칸이면 바로 반환
        if(dp[x][y] > 0) return dp[x][y];

        // 자기 자신 1칸
        dp[x][y] = 1;

        int max = 0;
        for(int d = 0; d < 4; d++){
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(!isIn(nx, ny)) continue;
            // 현재 칸보다 대나무가 더 많은 칸으로만 이동 가능
            if(map[nx][ny] <= map[x][y]) continue;

            // 인접 칸 중 최대 경로 길이를 구함
            if(dfs(nx, ny) > max) max = dp[nx][ny];
        }

        // 현재 칸 + 인접 칸에서의 최대 경로
        return dp[x][y] = dp[x][y] + max;
    }

    // 좌표 범위 체크
    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}