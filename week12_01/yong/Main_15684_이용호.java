package baek_week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_15684_이용호 {
/*
 * N 세로선, M 가로선
 * 행열이 바뀌는느낌이라 조금 햇갈린 문제
 */
	public static int[][] map;
	public static int N, M, H;
	public static boolean finish;
	public static int answer = 4;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken()); // 세로선 개수
		M = Integer.parseInt(st.nextToken()); // 가로선 개수
		H = Integer.parseInt(st.nextToken()); // 가로선 놓을수 있는 위치 개수
		
		map = new int[H+1][N+1]; // 1based 가로선 정보 담을곳
		
		// 가로선 정보
		for(int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			// a = 5, b = 1 -> (5,1)과(5,2)를 연결
			int a = Integer.parseInt(st.nextToken()); // 선 놓을 가로선 위치
			int b = Integer.parseInt(st.nextToken()); // 선 놓을 세로선 위치
//			System.out.println("a,b: " + a +", " +b);
			map[a][b] = 1; // 오른쪽으로 연결
			map[a][b+1] = -1; // 왼쪽으로 연결
		}
		// 0 ~ 3개 선 추가
		for(int i = 0; i <= 3; i++) {
			dfs(0, i);
			if(finish) break;
		}
		System.out.println(finish ? answer : -1);
	}
	
	static void dfs(int depth, int target) {
        if (finish) return;
        // 선 depth개 만큼 추가했으면 i -> i인지 체크 하고 리턴
        if (depth == target) {
            if (check()) {
                answer = target;
                finish = true;
            }
            return;
        }
        
        // 선 추가 해줄곳 찾고 추가해주기
        for (int i = 1; i <= H; i++) {
            for (int j = 1; j < N; j++) {
                // 현재 칸이나 오른쪽 칸에 선이 있으면 안 됨
                if (map[i][j] != 0 || map[i][j + 1] != 0) continue;

                map[i][j] = 1;
                map[i][j + 1] = -1;
                dfs(depth + 1, target); //백트
                // 선 추가해줬던거 수거(취소)
                map[i][j] = 0;
                map[i][j + 1] = 0;
            }
        }
    }
	// i 출발 -> i 도착인지 체크
	public static boolean check() {
		for(int start = 1; start <= N; start++) {
			int col = start;
			for(int row = 1; row <= H; row++) {
				if(map[row][col] == 1) col++;
				else if(map[row][col]==-1)col--;
			}
			if(col != start) return false;
		}
		return true;
	
	}


}
