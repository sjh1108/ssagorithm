package baek_week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main_21608_이용호 {
	/*
	 * 학생 1 ~ N^2번,
	 * 1. 비어있는 칸중 좋아하는 학생 많은칸으로
	 * 2. 1만족 여러개 -> 인접한 칸이 많이 비어있는곳
	 * 3. 2도 여러개 -> 행 번호 가장 작은칸, 열 번호 가장 작은칸
	 */
	public static int[] dx = {-1,1,0,0};
	public static int[] dy = {0,0,-1,1};
	public static int[][] map, favor;
	public static int N;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		
		map = new int[N+1][N+1]; // 좌석 배치도
		favor = new int[N * N + 1][4]; // [i]가 좋아하는 학생 리스트
		for(int i = 0; i < N * N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int student = Integer.parseInt(st.nextToken());
			for(int j = 0; j < 4; j++) {
				favor[student][j] = Integer.parseInt(st.nextToken());
			}
			set(student);
		}
		System.out.println(calScore());
	}
	public static int calScore(){
		int score = 0;
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= N; j++) {
				// 인접한 좋아하는 친구 카운트
				int count = 0;
				for(int k = 0; k < 4; k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					if(nx < 1 || ny < 1 || nx > N || ny > N) continue;
					for(int f : favor[map[i][j]]) {
						if(map[nx][ny] == f) count++;
					}
				}
				// 1 10 100 1000
				if(count >= 1) {
					score += Math.pow(10, count-1);
				}
			}
		}
		return score;
	}
	public static void set(int student) {
		int[][][] favorMap = new int[N+1][N+1][2]; //[i][j][0] - 인접칸 좋아하는 학생수, [i][j][1] 인접 공백 개수
		int maxFavor = 0; // 인접칸 좋아하는 학생 수 max 
		// 인접칸 좋아하는 학생 수 구하기
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= N; j++) {
				
				// 해당칸 이미 학생 배치 됐다면 pass
				if(map[i][j] != 0) continue;
				
				int favorCnt = 0;
				int empty = 0;
				// 인접자리 좋아하는 친구 몇명인지 카운트
				for(int k = 0; k < 4 ; k++) {
					int nx = i + dx[k];
					int ny = j + dy[k];
					
					if(nx < 1 || ny < 1 || nx > N || ny > N) continue;
					boolean isFavor = false;
					if(map[nx][ny] == 0) empty++; // 공석 카운트
					for(int c : favor[student]) { // 좋아하는 친구인지 체크
						if(map[nx][ny] == c) {
							isFavor = true;
							break;
						}
					}
					if(isFavor) favorCnt++;
				}
                // 자리 후보의 인접한 좋아하는 친구, 빈자리 갱신
				favorMap[i][j][0] = favorCnt;
				favorMap[i][j][1] = empty;
				maxFavor = Math.max(maxFavor, favorCnt);
			}
		}
		// 1번 만족하는자리 세고, 비어있는칸 가장 많은칸으로 자리 정하기
		ArrayDeque<int[]> sitCandidate = new ArrayDeque<>();
		int maxEmpty = 0;
		for(int i = 1; i <= N; i++) {
			for(int j = 1; j <= N; j++) {
                // 이미 자리배치 됐으면 패스
				if(map[i][j] != 0) continue;
				// 1번 만족하는자리 후보에 추가(좋아하는 친구 많은곳들)
				if(favorMap[i][j][0] == maxFavor) { 
					sitCandidate.add(new int[] {i,j});
                    // 1번 만족하는 자리중 최대 빈공간 기록
					maxEmpty = Math.max(maxEmpty, favorMap[i][j][1]);
				}
			}
		}
		
		// 후보중 위치 선정(1번 만족중)
		while(!sitCandidate.isEmpty()) {
			// 비어있는칸 가장 많은 칸 찾기
			int[] sit = sitCandidate.pollFirst();
			int r = sit[0]; int c = sit[1];
			// 2번 만족하는지 체크
			if(favorMap[r][c][1] != maxEmpty) continue;
            // 자리 배정
			map[r][c] = student;
			break;
		}
		
	}
}
