import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17141_연구소_2 {
	
	/*
	 * BOJ_17141 : 연구소 2(Gold_4)
	 * 자료구조 및 알고리즘 : 조합, BFS
	 *
	 * [문제 요약]
	 * - 연구소의 특정 위치(2)에 바이러스 M개를 놓을 수 있다.
	 * - 바이러스는 상하좌우로 매초 복제되어 퍼진다.
	 * - 연구소의 모든 빈 칸(0)과 비활성 바이러스 칸(2)에 바이러스를 퍼뜨리는 데 걸리는 '최소 시간'을 구하라.
	 * - 모든 칸에 퍼뜨릴 수 없다면 -1을 출력한다.
	 *
	 * [핵심 아이디어]
	 * - 바이러스를 놓을 수 있는 후보지(List) 중 M개를 뽑는 모든 경우의 수를 구한다 (조합).
	 * - 각 경우마다 BFS를 돌려 바이러스가 퍼지는 시간을 측정한다.
	 * - BFS 수행 시 '방문한 칸의 개수'를 세어, 전체 빈 칸 개수(areaCnt)와 같은지 확인해야 한다.
	 *
	 * [구현 메모]
	 * - map[][]을 boolean으로 처리: 벽(1)은 true, 빈칸(0)과 바이러스 후보(2)는 false로 두어 벽의 존재 여부만 판단.
	 * - areaCnt: 바이러스가 퍼져야 할 총 공간의 수 (초기 빈칸 + 바이러스 후보 자리).
	 * - spreadCnt: BFS 과정에서 실제로 바이러스가 도달한 칸의 수.
	 *
	 * [시간 복잡도]
	 * - 바이러스 후보지 개수(K) 중 M개를 뽑는 조합: kCm
	 * - 각 조합마다 BFS 수행: O(N^2)
	 * - 전체 복잡도: O(kCm * N^2) -> N=50, M=10이므로 충분히 통과
	 */
	
	static int n, vCnt;   // 연구소 크기, 놓을 수 있는 바이러스 개수
	static int areaCnt = 0; // 바이러스가 전파되어야 할 총 구역 수(빈칸 + 바이러스 후보)
	static int min = Integer.MAX_VALUE; // 최소 시간 결과값
	
	static boolean[][] map; // true: 벽, false: 이동 가능 공간
	static List<int[]> virus = new ArrayList<>(); // 바이러스를 놓을 수 있는 후보 위치 리스트
	static int[][] selected; // 조합으로 선택된 M개의 바이러스 위치
	
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		vCnt = Integer.parseInt(st.nextToken());
		
		map = new boolean[n][n];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				char x = st.nextToken().charAt(0);
				
				// 벽('1')인 경우 true, 나머지는 false(이동 가능)
				if(x == '1') {
					map[i][j] = true;
					continue;
				}
				
				// 바이러스를 놓을 수 있는 곳('2')은 리스트에 추가
				if(x == '2') virus.add(new int[] {i, j});
				
				// 빈 칸('0')이거나 바이러스 후보('2')인 경우 모두 전파 대상임
				areaCnt++;
			}
		}
		
		selected = new int[vCnt][2];
	}
	
	// 바이러스 후보지 중 vCnt개를 선택하는 조합 (DFS)
	static void combinate(int start, int cnt) {
		if(cnt == vCnt) {
			int res = bfs(); // 선택된 바이러스로 시뮬레이션
			// 모든 칸에 전파되었고(res != -1), 기존 최소 시간보다 짧다면 갱신
			if(res != -1 && res < min) min = res;
			return;
		}
		
		for(int i=start; i<virus.size(); i++) {
			selected[cnt] = virus.get(i);
			combinate(i+1, cnt+1);
		}
	}
	
	// 선택된 바이러스 위치(selected)에서 시작하여 전파 시간 계산
	static int bfs() {
		int timeCnt = 0;   // 마지막으로 감염된 시간(최대 시간)
		int spreadCnt = 0; // 감염된 칸의 수
		
		boolean[][] visited = new boolean[n][n]; // 매 BFS마다 방문 배열 새로 생성
		Queue<int[]> q = new ArrayDeque<>();
		
		// 초기 바이러스 투입
		for(int[] s : selected) {
			q.add(new int[] {s[0], s[1], 0}); // {y, x, 전파 시간}
			spreadCnt++;
			visited[s[0]][s[1]] = true;
		}
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			int time = cur[2];
			
			// 가장 늦게 감염된 시간이 전체 소요 시간
			if(time > timeCnt) timeCnt = time;
			
			for(int i=0; i<4; i++) {
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				// 범위 밖, 벽(true), 이미 방문한 곳이면 패스
				if(y < 0 || x < 0 || y >= n || x >= n || map[y][x] || visited[y][x]) continue;
				
				q.add(new int[] {y, x, time+1});
				visited[y][x] = true;
				spreadCnt++;
			}
		}
		
		// [검증] 감염된 칸의 수가 전체 빈 칸 수와 같은지 확인
		// 같다면 소요 시간 반환, 다르면(전체 감염 실패) -1 반환
		return spreadCnt == areaCnt ? timeCnt : -1;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		combinate(0, 0);
		
		// 불가능한 경우 -1 출력
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}

}