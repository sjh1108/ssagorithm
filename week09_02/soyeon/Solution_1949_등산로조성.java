package test.a;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

// DFS 풀기
public class Solution_1949_등산로조성 {

	// N : map 한 변 크기  // 8
	// K : 최대 공사 가능 깊이 // 5
	// 지형 높이는 최대 20
	
	// 1. 가장 높은 봉우리에서 시작
	// 2. 높은 -> 낮은 방향으로 상하좌우 연결 (같아도 x)
	// 3. 딱 한 곳을 정해 최대 K 깊이만큼 지형 깎기 가능
	
	// 가장 긴 등산로 길이 출력
	
	static int[][] map; // 낮은곳으로만 갈 수 있으니 방문처리 안 해도 될듯?
	static boolean[][] visited; // 방문처리 안 해도 되는줄 알았다가. 봉우리 컷당해서 추가 
	static int N, K;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static ArrayList<int[]> startPos;
	static int ans;
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int T = scanner.nextInt();
		
		for(int tc = 1; tc <= T; tc++) {
			
			N = scanner.nextInt();
			K = scanner.nextInt();
			map = new int[N][N];
			visited = new boolean[N][N];
			startPos = new ArrayList<int[]>();
			
			ans = 0; // 테케별 초기화 // 최대를 구하니 최소로.
			
			
			for(int[] s : startPos) {
				s = new int[2];
			}

			
			int max = 0;
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					map[r][c] = scanner.nextInt();
					if(max < map[r][c]) max = map[r][c];
				}
			} // 입력끝
			
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(map[r][c] == max) startPos.add(new int[] {r, c});
				}
			}
			
			// 4방 탐색을 하며 낮은 곳 이동
			// 최장거리 반환
			// 완탐? dfs.. 근데 바로 생각이 안 떠오름.. 뭘 .. 어캐해야하는지
			
			boolean canGongSa = true;
			for(int i = 0; i < startPos.size(); i++) {
				int sr = startPos.get(i)[0];
				int sc = startPos.get(i)[1];
				visited[sr][sc] = true;
				dfs(sr, sc, canGongSa, 1); // 꼭대기 하나만 선택해도 1임
			}
			

			System.out.println("#" + tc + " " + ans);
			
		}
	}

	private static void dfs(int r, int c, boolean canGongSa, int cnt) {
//		System.out.println(r + ", " + c + " = " + map[r][c] + " => " + cnt);
		
		// 갈곳이 없으면 저절로 종료됨
		// 어디서 cnt를 저장해야할까...
		ans = Math.max(ans, cnt); // 매순간 cnt를 갱신해보자
		
		
		for(int d = 0; d < 4; d++) {
			int nr = r + dr[d];
			int nc = c + dc[d];
			
			
			if(nr < 0 || nr >= N || nc < 0 || nc >= N) continue;
			if(visited[nr][nc]) continue;
			
			if(map[nr][nc] < map[r][c] ) {
				visited[r][c] = true;
				dfs(nr, nc, canGongSa, cnt + 1);
				visited[r][c] = false;
				
			// 여기가 안 떠오름. 공사를 어떻게 처리해야할지 
			}else if(canGongSa && map[nr][nc] - K < map[r][c]){
				int original = map[nr][nc]; // 복사해놓고
				map[nr][nc] = map[r][c] -1 ; // 최소한으로 깎고 탐색
				visited[r][c] = true;
				dfs(nr, nc, false, cnt + 1); // 그러면 이거 도는 동안은 -1로 돌아	
				map[nr][nc] = original; // 원상복구
				visited[r][c] = false;
			}
			
		}
	}
	
	
}


