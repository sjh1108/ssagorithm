import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2638_치즈 {
	
	/*
	 * BOJ_2638 : 치즈 (Gold_3)
	 * 자료구조 및 알고리즘 : BFS, 시뮬레이션
	 *
	 * [문제 요약]
	 * - 치즈가 놓여 있고, 외부 공기와 2변 이상 접촉한 치즈는 1시간 뒤 녹아 없어진다.
	 * - 치즈 내부의 공간은 외부 공기와 접촉하지 않은 것으로 간주한다.
	 * - 치즈가 모두 녹아 없어지는 데 걸리는 시간을 구하라.
	 *
	 * [핵심 아이디어]
	 * - 2636번(치즈)과 달리 접촉 면 개수를 세어야 하므로 `airContact` 배열을 추가로 사용한다.
	 * - BFS를 통해 외부 공기를 확장해 나가면서:
	 * 1. 공기를 만나면: 계속 큐에 넣어 확장한다.
	 * 2. 치즈를 만나면: 해당 치즈의 `airContact`를 1 증가시킨다.
	 * - `airContact`가 2가 되는 순간, 그 치즈는 이번 턴에 녹을 대상(`meltQ`)이 된다.
	 *
	 * [구현 메모]
	 * - visitedAir: 외부 공기 방문 체크 (한 번 공기가 된 곳은 영원히 공기이므로 초기화 불필요)
	 * - airContact: 각 치즈가 외부 공기와 닿은 횟수 누적. 외부 공기는 사라지지 않으므로 누적해도 무방함.
	 * - 최적화: `meltQ`에 넣을 때 `== 2` 조건을 사용하여, 3변/4변이 닿더라도 중복해서 큐에 넣지 않도록 처리함.
	 *
	 * [시간 복잡도]
	 * - 각 칸은 공기가 될 때 한 번, 치즈가 녹을 때 한 번 처리되므로 전체 O(N * M)
	 */
	
	static int r, c, cheezeCnt;
	static boolean[][] board;       // true: 치즈, false: 공기
	static boolean[][] visitedAir;  // 외부 공기 방문 여부 (재방문 방지)
	static int[][] airContact;      // 각 치즈가 외부 공기와 접촉한 면의 수
	
	static Queue<int[]> airQ, meltQ; // airQ: 공기 확산용, meltQ: 녹을 치즈 대기열
	static int[] dy = {-1, 1, 0, 0}, dx = {0, 0, -1, 1};
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		board = new boolean[r][c];
		visitedAir = new boolean[r][c];
		airContact = new int[r][c];
		airQ = new ArrayDeque<>();
		meltQ = new ArrayDeque<>();
		
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				int x = Integer.parseInt(st.nextToken());
				if(x == 1) {
					cheezeCnt++;
					board[i][j] = true;
				}
			}
		}
		
		// 가장자리는 항상 외부 공기
		airQ.add(new int[] {0, 0});
		visitedAir[0][0] = true;
	}
	
	// 외부 공기 확장 및 치즈 접촉 횟수 카운트
	static void findEdge() {
		while(!airQ.isEmpty()) {
			int[] cur = airQ.poll();
			
			for(int i=0; i<4; i++) {
				int ny = cur[0] + dy[i];
				int nx = cur[1] + dx[i];
				
				if(ny < 0 || nx < 0 || ny >= r || nx >= c) continue;
				
				if(board[ny][nx]) {
					// 치즈를 만난 경우: 접촉 횟수 증가
					airContact[ny][nx]++;
					
					// 접촉 횟수가 정확히 2가 될 때 녹을 대기열에 추가
					// (>= 2로 하면 3, 4일 때 중복 추가될 수 있으므로 == 2가 효율적)
					if(airContact[ny][nx] == 2) meltQ.add(new int[] {ny, nx});
					
				} else if(!board[ny][nx] && !visitedAir[ny][nx]) {
					// 방문하지 않은 공기를 만난 경우: 방문 처리 후 큐 추가 (확산)
					visitedAir[ny][nx] = true;
					airQ.add(new int[] {ny, nx});
				}
			}
		}
	}

	// 2변 이상 접촉한 치즈 녹이기
	static void melt() {
		while(!meltQ.isEmpty()) {
			int[] cur = meltQ.poll();
			int y = cur[0];
			int x = cur[1];
			
			// 큐에 들어있어도 이미 처리된(중복 등) 경우 방지용 체크
			if(board[y][x]) {
				board[y][x] = false; // 치즈 -> 공기
				cheezeCnt--;
				
				// [핵심] 녹은 자리는 새로운 '외부 공기'가 됨
				// 따라서 visitedAir 처리 후 airQ에 넣어 다음 findEdge 때 여기서부터 확산되게 함
				visitedAir[y][x] = true;
				airQ.add(new int[] {y, x});
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		int time = 0;
		
		while(cheezeCnt > 0) {
			time++;
			findEdge(); // 현재 공기 상태에서 접촉 판정
			melt();     // 조건 만족 치즈 녹이기 + 녹은 자리 공기화
		}
		
		System.out.println(time);
	}

}