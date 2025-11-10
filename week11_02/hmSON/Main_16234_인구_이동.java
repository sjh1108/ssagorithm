import java.io.*;
import java.util.*;

public class Main_16234_인구_이동 {
	
	/* 
	 * BOJ_16234 : 인구 이동 (Gold_4)
	 * 자료구조 및 알고리즘 : BFS, 시뮬레이션
	 *
	 * [문제 요약]
	 * N×N 국가의 격자에서 인접(상하좌우) 두 나라의 인구 차이가 [L, R] 범위이면 국경을 연다.
	 * 국경이 열린 나라들의 집합을 "연합"이라 하며, 그날 연합 내 모든 나라는 인구를 (연합 총합 / 연합 칸 수)로 재분배한다.
	 * 더 이상 어떤 연합도 생기지 않을 때까지 이 과정을 반복한다. 이때 인구 이동이 일어난 "일수"를 구하라.
	 *
	 * [접근 아이디어]
	 * - 하루 단위 반복:
	 *   1) 방문배열 초기화 후, 모든 칸에서 아직 방문하지 않았다면 BFS로 "연합"을 탐색한다.
	 *   2) BFS로 연합에 속한 칸 목록과 인구 총합을 구한 뒤, 연합 크기가 1보다 크면(=국경이 열림) 평균 인구로 재분배.
	 *   3) 하루 동안 단 한 번이라도 재분배가 있었다면 다음 날로 진행, 아니면 종료.
	 *
	 * [시간 복잡도]
	 * - 하루에 모든 칸을 최대 한 번씩 방문: O(N^2)
	 * - 최대 일수 D에 대해 O(D * N^2). (N ≤ 50이므로 충분히 통과)
	 */
	
	// 격자 크기 N, 인구 차이 하한/상한 L/R, 진행된 일수(cnt), 현재 연합의 인구 총합(total)
	static int n, minDiff, maxDiff, cnt = 0, total;
	
	// map[y][x] : 해당 나라의 인구수
	static int[][] map;
	// BFS 방문 체크
	static boolean[][] visited;
	
	// 4방향 탐색(상, 우, 하, 좌)
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	// 현재 BFS로 형성된 연합에 포함된 좌표들을 담는 리스트
	static List<int[]> union = new ArrayList<>();
	
	// 입력 및 초기화
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		
		// 인구 차이 허용 구간 [L, R]
		minDiff = Integer.parseInt(st.nextToken());
		maxDiff = Integer.parseInt(st.nextToken());
		
		// 초기 인구 정보 입력
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
	}
	
	/*
	 * BFS(sy, sx)
	 * - 시작 칸 (sy, sx)에서 인구 차이 조건[L, R]을 만족하는 인접 칸들을 확장하여 연합을 만든다.
	 * - union 리스트에 연합에 속한 좌표들을 누적하고, total에 연합 인구 총합을 계산한다.
	 */
	static void bfs(int sy, int sx) {
		// 이전 연합 정보 초기화
		union.clear();
		total = 0;
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {sy, sx});
		visited[sy][sx] = true; // 시작점 방문 처리
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			union.add(cur); // 연합에 현재 칸 포함
			int y = cur[0], x = cur[1];
			total += map[y][x]; // 연합 인구 총합 누적
			
			// 4방향 인접 칸 확인
			for(int i=0; i<4; i++) {
				int ny = y + dy[i];
				int nx = x + dx[i];
				
				// 범위 밖 또는 이미 방문한 칸은 스킵
				if(ny < 0 || nx < 0 || ny >= n || nx >= n || visited[ny][nx]) continue;
				
				// 인구 차이가 [L, R] 범위 내인지 확인
				int diff = Math.abs(map[ny][nx] - map[y][x]);
				if(diff < minDiff || diff > maxDiff) continue; // 열 수 없는 국경
				
				// 연합 확장
				q.add(new int[] {ny, nx});
				visited[ny][nx] = true;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 하루 단위로 반복 시뮬레이션
		while(true) {
			visited = new boolean[n][n]; // 매일 방문 배열 초기화
			boolean moved = false;       // 오늘 하루라도 인구 이동(재분배)이 있었는지
			
			// 모든 칸을 시작점으로 BFS 시도
			for(int i=0; i<n; i++) {
				for(int j=0; j<n; j++) {
					if(visited[i][j]) continue; // 이미 어떤 연합에 속해 처리된 칸
					
					bfs(i, j); // (i, j)를 포함하는 연합 형성
					
					// 연합 크기가 1이면 국경이 열린 것이 아님(재분배 없음)
					if(union.size() == 1) continue;
					
					// 연합이 2칸 이상이면 인구 재분배 진행
					moved = true;
					int avg = total / union.size(); // 연합 평균 인구 (정수 나눗셈)
					
					// 연합 내 모든 나라의 인구를 평균으로 갱신
					for(int[] cur : union) {
						map[cur[0]][cur[1]] = avg;
					}
				}
			}
			
			// 오늘 하루 동안 변화가 없었다면 종료
			if(!moved) break;
			
			// 이동이 있었으므로 하루 카운트 증가
			cnt++;
		}
		
		// 총 진행된 일수 출력
		System.out.println(cnt);
	}
	
}
