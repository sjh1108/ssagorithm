import java.io.*;
import java.util.*;

public class Main_16441_아기돼지와_늑대 {
	
	/*
	 * BOJ_16441 : 아기돼지와 늑대 (Gold_3)
	 * 자료구조 및 알고리즘 : BFS (너비 우선 탐색), 시뮬레이션
	 *
	 * [문제 요약]
	 * - 늑대('W')는 상하좌우로 이동하며, 빙판('+')을 만나면 산('#')에 부딪히거나 
	 * 빙판이 아닌 곳에 도착할 때까지 미끄러진다.
	 * - 늑대가 도달할 수 없는 안전한 초원('.')을 찾아 'P'로 표시하여 출력하라.
	 *
	 * [핵심 아이디어]
	 * - "늑대가 갈 수 없는 곳"을 찾는 문제이지만, 반대로 "늑대가 갈 수 있는 곳"을 모두 탐색한다.
	 * - 초기화 시 모든 초원('.')을 일단 안전지대 'P'로 변환한다.
	 * - 늑대 위치에서 BFS를 시작하여 도달 가능한 'P'를 다시 초원 '.'(위험지역)으로 바꾼다.
	 * - 탐색이 끝나고 남은 'P'가 실제 늑대가 올 수 없는 안전지대가 된다.
	 *
	 * [구현 메모]
	 * - 빙판 로직: `while`문을 통해 방향을 유지하며 이동 좌표를 갱신한다.
	 * - 무한 루프 방지: 빙판끼리 순환하는 구조가 있을 수 있으므로 `visited` 배열 필수.
	 *
	 * [시간 복잡도]
	 * - 각 칸은 최대 한 번씩 큐에 들어가므로 O(R * C).
	 * - 빙판 미끄러짐이 있지만 전체 맵 크기 내에서 동작하므로 시간 내 충분.
	 */
	
	static int r, c;
	static char[][] map;
	static boolean[][] visited;
	static Queue<int[]> q = new ArrayDeque<>();
	// 상하좌우 델타
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new char[r][c];
		visited = new boolean[r][c];
		
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				char c = line.charAt(j);
				
				// 1. 초원('.')은 일단 안전지대('P')라고 가정 (Optimistic approach)
				// 추후 BFS로 늑대가 도달하면 다시 '.'로 복구할 예정
				map[i][j] = c == '.' ? 'P' : c;
				
				// 2. 늑대('W')는 큐에 넣고 방문 처리
				if(c == 'W') {
					q.add(new int[] {i, j});
					visited[i][j] = true;
				}
			}
		}
	}
	
	static void bfs() {
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i=0; i<4; i++) {
				// 기본 1칸 이동 시도
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				// 빙판('+')을 만나면 미끄러짐 로직 수행
				// 조건: 현재 위치가 빙판이라면 계속 같은 방향으로 이동
				while(map[y][x] == '+') {
					// 다음 칸이 산('#')이라면 미끄러짐을 멈추고 현재 빙판 위에 멈춤
					if(map[y+dy[i]][x+dx[i]] == '#') break;
					
					// 산이 아니라면 계속 미끄러짐
					y += dy[i]; 
					x += dx[i];
				}
				
				// 이동이 끝난 지점에 대한 유효성 검사
				// 1. 이미 방문했거나, 원래 늑대였거나, 산이거나, 이미 위험지역('.')으로 판명된 경우 스킵
				if(map[y][x] == '.' || map[y][x] == 'W' || map[y][x] == '#' || visited[y][x]) continue;
				
				// 2. 늑대가 도달했으므로 'P'(가정된 안전지대)를 '.'(위험지역)으로 변경
				if(map[y][x] == 'P') map[y][x] = '.';
				
				// 3. 다음 탐색을 위해 큐에 삽입
				q.add(new int[] {y, x});
				visited[y][x] = true;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		bfs();
		
		// 최종 결과 출력
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<r; i++) {
			sb.append(String.valueOf(map[i]) + "\n");
		}
		System.out.println(sb);
	}
	
}