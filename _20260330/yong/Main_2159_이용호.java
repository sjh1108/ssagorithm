package _20260330.yong;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2159_이용호 {

	static final int[][] dt = {{-1, 0}, {1, 0}, {0, 0}, {0, -1}, {0, 1}}; // 상 하 중앙 좌 우
	static final int LIMIT = 100000;
	static final long INF = Long.MAX_VALUE / 4;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		StringTokenizer st = new StringTokenizer(br.readLine());
		int startX = Integer.parseInt(st.nextToken());
		int startY = Integer.parseInt(st.nextToken());

		int[][] customer = new int[N][2]; // 손님 좌표
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			customer[i][0] = Integer.parseInt(st.nextToken());
			customer[i][1] = Integer.parseInt(st.nextToken());
		}

		long[] prev = new long[5]; // 이전 손님 5방향 최소 이동거리
		for (int i = 0; i < 5; i++) {
			prev[i] = INF;
		}

		// 첫 번째 손님 인접 5칸까지의 최소 거리
		for (int d = 0; d < 5; d++) {
			int nx = customer[0][0] + dt[d][0];
			int ny = customer[0][1] + dt[d][1];

			if (!isValid(nx, ny)) continue;

			prev[d] = dist(startX, startY, nx, ny);
		}

		// 두 번째 손님부터 DP 진행
		for (int i = 1; i < N; i++) {
			long[] cur = new long[5];
			for (int j = 0; j < 5; j++) {
				cur[j] = INF;
			}

			for (int prevDir = 0; prevDir < 5; prevDir++) {
				if (prev[prevDir] == INF) continue;

				int px = customer[i-1][0] + dt[prevDir][0];
				int py = customer[i-1][1] + dt[prevDir][1];

				if (!isValid(px, py)) continue;

				for (int curDir = 0; curDir < 5; curDir++) {
					int cx = customer[i][0] + dt[curDir][0];
					int cy = customer[i][1] + dt[curDir][1];

					if (!isValid(cx, cy)) continue;

					cur[curDir] = Math.min(cur[curDir], prev[prevDir] + dist(px, py, cx, cy));
				}
			}

			prev = cur;
		}

		long answer = INF;
		for (int i = 0; i < 5; i++) {
			answer = Math.min(answer, prev[i]);
		}

		System.out.println(answer);
	}

	static boolean isValid(int x, int y) {
		return x >= 0 && y >= 0 && x <= LIMIT && y <= LIMIT;
	}

	static int dist(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
}