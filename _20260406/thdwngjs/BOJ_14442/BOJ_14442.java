import java.io.*;
import java.util.*;

class Main {
    private static int N, M;

    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        char[][] map = new char[N][M];

        for(int i = 0; i < N; i++){
            map[i] = br.readLine().toCharArray();
        }

        int[][] visited = new int[N][M];
        for(int[] row : visited) Arrays.fill(row, Integer.MAX_VALUE);
        visited[0][0] = 0;

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0, 1, 0}); // x, y, dist, cnt

        while(!q.isEmpty()){
            int[] cur = q.poll();

            int x = cur[0], y = cur[1], dist = cur[2], cnt = cur[3];

            if(x == N-1 && y == M-1){
                System.out.println(dist);
                return;
            }

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];
                if(!isIn(nx, ny)) continue;

                if(map[nx][ny] == '0'){
                    if(visited[nx][ny] > cnt){
                        visited[nx][ny] = cnt;
                        q.add(new int[]{nx, ny, dist + 1, cnt});
                    }
                } else {
                    if(cnt < K && visited[nx][ny] > cnt + 1){
                        visited[nx][ny] = cnt + 1;
                        q.add(new int[]{nx, ny, dist + 1, cnt + 1});
                    }
                }
            }
        }

        System.out.println(-1);
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
