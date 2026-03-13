package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16234_이용호 {
/*
 * N * N 땅 r행 c열에 A[r][c]명 사는중
 * 금일 부터 인구 이동 시작
 * 국경선을 공유하는 공유하는 두 나라 인구차이가 L 이상 R 이하라면 국경 개방
 * 조건에 맞는 국경이 다 열렸다면 인구 이동
 * 연결되면 연합이라고 하고, 연합을 이루는 각 칸의 인구수는 연합의 인구수 / 연합의 크기
 * 
 * 조건 체크 하면서 bfs 돌리기
 * 돌면서 해당 국가 연합에 추가
 * bfs 큐 크기로 연합 크기 체크 + 국가 크기 재설정
 */
	public static class Point{
		int r; int c;
		Point(int r, int c){
			this.r = r;
			this.c = c;
		}
	}
	static int N, L, R;
	static int[][] map;
	static boolean[][] visited;
	static int[][] dt = {{1,0},{-1,0},{0,-1},{0,1}}; // 상, 하, 좌, 우
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// N L R 입력
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		
		map = new int[N][N];
		
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());

			}
		}
		int days = 0;
		while(true) {
			visited = new boolean[N][N];
			boolean isMoved = false;
			
			for(int r = 0; r < N; r++) {
				for(int c = 0; c < N; c++) {
					if(visited[r][c]) continue;
					if(bfs(r,c)) {
						
						isMoved = true;
					}
				}
			}
			if(!isMoved) break;
			days++;
			
		}
		System.out.println(days);

	}
	public static boolean bfs(int r, int c) {	
		Queue<Point> q = new LinkedList<>();
		ArrayList<Point> union = new ArrayList<>(); // 연합 리스트
		
		q.add(new Point(r,c));
		union.add(new Point(r,c)); 
		visited[r][c] = true;
		
		int sum = map[r][c];
		
		while(!q.isEmpty()) {
			Point cur = q.poll();
			
			for(int d = 0; d < 4; d++) {
				int nr = cur.r + dt[d][0];
				int nc = cur.c + dt[d][1];
				
				if(nr >= N || nc >= N || nr < 0 || nc < 0) continue;
				if(visited[nr][nc]) continue;
				
				// 현재 위치와 다음 위치 차이 계산
				int diff = Math.abs(map[cur.r][cur.c] - map[nr][nc]);
				
				// L 이상 R 이하
				if(L <= diff && diff <= R) {
					visited[nr][nc] = true;
					q.add(new Point(nr, nc));
					union.add(new Point(nr, nc));
					sum += map[nr][nc];
				}
			}
		}
		
		// 연합 크기 체크
		if (union.size() == 1) return false;
		// 연합에 속한 국가 인구수 재설정
		int avg = sum / union.size();
		for(Point p : union) {
			map[p.r][p.c] = avg;
		}
		
		return true;
	}
}
