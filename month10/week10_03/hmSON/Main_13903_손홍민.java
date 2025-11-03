import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_13903 {
	
	// 세로 길이, 가로 길이, 이동 규칙 수, 최소 이동횟수
	static int r, c, dc, min = Integer.MAX_VALUE;
	// 맵 정보(true : 세로 블록, false : 가로 블록)
	static boolean[][] map;
	// 이동 규칙
	static int[] dy, dx;
	// BFS용 큐
	static Queue<Node> q = new ArrayDeque<>();
	
	static class Node {
		// 현재 위치의 좌표, 이동 횟수
		int y, x, time;
		
		public Node(int y, int x, int time) {
			this.y = y;
			this.x = x;
			this.time = time;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		map = new boolean[r][c];
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				// 밟을 수 있는 위치만 true로 기록
				map[i][j] = st.nextToken().equals("1");
			}
		}
		
		dc = Integer.parseInt(br.readLine());
		dy = new int[dc]; dx = new int[dc];
		for(int i=0; i<dc; i++) {
			st = new StringTokenizer(br.readLine());
			dy[i] = Integer.parseInt(st.nextToken());
			dx[i] = Integer.parseInt(st.nextToken());
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// r == 1인 경우(출발선 == 도착선인 경우) 즉시 0을 출력하고 실행 종료
		if(r == 1) {
			System.out.println(0);
			return;
		}
		
		// 0번 행의 세로 블록 좌표를 큐에 등록 및 방문 처리
		for(int j=0; j<c; j++) {
			if(map[0][j]) {
				q.add(new Node(0, j, 0));
				map[0][j] = false;
			}
		}
		
		System.out.println(bfs());
	}
	
	static int bfs() {
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			for(int i=0; i<dc; i++) {
				int y = cur.y + dy[i];
				int x = cur.x + dx[i];
				// 배열 범위 초과 및 중복 방문 방지
				if(!isIn(y, x) || !map[y][x]) continue;
				
				// 도착선까지 도달했다면 현재까지의 이동 횟수를 반환 및 출력
				if(y == r-1) return cur.time + 1;
				
				// 도착선이 아닌 위치인 경우 현재 위치를 큐에 등록하고 방문 처리
				q.add(new Node(y, x, cur.time + 1));
				map[y][x] = false;
			}
		}
		
		return -1;
	}
	
	// 배열 범위 초과 방지
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < r && x < c;
	}

}
