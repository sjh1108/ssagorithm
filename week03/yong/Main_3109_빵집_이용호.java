package algostudy.baek.b3109;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_3109_빵집_이용호 {
/*
 * 건물이 있을때 파이프 못 놓음
 * 건물은 x, .은 빈칸(처음과 마지막 열은 항상 비어있음
 * 각 칸은 오른쪽, 오른쪽 위 대각선, 오른쪽 아래 대각선으로 연결 가능
 */
	static int[] dx = {-1,0,1};//오른쪽 위, 오른쪽 아래, 오른쪽
	static int[] dy = {1,1,1};
	static boolean visited[][];
	static char[][] pipeMap;
	static int R,C;
	static boolean Finished;
	static int lineCnt = 0;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		
		visited = new boolean[R][C];
		pipeMap = new char[R][C];
		for(int i = 0; i < R; i++) {
			String line = br.readLine();
			for(int j = 0; j < C; j++) {
				pipeMap[i][j] = line.charAt(j);
			}
		}
		//행마다 파이프 놓는 dfs 시작
		for(int start = 0; start < R; start++) {
			Finished = false;
			dfs(start,0);
		}
		System.out.println(lineCnt);
		

	}

	private static void dfs(int x,int y) {
		visited[x][y] = true; //현재 위치 파이프설치
		if(y == C-1) { //오른쪽 끝 까지 연결 했다면
			Finished = true;
			lineCnt++;
			return;
		}
		
		for(int i = 0; i < 3;i++) {
			if(!Finished) {
				int nx = x + dx[i];
				int ny = y + dy[i];
				if(nx >= 0 && ny >= 0 && nx < R && ny < C) { //경계밖이 아니라면
					// 파이프 놓을 자리에 건물 없고 이미 파이프 설치된게 아니라면
					if(pipeMap[nx][ny] != 'x' && !visited[nx][ny]) { 
//						System.out.println(nx+" "+ny);
						dfs(nx,ny);

					}
				}
				
			}
		}
	}

}
