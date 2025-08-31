package algo2025_09;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_2382_장상민 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int T, N, M, K;
	static Micro[][] map;
	static Micro[] list;
	static int[] dr = {0,-1,1,0,0}; // 1:상 2:하
	static int[] dc = {0,0,0,-1,1}; // 3:좌 4:우
	
	
	static class Micro{
		int x, y, num, dir, total; // total 군집 합쳐지는 상황의 크기
		boolean killed; //기본값 false
		
		public Micro(int x, int y, int num, int dir) {
			this.x = x;
			this.y = y;
			this.total = this.num = num;
			this.dir = dir;
			
		}
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		T = Integer.parseInt(input.readLine());
		
		for (int tc = 0; tc < T; tc++) {
			tokens = new StringTokenizer(input.readLine());
			N = Integer.parseInt(tokens.nextToken()); // 맵 크기
			M = Integer.parseInt(tokens.nextToken()); // 시간
			K = Integer.parseInt(tokens.nextToken()); // 군집의 수
			
			// 전체 군집 리스트 생성
			list = new Micro[K];
			// 매 시간마다 셀에 이동한 미생물 정보
			map = new Micro[N][N]; // 초기값 null
			
			// 미생물 정보 입력 받기
			for (int i = 0; i < K; i++) {
				tokens = new StringTokenizer(input.readLine(), " ");
				list[i] = new Micro(Integer.parseInt(tokens.nextToken()),
									Integer.parseInt(tokens.nextToken()),
									Integer.parseInt(tokens.nextToken()),
									Integer.parseInt(tokens.nextToken()));
			}
			System.out.println("#" + tc + " " + move());
		}
		
		
		
	}
	
	private static int move(){ // 이동 후 살아있는 미생물 수의 리턴
		//M시간동안 이동처리
		int time = M, nr,nc,remainCnt = 0;
		
		while(time-- > 0) {
			for(Micro cur : list) {
				if(cur.killed) continue; //리스트에서 삭제하지 않았으니 넘기기
				//1. 한칸 이동
				nr = cur.x += dr[cur.dir];
				nc = cur.y += dc[cur.dir];
				//2. 약품칸 처리
				if(nr == 0 || nr == N-1 || nc == 0 || nc == N-1) { // 일때 약품칸
					// 군집의 크기를 절반으로 줄이고 역방향처리, 크기가 0이면 소멸
					cur.total = cur.num = cur.num/2;
					if(cur.num == 0) {
						cur.killed = true;
						continue;
					}
					// 방향 반대로
					if(cur.dir % 2 == 1) cur.dir++;
					else cur.dir--;
				}
				//3. 군집 병합 처리
				if(map[nr][nc] == null) { //그 셀에 처음 도착하는 군집
					map[nr][nc] = cur;
				} else { // 그 셀에 처음 도착하는 군집(이미 다른 군집이 있는경우)
					// 군집의 미생물 크기로 비교해서 큰쪽으로 병합시키고 작은 크기의 군집은 소멸
					if(map[nr][nc].num > cur.num) {
						map[nr][nc].total += cur.num;
						cur.killed = true; // 군집의 크기가 작은 친구는 흡수됐으니 사망처리
					}else {// 나중에 도착한 군집의 크기가 크면
						cur.total += map[nr][nc].total;
						map[nr][nc].killed = true;
						map[nr][nc] = cur;
					}
					
				}
				
			} // end for 군집  리스트 처리
			
			remainCnt = reset(); //현재 시간에 이동한 군집들을 정리후 살아있는 미생물 수 받기
		} // end for 반복

		return remainCnt;
	}

	private static int reset() { // 1시간마다 행동 초기화
		int total = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if(map[r][c] == null) continue;
				// 군집이 있다면 행동 정리해주기
				map[r][c].num = map[r][c].total;
				total += map[r][c].num; // 미생물 수 계산
				map[r][c] = null; // 다음 행동을 위해 초기화해주기
			}
		}
		return total;
	}
}
