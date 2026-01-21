package com.codingtest.bk;

import java.io.*;
import java.util.*;

/**
 * 문제 이해
 * - 상하좌우 인접한 덩어리가 몇개냐.
 * - BFS/DFS 뭐든 써서 더 탐색이 안 되면 멈추고 cnt + 1하면 될 듯
 * 
 * 입력
 * - T
 * - M : 가로 길이 (열)
 * - N : 세로 길이 (행)
 * - K : 배추 개수
 * - 좌표 X Y 
 * 
 * 출력
 * - 테케별 최소 지렁이수 
 * 
 * 후기  
 * - x,y와 r,c 헷갈리는 문제로 시간 잡아먹고 
 * - 방문처리 위치와 ans 카운팅 문제로 스스로 풀지 못함 
 * - 방문처리배열이 static 이니까 재귀 내부가 아니라 2중 for문에서도 유효하다는 걸 잊지말기
 * 
 * - 그리고 이런건 BFS가 정석인듯. 담에 BFS로 풀어보자 
 * - DFS는 재귀 깊이 때문에 배추밭이 클 때 StackOverflowError가 날 수도 있대 
 *
 */
public class Main_1012_유기농배추 {

	static int M, N, K;
	static int[][] map;
	static boolean[][] visited;
	static int[] dr = {-1, 1, 0, 0}; //일단 4방향이라니까 만들어 
	static int[] dc = {0, 0, -1, 1};
	
	static int ans; // 지렁이 수 
	
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			st = new StringTokenizer(br.readLine());
			M = Integer.parseInt(st.nextToken()); // 가로 (열)
			N = Integer.parseInt(st.nextToken()); // 세로 (행) 
			K = Integer.parseInt(st.nextToken());
			
			map = new int[N][M];
			visited = new boolean[N][M];
			
			for(int k = 0; k < K; k++) {
				st = new StringTokenizer(br.readLine());
				
				int x = Integer.parseInt(st.nextToken()); // 열
				int y = Integer.parseInt(st.nextToken()); // 행 
				
				// 오답노트 : x,y랑 c,r은 반대다. 진짜 이거때문에 시간 많이 잡아먹음
				// 				어떻게해야 안 헷갈릴까? 
				map[y][x] = 1;
			}// 입력끝 
			
			
			ans = 0; // 초기화
			
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < M; c++) {
					if(map[r][c] == 1 && !visited[r][c]) {
						dfs(r, c);
						ans++; // 오답노트 : 여기서 더해주기.
					}
				}
			}
			
			sb.append(ans).append("\n");
		}
		System.out.println(sb);
		
	}

	// 방문처리하려는 거임. 
	private static void dfs(int curR, int curC) {
		// 조건에 맞지 않으면 저절로 종료 
		
		visited[curR][curC] = true; // 방문처리는 dfs들어오자마자 하자 
		
		for(int d = 0; d < 4; d++) {
			int nr = curR + dr[d];
			int nc = curC + dc[d];
			
			// 범위를 벗어나지 않아야함 
			if(nr < 0 || nc < 0 || nr >= N || nc >= M ) continue; 
			
			// 배추면서 방문하지 않은 곳 계속 탐색 
			if(map[nr][nc] == 1 && !visited[nr][nc]) {
				dfs(nr, nc);
			}
			
		}
	}
}
