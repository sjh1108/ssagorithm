package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_7569 {
	
	static int M, N;
	static int[][] map;
	static int[] dx = {-1,1,0,0}; // 상하좌우
	static int[] dy = {0,0,-1,1};
	// BFS는 visited가 없어도 됨. 
	// 이미 map에 날짜를 기록하므로 visited 불필요 (중복 탐색 방지)
	// 확장된 토마토의 이븐한 익힘을 위해 턴을 익는 토마토에 기록하고, 마지막 출력시 
	// 0턴시 초기 토마토값이 1이었기에 라스트 토마토값 - 1 해서 출력
		
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		
		N = Integer.parseInt(st.nextToken()); // 열
		M = Integer.parseInt(st.nextToken()); // 행 		
		map = new int[M][N]; // 행 열 <- 이런식으로 써야 아래에서 안 헷갈릴 듯.

        Queue<int[]> q = new ArrayDeque<>();
        
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 1) q.offer(new int[]{i, j});
			}
		}

        bfs(q); //BFS 실행

        
		// 입력
//		for (int i = 0; i < M; i++) {
//			for (int j = 0; j < N; j++) {
//				System.out.print(map[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		int days = 0;
        for (int i = 0; i  < M; i++) {
            for (int j = 0; j < N; j++) {
                if(map[i][j] == 0) {
                    System.out.println(-1);
                    return; // 모든 토마토를 익히지 못한 경우 슬프지만.. 
                }

                days = Math.max(days, map[i][j]); // 토마토 턴이 map에 다 기입되있으니, 그중 최고값 찾기.               
            }
        }
		System.out.println(days - 1); // 첫 토마토가 1이기에 -1해줌.		
	}

	private static void bfs(Queue<int[]> q) {

        while(!q.isEmpty()) { // q요소 offer한거 다 불러와서 익힘을 감염시킴. 
            int[] cur = q.poll(); // 한 놈씩 혼내줘!
            int r = cur[0]; // int[] {i,j} 했으니까, 좌표 한 잔해!
            int c = cur[1];

            for (int d = 0; d < 4; d++) { // 4방 탐색의 묘미.
                int nr = r + dx[d];
                int nc = c + dy[d];

                if(nr < 0 || nc < 0 || nr >= M || nc >= N) continue; // 격자처리
                if(map[nr][nc] == 0){ // 감염당해야지?
                    map[nr][nc] = map[r][c] + 1; // 턴을 기입
                    q.offer(new int[]{nr,nc}); // q요소의 상하좌우 너희도 감염을 시작해라!
                }
            }
        }
	}
}
