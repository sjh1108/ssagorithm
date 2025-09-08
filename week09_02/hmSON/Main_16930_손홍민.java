package platinum_3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16930 {
	
	static int r, c, dist, sy, sx, fy, fx;
	static int[][] visited;
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

	public static void main(String[] args) throws Exception {
		init();
		System.out.println(bfs());
	}
	
	// 현재 위치의 좌표와 경과 시간을 관리하는 클래스
	static class Node {
		int y, x, elapsed;
		
		public Node(int y, int x, int elapsed) {
			this.y = y;
			this.x = x;
			this.elapsed = elapsed;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		dist = Integer.parseInt(st.nextToken());
		visited = new int[r][c];
		
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				// 벽은 -1로, 평지는 0으로
				visited[i][j] = line.charAt(j) == '#' ? -1 : 0;
			}
		}
		
		st = new StringTokenizer(br.readLine());
		sy = Integer.parseInt(st.nextToken())-1;
		sx = Integer.parseInt(st.nextToken())-1;
		fy = Integer.parseInt(st.nextToken())-1;
		fx = Integer.parseInt(st.nextToken())-1;
	}
	
	static int bfs() {
		// 시작점과 도착점이 동일한가? 0초 리턴
		if(sy == fy && sx == fx) return 0;
		
		Queue<Node> q = new ArrayDeque<>();
		q.add(new Node(sy, sx, 0));
		// 초기 위치 방문 처리. 단, 아직 이동하지 않은 공간인 0과 구별하기 위하여 초기 위치는 벽과 동일한 -1로 설정
		visited[sy][sx] = -1;
		while(!q.isEmpty()) {
			Node cur = q.poll();
			for(int i=0; i<4; i++) {
				int ny = cur.y, nx = cur.x;
				// 현재 위치에서 이동할 수 있는 모든 칸 확인
				for(int j=0; j<dist; j++) {
					ny += dy[i];
					nx += dx[i];
					// 배열 범위 밖이거나 벽인 경우 해당 방향은 탐색 중지
					if(!isIn(ny, nx) || visited[ny][nx] == -1) break;
					
					// 도착점에 갈 수 있는 상태라면 경과 시간 반환
					if(ny == fy && nx == fx) return cur.elapsed + 1;
					
					// 이동하려는 위치를 방문한 상태로 표기하고, 큐에 해당 좌표와 경과 시간 삽입
					if(visited[ny][nx] == 0) {
						visited[ny][nx] = cur.elapsed + 1;
						q.add(new Node(ny, nx, cur.elapsed + 1));
					} else if(visited[ny][nx] == cur.elapsed + 1) continue;
					else break;
				}
			}
		}
		
		// 도착점으로 이동 가능한 방법이 없었을 경우
		return -1;
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < r && x < c;
	}
	
}
