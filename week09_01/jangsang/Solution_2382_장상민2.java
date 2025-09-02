
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_2382_장상민2 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int T, N, M, K;
	static int[] dr = {0,-1,1,0,0};
	static int[] dc = {0,0,0,1,-1};
	private static Micro[] list;
	private static Micro[][] map;
	
	static class Micro{
		int x,y,num,dir,total;
		boolean killed;
		public Micro(int x, int y, int num, int dir) {
			super();
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
			N = Integer.parseInt(tokens.nextToken());
			M = Integer.parseInt(tokens.nextToken());
			K = Integer.parseInt(tokens.nextToken());
			
			// 군집 리스트 생성
			list = new Micro[K];
			//map에 저장
			map = new Micro[N][N];
			
			//군집 리스트에 저장
			for (int i = 0; i < K; i++) {
				tokens = new StringTokenizer(input.readLine());
				list[i] = new Micro(Integer.parseInt(tokens.nextToken()),
									Integer.parseInt(tokens.nextToken()),
									Integer.parseInt(tokens.nextToken()),
									Integer.parseInt(tokens.nextToken()));
			}
			System.out.println("#" + tc + " " + move());
		}
		
	}
	static int move() {
		int time = M, nr, nc, res = 0;
		while(time-- > 0) {
		
			for (Micro cur : list) {
				if (cur.killed) continue;
				//1. 1시간동안 이동
				nr = cur.x += dr[cur.dir];
				nc = cur.y += dc[cur.dir];
				//2. 약품셀에 도착하면 절반 죽고 역방향 0이면 사망
				if(nr == 0 || nr == N - 1 || nc == 0 || nc == N-1) {
					cur.num = cur.num / 2;
					if(cur.num == 0) {
						cur.killed = true;
						continue;
					}
					
					//역방향
					if(cur.dir % 2 == 0) {
						cur.dir = cur.dir - 1;
					}  else {
						cur.dir = cur.dir + 1;
					}
				}
				//3. 군집끼리 만나면 더 큰 군집으로 합류하고 이동방향도 큰 군집을 따름
				if(map[nr][nc] == null) {
					map[nr][nc] = cur;
				} else {
					if(map[nr][nc].num > cur.num) {
						map[nr][nc].num += cur.num;
						cur.killed = true;
					}else {
						cur.num += map[nr][nc].num;
						map[nr][nc].killed = true;
						map[nr][nc] = cur;
					}
				}
			} // for end
			res = reset();
			
		} // while end
		
		return res;
	}
	private static int reset() { 
		int total = 0;
		for (int r = 0; r < N; r++) {
			for (int c = 0; c < N; c++) {
				if(map[r][c] == null) continue;
				// 군집이 있다면 행동 정리해주기
				map[r][c].num = map[r][c].num;
				total += map[r][c].num; // 미생물 수 계산
				map[r][c] = null; // 다음 행동을 위해 초기화해주기
			}
		}
		return total;
	}
}
