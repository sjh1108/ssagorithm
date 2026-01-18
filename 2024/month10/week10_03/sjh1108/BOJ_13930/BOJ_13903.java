package week10_03.sjh1108.BOJ_13930;
import java.io.*;
import java.util.*;

class Main {
    private static int R, C;

    private static boolean isIn(int x, int y){
        return x >= 0 && x < R && y >= 0 && y < C;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        // 맵 초기화
        int[][] map = new int[R][C];
        for(int i = 0; i < R; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < C; j++){
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 명령어 입력
        int c = Integer.parseInt(br.readLine());
        int[][] cmdArray = new int[c][2];
        for(int i = 0; i < c; i++){
            st = new StringTokenizer(br.readLine());
            cmdArray[i][0] = Integer.parseInt(st.nextToken());
            cmdArray[i][1] = Integer.parseInt(st.nextToken());
        }

        // 시작지점 초기화
        Queue<int[]> q = new ArrayDeque<>();
        for(int i = 0; i < C; i++){
            if(map[0][i] == 1){
                q.add(new int[]{0, i});
            }
        }

        int cnt = 0;
        boolean[][] visited = new boolean[R][C];
        while(!q.isEmpty()){
            // 반복 수 설정을 위한 Queue의 size 계산
            int size = q.size();

            while(size-- > 0){
                int[] cur = q.poll();

                int x = cur[0];
                int y = cur[1];

                if(cur[0] == R-1){
                    System.out.println(cnt);
                    return;
                }
                for(int[] cmd: cmdArray){
                    int nx = x + cmd[0];
                    int ny = y + cmd[1];

                    // 맵 밖으로 나가면 탐색하지 않음
                    if(!isIn(nx, ny)) continue;

                    // 가로 블럭 || 방문한 위치면 탐색하지 않음
                    if(map[nx][ny] == 0 || visited[nx][ny]) continue;
                    // 방문처리
                    visited[nx][ny] = true;

                    q.add(new int[]{nx, ny});
                }
            }

            ++cnt;
        }

        // 탐색 실패
        System.out.println(-1);
    }
}