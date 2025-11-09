package swea.d4; // 보급로

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 2차원 배열의 map
 * - 상하좌우 1칸 이동
 * - 출발지(S) 좌상단
 * - 도착지(G) 우하단
 * 
 * - 0인 곳은 이동가능.
 * - 그 외는 이동하기 위해 도로 복구 작업  필요
 * - 깊이 1이면 복구에 드는 시간도 1
 * 
 * - 이동시간은 복구에 비해 너무 적은 시간이라 고려 x
 * - 때문에 S->G까지 가는 거리를 고려하지 않는다
 * 
 * 입력
 * - TC : 테케 수 
 * - N : 지도 NxN (최대 100x100)
 * - 지도정보
 * 
 * 출력
 * - #tc 가장 적은 복구시간 
 *
 * 전략
 * - 최소 비용 완탐?
 * - dx dy
 * 
 */
public class Solution_1249_보급로 {

	static int T, N;
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1};
	static int[][] map;
	static int[][] dist;
	static StringBuilder sb = new StringBuilder();
	// 웅니 화이팅~~
	static class Node implements Comparable<Node>{
		int r;
		int c;
		int cost;
		
		public Node(int r, int c, int cost) {
			super();
			this.r = r;
			this.c = c;
			this.cost = cost;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static void main(String[] args) throws Exception {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		T = Integer.parseInt(br.readLine());
		
		for(int tc = 1; tc <= T; tc++) {
			N = Integer.parseInt(br.readLine());
			map = new int[N][N];
			dist = new int[N][N];
			
			for(int r = 0; r < N; r++) {
				String input = br.readLine();
				for(int c = 0; c < N; c++) {
					map[r][c] = input.charAt(c) - '0';
				}
			} // 입력완료
			
			for(int[] d : dist) {
				Arrays.fill(d, Integer.MAX_VALUE); // 무한대로 초기화
			}
			
			 
			dijkstra();
			
			sb.append("#").append(tc).append(" ").append(dist[N-1][N-1]).append("\n");
			
		}
		System.out.println(sb);
		
	}
	private static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[0][0] = 0;
		pq.offer(new Node(0,0,0));
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int r = now.r;
			int c = now.c;
			int cost = now.cost;
			
			if(cost > dist[r][c]) continue; // visited 처리 대신 이걸 사용
			
			for(int d = 0; d < 4; d++) {
				int nr = r + dr[d];
				
				int nc = c + dc[d];
				
				if(nr >= 0 && nc >= 0 && nr < N && nc < N) {
					
					if(dist[nr][nc] > cost + map[nr][nc]) {
						dist[nr][nc] = cost + map[nr][nc];
						pq.offer(new Node(nr, nc, dist[nr][nc]));	
					}
					
				}
			}
		}
		
	}
}
