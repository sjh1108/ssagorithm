package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_13930_이용호 {
/*
 * 세로 블록만 밟는다
 * 특정 규칙으로 이동한다
 * 첫 번째 row 에서 출발 -> 마지막 row에 도착하면 출근 성공
 * 최소한 걸음으로 출근
 */
	static int[][] dt, map;
	static int N, R, C;
	static class Node{
		int r, c, dist;
		Node(int r, int c, int dist){
			this.r = r;
			this.c = c;
			this.dist = dist;
		}
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		map = new int[R][C];
		for(int i = 0; i < R; i ++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()); //가로 0, 세로 1
			}
		}
		
		//이동 가능한 규칙
		N = Integer.parseInt(br.readLine());
		dt = new int[N][2];
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			dt[i][0] = Integer.parseInt(st.nextToken());
			dt[i][1] = Integer.parseInt(st.nextToken());
		}
		
		int answer = bfs();
		
		System.out.println(answer);
		
	}
	
	public static int bfs() {
		boolean[][] visited = new boolean[R][C];
		Queue<Node> q =  new LinkedList<>();
		
		for(int col = 0; col < C; col++) {
			if(map[0][col] == 0) continue;
			q.offer(new Node(0,col,0));
			visited[0][col] = true;
		}
		
		while(!q.isEmpty()) {
			Node now = q.poll();
			
			// 출근 완료시 종료(마지막행 도달)
			if(now.r == R - 1) return now.dist;
			
			for(int[] next : dt) {
				int nr = now.r + next[0];
				int nc = now.c + next[1];
				
				if(nr < 0 || nc < 0 || nc >= C || nr >= R) continue;
				if(visited[nr][nc] || map[nr][nc] == 0) continue;
				
				visited[nr][nc] = true;
				q.offer(new Node(nr, nc, now.dist + 1));
			}
		}
		return -1;
	}

}
