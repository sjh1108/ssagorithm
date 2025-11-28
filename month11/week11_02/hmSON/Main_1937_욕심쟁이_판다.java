import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1937_욕심쟁이_판다 {
	
	/* 
	 * BOJ_1937 : 욕심쟁이 판다(Gold_3)
	 * 자료구조 및 알고리즘 : DFS, DP
	 * 
	 * 판다가 임의의 출발점에서 시작하여 대나무를 먹고, 현재 지역보다 더 많은 대나무가 있는 지역으로 자리를 옮긴다.
	 * 판다가 최대한 많은 칸을 이동할 수 있는 경로로 옴직인다고 할 때, 판다가 이동한 칸의 최대값을 구해야 한다.
	 * 
	 * 각 칸 (y,x)에서 시작했을 때의 최장 이동 길이를 dp[y][x]에 메모한다.
	 * dfs(y,x)는 인접한 더 큰 값으로만 이동하며, 자식의 최장 길이 + 1의 최대값을 구한다.
	 * 기저값: 더 이상 갈 수 없으면 자기 자신으로 1
	 * 메모이제이션: dp[y][x]가 0이 아니면 재계산 없이 즉시 반환
	 * 모든 칸을 시작점으로 시도하며 최댓값을 갱신한다.
	 * */
	
	// 맵 크기
	static int n;
	// 맵 정보
	static int[][] map;
	// (x, y)에서 시작하는 최장 이동 길이
	static int[][] dist;
	// 4방향 배열
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		dist = new int[n][n];
		
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	// DFS로 최장 이동 길이 반환
	// 인접 4방향 중 현재 위치보다 대나무가 많은 곳으로만 이동해야 함
	static int dfs(int y, int x) {
		// 이미 계산된 경우 계산된 값 반환
		if (dist[y][x] != 0) return dist[y][x];
		
		// 현재 위치도 카운트에 포함
		int best = 1;
		for(int i=0; i<4; i++) {
			int ny = y + dy[i];
			int nx = x + dx[i];
			// 배열 범위 초과 방지 및 현재 위치보다 대나무가 많지 않은 경우
			if(ny < 0 || nx < 0 || ny >= n || nx >= n || map[ny][nx] <= map[y][x]) continue;
			best = Math.max(best,  dfs(ny, nx) + 1);
		}
		
		dist[y][x] = best;
		return best;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		// 어디에서 시작해도 최소 1칸은 판다가 머무르므로 1로 시작
		int max = 1;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				max = Math.max(max, dfs(i, j));
			}
		}
		
		System.out.println(max);
	}

}
