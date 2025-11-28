package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_7569_이용호 {
	/*
	 * 토마토마토마토
	 */
	static int dx[] = {0,0,-1,1,0,0};
	static int dy[] = {0,0,0,0,-1,1};
	static int dh[] = {-1,1,0,0,0,0};
	static int M, N, H, maxDay;
	static int[][][] tomatos;
	static LinkedList<int[]> T;
	public static class Node{
		int x; int y; int h;
		int days;
		public Node(int h, int x, int y, int days) { // Node(H, N, M, days)
			this.x = x;
			this.y = y;
			this.h = h;
			this.days = days;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		M = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());
		H = Integer.parseInt(st.nextToken());
		T = new LinkedList<>();
		tomatos = new int[H][N][M];
		
		// 토마토 정보 입력
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < N; j++) {
				st = new StringTokenizer(br.readLine());
				for(int k = 0; k < M; k++) {
					// 1 익은 토마토, 0 익지 않은 토마토, -1 토마토 x
					tomatos[i][j][k] = Integer.parseInt(st.nextToken());
					// 익은 토마토 좌표 저장
					if(tomatos[i][j][k] == 1) T.offer(new int[] {i,j,k}); //h,x,y
				}
			}
		}
		maxDay = 0;
		bfs();
		
		System.out.println(maxDay);

	}
	public static void bfs() {
		Queue<Node> q = new LinkedList<>();
		boolean[][][] visited = new boolean[H][N][M];
		
		// 초기 익은토마토 큐에 저장
		for(int[] cor : T) {
			q.offer(new Node(cor[0], cor[1], cor[2], 0));
//			System.out.println(cor[0]+" "+cor[1]+" "+ cor[2]);
			visited[cor[0]][cor[1]][cor[2]] = true;
		}
		
		while(!q.isEmpty()) {
			Node now = q.poll();
			int h = now.h;
			int x = now.x;
			int y = now.y;
			maxDay = Math.max(maxDay, now.days);
			
			for(int i = 0; i < 6; i ++) {
				int nh = h + dh[i];
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nh < 0 || ny < 0 || nx < 0 || nh >= H || ny >= M || nx >= N) continue;
				// 방문했거나 토마토 없으면 패스
				if(tomatos[nh][nx][ny] == -1 || visited[nh][nx][ny]) continue;
				q.offer(new Node(nh, nx, ny, now.days+1));
				visited[nh][nx][ny] = true;
			}
		}
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < N; j++) {
				for(int k = 0; k < M; k++) {
					if(tomatos[i][j][k] == 0 && !visited[i][j][k]) maxDay = -1;
				}
			}
		}
		
		
	}

}
