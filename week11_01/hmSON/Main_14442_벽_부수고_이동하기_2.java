import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14442_벽_부수고_이동하기_2 {
	
	/*
	 * BOJ_14442 : 벽 부수고 이동하기 2(Gold_3)
	 * 자료구조 및 알고리즘 : BFS
	 * 
	 * (0, 0)부터 (r-1, c-1)까지의 최단 거리를 구해야 한다. 시작점과 도착점도 거리에 포함된다.
	 * 벽이 있는 곳으로는 이동할 수 없으나, 최대 K번까지는 벽을 부수고 이동할 수 있다.
	 * 도착할 수 있는 경우 최단거리를 , 그렇지 않은 경우 -1을 출력한다.
	 * 
	 * 3차원 boolean 타입 배열이 필요할 것으로 보인다. 
	 * 같은 좌표더라도 벽을 몇 번 부순 뒤 이동했는지에 대해 구분해줄 필요가 있어 보인다.
	 * K의 최대값이 10밖에 안되므로, 3차원 boolean 배열 대신 2차원 int 배열과 비트마스킹으로 대체한다.
	 */
	
	// 맵의 세로와 가로 크기, 부술 수 있는 벽의 수
	static int r, c, limit;
	// 방문 처리를 위한 배열. 벽을 부순 횟수별 방문 처리를 비트마스킹 처리
	static int[][] visited;
	// 맵 정보. 0은 빈 공간, 1은 벽
	static boolean[][] map;
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static class Node {
		// 현재의 좌표, 이동 거리, 지금까지 벽을 부순 횟수
		int y, x, cost, breakCnt;
		
		public Node(int y, int x, int cost, int breakCnt) {
			this.y = y;
			this.x = x;
			this.cost = cost;
			this.breakCnt = breakCnt;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		limit = Integer.parseInt(st.nextToken());
		
		visited = new int[r][c];
		map = new boolean[r][c];
		
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				map[i][j] = line.charAt(j) == '1';
			}
		}
	}
	
	// BFS로 이동 거리와 벽을 부순 횟수를 추적하면서 최단 거리를 구한다.
	static int bfs() {
		Queue<Node> q = new ArrayDeque<>();
		// 초기값 : 좌표 (0, 0), 이동 거리 1, 벽을 부순 횟수 0
		q.add(new Node(0, 0, 1, 0));
		visited[0][0] |= 1;
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			for(int i=0; i<4; i++) {
				int ny = cur.y + dy[i];
				int nx = cur.x + dx[i];
				
				// 배열 범위 초과 방지
				if(ny < 0 || nx < 0 || ny >= r || nx >= c) continue;
				
				// 도착점에 도달한 경우 총 이동 거리 반환
				if(ny == r-1 && nx == c-1) return cur.cost+1;
				
				/*
				 * 1. 다음 위치가 벽인 경우 :
				 * 이미 K번 벽을 부쉈거나, 동일한 파괴 횟수로 이미 방문한 곳이면 무시
				 * 아니라면 벽 파괴 횟수와 이동 거리 카운트
				 * 
				 * 2. 다음 위치가 빈 공간인 경우 :
				 * 동일한 파괴 횟수로 이미 방문한 곳이면 무시
				 * 아니라면 이동 거리만 카운트
				 */
				if(map[ny][nx]) {
					if(cur.breakCnt == limit || (visited[ny][nx] & (1 << cur.breakCnt+1)) != 0) continue;
					
					visited[ny][nx] |= (1 << (cur.breakCnt+1));
					q.add(new Node(ny, nx, cur.cost+1, cur.breakCnt+1));
				} else {
					if((visited[ny][nx] & (1 << cur.breakCnt)) != 0) continue;
					
					visited[ny][nx] |= (1 << (cur.breakCnt));
					q.add(new Node(ny, nx, cur.cost+1, cur.breakCnt));
				}
			}
		}
		
		// 무슨 수를 써도 도착점에 도달하지 못한 경우 -1 반환
		return -1;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 시작점 == 도착점인 경우 -> 이동 거리 1로 간주
		if(r == 1 && c == 1) {
			System.out.println(1);
			return;
		}
		
		System.out.println(bfs());
	}
	
}
