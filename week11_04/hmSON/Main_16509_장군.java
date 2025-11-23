import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16509_장군 {
	
	/*
	 * BOJ_16509 : 장군 (Gold_5)
	 * 자료구조 및 알고리즘 : BFS (너비 우선 탐색), 구현
	 *
	 * [문제 요약]
	 * - 10 x 9 크기의 장기판에서 '상'이 '왕'을 잡기 위한 최소 이동 횟수를 구하라.
	 * - 상의 이동 규칙: 상하좌우 1칸 -> 대각선 1칸 -> 대각선 1칸 (총 3단계 이동).
	 * - 멱(장애물) 규칙: 이동 경로 중(1단계, 2단계)에 다른 기물이 있으면 그 방향으로 이동 불가.
	 * - 왕도 하나의 기물이므로, 이동 경로 중간에 왕이 있으면 지나갈 수 없다.
	 *
	 * [핵심 아이디어]
	 * - 최단 거리를 구해야 하므로 BFS를 사용한다.
	 * - 상의 8가지 이동 방향에 대해, 각 방향마다 거쳐가는 3개의 좌표를 순차적으로 검증해야 한다.
	 * - 단순히 도착점만 확인하는 것이 아니라, 경로 중간(1, 2번째 스텝)에 왕이 위치하는지 확인하여
	 * 이동 가능 여부(가지치기)를 판단해야 한다.
	 *
	 * [구현 메모]
	 * - move[][][]: 8방향 * 3단계 이동 좌표 변화량 (dy, dx)을 미리 정의하여 하드코딩을 줄임.
	 * - 도착 확인: 3번째 스텝(j==2)에서 왕을 만나면 정답.
	 * - 장애물 확인: 1, 2번째 스텝(j<2)에서 왕을 만나면 이동 불가 판정.
	 *
	 * [시간 복잡도]
	 * - 맵 크기가 10x9로 매우 작음. O(R * C * 8방향) -> 충분히 통과.
	 */
	
	// 상의 이동 경로 8가지. 각 경로마다 3번의 이동(y, x) 변화량을 저장.
	// 구조: { {1단계}, {2단계}, {3단계(최종)} }
	static int[][][] move = {
			{{-1, 0}, {-1, -1}, {-1, -1}}, // 상 -> 좌대각
			{{-1, 0}, {-1, 1}, {-1, 1}},   // 상 -> 우대각
			{{0, 1}, {-1, 1}, {-1, 1}},    // 우 -> 상대각
			{{0, 1}, {1, 1}, {1, 1}},      // 우 -> 하대각
			{{1, 0}, {1, 1}, {1, 1}},      // 하 -> 우대각
			{{1, 0}, {1, -1}, {1, -1}},    // 하 -> 좌대각
			{{0, -1}, {1, -1}, {1, -1}},   // 좌 -> 하대각
			{{0, -1}, {-1, -1}, {-1, -1}}, // 좌 -> 상대각
	};
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		boolean[][] visited = new boolean[10][9];
		
		// 상(출발점) 위치 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		int sy = Integer.parseInt(st.nextToken());
		int sx = Integer.parseInt(st.nextToken());
		visited[sy][sx] = true;
		
		// 왕(목표점) 위치 입력
		st = new StringTokenizer(br.readLine());
		int ey = Integer.parseInt(st.nextToken());
		int ex = Integer.parseInt(st.nextToken());
		
		// BFS 탐색 {y, x, 이동횟수}
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {sy, sx, 0});
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			// 8가지 이동 방향 시도
			for(int i=0; i<8; i++) {
				int y = cur[0], x = cur[1];
				
				boolean flag = true; // 이동 가능 여부 플래그
				
				// 각 방향별 3단계 이동 검증
				for(int j=0; j<3; j++) {
					y += move[i][j][0];
					x += move[i][j][1];
					
					// 1. 장기판 범위를 벗어나면 이동 불가
					if(y < 0 || x < 0 || y >= 10 || x >= 9) {
						flag = false; break;
					}
					
					// 2. 이동 중 왕(목표물)과 마주친 경우
					if(y == ey && x == ex) {
						if(j == 2) {
							// 마지막 단계(3단계)에서 만났다면 잡을 수 있음 -> 정답 출력
							System.out.println(cur[2] + 1);
							return;
						} else {
							// 이동 경로 중간(1, 2단계)에 왕이 있으면 길이 막힌 것 -> 이동 불가
							flag = false; break;
						}
					}
				}
				
				// 이동 불가능하거나 이미 방문한 지점이면 Skip
				if(!flag || visited[y][x]) continue;
				
				q.add(new int[] {y, x, cur[2]+1});
				visited[y][x] = true;
			}
		}
		
		// 큐가 빌 때까지 왕을 잡지 못함
		System.out.println(-1);
	}
	
}