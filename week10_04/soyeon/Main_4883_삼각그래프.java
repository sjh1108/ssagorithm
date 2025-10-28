package bk.silver; //1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4883_삼각그래프 {

	/**
	 *  (2 ≤ N ≤ 100,000) 
	 * 
	 * 각 정점까지의 비용을 미리 계산해서 map에 저장 
	 * 
	 * 
	 * 후기
	 * - DP를 어떻게 했는지 까먹어서 점화식을 사용하지 않았음
	 * - 일단 풀리긴했는데 상당히 꼬인 코드 같습니다.
	 * - 다음에 dp로 다시 풀어봐야겠음
	 */
	static int N;
	static int[][] map;
	static int[] dr = {-1,-1,-1, 0};
	static int[] dc = {-1,0,1, -1};
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int tc = 0;
		while(true) {
			tc++;
			
			N = Integer.parseInt(br.readLine());
			if(N == 0) break;

			map = new int[N][3];
			
			for(int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine(), "s ");
				// 첫줄에서 다른 정점 출발을 막기 위해 출발 비용으로 변경 

			
				for(int c = 0; c < 3; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
			
					
					if(r == 0 && (c == 0 || c== 1)) continue;
					if(r == 1) {
						map[0][0] = map[0][1];
					}
					// 맵 생성할때부터 최소값을 저장하고자 함 
					// -1행의 3개의 정점을 고려해 가장 최소값을 map[r][c]에 저장  
					int min = Integer.MAX_VALUE;
					for(int d = 0; d < 4; d++) {
						int nr = r + dr[d];
						int nc = c + dc[d];
						// map을 벗어나지 않는 다면
						if(nr >= 0 && nc >= 0 && nc <3) {
							// 최소값 갱신
							if(min > map[nr][nc] + map[r][c]) {
								min = map[nr][nc] + map[r][c];
							};
						}
					}
					if(min != Integer.MAX_VALUE) map[r][c] = min;

				}
			}// 입력 끝
			
			
			sb.append(tc).append(". ").append(map[N-1][1]).append("\n");
			
			
		}
		System.out.println(sb.toString());
	}
}
