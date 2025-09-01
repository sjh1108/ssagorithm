package week09_02.sjh1108;

import java.io.BufferedReader;
//import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16930_송주헌 {
	private static int N, M, K;
	private static int min;
	private static int sx, sy;
	private static int ex, ey;
	
	private static int[][] map;
	private static int[][] dist;
	
	private static int[] dx = {-1, 1, 0, 0};
	private static int[] dy = {0, 0, -1, 1};
	
	private static boolean isIn(int x, int y) {
		return (x >= 0 && x < N) && (y >= 0 && y < M);
	}
	
	private static void bfs() {
		int[] tmp = new int[] {sx, sy};
		dist[sx][sy] = 0;
		
		Queue<int[]> q = new ArrayDeque<>();
		q.add(tmp);
		
		int time = 1;
		while(!q.isEmpty()) {
			int size = q.size();
			
			while(size-- > 0) {
				int[] cur = q.poll();
				
				int x = cur[0];
				int y = cur[1];
				
				for(int d = 0; d < 4; d++) {
					for(int l = 1; l <= K; l++) {
						int nx = x + dx[d] * l;
						int ny = y + dy[d] * l;
						
						
						if(!isIn(nx, ny) || map[nx][ny] == 1) break;
						// if(dist[nx][ny]) continue;
                        if(dist[nx][ny] < time) break;
                        if(dist[nx][ny] == time) continue;
//						System.out.println(nx + " : " + ny);
						
						if(nx == ex && ny == ey) {
							min = time;
							return;
						}
						
						dist[nx][ny] = time;
						q.add(new int[] {nx, ny});
					}
				}
			}
			
			time++;
		}
	}
	public static void main(String[] args) throws IOException{
//		System.setIn(new FileInputStream("Test4.txt"));
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		
		min = Integer.MAX_VALUE;
		map = new int[N][M];
		dist = new int[N][M];
		for(int i = 0; i < N; i++) {
			String str = br.readLine();
            Arrays.fill(dist[i], Integer.MAX_VALUE);
			for(int j = 0; j < M; j++) {
				map[i][j] = str.charAt(j) == '.' ? 0 : 1;
			}
		}
		
		st = new StringTokenizer(br.readLine());
		sx = Integer.parseInt(st.nextToken())-1;
		sy = Integer.parseInt(st.nextToken())-1;
		ex = Integer.parseInt(st.nextToken())-1;
		ey = Integer.parseInt(st.nextToken())-1;
		
		if(sx == ex && sy == ey) {
			System.out.println(0);
            return;
		}
		
		bfs();
		
		System.out.println(min == Integer.MAX_VALUE ? -1 : min);
	}

}
