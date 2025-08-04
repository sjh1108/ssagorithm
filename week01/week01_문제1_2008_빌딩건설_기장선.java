package ssagorithm;

import java.util.Scanner;

public class week01_문제1_2008_빌딩건설_기장선 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		int T = Integer.parseInt(sc.nextLine());

		// 7 8 9 6 3 2 1 4
		int[] dr = { -1, -1, -1, 0, 1, 1, 1, 0 };
		int[] dc = { -1, 0, 1, 1, 1, 0, -1, -1 };

		for (int t = 1; t <= T; t++) {
			int N = Integer.parseInt(sc.nextLine());
			char[][] sq = new char[N][N];
			for (int i = 0; i < N; i++) {
				String[] row = sc.nextLine().split(" ");
				for (int j = 0; j < N; j++) {
					sq[i][j] = row[j].charAt(0);
					System.out.println(sq[i][j]);
				}
			}
			int maxHeight = 0;

			// 모든 위치 탐색
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					if (sq[r][c] != 'B')
						continue;
					// 해당 위치 G면 다음 위치 넘어가기

					boolean yesG = false;

					// 방향 확인하기
					for (int d = 0; d < 8; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];

						if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
							if (sq[nr][nc] == 'G') {
								yesG = true;
								break;
								// G 있으면 반복문 종료하기
							}
						}
					}
					// 해당 위치 구분 끝
					
					// 계산
					if (yesG) {
						maxHeight = Math.max(maxHeight, 2);
					} else {
						int count = 0;

						for (int i = 0; i < N; i++) {
							if (sq[r][i] == 'B')
								count++;
						}

						for (int i = 0; i < N; i++) {
							if (sq[i][c] == 'B')
								count++;
						}

						count--;
						maxHeight = Math.max(maxHeight, count);
					}
				}
			}

			System.out.println("#" + t + " " + maxHeight);

		}

	}

}
