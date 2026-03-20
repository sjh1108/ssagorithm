import java.util.*;
import java.io.*;

class Main{
    private static int N, M;

    private static int[] dx = {-1, 1, 0, 0};
    private static int[] dy = {0, 0, -1, 1};

    private static int[][] map;
    private static boolean[][] visited;
    private static Queue<int[]> q;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        visited = new boolean[N][M];

        Queue<int[]> cheese = new ArrayDeque<>();
        q = new ArrayDeque<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                map[i][j] = Integer.parseInt(st.nextToken());

                if(map[i][j] == 0 && (i == 0 || j == 0 || i == N-1 || j == M-1)){
                    visited[i][j] = true;
                    q.offer(new int[]{i, j});
                } else if(map[i][j] == 1){
                    cheese.offer(new int[]{i, j});
                }
            }
        }

        //flood
        flood();

        // print(visited);
        int time = 0;
        int size = 0;
        Queue<int[]> melt = new ArrayDeque<>();;
        while(!cheese.isEmpty()){
            time++;
            size = cheese.size();

            int cnt = size;
            while(cnt-- > 0){
                int[] cur = cheese.poll();
                int x = cur[0];
                int y = cur[1];
                
                boolean flag = false;
                for(int d = 0; d < 4; d++){
                    int nx = x + dx[d];
                    int ny = y + dy[d];

                    if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                    if(visited[nx][ny]){
                        flag = true;
                        break;
                    }
                }

                if(!flag){
                    cheese.offer(new int[]{x, y});
                } else{
                    melt.offer(new int[]{x, y});
                }
            }

            //flood
            while(!melt.isEmpty()){
                int[] cur = melt.poll();
                visited[cur[0]][cur[1]] = true;
                map[cur[0]][cur[1]] = 0;
                q.offer(new int[]{cur[0], cur[1]});
            }

            flood();

            // System.out.println("Time: " + time + ", Melted: " + (size - cheese.size()));
            // print(visited);
        }
        System.out.println(time + "\n" + size);
    }

    private static void print(boolean[][] visited){
        System.out.println("===================");
        for(int i = 0; i < visited.length; i++){
            for(int j = 0; j < visited[0].length; j++){
                System.out.print((visited[i][j] ? 0 : 1) + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void flood(){
        while(!q.isEmpty()){
            int[] cur = q.poll();
            int x = cur[0];
            int y = cur[1];

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(nx < 0 || ny < 0 || nx >= N || ny >= M) continue;
                if(visited[nx][ny]) continue;
                if(map[nx][ny] == 1) continue;

                visited[nx][ny] = true;
                q.offer(new int[]{nx, ny});
            }
        }
    }
}