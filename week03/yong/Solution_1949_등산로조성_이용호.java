package algostudy.swea.a1949;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_1949_등산로조성_이용호 {
/*등산로는 가장 높은 봉우리에서 시작
 * 산으로 올라갈수록 높은지형에서 낮은지형으로 가로 또는 세로 방향으로 연결이 되여야한다 -> 현재보다 높이가 같은곳 혹은 높은곳은 불가능 + 대각으로 연결은 불가능
 * 딱 한곳을 정해서 최대 K깊이 만큼 지형을 깎는 공사 가능 
 */
	static int[][] hikingMap;
	static int[] dx = {-1,1,0,0}; // 상, 하, 좌, 우
	static int[] dy = {0,0,-1,1};
	static int N, K, top;
	static boolean visited[][]; //dfs 방문기록용
	static int maxLen;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());//공사 가능 최대깊이
			hikingMap = new int[N][N]; //등산로 초기화
			top = 0; // 가장 높은 봉우리 초기화
			
			
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					hikingMap[i][j] = Integer.parseInt(st.nextToken());
					top = Math.max(top, hikingMap[i][j]); // 가장 높은 봉우리값 기록
				}
			}
			maxLen = 0;
			for(int i = 0; i < N; i++) { //가장 높은 봉우리에서 등산로 만들기
				for(int j = 0; j < N; j++) {
					if(hikingMap[i][j] == top) { // 가장 높은 봉우리체크
						visited = new boolean[N][N];
						
						dfs(i, j, 0, K); //현재 봉우리에서 등산로 만들기 시작
					}
				}
			}
			System.out.println("#" + tc + " " + maxLen);

		}

	}
	private static void dfs(int x,int y, int roadLength, int K) {//가장 긴 길 찾기
		visited[x][y] = true; //현재 위치 등산로길 만들기
		roadLength++; //길 길이 증가
		maxLen = Math.max(maxLen, roadLength);//가장 긴 등산로 갱신
		for(int i = 0; i < 4; i++) { //사방 탐색
			int nx = x + dx[i];
			int ny = y + dy[i];
			
			if(nx >= 0 && ny >= 0 && nx < N && ny < N && !visited[nx][ny]) { // 경계밖으로 나가지 않는지 체크
				if(K != 0) {//공사 가능하면
					if(hikingMap[nx][ny] < hikingMap[x][y]) { //공사 안해도 낮은 봉우리면 공사 안하고 길 만들기
						dfs(nx, ny, roadLength, K);
					}
					else if(hikingMap[nx][ny] - K < hikingMap[x][y]) {//공사해야 낮으면 현재 봉우리보다 1낮게 공사
//						System.out.println(x+" "+y+" | "+ nx + " "+ ny);
						int temp = hikingMap[nx][ny]; // 공사전 봉우리 높이 기억
						hikingMap[nx][ny] = hikingMap[x][y]-1; // 현재 봉우리보다 1 낮게 공사
						dfs(nx,ny,roadLength,0); //공사 했으니 K = 0으로 공사함 표시
						hikingMap[nx][ny] = temp; // 공사했을때 길이체크하고 되돌려 놓기
					}
					
				}
				else {//공사 이미 진행했으면 이후엔 낮은 봉우리만 탐색
					if(hikingMap[nx][ny] < hikingMap[x][y]) {//낮은 봉우리면 길 만들기
						dfs(nx, ny, roadLength, K);
					}
				}
			}
			
		}
		visited[x][y] = false; //탐색 끝나고 다른 갈래 탐색을 위한 false처리 <- 까먹었었음
		
	}

}
