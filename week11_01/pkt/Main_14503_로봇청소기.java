package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Robot {
	int x, y, dir, cnt;
	Robot(int x, int y, int dir, int cnt) {
		this.x = x; this.y = y; this.dir = dir; this.cnt = cnt;
	}
}

public class Main_14503_로봇청소기 {

	static int N, M, r, c, dir;
	static int[][] map;
	static int[] dx = {0, 1, 0, -1}; // 북동남서 (0,1,2,3)
	static int[] dy = {-1, 0, 1, 0};

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken()); // 행 (y)
		M = Integer.parseInt(st.nextToken()); // 열 (x)

		st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken()); // 행 (y)
		c = Integer.parseInt(st.nextToken()); // 열 (x)
		dir = Integer.parseInt(st.nextToken()); // 방향

		map = new int[N][M]; // 행 N, 열 M — map[y][x]

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < M; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		Robot robot = new Robot(c, r, dir, 0);
		simulator(robot);
	}

	private static void simulator(Robot robot) {
		while (true) {
			// 1. 현재 칸 청소
			if (map[robot.y][robot.x] == 0) {
				map[robot.y][robot.x] = 2;
				robot.cnt++;
			}

			boolean moved = false;

			// 2. 왼쪽부터 탐색
			for (int t = 0; t < 4; t++) {
				robot.dir = (robot.dir + 3) % 4; // 반시계 회전
				int nx = robot.x + dx[robot.dir];
				int ny = robot.y + dy[robot.dir];
				if (nx < 0 || ny < 0 || nx >= M || ny >= N) continue;
				if (map[ny][nx] == 0) {
					robot.x = nx;
					robot.y = ny;
					moved = true;
					break;
				}
			}

			if (moved) continue;

			// 3. 후진
			int back = (robot.dir + 2) % 4;
			int bx = robot.x + dx[back];
			int by = robot.y + dy[back];

			if (bx < 0 || by < 0 || bx >= M || by >= N || map[by][bx] == 1) {
				System.out.println(robot.cnt);
				return;
			}

			robot.x = bx;
			robot.y = by;
		}
	}
}