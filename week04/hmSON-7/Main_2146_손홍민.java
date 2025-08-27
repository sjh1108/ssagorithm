import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2146_손홍민 {

	// n: 맵 크기, map: 맵 정보. 육지가 true
	// spreadMap : 맵 확산 현황 기록. 각 좌표의 배열은 섬 번호와 섬과의 거리임. 0은 각 섬의 가장자리, 1부터는 각 섬과의 거리임
	static int n;
	static boolean[][] map;
	static int[][][] spreadMap;
	static int[] dy = {-1, 0, 0, 1}, dx = {0, -1, 1 , 0};
	static Queue<int[]> qEdge = new ArrayDeque<>();
	
	public static void main(String[] args) throws Exception {
		init();
		solve();
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new boolean[n][n];
		spreadMap = new int[n][n][2];
		StringTokenizer st;
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				map[i][j] = st.nextToken().equals("1");
			}
		}
	}
	
	static void solve() {
		// BFS 탐색 전 섬의 인덱스를 기록
		int islandCnt = 1;
		for(int i=0; i<n; i++) {
			for(int j=0; j<n; j++) {
				// 바다 또는 이미 확인한 섬은 무시
				if(!map[i][j]) continue;
				checkIsland(i, j, islandCnt);
				islandCnt++;
			}
		}
		
		// 다리 확장 시작
		System.out.println(spread());
	}
	
	static void checkIsland(int y, int x, int idx) {
		// 각 섬을 위치 및 크기를 확인하고 가장 자리를 확인하기 위한 과정
		// Flood Fill
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {y, x});
		map[y][x] = false;
		spreadMap[y][x][0] = idx;
		spreadMap[y][x][1] = 0;
		qEdge.add(new int[] {y, x, 0});
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int cy = cur[0], cx = cur[1];
			for(int i=0; i<4; i++) {
				int ny = cy + dy[i], nx = cx + dx[i];
				if(!isIn(ny, nx) || !map[ny][nx]) continue;
				map[ny][nx] = false;
				q.add(new int[] {ny, nx});
				spreadMap[ny][nx][0] = idx;
				spreadMap[ny][nx][1] = 0;
				qEdge.add(new int[] {ny, nx, 0});
			}
		}
	}
	
	static int spread() {
		// 각각 현재 최소 길이와, 추가 탐색 방지용 턴 플래그
		int len = 200, turnFlag = 200;
		while(!qEdge.isEmpty()) {
			int[] cur = qEdge.poll();
			int cy = cur[0], cx = cur[1], turn = cur[2];
			// 다리 연결이 확정된 상태에서 다음 확장 턴으로 넘어갈 때 탐색 종료
			if(turn > turnFlag) break;
			int[] curInfo = spreadMap[cy][cx];
			for(int i=0; i<4; i++) {
				int ny = cy + dy[i], nx = cx + dx[i];
				if(!isIn(ny, nx)) continue;
				int[] nextInfo = spreadMap[ny][nx];
				// 이미 확장된 칸이라면?
				if(nextInfo[0] != 0) {
					// 같은 섬으로부터 확장된 땅인 경우 무시
					if(nextInfo[0] == curInfo[0]) continue;
					
					// 다른 섬과 만남 -> 현재 길이와 기록된 최소 길이를 비교 및 갱신
					// 일단 다리가 이어졌으므로 현재 턴까지만 탐색
					len = Math.min(len, nextInfo[1] + curInfo[1]);
					turnFlag = turn;
				}
				// 바다 -> 확장
				spreadMap[ny][nx][0] = curInfo[0];
				spreadMap[ny][nx][1] = curInfo[1] + 1;
				qEdge.add(new int[] {ny, nx, turn+1});
			}
		}
		
		// 가장 짧은 다리 길이 반환
		return len;
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < n && x < n;
	}
	
}
