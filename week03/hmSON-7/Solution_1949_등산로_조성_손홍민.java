package non_ranked;

import java.io.*;
import java.util.*;

public class Solution_1949 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 각각 맵의 크기, 공사 가능한 최대 깊이, 가장 높은 봉우리의 높이, 등산로 최장거리
	// N의 크기가 작아 visited는 비트마스킹으로 처리
	// list : 가장 높은 봉우리 좌표 리스트
	static int n, depth, highest, maxLen;
	static int[] dy = {-1, 0, 0, 1}, dx = {0, -1, 1, 0};
	static int[] visited;
	static int[][] map;
	static List<int[]> list = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		int t = Integer.parseInt(br.readLine());
		for(int i=1; i<=t; i++) {
			init();
			sb.append("#").append(i).append(" ");
			search();
			sb.append(maxLen).append("\n");
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		depth = Integer.parseInt(st.nextToken());
		map = new int[n][n];
		highest = 0;
		
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			// 맵 정보 입력
			// 가장 높은 봉우리를 발견하면 최대 높이 및 리스트 갱신
			for(int j=0; j<n; j++) {
				int x = Integer.parseInt(st.nextToken());
				map[i][j] = x;
				if(x > highest) {
					highest = x; 
					list.clear();
					list.add(new int[] {i, j});
				} else if(x == highest) {
					list.add(new int[] {i, j});
				}
			}
		}
	}
	
	static void search() {
		maxLen = 0;
		visited = new int[n];
		for(int[] top : list) {
			visited[top[0]] |= (1 << top[1]);
			dfs(top[0], top[1], 1, highest, false);
			visited[top[0]] &= ~(1 << top[1]);
		}
	}
	
	static void dfs(int y, int x, int cnt, int curH, boolean flag) {
		for(int i=0; i<4; i++) {
			int ny = y + dy[i], nx = x + dx[i];
			// 배열 범위 초과인가? or 이미 방문한 위치인가?
			if(!isIn(ny, nx) || (visited[ny] & (1<<nx)) != 0) continue;
			
			// 현재 위치의 봉우리보다 높은가?
			if(map[ny][nx] >= curH) {
				// depth 깊이만큼 봉우리를 깎으면 등산로 연장이 가능한가?
				if(!flag && map[ny][nx] - depth < curH) {
					// 봉우리를 깎으면 충분히 등산로 연장 가능
					visited[ny] |= (1<<nx);
					dfs(ny, nx, cnt+1, curH-1, true);
					visited[ny] &= ~(1<<nx);
				} else continue;
			} else {
				// 현재 위치의 봉우리보다 낮으므로 등산로 연장
				visited[ny] |= (1<<nx);
				dfs(ny, nx, cnt+1, map[ny][nx], flag);
				visited[ny] &= ~(1<<nx);
			}
		}
		// 더 이상 갈 곳이 없으면 지금까지 연장한 등산로의 최대 길이 갱신
		if(cnt > maxLen) maxLen = cnt;
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < n && x < n;
	}
	
}
