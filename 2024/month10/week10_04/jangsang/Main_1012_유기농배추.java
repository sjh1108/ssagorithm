package algo2025_10_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1012_유기농배추 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static int T, M, N, K;
    static int[][] map;
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        T = Integer.parseInt(input.readLine());

        for (int t = 0; t < T; t++) {
            tokens = new StringTokenizer(input.readLine());
            M = Integer.parseInt(tokens.nextToken()); // 가로
            N = Integer.parseInt(tokens.nextToken()); // 세로
            K = Integer.parseInt(tokens.nextToken()); // 배추 개수

            map = new int[M][N];

            for (int k = 0; k < K; k++) {
                tokens = new StringTokenizer(input.readLine());
                int x = Integer.parseInt(tokens.nextToken());
                int y = Integer.parseInt(tokens.nextToken());
                map[x][y] = 1;  // 배추가 있는 위치
            }
            
            // 력출 용깅버디
//            System.out.println();
//            for (int i = 0; i < M; i++) {
//                for (int j = 0; j < N; j++) {
//                    System.out.print(map[i][j] + " ");
//                }
//                System.out.println();
//            }
            visited = new boolean[M][N];
            int count = 0;

            for (int i = 0; i < M; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == 1 && !visited[i][j]) {
                        dfs(i, j);
                        count++;
                    }
                }
            }
            System.out.println(count);

        }
    } // main
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0}; // 상하
    static int[] dy = {0, 0, -1, 1}; // 좌우

    static void dfs(int x, int y) {
        visited[x][y] = true;

        for (int d = 0; d < 4; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < M && ny >= 0 && ny < N) {
                if (map[nx][ny] == 1 && !visited[nx][ny]) {
                    dfs(nx, ny);
                }
            }
        }
    }

} //class
