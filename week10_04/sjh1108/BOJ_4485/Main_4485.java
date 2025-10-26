package week10_04.sjh1108.BOJ_4485;
import java.io.*;
import java.util.*;

class Main {
    private static int N;

    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        StringTokenizer st;
        StringBuilder sb = new StringBuilder();
        int idx = 0;
        while(N > 0){
            int[][] map = new int[N][N];

            for(int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            sb.append("Problem " + ++idx + ": ");
            sb.append(dijkstra(map)).append('\n');
            N = Integer.parseInt(br.readLine());
        }
        System.out.println(sb);
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    private static int dijkstra(int[][] map){
        int[][] arr = new int[N][N];
        for(int[] a: arr) Arrays.fill(a, Integer.MAX_VALUE);

        Queue<int[]> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));

        q.add(new int[]{0, 0, map[0][0]});
        while(!q.isEmpty()){
            int[] cur = q.poll();

            int x = cur[0];
            int y = cur[1];
            int c = cur[2];

            if(x == N-1 && y == N-1) return c;

            for(int i = 0; i < 4; i++){
                int nx = x + dx[i];
                int ny = y + dy[i];

                if(!isIn(nx, ny)) continue;

                if(c+map[ny][nx] < arr[ny][nx]) {
					arr[ny][nx] = c+map[ny][nx];
					q.add(new int[]{nx, ny, c+map[ny][nx]});
				}
            }
        }

        return -1;
    }
}