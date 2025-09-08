package month08.week02.yong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_18809_이용호 {
static int[][] garden, rGarden, gGarden;
static int N,M,G,R;
static int maxFlower;
static int[] dx = {-1,1,0,0};
static int[] dy = {0,0,-1,1};
static boolean[][] visited;
static List<int[]> canDir;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		R = Integer.parseInt(st.nextToken());
		G = Integer.parseInt(st.nextToken());
		maxFlower = 0;
		garden = new int[N][M];
		
		
		
		canDir = new ArrayList<>();
		for(int i = 0;i < N;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M;j++) {
				garden[i][j] = Integer.parseInt(st.nextToken());
				if(garden[i][j] == 2) {//배양액 뿌릴수 있는 땅이면 좌표 저장
					canDir.add(new int[]{i,j});
				}
			}
		}
		
		for(int i = 0; i < canDir.size();i++) {//배양액 뿌리는 모든 좌표 경우의 수(중복없음)
			for(int j = i+1;j<canDir.size();j++) {
//				System.out.println(Arrays.toString(canDir.get(i))+" "+Arrays.toString(canDir.get(j)));
				//각각 bfs실행
				rGarden = new int[N][M];
				gGarden= new int[N][M];
				
				//문제에서는 배양액이 하나씩 떨어지는게 아니고 R,G개씩 떨어진다
				visited = new boolean[N][M];
				bfs(canDir.get(i),1);
				visited = new boolean[N][M];
				bfs(canDir.get(j),0);
				
				maxFlower = Math.max(maxFlower, sumFlower(rGarden,gGarden));
			}
		}
		System.out.println(maxFlower);
		
	}
	static void bfs(int[] start,int isR) {
		Queue<int[]> queue = new LinkedList<>();
		queue.add(new int[]{start[0],start[1]});

		while(!queue.isEmpty()) {
			int[] now = queue.poll();
			visited[now[0]][now[1]] = true;
			if(isR==1) {//빨간 배양액
				rGarden[now[0]][now[1]]++;
			}
			else { // 초록 배양액
				gGarden[now[0]][now[1]]++;
			}
			for(int i = 0;i < 4;i++) {// 사방 십자 탐색
				int nx = now[0]+dx[i];
				int ny = now[1]+dy[i];
				if(nx>=0 && ny >=0 && nx < N && ny < M) {
					if(garden[nx][ny] != 1 && !visited[nx][ny]) //호수(1)가 아니고 방문 안했으면 큐에 추가
					queue.add(new int[] {nx,ny});
				}
			}
		}
		
	}
	static int sumFlower(int[][] rG, int[][] gG) {
		int same = 0;
		for(int i = 0;i < N; i++) {
			for(int j = 0;j < M;j++) {
				if(rG[i][j] == gG[i][j] && rG[i][j] != 0) {
					same++;
				}
			}
		}
		return same;
	}
	
	

}
