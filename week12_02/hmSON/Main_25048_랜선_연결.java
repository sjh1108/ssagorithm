import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_25048_랜선_연결 {
	
	/*
	 * BOJ_25048 : 랜선 연결(Gold_2)
	 * 자료구조 및 알고리즘 : Knapsack(DP)
	 *
	 * [문제 요약]
	 * - 스위치와 컴퓨터를 연결하여 M개의 컴퓨터에 인터넷을 공급하기 위한 최소 비용을 구하라.
	 *
	 * [핵심 아이디어]
	 * - 랜선, 스위치, 랜 포트의 특성상 인터넷을 연결해주는 랜선의 존재로 인해
	 * 각 스위치마다 하나의 포트는 무조건 업링크(Uplink) 용도로 사용해야 한다.
	 * - 따라서 한 스위치에 다른 스위치를 연계하면서 일정 개수의 컴퓨터에 모두 인터넷을 공급하려면,
	 * 기존 연결에서 포트 여유분을 하나씩 더 확보해야 한다는 논리로 접근했다.
	 * - 이를 냅색(Knapsack) 알고리즘에 적용하여 최소 비용을 계산한다.
	 *
	 * [구현 메모]
	 * - port[i] = 입력값 - 1 : 연계 용도로 쓸 포트 1개를 미리 제외하고 저장.
	 * - dp[j - port[i] + 1] :
	 * 현재 스위치를 추가하여 j개를 만족시키려면, 이전 상태는 (j - port[i])가 아니라
	 * 스위치를 꽂을 포트 하나가 더 필요하므로 +1을 하여 여유분을 확보한 상태여야 한다.
	 *
	 * [시간 복잡도]
	 * - 스위치 수(N) * 목표 컴퓨터 수(M) -> O(N*M)
	 */
	
	static final long INF = 100_000_000_000_000L; // 충분히 큰 값(비용 합의 최대치 고려)
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int sw = Integer.parseInt(br.readLine()); // 스위치 개수
		int[] port = new int[sw]; // 각 스위치의 포트 수(P-1 저장)
		int[] cost = new int[sw]; // 각 스위치의 비용
		
		for(int i=0; i<sw; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 스위치 자체의 포트 1개는 상위 연결(업링크)에 필수적이므로 미리 1을 뺌
			port[i] = Integer.parseInt(st.nextToken()) - 1;
			cost[i] = Integer.parseInt(st.nextToken());
		}
		
		int com = Integer.parseInt(br.readLine()); // 목표 컴퓨터 수
		
		// 컴퓨터가 1대라면 스위치 없이 벽면 포트에 꽂으면 됨(비용 0)
		if(com == 1) {
			System.out.println(0);
			return;
		}
		
		// DP 배열 초기화
		long[] dp = new long[com+1];
		Arrays.fill(dp, INF);
		
		// 초기 상태 설정
		// dp[1] = 0 : 컴퓨터 1대는 기본 제공됨
		// dp[0] = 0 : 0대는 비용 0 (논리적 기저)
		dp[0] = 0L; 
		dp[1] = 0L;
		
		// 냅색 알고리즘 수행
		for(int i=0; i<sw; i++) {
			// 포트가 1개인 스위치(입력값 기준 2개)는 연결해도 의미 없음.
			// 상위 포트를 1개 차지하고 자기 포트를 1개 내주는 형태이므로 의미 없는 행위이거나, 손해임.
			if(port[i] == 1) continue; 
			
			// 1차원 배열을 이용한 냅색이므로 뒤에서부터 탐색(중복 사용 방지)
			// j는 목표 컴퓨터 수부터 현재 스위치로 커버 가능한 범위까지
			for(int j=com; j>=port[i]; j--) {
				// 점화식:
				// 현재 j대를 연결하는 비용 = min(기존 비용, (j - 순수증가량)대의 비용 + 현재 스위치 비용)
				// 여기서 순수 증가량은 (P-2)개이다.
				// 코드의 port[i]는 P-1이므로, (j - (port[i] - 1)) = (j - port[i] + 1) 인덱스를 참조한다.
				if(dp[j-port[i]+1] != INF) {
					dp[j] = Math.min(dp[j], dp[j-port[i]+1] + cost[i]);
				}
			}
		}
		
		// 목표 컴퓨터 수(com)를 달성하는 것이 불가능하면 -1, 가능하면 최소 비용 출력
		System.out.println(dp[com] == INF ? -1 : dp[com]);
	}

}