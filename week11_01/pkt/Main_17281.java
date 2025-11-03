package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 0. 문제이해: 
 * 번호 순서 = 각 타자 점수 가능 점수.
 * 400 000 000 -> 1이닝 1번주자가 홈런쳐도 나머지는 아웃이라
 * 400 000 000 -> 다음 타자는 5번타자부터 시작이라 1점 밖에 못 얻음
 * 
 * 1. 순열 구현
 * 1) 타순 지정
 * 2) 최대점수 (순열) -> 2루타 후 홈런 점수 != 홈런 후 2루타 점수
 * 
 * 
 * 2. 경기진행 N루에 사람 있냐 없냐에 따라 1,0 지정 ㅇ
 * 
 * 
 */

// 백준 17281 야구공
public class Main_17281 {

	static int[][] a; // 각 이닝별 선수의 결과
	static int[] place; // 타순 (인덱스: 0~8번 타순, 값: 선수 번호)
	static boolean[] check; // 순열 생성을 위한 선수 방문 체크
	static int max = Integer.MIN_VALUE;
	static int n; // 이닝 수

	public static void main(String[] args) throws NumberFormatException, IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		a = new int[n][9];
		check = new boolean[9];
		place = new int[9];

		for (int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < 9; j++) {
				a[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		
		place[3] = 0;  // 4번 타순(인덱스 3)에 1번 선수(인덱스 0) 고정
		check[0] = true; // 1번 선수(인덱스 0)는 순열에서 사용됨을 표시

		dfs(0); // 0번 타순부터 순열 시작

		System.out.println(max);
	}

	// 타순(place)을 정하는 순열(dfs)
	private static void dfs(int depth) {
		if (depth == 9) { // 9명의 타순이 모두 정해졌으면
			score(); // 점수 계산
			return;
		}

		if (depth == 3) { // 4번 타순(인덱스 3)은 이미 1번 선수로 고정했으므로 건너뜀
			dfs(depth + 1);
			return;
		}

		// 1번 선수(0)를 제외한 나머지 선수(1~8)로 순열 생성
		for (int i = 1; i < 9; i++) { 
			if (!check[i]) {
				check[i] = true;
				place[depth] = i; // depth번째 타순에 i번 선수를 배치
				dfs(depth + 1);
				check[i] = false;
			}
		}
	}

	
	private static void score() {
		int score = 0; // 현재 타순으로 얻은 총 점수
		int batterIndex = 0; // 타순 인덱스 (0~8). 이닝이 끝나도 유지되어야 함.

		for (int i = 0; i < n; i++) { // i: 현재 이닝 (0 ~ n-1)
			int out = 0; // 아웃 카운트
			// 베이스 상태: vi[0]=1루, vi[1]=2루, vi[2]=3루
			boolean[] vi = new boolean[3];

			// 3아웃이 될 때까지 이닝 진행
			while (out < 3) {
				// 1. 현재 타자 결정
				int currentPlayer = place[batterIndex]; // 현재 타순(batterIndex)에 있는 선수 번호

				// 2. 타격 결과
				int now = a[i][currentPlayer]; // (i)이닝의 (currentPlayer)선수의 결과

				if (now == 0) { // 아웃
					out++;
				} else if (now == 1) { // 안타
					if (vi[2]) score++; // 3루 주자 홈인
					vi[2] = vi[1]; // 2루 -> 3루
					vi[1] = vi[0]; // 1루 -> 2루
					vi[0] = true;  // 타자 -> 1루
				} else if (now == 2) { // 2루타
					if (vi[2]) score++; // 3루 주자 홈인
					if (vi[1]) score++; // 2루 주자 홈인
					vi[2] = vi[0]; // 1루 -> 3루
					vi[1] = true;  // 타자 -> 2루
					vi[0] = false; // 1루 비움
				} else if (now == 3) { // 3루타
					if (vi[2]) score++; // 3루 주자 홈인
					if (vi[1]) score++; // 2루 주자 홈인
					if (vi[0]) score++; // 1루 주자 홈인
					vi[2] = true;  // 타자 -> 3루
					vi[1] = false; // 2루 비움
					vi[0] = false; // 1루 비움
				} else if (now == 4) { // 홈런
					if (vi[2]) score++; // 3루 주자 홈인
					if (vi[1]) score++; // 2루 주자 홈인
					if (vi[0]) score++; // 1루 주자 홈인
					score++;           // 타자 홈인
					vi[2] = false;
					vi[1] = false;
					vi[0] = false;
				}

				// 3. 다음 타자로 인덱스 이동 (9번 -> 1번 타순으로 순환)
				batterIndex = (batterIndex + 1) % 9;
			}
			// 3 아웃, 이닝 종료. batterIndex는 다음 이닝 시작 타자로 유지됨.
		}
		max = Math.max(score, max); // 모든 이닝 종료 후 최대 점수 갱신
	}
}