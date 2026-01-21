package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_14503_이용호 {
/* 백준 14503번: 로봇 청소기
 * 로봇 청소기와 방상태 주어졌을때, 청소하는 영역의 개수
 * 방크기: N * M, 청소기 방향 있음(동 서 남 북)
 * 1. 현재칸 청소 -> 주변 보고 방향 유지 하면서 한칸 후진 -> 현재칸 청소
 *             -> 뒤가 벽이라 후진 못하면 작동 멈춤
 * 2. 청소 안된곳 있으면 -> 반시계 회전 -> 앞쪽 청소 x -> 한칸 정지 -> 1번
 * 
 */
	public static int[] dx = {-1, 0, 1, 0}; //상 우 하 좌
	public static int[] dy = {0, 1, 0, -1};
	public static int[][] room;
	public static int N, M, cleanCnt;
	
	public static class Robot{
		int x; int y; int dir;
		public Robot(int x, int y, int dir) {
			this.x = x;
			this.y = y;
			this.dir = dir;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		st = new StringTokenizer(br.readLine());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		int dir = Integer.parseInt(st.nextToken());
		
		room = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j = 0; j < M; j++) {
				room[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		simulate(r,c,dir);
		System.out.println(cleanCnt);
	}
	public static void simulate(int r, int c, int dir) {
		Queue<Robot> q = new LinkedList<>();
		q.offer(new Robot(r, c, dir));
		boolean[][] cleaned = new boolean[N][M];
		cleaned[r][c] = true;
		cleanCnt++;
		
		while(!q.isEmpty()) {
			Robot now = q.poll();
			// 현재칸 청소
			if(!cleaned[now.x][now.y]) {
				cleaned[now.x][now.y] = true;
				cleanCnt++;
			}
			boolean exist = false; 
			for(int i = 0; i < 4; i++) {
				int nx = now.x + dx[i];
				int ny = now.y + dy[i];
				if(nx < 0 || ny < 0 || nx >= N || ny >= M || room[nx][ny] == 1) continue;
			
				// 4칸 주변 청소 안된칸 있는경우
				if(!cleaned[nx][ny]) exist = true; // break; 이렇게 해줬었음 ;;
				// break 하면 안 됨: exist만 표시하고 루프는 끝나도 되지만,
                // 여기서 종료해도 로직상 문제는 없지만 전부 확인 취지로 유지
				
			}
			// 청소 안된곳 있으면
			if(exist) {
				// 90도 반시계 회전
				int ndir = (now.dir + 3) % 4;
				int nx = now.x + dx[ndir];
				int ny = now.y + dy[ndir];
				// 앞쪽 칸 청소 되지 않은 경우 한칸 전진
				if(nx >= 0 && ny >= 0 && nx < N && ny < M 
						&& !cleaned[nx][ny] && room[nx][ny] == 0) {
					q.offer(new Robot(nx, ny, ndir));
				}
				else {
					q.offer(new Robot(now.x,now.y,ndir));
				}
			}
			// 사방 청소 다 됐으면
			else {
				int ndir = (now.dir + 2) % 4;
				int nx = now.x + dx[ndir];
				int ny = now.y + dy[ndir];
				// 후진 가능하면 후진
				if(nx >= 0 && ny >= 0 && nx < N && ny < M && room[nx][ny] == 0) {
					q.offer(new Robot(nx,ny,now.dir));
				}
				else {
					break;
				}
			}
			
		}
	}

}
