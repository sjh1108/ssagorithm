package exam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_2382_박기택 
{
	
	
	static class Micro
	{
		int r, c, cnt, dir, total; // total 추가
		boolean isDead;
		
		public Micro(int r, int c, int cnt, int dir) 
		{
			super();
			this.r = r;
			this.c = c;
			this.total = this.cnt = cnt; // this.total 추가
			this.dir = dir;
		}
		
		
		
	}
	
	static int N, M, K;
	static int[] dr = { 0, -1, 1, 0, 0}; // 상 하 좌 우
	static int[] dc = { 0, 0, 0, -1, 1};
	static Micro[] list;
	static Micro[][] map;
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
	
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(in.readLine());
		for (int tc = 1; tc <= TC; ++tc) 
		{
			StringTokenizer st = new StringTokenizer(in.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			// 전체 군집 리스트
			list = new Micro[K];
			// 매시간마다 각 셀에 이동한 미생물 정보
			map = new Micro[N][N];
			
			
		
			for (int i = 0; i < K; ++i) 
			{
				st = new StringTokenizer(in.readLine(), " ");
				list[i] = new Micro(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
	
			}
			
			
			System.out.println("#" + tc + " "+ move()); // 입출력 블록 맞나 확인하기!!
			
			
		}
	}
		
		
		private static int move()
		{
			// M시간동안 굽집 이동 처리
			int time = M, nr, nc, remainCnt = 0; // remainCnt를 초기화하기 (빨간줄 없애기)
			while (time-- > 0)
			{
				// 군집 꺼내기. 
				for (Micro cur : list) 
				{
					// 한칸이동
					nr = cur.r += dr[cur.dir]; //ki cur.땡땡땡 이거 조심하고, 익숙해지기. 언제쓰는지 확인하기.
					nc = cur.c += dc[cur.dir];
					
					// 소멸 ㅇ: 약품 칸 처리 ki 약품처리 N-1임을 주의하기
					if (nr == 0 || nr == N-1 || nc == 0 || nc == N-1)
					{
						
						cur.cnt = cur.cnt / 2; // 이거 약품만나야 함
						
						if (cur.cnt == 0)
						{
							cur.isDead = true;
							continue; // ki 현재 반복문에서 아래 코드를 무시하고, 다음 반복으로 넘어가라
						}
					
					}	
					// 소멸 x: 약품 칸 처리 if문 결과라서 밖에다가 빼줘야 함
					if (cur.dir % 2 == 1) cur.dir++;
					else cur.dir--;
					
					
					// step3) 군집리스트 처리하기., 블록처리 수정함. why? cur때문..
					if (map[nr][nc] == null)
					{
						map[nr][nc] = cur;
					}
					else
					{
						if (map[nr][nc].cnt > cur.cnt)
						{
							map[nr][nc].total += cur.cnt;
							cur.isDead = true;
						}
						
						else
						{
							cur.total += map[nr][nc].total;
							map[nr][nc].isDead = true;
							map[nr][nc] = cur; // 이게 방향값을 위해 크기조정한건가?

							
							
						}
					}
				} //  end for : 군집리스트 처리
				remainCnt = reset();
			
				
				
			} // end while : 시간 반복
			return remainCnt;
		}
		
		
		private static int reset()
		{
			int total = 0;
			
			for (int r = 0; r < N; r++) {
				for (int c = 0; c < N; c++) {
					// 미생물 군집이 없다면 넘기고
					if(map[r][c] == null) continue;
					// 군집이 있다면 정리
					map[r][c].cnt = map[r][c].total; // total을 cnt로 넘기기
					total += map[r][c].cnt; // total에 정리해주기.
					map[r][c] = null; // 다음 판을 위해 판갈기
					
				}
				
			}
			
			
			
			
			return total;
		}
		
		// 아직 다 안된 부분이 있는데, 금일 내에 커밋해서 올리겠습니다. ㅠㅠㅠ
		
		
		
		
}

// 오답: cnt가 아니라 dir
// int time = M, nr, nc, dir, remainCnt = 0; // dir맞나? -> 안써도 됨 dir
//if (cur.cnt % 2 == 0) cur.cnt -= cur.cnt; ->cur.dir--;