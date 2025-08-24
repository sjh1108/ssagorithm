package algostudy.swea.a1953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_1953_이용호 {
/*
 * 터널끼리 연결되어 있으면 이동가능
 * 파이프는 map[][]에 int로 표현하기
 * 경과한 시간 -> depth
 * bfs로 visited 확인해보면 될듯
 */
	static int[][] map;
	static boolean[][] visited;
	static int N, M, R, C, L;
	static boolean[][] pipe;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int[] oppsite = {1,0,3,2};
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= T; tc++) {
			// 지하터널 초기화
			StringTokenizer st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			map = new int[N][M];
			// bfs 시작 지점(맨홀 뚜껑 위치)
			R = Integer.parseInt(st.nextToken()); // 맨홀 row
			C = Integer.parseInt(st.nextToken()); // 맨홀 col
			L = Integer.parseInt(st.nextToken()); // 소요된 시간
			pipe = new boolean[8][4];
			pipe = new boolean[][]{{false,false,false,false}, //파이프마다 상하좌우 가능한곳  1base
						{true, true, true, true}, // 1번 파이프(상, 하, 좌, 우)
						{true, true, false, false}, // 2번 파이프(상, 하, x, x)
						{false, false, true, true}, // 3번 파이프
						{true, false, false, true}, // 4번 파이프
						{false, true, false, true},	// 5번 파이프
						{false, true, true, false},	// 6번 파이프
						{true, false, true, false}};// 7번 파이프
		
			
			
			for(int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j = 0 ; j < M; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			bfs(R,C);
			int cnt = 0;
			for(int i = 0; i < N; i++) {
				for(int j = 0 ; j < M; j++) {
					if(visited[i][j])cnt++;
					
				}
			}
			System.out.println("#"+tc+" "+cnt);
		}
	}
	static class node{
		int x; int y; int v;
		int depth;
		node(int x, int y, int v, int depth){
			this.x = x;
			this.y = y;
			this.v = v;
			this.depth = depth;
		}
		
	}
	private static void bfs(int startX,int startY) {
		Queue<node> q = new LinkedList<>();
		q.add(new node(startX, startY,map[startX][startY],1)); 
		visited = new boolean[N][M];
		visited[startX][startY] = true;
		while(!q.isEmpty()) { 
			node now = q.poll();
			
//			System.out.println(now.x + " " + now.y);
			for(int i = 0; i < 4; i++) {//상하좌우 
				//현재 파이프에서 이동할수 있는지 체크 + 현재depth는 L보다 작아야함
				if(!pipe[now.v][i] || now.depth >= L) continue;
					
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				if(nx >= 0 && ny >= 0 && nx < N && ny < M && !visited[nx][ny]) {//다음 위치가 맵에서 벗어나지 않는지 체크
					if(pipe[map[nx][ny]][oppsite[i]]) {
						visited[nx][ny] = true;
						q.add(new node(nx,ny,map[nx][ny],now.depth+1));
					}

				}
		
			}
		}
	}
}