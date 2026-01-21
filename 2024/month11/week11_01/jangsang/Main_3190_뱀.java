package algo2025_10_05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_3190_뱀{
	/**
	 * 뱀이 좌 우로 방향을 바꿀때 얘가 어디를 보고 있는지?
	 */
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int []map[];
	static int sec[];
	static String[] dir;
	static int N,K,L;
	static boolean isDie = false;
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine()); // map 크기
		K = Integer.parseInt(input.readLine()); // 사과의 수
		map = new int [N+1][N+1]; // 좌표 그대로 사용하기 위해서 +1 해주기
		
		for (int i = 0; i < K; i++) { //사과 입력
			tokens = new StringTokenizer(input.readLine());
			int x = Integer.parseInt(tokens.nextToken());
			int y = Integer.parseInt(tokens.nextToken());
			map[x][y] = 1; 
		}
		L = Integer.parseInt(input.readLine()); // 변환정보
		//시간초, 방향정보 입력 이렇게 해도 될까?
		sec = new int[L]; 
		dir = new String[L];
		for (int i = 0; i < L; i++) { 
			tokens = new StringTokenizer(input.readLine());
			sec[i] = Integer.parseInt(tokens.nextToken());
			dir[i] = tokens.nextToken();
		}
		
//		int secCnt = 0;
//		snake.add(new int[] {i, j});
		
		// 오른쪽부터 시계방향으로 오 아래 왼 위
		int[] dx = {0,1,0,-1};
		int[] dy = {1,0,-1,0};
		int startIdx = 0;
		Deque<int[]> snake = new ArrayDeque<>();
		snake.add(new int[] {1,1});
		map[1][1] = 2; // 오른쪽부터 시작하는 뱀 몸통

		
		int time = 0;
		int changeIdx = 0;
		boolean isDie = false;
		
		// 사과 = 1
		// 뱀 = 2
		while (!isDie) {
			time++;
			
			int[] head = snake.peekFirst();
			int nx = head[0] + dx[startIdx];
			int ny = head[1] + dy[startIdx];
			
			// 벽 or 자기 몸 부딪히면 죽음
			if (nx < 1 || ny < 1 || nx > N || ny > N || map[nx][ny] == 2) {
				isDie = true;
				break;
			}
			
			// 사과면 몸 길이 증가
			if (map[nx][ny] == 1) {
				map[nx][ny] = 2;
				snake.addFirst(new int[] {nx, ny});
			} else { // 그냥 이동
				map[nx][ny] = 2;
				snake.addFirst(new int[] {nx, ny});
				int[] tail = snake.pollLast();
				map[tail[0]][tail[1]] = 0; // 전에 있던 칸 다시 0으로
			}
			
			// 방향 전환 타이밍
			if (changeIdx < L && time == sec[changeIdx]) {
				if (dir[changeIdx].equals("L")) {
					startIdx = (startIdx + 3) % 4;
				} else {
					startIdx = (startIdx + 1) % 4;
				}
				changeIdx++;
			}
		}
		
		System.out.println(time);
	}
}