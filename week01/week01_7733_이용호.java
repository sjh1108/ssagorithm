package forstudy.week1.swea.a7733;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class week01_7733_이용호 {
	static int map[][];
	static boolean visited[][];
	static int day;
	static int lastday; //치즈 없어지는날
	static int dx[] = {-1,1,0,0};
	static int dy[] = {0,0,-1,1};
	static int cheese; //치즈 덩어리 개수
	static int maxPiece; //가장 많은 덩어리
	public static void main(String[] args) throws NumberFormatException, IOException {
		//1~N일 먹힌곳 map false
		//map true인곳 에서 dfs실행
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int TC = Integer.parseInt(br.readLine());
		for(int testCase = 1;testCase <= TC; testCase++) {
			lastday = 0;
			maxPiece = 1;
			int N = Integer.parseInt(br.readLine());
			
			//맛있는 정도 map 입력받기
			map = new int[N][N];
			for(int i = 0;i < N;i++) {
				StringTokenizer st = new StringTokenizer(br.readLine());
				for(int j = 0;j < N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
					if (map[i][j] > lastday) lastday = map[i][j];
				}
			}
			day = 1; // 갉아먹은 날
			
			while(day<lastday) {
				
				cheese = 0;				
				yami(day,N); //요정 치즈 먹방
				
				//치즈 조각 덩어리 개수 세기
				for(int i = 0; i < N; i++) {
					for(int j = 0; j < N; j++) {
						if(visited[i][j] == false) {
							dfs(i,j,N);
							cheese++;
						}
					}
				}
				// 최대 덩어리 갱신
				if(maxPiece < cheese) {
					maxPiece = cheese;
				}
				day++;
			}
			System.out.println("#"+testCase+" "+maxPiece);
		}

	}
	static public void yami(int day,int N) {
		visited = new boolean[N][N];
		for(int i = 0;i< N;i++) {
			for(int j=0;j<N;j++) {
				if(map[i][j] <= day) {
					visited[i][j] = true; 
				}
			}
		}
	}
	static public void dfs(int x,int y,int N) {
		visited[x][y] = true;
		for(int i = 0;i < 4;i++) {
			int nx = x + dx[i];
			int ny = y + dy[i];
			if(nx >=0 && ny >= 0 && nx < N && ny < N) {
				if(visited[nx][ny] == false) {
					dfs(nx,ny,N);
				}
			}
			
		}
		
	}

}
