import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2636_치즈 {
	
	/*
	 * BOJ_2636 : 치즈 (Gold_4)
	 * 자료구조 및 알고리즘 : BFS, 시뮬레이션
	 *
	 * [문제 요약]
	 * - 사각형 판 위에 치즈가 놓여 있다. 치즈에는 구멍이 있을 수 있다.
	 * - 치즈는 '외부 공기'와 접촉한 면이 1시간 뒤에 녹아 없어진다.
	 * - 구멍(내부 공기)은 외부 공기와 접촉하지 않으므로 치즈를 녹이지 않는다.
	 * - 치즈가 모두 녹는 데 걸리는 시간과, 다 녹기 한 시간 전의 치즈 조각 수를 구하라.
	 *
	 * [핵심 아이디어]
	 * - (0, 0)은 항상 공기이므로, 여기서부터 BFS를 시작하면 '외부 공기'만 탐색할 수 있다.
	 * - 공기를 타고 퍼지다가 치즈(1)를 만나면, 그 치즈는 '이번 시간에 녹을 예정'인 치즈다.
	 * - [최적화 포인트]: 매 시간 visited를 초기화할 필요가 없다.
	 * 녹은 치즈 자리는 곧 새로운 공기가 되므로, 그 위치에서부터 다시 BFS를 이어서 진행하면 된다.
	 *
	 * [구현 메모]
	 * - airQ: 외부 공기의 좌표를 담는 큐. 계속 확장해 나간다.
	 * - meltQ: 이번 턴에 녹을 치즈의 좌표를 담는 큐. (가장자리)
	 * - 로직 순서: findEdge(공기 확산 & 녹을 치즈 찾기) -> melt(치즈 녹이기 & 녹은 자리 공기 큐에 추가)
	 *
	 * [시간 복잡도]
	 * - 각 칸은 공기 큐에 한 번, 녹을 때 한 번 처리되므로 전체적으로 O(R * C)에 해결된다.
	 * - (일반적인 매 턴 초기화 방식보다 훨씬 빠름)
	 */

	static int r, c, cheezeCnt;
	static boolean[][] board; // true: 치즈, false: 공기
	static boolean[][] visited; // 방문 체크 (초기화 없이 계속 사용)
	static Queue<int[]> airQ, meltQ;
	static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		board = new boolean[r][c];
		visited = new boolean[r][c];
		airQ = new ArrayDeque<>();
		meltQ = new ArrayDeque<>();
		
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				String cur = st.nextToken();
				if(cur.equals("1")) cheezeCnt++;
				board[i][j] = cur.equals("1");
			}
		}
		
		// 가장자리 (0,0)은 무조건 외부 공기
		airQ.add(new int[] {0, 0});
		visited[0][0] = true;
	}
	
	// BFS 1단계: 외부 공기를 확장하여 접촉한 치즈(가장자리)를 찾아냄
	static void findEdge() {
		while(!airQ.isEmpty()) {
			int[] cur = airQ.poll();
			
			for(int i=0; i<4; i++) {
				int ny = cur[0] + dy[i];
				int nx = cur[1] + dx[i];
				
				// 범위 체크 및 이미 방문한 곳(이미 공기이거나, 이미 녹을 예정인 치즈) 패스
				if(ny < 0 || nx < 0 || ny >= r || nx >= c || visited[ny][nx]) continue;
				
				visited[ny][nx] = true; // 방문 처리 (영구적)
				
				if(board[ny][nx]) {
					// 치즈를 만나면 더 이상 공기가 퍼지지 못하고, 녹을 대기열(meltQ)에 넣음
					meltQ.add(new int[] {ny, nx});
				} else {
					// 공기를 만나면 계속 확장 (내부 공기는 도달 못함)
					airQ.add(new int[] {ny, nx});
				}
			}
		}
	}
	
	// BFS 2단계: 찾아낸 가장자리 치즈를 녹임
	static void melt() {
		while(!meltQ.isEmpty()) {
			int[] cur = meltQ.poll();
			int row = cur[0];
			int col = cur[1];
			
			// 치즈 녹이기 (공기로 변환)
			board[row][col] = false;
			cheezeCnt--;
			
			// [핵심] 녹은 자리는 이제 '외부 공기'가 되었으므로, 
			// 다음 턴에 여기서부터 공기가 안쪽으로 파고들 수 있게 airQ에 넣어줌
			airQ.add(new int[] {row, col});
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		int time = 0;
		int lastCheezeCnt = 0;
		
		while(cheezeCnt > 0) {
			time++;
			lastCheezeCnt = cheezeCnt; // 다 녹기 전 개수 저장
			
			findEdge(); // 1. 현재 외부 공기에서 갈 수 있는 치즈 찾기
			melt();     // 2. 찾은 치즈 녹이고, 그 자리를 공기 큐에 추가
		}
		
		System.out.println(time);
		System.out.println(lastCheezeCnt);
	}
	
}