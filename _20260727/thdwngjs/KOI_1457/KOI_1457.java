import java.io.*;
import java.util.*;

class Main{
    private static int N, M, K;

    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    private static boolean[][] visited;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        visited = new boolean[N][M];
        while(K-- > 0){
            st = new StringTokenizer(br.readLine());
            
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            for(int i = y1; i < y2; i++){
                for(int j = x1; j < x2; j++){
                    visited[i][j] = true;
                }
            }
        }

        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(visited[i][j]) continue;
                
                list.add(bfs(i, j));
            }
        }

        Collections.sort(list);
        StringBuilder sb = new StringBuilder();
        sb.append(list.size());
        sb.append('\n');
        for(int i: list){
            sb.append(i);
            sb.append(' ');
        }

        System.out.println(sb);
    }

    private static int bfs(int sx, int sy){
        Queue<int[]> q = new ArrayDeque<>();

        int cnt = 0;
        q.add(new int[]{sx, sy});
        while(!q.isEmpty()){
            int[] cur = q.poll();

            int x = cur[0];
            int y = cur[1];

            if(visited[x][y]) continue;
            visited[x][y] = true;
            cnt++;

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(!isIn(nx, ny)) continue;
                if(visited[nx][ny]) continue;

                q.add(new int[]{nx, ny});
            }
        }

        return cnt;
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}