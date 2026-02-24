import java.io.*;
import java.util.*;

public class Main_3109_빵집 {

	/*
	 * BOJ_3109 : 빵집 (Gold_2)
	 * 자료구조 및 알고리즘 : DFS, 그리디
	 *
	 * [문제 요약]
	 * - R×C 격자에서 각 행의 0열(왼쪽 끝)에서 시작해 C-1열(오른쪽 끝)까지 파이프를 연결하려 한다.
	 * - 이동은 오른쪽 위 / 오른쪽 / 오른쪽 아래(총 3방향)만 가능하다.
	 * - 장애물('x')이 있는 칸은 지나갈 수 없고, 파이프끼리 겹치면 안 된다.
	 * - 설치할 수 있는 파이프(=서로 겹치지 않는 경로)의 최대 개수를 구한다.
	 *
	 * [핵심 아이디어]
	 * - 목표는 "최대 개수"이므로, 한 경로가 다른 경로의 공간을 최대한 덜 방해하게 만드는 선택이 중요하다.
	 * - 이 문제는 각 행에서 시작해 가능한 경로를 찾되,
	 *   '오른쪽 위 -> 오른쪽 -> 오른쪽 아래' 순서로 먼저 탐색하는 것이 그리디하게 최적이다.
	 *   이유: 위쪽 경로를 먼저 확보하면 아래쪽 경로들의 선택지가 더 많이 남는 경향이 있어
	 *   전체적으로 연결 가능한 개수를 최대화할 수 있다.
	 * - 또한 한 번 방문(사용)한 칸은 이후 어떤 경로에서도 재사용할 수 없으므로,
	 *   방문 처리는 "복구 없이" 막아버리는 방식으로 진행한다.
	 *
	 * [구현 메모]
	 * - map[y][x] = true는 (원래 장애물이거나) 이미 파이프가 사용한 칸으로 취급한다.
	 * - dfs(y, x):
	 *   - x가 마지막 열(c-1)에 도달하면 해당 시작 행의 파이프 설치 성공.
	 *   - 다음 칸 (y+i, x+1)을 i=-1..1 순서로 탐색(위 -> 가운데 -> 아래).
	 *   - 이동 가능한 칸을 찾으면 즉시 map을 true로 만들어 점유 처리 후 재귀 진행.
	 *   - flag를 이용해 "한 시작점에서 경로 하나만" 찾으면 더 깊은/다른 분기 탐색을 중단한다.
	 *
	 * [시간 복잡도]
	 * - 각 칸은 최대 한 번만 점유(방문)되며, 방문 후에는 다시 시도하지 않는다.
	 * - 전체적으로 O(R * C) 수준에서 동작한다. (각 칸에서 상수(3) 방향만 확인)
	 */

	static int r, c;
	static boolean[][] map;
	static boolean flag = false;

	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());

		map = new boolean[r][c];
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				map[i][j] = line.charAt(j) == 'x'; // true면 막힌 칸(장애물)
			}
		}
	}

	static void dfs(int y, int x) {
		// 마지막 열에 도달하면 해당 시작점의 파이프 설치 성공
		if(x == c-1) {
			flag = true;
			return;
		}

		// 그리디 우선순위: 오른쪽 위(-1) -> 오른쪽(0) -> 오른쪽 아래(+1)
		for(int i=-1; i<=1; i++) {
			// 다음 칸은 항상 x+1 열
			if(y+i < 0 || y+i >= r || map[y+i][x+1]) continue;

			// 방문(점유) 처리: 이후 경로에서 겹치지 않도록 막아버린다(되돌리지 않음)
			map[y+i][x+1] = true;
			dfs(y+i, x+1);

			// 한 번 성공 경로를 찾았으면 더 탐색할 필요가 없다.
			if(flag) return;
		}
	}

	public static void main(String[] args) throws Exception {
		init();

		int cnt = 0;
		for(int i=0; i<r; i++) {
			dfs(i, 0);
			if(flag) {
				cnt++;
				flag = false; // 다음 시작 행을 위해 성공 플래그 초기화
			}
		}

		System.out.println(cnt);
	}

}
