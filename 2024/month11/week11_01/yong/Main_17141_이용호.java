package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17141_이용호 {
/*
 * 백준 17141번: 연구소 2
 * 벽은 -, 바이러스 위치 0, 빈칸은 퍼지는 시간
 * 모든 빈칸에 바이러스를 퍼뜨리는 최소 시간
 */
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int N, M, corCnt, minTime;
	static int[][] labState;
	static LinkedList<int[]> virusCord; 
	
	static class Node{
		int x; int y;
		int time;
		public Node(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		labState = new int[N][N];
		virusCord = new LinkedList<>(); //바이러스 좌표
		// 연구소 상태 입력 받기
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				// 0 빈칸, 1 벽, 2 바이러스 가능
				labState[i][j] = Integer.parseInt(st.nextToken());
				// 바이러스 가능 좌표 추가
				if(labState[i][j] == 2) virusCord.add(new int[] {i,j});
				else if(labState[i][j] == 1) labState[i][j] = -1;
			}
		}
		corCnt = virusCord.size();
		// 조합으로 바이러스 위치 선택하고 최소 시간 찾기
		int[] virus = new int[M];
		minTime = Integer.MAX_VALUE;
		comb(0,0,virus,labState);
		System.out.println(minTime == Integer.MAX_VALUE ? "-1" : minTime);
	}
	
	// 바이러스 시작위치 조합 선택
	public static void comb(int start, int idx, int[] choose, int[][] map) {
		if(idx == M) { // M개 바이러스 선택했다면 바이러스 퍼뜨리기
			// lab 배열 복사 (각 조합마다 새로운 상태에서 BFS)
            int[][] copy = new int[N][N];
            for (int i = 0; i < N; i++) copy[i] = labState[i].clone();
			bfs(choose, copy);
			return;
		}
		
		for(int i = start; i < corCnt; i++) {
			choose[idx] = i;
			comb(i+1, idx+1,choose,map);
		}		
	}
	
	// 바이러스 퍼뜨리기
	public static void bfs(int[] virus, int[][] map) {
		Queue<Node> q = new LinkedList<>();
		boolean[][] visited = new boolean[N][N];
		int[][] dist = new int[N][N]; // 시간 기록용
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) dist[i][j] = -1;
		}
		
		// 초기 바이러스 위치
		for(int i : virus) {
			int x = virusCord.get(i)[0];
			int y = virusCord.get(i)[1];
			visited[x][y] = true;
			q.offer(new Node(x,y,0));
			dist[x][y] = 0;
		}
		// 각 bfs에서 바이러스가 다 퍼지는데 걸리는 시간
		while(!q.isEmpty()) {
			Node now = q.poll();

			for(int i = 0 ; i < 4; i++) {
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				// 방문 했거나 벽이면 패스
				if(map[nx][ny] == -1 || visited[nx][ny]) continue;
				
				visited[nx][ny] = true;
				dist[nx][ny] = now.time + 1;
				q.offer(new Node(nx, ny, now.time + 1));
			}
		}
		int maxTime = 0;
		// 바이러스 다 펴졌을 때만 갱신
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(!visited[i][j] && (map[i][j] == 0 ||map[i][j] == 2)) return;
				maxTime = Math.max(maxTime, dist[i][j]);
			}
		}
		
		minTime = Math.min(minTime, maxTime);
	}
	
}
