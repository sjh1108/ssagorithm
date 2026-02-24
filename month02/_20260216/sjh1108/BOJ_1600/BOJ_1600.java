package _20260216.sjh1108.BOJ_1600;

import java.io.*;
import java.util.*;

// 백준 1600 - 말이 되고픈 원숭이 (BFS + State)
// 원숭이의 이동(상하좌우)과 말의 이동(체스 나이트)을 섞어서 최단 거리를 찾는 문제입니다.
// 남은 말 점프 횟수(K)를 상태로 포함하여 3차원 방문 배열을 사용합니다.
class Main {
    private static int W, H;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int K = Integer.parseInt(br.readLine()); // 말처럼 움직일 수 있는 횟수

        StringTokenizer st = new StringTokenizer(br.readLine());
        W = Integer.parseInt(st.nextToken()); // 가로 (열)
        H = Integer.parseInt(st.nextToken()); // 세로 (행)

        int[][] map = new int[H][W];
        
        // visited[y][x][k]: (x,y)에 말 점프 횟수 k번 '남은' 상태로 방문했는지 여부
        // K는 최대 30이므로 크기 31
        boolean[][][] visited = new boolean[H][W][31];
        
        for(int i = 0; i < H; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < W; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 원숭이 이동 (상하좌우)
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, -1, 0, 1};
        
        // 말 이동 (나이트 이동)
        int[] hx = new int[]{-2, -2, -1, 1, 2, 2, 1, -1};
        int[] hy = new int[]{-1, 1, 2, 2, 1, -1, -2, -2};
        
        // BFS 큐: {x, y, 이동횟수(cnt), 남은 말 점프 횟수(horse)}
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0, 0, K});
        visited[0][0][K] = true;

        while(!q.isEmpty()){
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];
            int cnt = cur[2];
            int horse = cur[3];

            // 도착점(오른쪽 맨 아래) 도달 시 종료
            if(x == H - 1 && y == W - 1) {
                System.out.println(cnt);
                return;
            }

            // 1. 인접한 칸으로 이동 (상하좌우) - 말 점프 횟수 소모 X
            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                // 범위 내 && 평지(0) && 해당 말 횟수 상태로 방문한 적 없음
                if(isIn(nx, ny) && map[nx][ny] == 0 && !visited[nx][ny][horse]){
                    visited[nx][ny][horse] = true;
                    q.add(new int[]{nx, ny, cnt + 1, horse});
                }
            }

            // 2. 말처럼 이동 (나이트) - 말 점프 횟수 소모 O
            // 남은 횟수가 있어야 함
            if(horse > 0){
                for(int d = 0; d < 8; d++){
                    int nx = x + hx[d];
                    int ny = y + hy[d];

                    // 범위 내 && 평지(0) && (horse-1) 상태로 방문한 적 없음
                    if(isIn(nx, ny) && map[nx][ny] == 0 && !visited[nx][ny][horse - 1]){
                        visited[nx][ny][horse - 1] = true;
                        q.add(new int[]{nx, ny, cnt + 1, horse - 1});
                    }
                }
            }
        }

        // 도착할 수 없는 경우
        System.out.println(-1);
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < H && y >= 0 && y < W;
    }
}