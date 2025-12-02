import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2573_빙산 {
	
	/*
	 * BOJ_2573 : 빙산 (Gold_4)
	 * 자료구조 및 알고리즘 : 구현, BFS/DFS (Flood Fill)
	 *
	 * [문제 요약]
	 * - 2차원 배열에 빙산의 높이가 주어진다. (0은 바다)
	 * - 빙산은 1년마다 동서남북 4방향으로 접한 바다(0)의 개수만큼 높이가 줄어든다. (음수 불가, 0까지만)
	 * - 빙산이 두 덩어리 이상으로 분리되는 최초의 시간을 구하라.
	 * - 만약 분리되지 않고 다 녹을 때까지 0을 출력한다.
	 *
	 * [핵심 아이디어]
	 * - '녹이기(Melt)'와 '덩어리 세기(Flood Fill)' 과정을 반복한다.
	 * - [녹이기 주의점]: 이번 턴에 녹아서 0이 된 빙산을, 바로 옆 빙산이 "원래 바다였다"고 착각하면 안 된다.
	 * - 보통 임시 배열(copy map)을 사용하지만, 여기서는 `visited` 배열을 재활용하여
	 * "이미 이번 턴에 방문(처리)된 0"은 물로 카운트하지 않도록 처리했다.
	 *
	 * [구현 메모]
	 * - melt(): 빙산 높이 감소 로직. visited 배열을 사용하여 동시성 문제 해결.
	 * - floodFill(): 현재 빙산 덩어리 개수 파악.
	 * - nothing 플래그: 빙산이 분리되지 않고 0이 되는 경우 처리.
	 *
	 * [시간 복잡도]
	 * - 매년 전체 맵 순회(녹이기 + 카운팅) : O(R * C)
	 * - 최악의 경우 빙산 높이가 높고 오래 걸릴 수 있으나, 일반적으로 O(T * R * C) (T는 시간)
	 */
	
	static int r, c;
	static int[][] map;
	static boolean[][] visited; // 방문 체크 및 녹이기 단계에서 상태 확인용
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new int[r][c];
		visited = new boolean[r][c];
		
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	// 빙산을 녹이는 메서드
	static void melt() {
		// 방문 배열 초기화
		for(int i=1; i<r-1; i++) {
			Arrays.fill(visited[i], false);
		}
		
		for(int i=1; i<r-1; i++) {
			for(int j=1; j<c-1; j++) {
				if(map[i][j] == 0) continue; // 이미 물이면 패스
				
				int cnt = 0; // 주변 물(0)의 개수
				for(int k=0; k<4; k++) {
					int y = i + dy[k];
					int x = j + dx[k];
					
					// [중요 로직]
					// 1. map[y][x] != 0 : 빙산이면 물이 아니므로 카운트 X
					// 2. visited[y][x] == true : '이번 턴에 이미 처리된' 곳임.
					//    즉, 원래 빙산이었는데 방금 녹아서 0이 된 경우일 수 있으므로 물로 카운트하면 안 됨.
					if(map[y][x] != 0 || visited[y][x]) continue;
					
					cnt++;
				}
				
				// 높이 감소 (0 밑으로 내려가지 않음)
				map[i][j] = Math.max(0, map[i][j] - cnt);
				visited[i][j] = true; // 처리 완료 표시
			}
		}
	}
	
	// 빙산 덩어리 개수를 세는 메서드 (반환값: 덩어리 수)
	static int floodFill() {
		int cnt = 0;
		// Flood Fill을 위해 방문 배열 다시 초기화
		for(int i=1; i<r-1; i++) {
			Arrays.fill(visited[i], false);
		}
		
		for(int i=1; i<r-1; i++) {
			for(int j=1; j<c-1; j++) {
				// 빙산이면서 아직 방문하지 않은 곳에서 탐색 시작
				if(map[i][j] == 0 || visited[i][j]) continue;
				bfs(i, j); // 연결된 빙산 모두 방문 처리
				cnt++;     // 덩어리 수 증가
			}
		}
		
		return cnt;
	}
	
	// BFS를 이용한 연결된 빙산 탐색
	static void bfs(int sy, int sx) {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[]{sy, sx});
		visited[sy][sx] = true;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i=0; i<4; i++) {
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				if(map[y][x] == 0 || visited[y][x]) continue;
				
				visited[y][x] = true;
				q.add(new int []{y, x});
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		int date = 1;
		boolean nothing = false; // 다 녹을 때까지 분리되지 않는 경우 체크
		
		while(true) {
			melt(); // 1. 빙산 녹이기
			int res = floodFill(); // 2. 덩어리 개수 세기
			
			if(res == 0) {
				// 덩어리가 0개라는 것은 다 녹아서 없어졌다는 뜻 (분리되지 않음)
				nothing = true;
				break;
			} else if(res > 1) {
				// 2덩어리 이상이면 정답
				break;
			}
			
			date++;
		}
		
		System.out.println(nothing ? 0 : date);
	}

}