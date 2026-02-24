import java.io.*;
import java.util.*;

public class Main_1600_말이_되고픈_원숭이 {

	/*
	 * BOJ_1600 : 말이 되고픈 원숭이 (Gold_3)
	 * 자료구조 및 알고리즘 : BFS, 비트마스킹
	 *
	 * [문제 요약]
	 * - R×C 격자에서 (0,0) -> (R-1,C-1)로 이동하는 최소 이동 횟수를 구한다.
	 * - 기본 이동은 상/하/좌/우 1칸.
	 * - 추가로 "말(나이트) 점프" 이동을 최대 K번까지 사용할 수 있다.
	 * - 장애물(1)은 지나갈 수 없으며, 도달 불가능하면 -1을 출력한다.
	 *
	 * [핵심 아이디어]
	 * - 최단거리 문제이므로 BFS가 기본.
	 * - 단, 같은 칸이라도 "지금까지 사용한 점프 횟수"가 다르면 이후 선택지가 달라진다.
	 *   => 상태를 (y, x, jumpUsed)로 확장한 BFS가 필요.
	 * - 일반적으로 visited[y][x][jumpUsed] (3차원)로 방문처리하지만,
	 *   K ≤ 30이므로 jumpUsed를 비트로 관리 가능:
	 *   visited[y][x]의 jumpUsed번째 비트가 1이면 해당 상태로 방문한 적이 있음.
	 *
	 * [구현 메모]
	 * - Node: (y, x, jump, move)
	 *   - jump: 지금까지 사용한 점프 횟수
	 *   - move: 현재까지 이동 횟수(거리)
	 * - visited[y][x]는 int 비트마스크:
	 *   - (visited[y][x] & (1 << jump)) != 0 이면 이미 방문한 상태
	 *   - 방문 시 visited[y][x] |= (1 << jump)
	 * - 각 노드에서
	 *   1) 일반 이동 4방향 시도 (jump 유지)
	 *   2) jump < k 인 경우에만 말 점프 8방향 시도 (jump+1)
	 * - 목표 칸에 도착하면 BFS 특성상 최초 도착이 최소 move이므로 즉시 반환.
	 *
	 * [시간 복잡도]
	 * - 상태 수: R * C * (K+1)
	 * - 각 상태에서 최대 12가지 이동(4 + 8)
	 * - 총: O(R * C * K)
	 */

	static int k, r, c;
	static boolean[][] map;
	static int[][] visited;
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	static int[] jy = {-2, -2, -1, 1, 2, 2, 1, -1}, jx = {-1, 1, 2, 2, 1, -1, -2, -2};

	static class Node {
		int y, x, jump, move;

		public Node(int y, int x, int jump, int move) {
			this.y = y;
			this.x = x;
			this.jump = jump;
			this.move = move;
		}
	}

	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		k = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		c = Integer.parseInt(st.nextToken());
		r = Integer.parseInt(st.nextToken());
		// 시작 == 도착인 경우(1x1)은 이동이 필요 없으므로 0
		if (r == 1 && c == 1) {
			System.out.println(0);
			System.exit(0);
		}

		map = new boolean[r][c];
		visited = new int[r][c];

		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				map[i][j] = st.nextToken().equals("1"); // true면 장애물
			}
		}
	}

	static int bfs() {
		Queue<Node> q = new ArrayDeque<>();
		q.add(new Node(0, 0, 0, 0));
		// (0,0)을 jump=0 상태로 방문 처리
		visited[0][0] = 1; // 1 << 0

		while(!q.isEmpty()) {
			Node cur = q.poll();

			// 1) 일반 이동 처리 (4방향), 점프 횟수 유지(cur.jump)
			for(int i=0; i<4; i++) {
				int y = cur.y + dy[i];
				int x = cur.x + dx[i];
				// 범위 밖, 장애물, 또는 (y,x)를 같은 jump 상태로 이미 방문했다면 skip
				if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] || (visited[y][x] & 1 << cur.jump) != 0) continue;

				// 도착점이면 현재 move에서 1 증가한 값이 최단거리
				if(y == r-1 && x == c-1) return cur.move+1;

				visited[y][x] |= 1 << cur.jump;
				q.add(new Node(y, x, cur.jump, cur.move+1));
			}

			// 2) 점프(말 이동) 처리: jump를 이미 K번 썼다면 더 이상 불가
			if(cur.jump == k) continue;
			for(int i=0; i<8; i++) {
				int y = cur.y + jy[i];
				int x = cur.x + jx[i];
				// 점프는 jump+1 상태로 방문 여부를 체크해야 한다.
				if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] || (visited[y][x] & 1 << (cur.jump+1)) != 0) continue;

				if(y == r-1 && x == c-1) return cur.move+1;

				visited[y][x] |= 1 << (cur.jump + 1);
				q.add(new Node(y, x, cur.jump+1, cur.move+1));
			}
		}

		return -1; // 도달 불가
	}

	public static void main(String[] args) throws Exception {
		init();
		System.out.println(bfs());
	}

}
