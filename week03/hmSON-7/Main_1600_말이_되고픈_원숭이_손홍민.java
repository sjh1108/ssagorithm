import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1600_말이_되고픈_원숭이_손홍민 {

	// 점프 가능한 횟수, 맵의 가로 세로
	static int jump, w, h;
	// map은 입력으로 주어지는 맵 정보, visited는 좌표별 점프 사용 횟수 기록용 비트마스킹 배열
	static boolean[][] map;
	static int[][] visited;
	// 각각 나이트 이동, 4방향 이동
	static int[] hy = {-2, -2, -1, 1, 2, 2, 1, -1}, dy = {-1, 0, 0, 1};
	static int[] hx = {-1, 1, 2, 2, 1, -1, -2, -2}, dx = {0, -1, 1, 0};
	
	public static void main(String[] args) throws Exception {
		init(); 
		bfs();
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		jump = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		w = Integer.parseInt(st.nextToken());
		h = Integer.parseInt(st.nextToken());

		// 맵의 크기가 1, 즉 시작점 == 도착점이면 즉시 종료
		if(w == 1 && h == 1) {
			System.out.println(0);
			System.exit(0);
		}

		// 맵 정보 입력. 1은 벽, 0은 땅
		map = new boolean[h][w];
		visited = new int[h][w];
		for(int i=0; i<h; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<w; j++) {
				map[i][j] = st.nextToken().equals("1");
			}
		}
	}
	
	static void bfs() {
		Queue<Node> q = new ArrayDeque<>();
		q.add(new Node());
		visited[0][0] = 1;
		int ny, nx;
		while(!q.isEmpty()) {
			Node cur = q.poll();
			// 나이트 이동 횟수가 남아있는 경우
			if(cur.jumpCnt < jump) {
				for(int i=0; i<8; i++) {
					ny = cur.h + hy[i];
					nx = cur.w + hx[i];
					// 배열 범위 밖이거나 이미 동일한 점프 횟수로 방문한 전례가 있다면 무시
					if(!isIn(ny, nx) || (visited[ny][nx] & (1 << (cur.jumpCnt+1))) != 0) continue;
					// 도착점 도달
					if(ny == h-1 && nx == w-1) {
						System.out.println(cur.moveCnt + 1);
						System.exit(0);
					}
					visited[ny][nx] |= 1 << (cur.jumpCnt+1);
					q.add(new Node(ny, nx, cur.moveCnt + 1, cur.jumpCnt + 1));
				}
			}
			// 일반 이동
			for(int i=0; i<4; i++) {
				ny = cur.h + dy[i];
				nx = cur.w + dx[i];
				// 배열 범위 밖이거나 이미 동일한 점프 횟수로 방문한 전례가 있다면 무시
				if(!isIn(ny, nx) || (visited[ny][nx] & (1 << cur.jumpCnt)) != 0) continue;
				// 도착점 도달
				if(ny == h-1 && nx == w-1) {
					System.out.println(cur.moveCnt + 1);
					System.exit(0);
				}
				visited[ny][nx] |= 1 << cur.jumpCnt;
				q.add(new Node(ny, nx, cur.moveCnt + 1, cur.jumpCnt));
			}
			
		}
		
		System.out.println(-1);
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < h && x < w && !map[y][x];
	}

	static class Node {
		// 행열 좌표, 이동 횟수, 점프 횟수
		int h, w, moveCnt, jumpCnt;
		
		public Node(int h, int w, int moveCnt, int jumpCnt) {
			this.h = h;
			this.w = w;
			this.moveCnt = moveCnt;
			this.jumpCnt = jumpCnt;
		}

		// 초기값은 반드시 전부 0
		public Node() {
			this(0, 0, 0, 0);
		}
	}
	
}
