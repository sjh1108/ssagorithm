package week09_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Solution_1949
{
	
	static int N, K;
	static int[][] map;
	static int Maxlen;
	static boolean[][] visited;
	static int top;
	
	static final int[] dx = {-1, 1, 0, 0};
	static final int[] dy = {0, 0, -1, 1};
	
	public static void main(String[] args) throws NumberFormatException, IOException 
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for(int tc = 1; tc <= T; tc++)
		{
			StringTokenizer st;
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			K = Integer.parseInt(st.nextToken());
			
			// 지도 입력과 동시에 최고점(top) 갱신 -> 끼워넣기. map,top,if
			map = new int[N][N]; 
			top = 0;
			
			for (int i = 0; i < N; i++) 
			{
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) 
				{
					map[i][j] = Integer.parseInt(st.nextToken());
					if(map[i][j] > top) 
					{
						top = map[i][j];
					}
				}
			}
			
			// 정답(최장길이) 초기화, 아래를 위한 처리 방문처리 초기화. 나머지
			Maxlen = 1;
			visited = new boolean[N][N];
			
			// 모든 최고점에서 DFS 시작 (여러 시작점 가능) 여기특히주의
			for (int x = 0; x < N; x++)
			{
				for (int y = 0; y < N; y++) 
				{
					if (map[x][y] == top) // top이 한번더,, 
						{
							dfs(x, y, 1, false);
						}
				}
			}
			sb.append("#").append(tc).append(" ").append(Maxlen).append("\n");
		}
		System.out.println(sb); // 앞에서 "\n" 붙였기 때문에 ln없어.
	}
	
	
	private static void dfs(int x, int y, int len, boolean usedCut)
	{
		// 현재 칸 방문 체크 및 최대 길이 갱신
		// 방문과 가장긴
		visited[x][y] = true; // 본인, 방문처리
		
		if (len > Maxlen) // 위에서 가져온 길이 갱신하기. 

		{
			Maxlen = len;
		}
		
		// 4방향으로 확장 시도
		for (int d = 0; d < 4; d++)
		{
			int nx = x + dx[d];
			int ny = y + dy[d];
			
			// 경계/방문 체크: 범위 밖 or 이미 방문 → 스킵
			if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
			if (visited[nx][ny]) continue;
			
			
			// 높낮이 조건
			if(map[nx][ny] < map[x][y])
			{
				dfs(nx, ny, len + 1, usedCut); // + 1 사용한거 디버깅. 
			}
			else if(!usedCut && map[nx][ny] - K < map[x][y])
			{// (케이스 B) 더 높거나 같은 칸이지만, 아직 공사를 안 썼고(false니까, true가 됨.)
			// "최대 K만큼 깎으면 현재보다 낮게 만들 수" 있는 경우	
				int temp = map[nx][ny]; // 원복을 위한 백업
				
				map[nx][ny] = map[x][y] - 1; // 현재보다 딱 1 낮게 만들어 진행
				
				dfs(nx, ny, len + 1, true); // 공사 사용 표시
				
				map[nx][ny] = temp; // (중요) 다른 분기를 위해 원상복구
			}
		}
		visited[x][y] = false; // 백트래킹: 다음 분기를 위해 방문 해제
	}	
}

/*
 * 1. map = new int[N][N]; 
 * 
 * 2. System.out.print(sb);
 * 
 * 3. 주석처리하고, 코드 집중해서 짜고.
 * 
 * 4. 처음 분기에 map[][] = map[][] 넣어줄 필요없음.
 * 
 * 5. main 첫 for문에서 top이랑 선언하기. 5시부터는 키순서 완성과 그 잡기 틀 잡기.
 * 
 * 6. 최장길이 설정, 방문처리 배열,  이중for문
 * 
 * 7. Maxlen 
 * 8.  격자 후 꼭 방문
 * 9.  절삭 로직 (3 x 4 o) map[nx][ny] = map[x][y] - 1;
 * 10. 절삭, DFS
*/








