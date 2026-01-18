package baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2146_이용호 {
/*
 * 동서남북으로 연결 -> 같은섬
 * 섬을 잇는 다리를 가장 짧게 하나만
 * 다리를 놓을수 있는곳(사방이 1이 아닌곳)에서 bfs로 다른섬 탐색후 가장 짧은 길이 반환
 * 소요시간 : 1시간 30분(구상 포함) 
 */
	static int[][] map;
	static boolean[][] numVisited, visited;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static int N;
	static List<Integer> edgeList;
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N][N];
		
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		int num = 1;
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] != 1) continue;
				num++;
				numVisited = new boolean[N][N];
				numbering(i,j,num); //섬 넘버링
			}
		}

		edgeList = new LinkedList<>(); //다리 개수 리스트
		//섬에서 다리 놓을수 있으면 다리 놓고 리스트에 다리개수 추가
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(map[i][j] == 0) continue;
				if(checkCross(i,j)) { //다리 놓을수 있는지 체크
					visited = new boolean[N][N];
					edgeList.add(makeB(i,j,map[i][j]));
				};
			}
		}
		Collections.sort(edgeList);
		System.out.println(edgeList.get(0));

	}
	//해당 위치에서 다른섬까지 다리 놓고 다리개수 리턴
	static int makeB(int x, int y, int islandNum) {
		Queue<int[]> q = new LinkedList<int[]>();
		visited[x][y] = true;
		
		q.add(new int[] {x,y,0});
		
		while(!q.isEmpty()) {
			int[] now = q.poll();
			for(int i = 0; i < 4; i++) {
				int nx = now[0] + dx[i];
				int ny = now[1] + dy[i];
				int edgeCnt = now[2];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				
				//같은 섬이거나 방문했으면 다리 안놓음
				if(map[nx][ny] == islandNum || visited[nx][ny]) continue; 
				//바다면 다리 놓기
				else if(map[nx][ny] == 0) { 
					visited[nx][ny] = true;
					q.add(new int[] {nx,ny,edgeCnt+1});
				}
				else {
					return edgeCnt;
				}
			}
		}
		//놓을수 있는곳에 다리다 놨는데 다른섬 못찾았으면 최대값 리턴(다른섬 도달불가)
		return Integer.MAX_VALUE;
	}
	//사방중 다리 놓을 수 있으면 true
	static boolean checkCross(int x, int y) {
		for(int i = 0; i < 4; i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
			if(map[nx][ny] == 0) return true; //바다면(다리 놓을수 있으면) true 리턴
		}
		return false;
	}
	//같은섬 구분을 위해 섬 넘버링
	static void numbering(int x, int y, int num) {
		Queue<int[]> q = new LinkedList<int[]>();
		numVisited[x][y] = true;
		map[x][y] = num;
		q.add(new int[] {x,y});
		
		while(!q.isEmpty()) {
			int[] now = q.poll();
			for(int i = 0; i < 4; i++) {
				int nx = now[0] + dx[i];
				int ny = now[1] + dy[i];
				if(nx < 0 || ny < 0|| nx >= N || ny >= N) continue;
				if(numVisited[nx][ny] || map[nx][ny] != 1) continue;
				map[nx][ny] = num;
				numVisited[nx][ny] = true;
				q.add(new int[] {nx,ny});
				
			}
		}
	}

}
