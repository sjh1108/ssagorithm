import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2169_로봇_조종하기 {
	
	/*
	 * BOJ_2169 : 로봇 조종하기 (Gold_2)
	 * 자료구조 및 알고리즘 : DP(행 누적), 양방향 스캔(toRight/toLeft)
	 *
	 * [문제 요약]
	 * - 시작점 (0,0)에서 도착점 (R-1, C-1)까지 이동.
	 * - 이동 가능: 오른쪽, 왼쪽, 아래 (위로는 불가).
	 * - 각 칸의 가치를 최대 합으로 만들도록 경로를 선택.
	 *
	 * [접근 아이디어]
	 * - 위로 갈 수 없으므로 '행을 내려가며' 최적값을 전개한다.
	 * - i행에서 바로 윗행 dp[i-1][*]를 출발선으로 두고,
	 *   1) 왼→오 스캔(toRight):  왼쪽에서 온 값 vs 위에서 내려온 값
	 *   2) 오→왼 스캔(toLeft):   오른쪽에서 온 값 vs 위에서 내려온 값
	 *   - dp[i][j] = max(toRight[j], toLeft[j])
	 * - 본 코드에서는 마지막 행(R-1)은 별도로 처리:
	 *   - dp[R-1][0]을 윗칸과 현재 칸 합으로 시작하고,
	 *   - 오른쪽으로만 스캔하며 윗칸/왼칸에서 오는 경우 중 큰 값을 택해 누적.
	 *
	 * [시간/공간 복잡도]
	 * - 시간 O(R*C), 공간 O(R*C) (행당 보조 배열 O(C) 사용)
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// r: 행 수, c: 열 수
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());
		
		// map[y][x] : 칸의 가치
		// dp[y][x]  : (y,x)에 도달했을 때 가능한 최대 누적 가치
		int[][] map = new int[r][c];
		int[][] dp  = new int[r][c];
		
		// 격자 입력
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// --- 첫 행 초기화: (0,0)에서 오른쪽으로만 진행 가능 ---
		dp[0][0] = map[0][0];
		for(int i=1; i<c; i++) {
			dp[0][i] = dp[0][i-1] + map[0][i];
		}
		
		// 행이 1개면 첫 행 누적 합이 곧 정답
		if(r == 1) {
			System.out.println(dp[0][c-1]);
			return;
		}
		
		// --- 1행부터 R-2행까지: 각 행을 양방향 스캔으로 갱신 ---
		for(int i=1; i<r-1; i++) {
			int[] toRight = new int[c]; // i행에서 왼→오 진행 시 최대값
			int[] toLeft  = new int[c]; // i행에서 오→왼 진행 시 최대값
			
			// i행의 가장 왼/오른쪽 시작점은 '위에서 내려오는 값'으로 시동
			toRight[0]  = dp[i-1][0]    + map[i][0];
			toLeft[c-1] = dp[i-1][c-1]  + map[i][c-1];
			
			// 왼→오 스캔: (왼쪽에서 계속 오기) vs (위에서 바로 내려오기)
			for(int j=1; j<c; j++) {
				toRight[j] = Math.max(toRight[j-1], dp[i-1][j]) + map[i][j];
			}
			// 오→왼 스캔: (오른쪽에서 계속 오기) vs (위에서 바로 내려오기)
			for(int j=c-2; j>=0; j--) {
				toLeft[j] = Math.max(toLeft[j+1], dp[i-1][j]) + map[i][j];
			}
			
			// i행 최적값 확정
			for(int j=0; j<c; j++) {
				dp[i][j] = Math.max(toRight[j], toLeft[j]);
			}
		}
		
		// --- 마지막 행(R-1) 특수 처리: 오른쪽으로 스캔하며 갱신 ---
		// 첫 열: 위에서 내려온 값으로 시작
		dp[r-1][0] = dp[r-2][0] + map[r-1][0];
		
		// j열: (왼쪽에서 온 경로 연장) vs (위에서 내려온 경로 시작/연장) 중 택1
		for(int j=1; j<c; j++) {
			dp[r-1][j] = Math.max(dp[r-1][j-1], dp[r-2][j]) + map[r-1][j];
		}
		
		// 도착점의 최대 가치 출력
		System.out.println(dp[r-1][c-1]);
	}

}
