package week03.sjh1108.BOJ_17281;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_17281_송주헌 {
	private static int N;
	private static int point;
	
	private static int[] lineUp;
	private static boolean[] visited;
	
	private static int[][] player;
	
	// 경기를 진행하는 메소드
	private static void play(int inning, int score, int start) {
		// 모든 이닝을 진행했으면 현재까지의 점수를 갱신
		if(inning == N) {
			point = Math.max(point, score);
			return;
		}
		
		// 현재 이닝의 타순에 따라 점수를 계산
		int outCount = 0;
		boolean[] bat = new boolean[3];
		while(outCount < 3) {
			int temp = player[inning][lineUp[start]];
			
			switch (temp) {
			// 타자가 아웃된 경우
			case 0:
				outCount++;
				break;
			
			// 타자가 1루에 진루한 경우
			case 1:
				if(bat[2]) score += 1;
				for(int i = 2; i > 0; i--) {
					bat[i] = bat[i-1];
				}
				bat[0] = true;
				break;
				
			// 타자가 2루에 진루한 경우
			case 2:
				if(bat[2]) score += 1;
				if(bat[1]) score += 1;
				
				bat[2] = bat[0];
				bat[1] = true;
				bat[0] = false;
				
				break;
				
			// 타자가 3루에 진루한 경우
			case 3:
				for(int i = 0; i < 3; i++) {
					if(bat[i]) ++score;
					bat[i] = false;
				}
				
				bat[2] = true;
				
				break;

			// 타자가 홈런을 친 경우
			case 4:
				for(int i = 0; i < 3; i++) {
					if(bat[i]) ++score;
					bat[i] = false;
				}
				++score;
				break;
			}
			
			start = (start + 1) % 9; 
		}

		play(inning+1, score, start);
	}
	
	private static void dfs(int depth) {
		// 9명의 타순을 모두 정했으면 경기를 진행
		if(depth == 9) {
			play(0, 0, 0);
			return;
		}
		
		// 4번 타자는 고정되어 있으므로, 3번 타자까지는 순열을 이용해 나머지 8명의 타순을 정함
		if(depth == 3) {
			dfs(depth+1);
			return;
		} 
		// 4번 타자는 제외하고 나머지 8명의 타순을 정함
		// 순열을 이용함
		else {
			for(int i = 1; i < 9; i++) {
				if(visited[i]) continue;
				
				lineUp[depth] = i;
				visited[i] = true;
				
				dfs(depth+1);
				
				lineUp[depth] = 0;
				visited[i] = false;
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력
		N = Integer.parseInt(br.readLine());
		
		lineUp = new int[9];
		visited = new boolean[9];
		
		player = new int[N][9];
		for(int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			
			for(int j = 0; j < 9; j++) {
				player[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// 1번 타자는 4번에 고정
		lineUp[3] = 0;
		visited[0] = true;
		
		// 순열을 이용해 나머지 8명의 타순을 정함
		dfs(0);
		
		System.out.println(point);
	}
}
