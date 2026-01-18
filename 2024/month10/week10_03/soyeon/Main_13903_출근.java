package bk.silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_13903_출근 {

	/**
	 * 문제 이해
	 * - 시작은 첫번째 row 세로 블록 중 아무곳 
	 * - 세로 블록만 밟기 
	 * - 특정 규칙으로 이동
	 * - 마지막 row에 도착하면 출근 성공
	 * - 최소 걸음 
	 * 
	 * 입력
	 * - 보도블럭 R, C
	 * - 가로블럭 0, 세로블럭 1
	 * - 이동가능 규칙 개수
	 * - N개줄 - 규칙
	 * 
	 * 출력
	 * - 최소 몇 걸음 or -1 (출근불가) 
	 * 
	 * 후기 
	 * - 답을 봄.
	 * - 큐에 삽입되는 데이터를 배열로 하는 방법을 몰랐음
	 * - 시작점이 여러개라고 bfs를 여러번 돌리면 안 됨. 그냥 큐에 넣기
	 * - bfs는 가장 먼저 도달하는 게 최소거리므로 따로 갱신할 필요 x 
	 */
	
	static int R, C, N;
	static int min = Integer.MAX_VALUE;
	static int[][] map;
	static boolean[][] visited;
	static int[] dr;
	static int[] dc;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		map = new int[R+2][C+2];
		visited = new boolean[R+2][C+2];
		for(int r = 1; r < R+1; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 1; c < C+1; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		
		N = Integer.parseInt(br.readLine());
		
		dr = new int[N];
		dc = new int[N];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());

			dr[i] = Integer.parseInt(st.nextToken());
			dc[i] = Integer.parseInt(st.nextToken());
		}
		// 입력끝
	
		// bfs를 모든 시작점마다 따로 돌려서 시간초과 발생
//		for(int c = 1; c < C+1; c++) {
//			if(map[1][c] == 0) continue; // 가로면 ㅂㅂ 
//			bfs(c); // 시작 좌표 c 찾기 
//		}
		bfs();
		
		if(min == Integer.MAX_VALUE) {
			System.out.println(-1);
		}else {
			System.out.println(min);
		}
	}
	
	private static void bfs() {
		Queue<int[]> q = new ArrayDeque<>();
		
		// 오답노트 : 이거 왜 여기 있어야 하는지 확인 
		for(int c = 1; c < C+1; c++) {
			if(map[1][c] == 1) {
				q.add(new int[] {1,c, 0}); //배열 add할때 이렇게 해야하는지 몰라서 검색함
				// depth도 추가해줌
				visited[1][c] = true;
			}
		}
		
		while(!q.isEmpty()) {
			
			int[] now = q.poll();

			for(int i = 0; i < N; i++) {
				
				int nr = now[0] + dr[i];
				int nc = now[1] + dc[i];
				int nDepth = now[2] + 1;
				
				if(check(nr, nc) && map[nr][nc] == 1 && !visited[nr][nc]) {
				
					if(nr == R ) {
				
						min = nDepth;
						return; //min 갱신 로직이 불필요하대. BFS는 가장 먼저 도착한 경로가 최소 거리 
					}
					q.add(new int[] {nr, nc, nDepth});
					visited[nr][nc] = true;		
				}
			}
			
		}	
	}

	private static boolean check(int nr, int nc) {
		return nr > 0 && nc > 0 && nr < R+1 && nc < C+1;
	}

}
