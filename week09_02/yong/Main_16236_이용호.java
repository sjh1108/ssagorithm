package baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_16236_이용호 {
/*
 * 아기상어 크기 2, 상하좌우 한칸 이동
 * 자신보다 큰 물고기 있으면 못지나가고 나머지칸 모두 지나갈수 있다.(같은거 됨)
 * 자신보다 작은 물고기 먹을수 있다(같은건 안됨)
 * 먹을수 있는 물고기 1 -> 먹으러감, 이상이면 가까운물고기 먹으러감 , 거리는 이동 최솟값
 * 거리 같으면 우선순위는 위쪽, 왼쪽
 * 먹을때 마다 상어 크기 증가
 * 더이상 먹을수 있는게 없으면 엄마를 부르고 종료
 */
	static int[][] map;
	static List<int[]> feeds;
	static shark s;
	static int time, N;
	static boolean[][] visited;
	static int[] dx = {-1,0,1,0}; //상 좌 하 우
	static int[] dy = {0,-1,0,1};
	static class shark{
		int x; int y; int size; int nyam;
		shark(int x, int y, int size){
			this.x = x;
			this.y = y;
			this.size = size;
			this.nyam = 0;
		}
		public void eat() {
			this.nyam++;
			if(size == nyam) { //자신의 크기만큼 먹으면
				this.nyam = 0;
				this.size++;
			}
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		feeds = new LinkedList<>();
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if(map[i][j] == 0) continue;
				if(map[i][j] == 9) {
					s = new shark(i,j,2);
					map[i][j] = 0;
				}
				
			}
		}
		time = 0;
		while(true) {
			//먹을 먹이 탐색 
			int[] f = findFeed(s.x,s.y);
			if(f[0] == -1) break;
			
			//상어 이동
			s.x = f[0]; s.y = f[1];
			time += f[2];
//			System.out.println(s.x +" "+s.y+" "+time);
			s.eat();
			map[s.x][s.y] = 0;
		}
		System.out.println(time);	
	}
	
	//bfs로 가장 가까운 먹이 탐색후 좌표, 소요 시간 반환(도달가능 먹이 없으면 -1 반환
	static int[] findFeed(int x, int y) {
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] {x,y,0});
		visited = new boolean[N][N];
		visited[x][y] = true;
		
		//같은 거리의 물고기 리스트
		List<int[]> candidate = new LinkedList<>();
		int minMove = Integer.MAX_VALUE;
		
		while(!q.isEmpty()) {
			int[] now = q.poll();
			int moveCnt =  now[2]; //소요 시간
			if(now[2] > minMove)break;
			
			for(int i = 0; i < 4; i++) {
				int nx = now[0] + dx[i];
				int ny = now[1] + dy[i];
							
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				//방문했거나 상어보다 사이즈 크면 패스
				if(visited[nx][ny] || map[nx][ny] > s.size) continue;
				visited[nx][ny] = true;
				
				//물고기 없거나 상어와 같은 사이즈면 큐에 추가
				if(map[nx][ny] == 0 || map[nx][ny] == s.size) {
					q.add(new int[] {nx, ny, moveCnt + 1});
					
				}
				else {//먹을수 있는 물고기면
					//더 짧은 거리면 후보들 초기화 하고 minMove갱신
					if(moveCnt + 1 < minMove) {
						minMove = moveCnt + 1;
						candidate.clear();
						candidate.add(new int[] {nx, ny, minMove});
					}
					//같은 거리면 후보만 추가
					else if(moveCnt + 1 == minMove) {
						candidate.add(new int[] {nx, ny, moveCnt + 1});
					}
					
				}
			}
			
		}
		// 먹을수 있는 후보리스트 비어있으면 -1 -1 -1 리턴
		if(candidate.isEmpty()) return new int[] {-1,-1,-1};
//		Collections.sort(candidate,(a,b) -> {
//			if(a[0] == b[0]) return a[1] - b[1]; //y 오름차순
//			return a[0] - b[0]; //x 오름차순
//		});
		candidate.sort((a,b) -> {
			if(a[0] == b[0]) return a[1] - b[1]; //y 오름차순
			return a[0] - b[0]; //x 오름차순
		});
		return candidate.get(0);
		
	}

}
