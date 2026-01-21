package month08.week00;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class week00_1227_이용호 {
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int[] start; 
	static int possible;
	static boolean[][] visited;
	static int[][] map = new int[100][100];
	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
		for(int t = 1;t<=10;t++) {
			//초기화
			possible = 0;
			start = new int[2];
			visited = new boolean[100][100];
			
			//테스트 케이스
			int testcase = Integer.parseInt(br.readLine());
			for(int i = 0;i<100;i++) {
				String st = br.readLine(); //미로 한줄
				for(int j = 0;j < 100;j++) {
					map[i][j] = st.charAt(j)-'0';
					if(map[i][j] == 2) {
						start[0] = i;
						start[1] = j;
					}
				}
			}
			dfs(start[0],start[1]); 
			System.out.println("#"+testcase+" "+possible);
			
		}

		

	}
	public static void dfs(int x, int y) {
		visited[x][y] = true;
		if(map[x][y] == 3) {
			possible = 1;
			return;
		}
		for(int i = 0;i < 4;i++) {
			int nx = x+dx[i];
			int ny = y+dy[i];
			if(nx>=0 && ny >= 0 && nx < 100 && ny < 100) {
				if(map[nx][ny] != 1 && visited[nx][ny] == false) {
					
					dfs(nx,ny);
					if (possible ==1) return;
				}
			}
		}
	}

}
