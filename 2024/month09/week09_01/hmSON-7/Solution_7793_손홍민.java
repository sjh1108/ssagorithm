package d5;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_7793 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 맵 크기, 초기 좌표
	static int r, c, sy, sx;
	// 맵 정보(시작점, 도착점, 벽, 평지, 오염 구역)
	static char[][] map;
	// 방문 구역 표기
	static boolean[][] visited;
	// 오염 구역 확장 시간대 표기
	static int[][] polluted;
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0 ,-1};
	
	// 오염구역 확장을 위한 BFS용 큐
	static Queue<Node> pollution = new ArrayDeque<>();
	
	static class Node {
		int y, x, time;
		
		public Node(int y, int x, int time) {
			this.y = y;
			this.x = x;
			this.time = time;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		for(int i=1; i<=tc; i++) {
			init();
			// 미리 구역별 오염 시간 체크
			pollute();
			int time = bfs();
			
			// 시간이 출력된다면 그대로 출력, -1이 출력된다면 게임 오버
			sb.append("#" + i + " ")
			.append(time == -1 ? "GAME OVER" : time)
			.append("\n");
			
			pollution.clear();
		}
		
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new char[r][c];
		visited = new boolean[r][c];
		polluted = new int[r][c];
		for(int i=0; i<r; i++) {
			Arrays.fill(polluted[i], Integer.MAX_VALUE);
		}
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				char ch = line.charAt(j);
				map[i][j] = ch;
				
				// 시작점 발견시 초기 좌표 저장
				// 오염 지역 발견시 큐에 확장 시간 0초로 저장
				if(ch == 'S') {
					sy = i; sx = j;
				} else if(ch == '*') {
					pollution.add(new Node(i, j, 0));
					polluted[i][j] = 0;
				}
			}
		}
	}
	
	// 이동 전 미리 구역별 오염 시간 측정
	static void pollute() {
		while(!pollution.isEmpty()) {
			Node cur = pollution.poll();
			for(int i=0; i<4; i++) {
				int ny = cur.y + dy[i];
				int nx = cur.x + dx[i];
				if(!isIn(ny, nx)) continue;
				if(map[ny][nx] == 'X' || map[ny][nx] == 'D' || polluted[ny][nx] != Integer.MAX_VALUE) continue;
				pollution.add(new Node(ny, nx, cur.time+1));
				polluted[ny][nx] = cur.time+1;
			}
		}
	}
	
	// 이동 시간 측정을 위한 BFS
	// 성역(D)에 도착하면 소요 시간 반환, 도착할 수 없는 경우 -1 반환
	static int bfs() {
		Queue<Node> q = new ArrayDeque<>();
		q.add(new Node(sy, sx, 0));
		visited[sy][sx] = true;
		while(!q.isEmpty()) {
			Node cur = q.poll();
			for(int i=0; i<4; i++) {
				int ny = cur.y + dy[i];
				int nx = cur.x + dx[i];
				
				// 이미 지나갔거나, 오염으로 인해 움직일 수 없다면 무시
				if(!isIn(ny, nx) || visited[ny][nx] || polluted[ny][nx] <= cur.time+1) continue;
				char next = map[ny][nx];
				
				// 이동 불가능한 구역이라면
				if(next == 'X' || next == '*') continue;
				
				// 성역이 눈앞에 있다면 즉시 시간 반환 및 종료
				if(next == 'D') return cur.time + 1;
				
				// 이동할 수 있는 공간이라면
				q.add(new Node(ny, nx, cur.time + 1));
				visited[ny][nx] = true;
			}
		}
		
		return -1;
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < r && x < c;
	}

}
