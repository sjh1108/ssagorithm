import java.io.*;
import java.util.*;

class Main {
    private static int W, H;

    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    private static int[] C;
    private static char[][] map;
    private static int[][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        int temp = 0;
        C = new int[4];
        map = new char[H][W];
        visited = new int[H][W];
        for(int i = 0; i < H; i++){
            map[i] = br.readLine().toCharArray();

            for(int j = 0; j < W; j++){
                if(map[i][j] == 'C'){
                    C[temp++] = i;
                    C[temp++] = j;
                }
            }

            Arrays.fill(visited[i], (int)1e9);
        }

        System.out.println(bfs());
    }

    private static int bfs(){
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[3]));
        pq.add(new int[]{C[0], C[1], -1, 0});

        visited[C[0]][C[1]] = 0;

        int cnt = 0;

        while(!pq.isEmpty()){
            int[] cur = pq.poll();

            if(map[cur[0]][cur[1]] == 'C'){
                cnt++;
                if(cnt == 2) return visited[cur[0]][cur[1]];
            }
            if(cur[3] > visited[cur[0]][cur[1]]) continue;

            for(int d = 0; d < 4; d++){
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];

                if(!isIn(nx, ny) || map[nx][ny] == '*') continue;

                if(d != cur[2] && cur[2] != -1){
                    if(visited[nx][ny] > cur[3]){
                        visited[nx][ny] = cur[3] + 1;
                        pq.add(new int[]{nx, ny, d, cur[3]+1});
                    }
                } else{
                    if (visited[nx][ny] >= cur[3]) {
                        visited[nx][ny] = cur[3];
                        pq.add(new int[]{nx, ny, d, cur[3]});
                    }
                }
            }
        }
        return -1;
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < H && y >= 0 && y < W;
    }
}
