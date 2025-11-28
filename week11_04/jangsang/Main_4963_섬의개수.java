package 똥코드;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4963_섬의개수{
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int w, h;
	static int[][] map;
	static boolean[][] visited;
	static int[] dx = {0,1,1,1,0,-1,-1,-1};
	static int[] dy = {1,1,0,-1,-1,-1,0,1};
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		while(true) {
			tokens = new StringTokenizer(input.readLine());
			w = Integer.parseInt(tokens.nextToken());
			h = Integer.parseInt(tokens.nextToken());
			
			if(w == 0 || h == 0) 	break;
			
			map = new int[h][w];
			visited = new boolean[h][w];
			
			for (int i = 0; i < h; i++) {
				tokens = new StringTokenizer(input.readLine());
				for (int j = 0; j < w; j++) {
					map[i][j] = Integer.parseInt(tokens.nextToken());
				}
			}
			
			// 섬 찾기
			int cnt = 0;
			for (int i = 0; i < h; i++) {
				for (int j = 0; j < w; j++) {
					if(!visited[i][j] && map[i][j] == 1) {
						dfs(i,j);
						cnt++;
					}
				}
			}
			System.out.println(cnt);
			
		}// while
		
	} // main
	static void dfs(int y, int x) {
		visited[y][x] = true;
        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx >= 0 && nx < w && ny >= 0 && ny < h) {
                if (!visited[ny][nx] && map[ny][nx] == 1) {
                    dfs(ny, nx);
                }
            }
        }
	}
} // class
