package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_20058_이용호 {
/*
 * spin 로직 구현 못함 
 * 시전할때마다 단계 L -> 2^L * 2^L 격자
 * 90도 시계 방향 격자 회전
 * 얼음칸 3칸 이상 인접 x -> 얼음 -1
 * 얼음합, 얼음중 가장 큰 덩어리
 */
	public static int[] dx = {-1,1,0,0};
	public static int[] dy = {0,0,-1,1};
	public static int[][] map, copy;
	public static int N;
	static boolean[][] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());
		N = (int) Math.pow(2, N);
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < Q; i++) {
			int L = Integer.parseInt(st.nextToken());
			L = (int) Math.pow(2, L);
			simulate(L);
		}
		
		
	}
	
	public static void simulate(int L) {
		copy = new int[N][N];
		for(int i = 0; i < N; i++) {
			copy[i] = map[i].clone();
		}
		// N / L 만큼 반복 
		for(int i = 0; i < N / L; i++) {
			for(int j = 0; j < N / L; j++) { // 0 2 4 6
				int startX = 2 * i;
				int startY = 2 * j;
				System.out.print(startX + " " + startY + " ");
				System.out.println(2*(j+1));
				spin(startX, startY, 2*(i+1));
			}
		}
		for(int i = 0; i < N; i++) {
			System.out.println(Arrays.toString(map[i]));
		}
		System.out.println();
		// 얼음 감소(사방탐색)
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				int ice = 0;
				for(int d = 0; d < 4; d++) {
					int nx = i + dx[d];
					int ny = j + dy[d];
					if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
					if(map[i][j] != 0) ice++;
				}
				
				if(ice < 3) {
					map[i][j] -= 1;
				}
				
			}
		}
		// 얼음 개수 세기
		int remained = 0;
		int maxdung = 0;
		visited = new boolean[N][N];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				remained += map[i][j];
				if(visited[i][j]) continue;
				maxdung = Math.max(maxdung, bfs(i,j));
			}
		}
		
		System.out.println(remained);
		System.out.println(maxdung);
	}
	private static int bfs(int sx, int sy) {
		Queue<int[]> q = new LinkedList<>();
		q.offer(new int[] {sx,sy});
		visited[sx][sy] = true;
		int cnt = 1;
		while(!q.isEmpty()) {
			int[] now = q.poll();
			int x = now[0];
			int y = now[1];
			
			for(int i = 0; i < 4; i++) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nx < 0 || ny < 0 || nx >= N || ny >= N || visited[nx][ny]) continue;
				if(map[nx][ny] == 0) continue;
				cnt++;
				visited[nx][ny] = true;
				q.offer(new int[] {nx, ny});
			}
		}
		return cnt;
	}
	private static void spin(int startX, int startY, int end) {
		for(int i = startX; i < end; i++) {
			for(int j = startY; j < end; j++)
			map[i][j] = copy[end - j - 1][i];
		}
		
	}

}
