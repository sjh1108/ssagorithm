import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_5427_불 {
	
	/*
	 * BOJ_5427 : 불 (Gold_4)
	 * 자료구조 및 알고리즘 : BFS
	 *
	 * [문제 요약]
	 * - 불은 매초 동서남북으로 퍼진다.
	 * - 상근이는 벽이 아닌 곳으로 매초 이동할 수 있다.
	 * - 불이 옮겨옴과 동시에 상근이가 그 칸으로 이동할 수는 없다. (불이 도착하기 전에만 이동 가능)
	 * - 빌딩을 탈출하는(범위를 벗어나는) 가장 빠른 시간을 구하라.
	 *
	 * [핵심 아이디어]
	 * - 불과 상근이의 움직임을 동시에 시뮬레이션하면 복잡도가 증가한다.
	 * - 따라서 두 단계로 나누어 BFS를 수행한다.
	 * 1. 불 BFS: 맵의 각 칸에 불이 도달하는 최소 시간을 `fireSpread` 배열에 미리 기록한다.
	 * 2. 상근 BFS: 이동하려는 칸의 `fireSpread` 시간과 나의 도착 시간을 비교하여,
	 * 불보다 먼저 도착하는 경우에만 이동한다.
	 *
	 * [구현 메모]
	 * - fireSpread 초기화: 불이 닿지 않는 곳은 MAX_VALUE로 설정하여 비교 연산을 용이하게 한다.
	 * - 탈출 성공 조건: 다음 이동 좌표가 맵의 범위를 벗어나는 경우.
	 * - 방문 처리: 상근이의 방문 처리는 별도 배열 없이 맵을 벽('#')으로 바꿔서 재방문 방지.
	 *
	 * [시간 복잡도]
	 * - 맵 크기 R*C에 대해 불 BFS(O(RC)) + 상근 BFS(O(RC)) 수행.
	 * - 전체 O(R * C)로 충분히 통과 가능.
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	
	static int r, c, sy, sx; // 행, 열, 시작Y, 시작X
	static char[][] map;
	static int[][] fireSpread; // 불이 도달하는 시간을 저장할 배열
	static Queue<int[]> fire = new ArrayDeque<>(); // 불의 좌표를 관리할 큐
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static void init() throws Exception {
		StringTokenizer st = new StringTokenizer(br.readLine());
		c = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		
		map = new char[r][c];
		fireSpread = new int[r][c];
		
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				char ch = line.charAt(j);
				map[i][j] = ch;
				
				if(ch == '*') {
					// 초기 발화점은 시간 0
					fire.offer(new int[] {i, j, 0});
					fireSpread[i][j] = 0;
				} else if(ch == '#') {
					fireSpread[i][j] = -1; // 벽
				} else if(ch == '@') {
					sy = i; sx = j;
					fireSpread[i][j] = Integer.MAX_VALUE; // 아직 불 안 옴
				} else {
					// 빈 공간 (아직 불 안 옴)
					fireSpread[i][j] = Integer.MAX_VALUE;
				}
			}
		}
	}
	
	// 1단계: 불이 퍼지는 시간을 미리 계산하는 BFS
	static void simulate() {
		while(!fire.isEmpty()) {
			int[] cur = fire.poll();
			int time = cur[2];
			
			for(int i=0; i<4; i++) {
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				// 범위 밖, 벽, 또는 이미 더 빨리 불이 도착한 곳이면 패스
				if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] == '#' || fireSpread[y][x] <= time+1) continue;
				
				fire.add(new int[] {y, x, time+1});
				fireSpread[y][x] = time+1;
			}
		}
	}
	
	// 2단계: 상근이의 탈출 BFS
	static int escape() {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {sy, sx, 0});
		map[sy][sx] = '#'; // 방문 처리 (벽으로 만들어서 다시 못 오게 함)
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int time = cur[2];
			
			for(int i=0; i<4; i++) {
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				// 범위를 벗어나면 탈출 성공! (현재 시간 + 1초 소요)
				if(y < 0 || x < 0 || y >= r || x >= c) return time+1;
				
				// 벽이거나, 상근이가 도착하는 시간(time+1)보다 불이 같거나 빨리 도착하면 이동 불가
				if(map[y][x] == '#' || fireSpread[y][x] <= time+1) continue;
				
				q.add(new int[] {y, x, time+1});
				map[y][x] = '#'; // 방문 처리
			}
		}
		
		return -1; // 탈출 불가
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			simulate(); // 불 먼저 확산 시뮬레이션
			int res = escape(); // 이후 상근이 이동
			
			sb.append((res == -1 ? "IMPOSSIBLE" : res) + "\n");
		}
		
		System.out.println(sb);
	}

}