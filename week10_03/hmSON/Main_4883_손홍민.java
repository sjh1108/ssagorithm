import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4883 {
	
	// BOJ_4883 : 삼각 그래프(Silver_1)
	// 자료구조 및 알고리즘 : DP
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 행 수
	static int r;
	// 맵 정보, DP 배열
	static int[][] map, dp;
	
	static void init() throws Exception {
		r = Integer.parseInt(br.readLine());
		// 0을 입력받은 경우 추가 입력이 필요 없음
		if(r == 0) return;
		
		// 열 수는 반드시 3으로 고정됨
		map = new int[r][3];
		dp = new int[r][3];
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<3; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		
		// dp[0][0]으로는 절대 이동할 수 없으므로 비교를 위해 dp[0][1]보다 큰 값 입력
		// dp[0][1]은 시작점이므로 map[0][1]의 값을 그대로 저장
		// dp[0][2]는 시작점으로부터 이동하는 경로만 존재하므로 두 위치의 값을 더한 뒤 저장
		dp[0][0] = map[0][1] + 1;
		dp[0][1] = map[0][1];
		dp[0][2] = dp[0][1] + map[0][2];
	}
	
	public static void main(String[] args) throws Exception {
		int clock = 1;
		while(true) {
			init();
			// 0을 입력받는 경우 즉시 루프 종료
			if(r == 0) break;
			
			// 자신을 참조하는 이전 경로 중 최소값 + 현재 위치의 비용
			for(int i=1; i<r; i++) {
				dp[i][0] = map[i][0] + Math.min(dp[i-1][0], dp[i-1][1]);
				dp[i][1] = map[i][1] + Math.min(dp[i-1][0], Math.min(dp[i][0], Math.min(dp[i-1][1], dp[i-1][2])));
				dp[i][2] = map[i][2] + Math.min(dp[i-1][1], Math.min(dp[i][1], dp[i-1][2]));
			}
			
			// 목적지는 (r-1, 1)이므로 해당 위치의 dp 배열 값을 출력
			sb.append(clock + ". " + dp[r-1][1] + "\n");
			clock++;
		}
		
		System.out.println(sb);
	}

}
