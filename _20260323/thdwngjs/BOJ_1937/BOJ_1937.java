import java.io.*;
import java.util.*;

class Main {
    private static int N;

    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, 1, 0, -1};

    private static int[][] map, dp;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        dp = new int[N][N];

        StringTokenizer st;
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) dfs(i, j);
        }

        int max = 0;
        for(int[] arr: dp){
            for(int i: arr) max = Math.max(max, i);
        }

        System.out.println(max);
    }

    private static int dfs(int x, int y){
        if(dp[x][y] > 0) return dp[x][y];

        dp[x][y] = 1;

        int max = 0;
        for(int d = 0; d < 4; d++){
            int nx = x + dx[d];
            int ny = y + dy[d];

            if(!isIn(nx, ny)) continue;
            if(map[nx][ny] <= map[x][y]) continue;

            if(dfs(nx, ny) > max) max = dp[nx][ny];
        }

        return dp[x][y] = dp[x][y] + max;
    }
    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < N;
    }
}