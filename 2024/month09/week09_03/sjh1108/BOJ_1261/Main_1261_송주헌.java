package week09_03.sjh1108.BOJ_1261;

import java.io.*;
import java.util.*;

class Main_1261_송주헌 {
    private static int N, M;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 실수 1 : 가로와 세로를 반대로 줌
        M = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());
        int[][] map = new int[N][M];
        
        for(int i = 0; i < N; i++){
            String str = br.readLine();
            for(int j = 0; j < M; j++){
                map[i][j] = str.charAt(j) - '0';
            }
        }

        boolean[][] visited = new boolean[N][M];
        
        // 벽을 깬 횟수 순으로 정렬
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[2]));
        pq.add(new int[]{0, 0, 0});
        visited[0][0] = true;

        int[] dx = {-1, 1, 0, 0};
        int[] dy = {0, 0, -1, 1};
        while(!pq.isEmpty()){
            int[] cur = pq.poll();

            int x = cur[0];
            int y = cur[1];
            int t = cur[2];

            // 끝 점에 도착하면 종료
            if(x == N-1 && y == M-1){
                System.out.println(t);
                return;
            }
            
            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];
                
                // 맵 밖에 있으면 제외
                if(!isIn(nx, ny)) continue;
                // 방문한 곳이면 제외
                // (방문한 곳이 필연적으로 벽을 깬 횟수가 더 적거나 같음)
                if(visited[nx][ny]) continue;

                visited[nx][ny] = true;

                pq.add(new int[]{nx, ny, t + map[nx][ny]});
            }
        }
    }
    private static boolean isIn(int x, int y){
        return (x >= 0 && x < N) && (y >= 0 && y < M);
    }
}
