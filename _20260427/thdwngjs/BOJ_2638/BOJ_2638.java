package _20260427.thdwngjs.BOJ_2638;

import java.util.*;
import java.io.*;

class Main{
    static int N, M;

    static int[][] arr;           // 격자 상태 (0: 빈칸, 1: 치즈, 2: 외부 공기)
    static boolean[][] cheeze, visited;
    static int cnt = 0;           // 남은 치즈 칸 수

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 행
        M = Integer.parseInt(st.nextToken()); // 열

        arr = new int[N][M];
        cheeze = new boolean[N][M];

        // 격자 입력 및 치즈 위치 기록
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                arr[i][j] = Integer.parseInt(st.nextToken());

                if(arr[i][j] == 1){
                    cheeze[i][j] = true;
                    cnt++;
                }
            }
        }


        int time = 0;
        // 치즈가 모두 녹을 때까지 반복
        while(cnt > 0){
            visited = new boolean[N][M];
            time++;

            // 격자의 네 꼭짓점에서 DFS로 외부 공기(2) 표시
            int[] dx = {0, 0, N-1, N-1};
            int[] dy = {0, M-1, 0, M-1};

            for(int i = 0; i < 4; i++){
                dfs(dx[i], dy[i]);
            }

            // 모든 치즈에 대해 녹는지 확인
            for(int i = 0; i < N; i++){
                for(int j = 0; j < M; j++){
                    if(cheeze[i][j]){
                        melt(i, j);
                    }
                }
            }
        }

        System.out.println(time);
    }

    // DFS로 외부 공기 영역을 탐색하여 2로 표시
    private static void dfs(int x, int y){
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x, y});
        visited[x][y] = true;
        arr[x][y] = 2; // 외부 공기로 마킹

        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(isOut(nx, ny)) continue;
            if(visited[nx][ny] || cheeze[nx][ny]) continue; // 방문했거나 치즈면 스킵

            dfs(nx, ny);
        }
    }

    // 치즈가 외부 공기와 2면 이상 접하면 녹음
    static void melt(int x, int y){
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        int air = 0; // 인접한 외부 공기 수
        for(int i = 0; i < 4; i++){
            int nx = x + dx[i];
            int ny = y + dy[i];

            if(isOut(nx, ny)) continue;
            if(arr[nx][ny] == 2) air++; // 외부 공기(2)와 접하면 카운트
        }

        // 2면 이상 외부 공기와 접하면 녹음
        if(air >= 2){
            cheeze[x][y] = false;
            cnt--;
        }
    }

    // 격자 범위 밖인지 확인
    private static boolean isOut(int x, int y){
        return x < 0 || y < 0 || x >= N || y >= M;
    }
}
