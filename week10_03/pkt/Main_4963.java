package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4963 {

    static int w, h; 
    static int[][] map;
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1}; // 좌 우 상 하 좌상 우상 좌하 우하
    static int[] dy = {0, 0, -1, 1, -1, -1, 1, 1};

    public static void main(String[] args) throws NumberFormatException, IOException { 

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();


        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            w = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());
            
            
            
            if(w == 0 && h == 0) break;

            map = new int[h][w];
            visited = new boolean[h][w];

            
            for (int i = 0; i < h; i++) {
            	st = new StringTokenizer(br.readLine()); // 실수: 이거 위치, 디버깅하면 앎.
				for (int j = 0; j < w; j++) {
					map[i][j] = Integer.parseInt(st.nextToken()); // 실수 hw가 아닌 ij
				}
			}
            
            int cnt = 0;
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    if (map[i][j] == 1 && !visited[i][j]) {
                        visited[i][j] = true;
                        dfs(i, j);
                        cnt++;
                    }
                }
            }
            
            sb.append(cnt).append("\n");
        }
        System.out.println(sb);
    }

    private static void dfs(int x, int y) {
        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];
            if (nx < 0 || ny < 0 || nx >= h || ny >= w) continue;
            if (map[nx][ny] == 1 && !visited[nx][ny]) {
                visited[nx][ny] = true;
                dfs(nx, ny);
            }
        }
    }
}

