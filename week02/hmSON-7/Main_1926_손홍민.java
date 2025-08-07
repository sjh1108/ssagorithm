package silver_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1926_손홍민 {
	
	/**
	 * BFS를 혼용한 배열 완전 탐색 문제
	 * 모든 배열을 돌면서 그림이 그려진 구역(1)을 발견하면 그 좌표를 기점으로 BFS를 시작
	 * 탐색 과정에서 이미 지나간 구역의 값을 0으로 변경하여 중복 방지
	 * 배열 순회 과정중 1인 구역을 발견할 때마다 drawCnt 증가
	 * BFS로 탐색한 구역의 수를 모두 카운트하고, 탐색 종료 후 maxArea 값 갱신
	*/

	// 각각 맵의 세로, 가로, 그림 개수 카운트, 가장 넓은 그림의 넓이
	static int r, c, drawCnt = 0, maxArea = 0;
	// 맵 정보. 0과 1만 취급하므로 메모리 절약을 위해 boolean 타입 사용
	static boolean[][] map;
	// 델타 배열. 각각 북 동 남 서 순서임
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	// BFS 탐색용 큐
	static Queue<int[]> q = new ArrayDeque<>();
	
	public static void main(String[] args) throws Exception {
		init(); solve();
	}
	
	// 맵 정보 초기화 메서드
	// 맵의 크기를 입력받은 후 각 구역의 값이 1이면 true, 0이면 false 입력
	public static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new boolean[r][c];
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				map[i][j] = st.nextToken().equals("1") ? true : false;
			}
		}
	}
	
	// 배열 완전 탐색 및 결과 출력 메서드
	// 그림이 존재하면 bfs 탐색 시작
	// 배열 순회 종료 후 그림의 최대 넓이 출력
	public static void solve() {
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				// false가 존재하는 칸은 무시
				if(!map[i][j]) continue;
				// true인 칸이 존재하므로 그림 수 카운트 및 BFS 시작
				drawCnt++;
				int area = bfs(i, j);
				// 결과 갱신
				if(area > maxArea) maxArea = area;
			}
		}
		System.out.println(drawCnt + "\n" + maxArea);
	}
	
	// BFS 탐색 메서드
	// 초기 좌표를 받아 큐에 입력하고 큐가 빌 때까지 탐색 수행
	// 탐색 과정에서 조건에 맞는 지역을 탐색한 경우 넓이 카운트
	public static int bfs(int y, int x) {
		int cnt = 1;
		q.add(new int[]{y, x});
		// 이미 탐색한 위치는 false로 변경 -> 중복 방지
		map[y][x] = false;
		while(!q.isEmpty()) {
			// 현재 접근한 위치의 좌표
			int[] curr =q.poll();
			int currY = curr[0], currX = curr[1];
			// 4방향 탐색
			for(int i=0; i<4; i++) {
				int newY = currY + dy[i];
				int newX = currX + dx[i];
				// 바라보는 지역이 맵 외부거나, 그림이 없는 구역(0)이면 무시
				if(!isIn(newY, newX) || !map[newY][newX]) continue;
				// 중복 방지를 위한 그림 제거
				map[newY][newX] = false;
				q.add(new int[] {newY, newX});
				// 넓이 카운트
				cnt++;
			}
		}
		
		// BFS 탐색 종료. 해당 그림의 넓이를 solve()로 반환
		return cnt;
	}
	
	// 배열 범위 초과 방지 메서드
	public static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < r && x < c;
	}
	
}
