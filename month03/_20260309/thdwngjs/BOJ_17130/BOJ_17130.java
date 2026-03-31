package _20260309.thdwngjs.BOJ_17130;

import java.io.*;
import java.util.*;

class Main {
    private static int N, M;
    private static int rabbit_x, rabbit_y;
    private static int ans;
    
    // 토끼는 오른쪽 위/오른쪽/오른쪽 아래로만 이동 가능
    private static int[] dx = {-1, 0, 1};
    private static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];

        for(int i = 0; i < N; i++){
            char[] input = br.readLine().toCharArray();

            for(int j = 0; j < M; j++){
                // C: 당근, O: 굴(도착 가능 지점), #: 벽
                if(input[j] == 'C') map[i][j] = 1;
                else if(input[j] == 'O') map[i][j] = 2;
                else if(input[j] == '#') map[i][j] = 3;
                else if(input[j] == 'R'){
                    rabbit_x = i; rabbit_y = j;
                }
            }
        }
        
        ans = -1;
        dp();

        System.out.println(ans);
    }

    private static void dp(){
        // dp[x][y]: (x, y)에 도달했을 때 먹을 수 있는 당근의 최대 개수
        int[][] dp = new int[N][M];
        for (int[] d : dp) Arrays.fill(d, -1);

        dp[rabbit_x][rabbit_y] = 0;

        // 시작 열부터 오른쪽으로 진행하면서 상태 전이
        for (int y = rabbit_y; y < M; y++) {
            for (int x = 0; x < N; x++) {
                if (dp[x][y] < 0) continue;
                if (map[x][y] == 3) continue;

                // 굴에 도달한 경우 정답 후보 갱신
                if (map[x][y] == 2) {
                    ans = Math.max(ans, dp[x][y]);
                }

                int ny = y + 1;
                if (ny >= M) continue;

                for (int d = 0; d < 3; d++) {
                    int nx = x + dx[d];
                    if (!isIn(nx, ny)) continue;
                    if (map[nx][ny] == 3) continue;

                    // 다음 칸이 당근이면 1개 추가
                    int next = dp[x][y] + (map[nx][ny] == 1 ? 1 : 0);
                    if (dp[nx][ny] < next) {
                        dp[nx][ny] = next;
                    }
                }
            }
        }
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x< N && y >= 0 && y < M;
    }
}
