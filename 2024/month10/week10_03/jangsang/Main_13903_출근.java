package algo2025_10_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_13903_출근 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int[][] map;
	static int R,C,N;
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		R = Integer.parseInt(tokens.nextToken());
		C = Integer.parseInt(tokens.nextToken());
		map = new int[R][C];
		
		for (int i = 0; i < R; i++) {
			tokens = new StringTokenizer(input.readLine());
			for (int j = 0; j < C; j++) {
				map[i][j] = Integer.parseInt(tokens.nextToken());
			}
		}
		N = Integer.parseInt(input.readLine());
		int[][] dir = new int[N][2];
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
		    dir[i][0] = Integer.parseInt(tokens.nextToken()); // x 좌표
		    dir[i][1] = Integer.parseInt(tokens.nextToken()); // y 좌표
		}
		
		System.out.println(bfs(dir)); 
	}

	static int bfs(int[][] dir) {
		boolean[][] visited = new boolean[R][C];
		Queue<int[]> q = new LinkedList<>();

		//출발 가능한 칸들을 모두 큐에 add
		for (int c = 0; c < C; c++) {
			if (map[0][c] == 1) {
				q.offer(new int[] {0, c, 0}); // (r, c, 거리)
				visited[0][c] = true;
			}
		}

		while (!q.isEmpty()) {
			int[] now = q.poll();
			int r = now[0];
			int c = now[1];
			int dist = now[2];

			// 마지막 행(R-1)에 도착하면 거리 반환
			if (r == R - 1) {
				return dist;
			}

			for (int[] d : dir) {
				int nr = r + d[0];
				int nc = c + d[1];

				if (nr < 0 || nc < 0 || nr >= R || nc >= C) continue;
				if (map[nr][nc] == 0 || visited[nr][nc]) continue;

				visited[nr][nc] = true;
				q.offer(new int[] {nr, nc, dist + 1});
			}
		}
		return -1; // 출근 불가시 -1
	}
}