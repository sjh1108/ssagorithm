import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_7579_앱 {
	
	/*
	 * BOJ_7579 : 앱(Gold_3)
	 * 자료구조 및 알고리즘 : Knapsack(DP)
	 *
	 * [문제 요약]
	 * - 활성화된 앱들이 각각 m_i의 메모리와 c_i의 비활성화 비용을 가진다.
	 * - 앱들을 비활성화하여 최소 M 바이트 이상의 메모리를 확보해야 한다.
	 * - 이때 필요한 최소 비용을 구하라.
	 *
	 * [핵심 아이디어]
	 * 1. Index <-> Value
	 * - 필요한 메모리 M은 최대 10,000,000이므로 `dp[메모리] = 비용`으로 잡으면 배열 크기가 너무 커져 메모리 초과가 발생한다.
	 * - 반면, 비용(c_i)의 합은 최대 100 * 100 = 10,000으로 매우 작다.
	 * - 따라서 `dp[비용] = 확보할 수 있는 최대 메모리`로 정의하여 문제를 해결한다.
	 *
	 * 2. 탐색 범위 최적화 (Pruning)
	 * - 보통은 DP 테이블을 모두 채운 뒤 0부터 순회하며 M 이상이 되는 최소 비용을 찾는다.
	 * - 하지만 이 코드는 DP 갱신 과정에서 이미 목표 메모리 M을 달성한 고비용 구간을 
	 * 매 반복마다 `cnt`로 세어 탐색 범위(`max`)를 뒤에서부터 줄여나간다.
	 * - 결과적으로 불필요한 고비용 구간의 연산을 줄이고, 최종적으로 `max + 1`이 정답이 되도록 설계했다.
	 *
	 * [구현 메모]
	 * - max: 초기에는 모든 비용의 합(탐색 상한선)으로 설정하고, 최적화를 통해 점차 값을 줄여나감.
	 * - 점화식: dp[j] = Math.max(dp[j], dp[j - cost] + memory)
	 *
	 * [시간 복잡도]
	 * - 기본적으로 O(N * Sum(Cost))이다.
	 * - Sum(Cost)가 10,000으로 작아 충분히 통과하며, 최적화 로직으로 인해 실제 연산량은 더 적다.
	 */
	
	static int n, m, max;
	static int[] memory, cost, dp;
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); // 앱의 수
		m = Integer.parseInt(st.nextToken()); // 확보해야 할 목표 메모리
		
		memory = new int[n];
		cost = new int[n];
		
		// 메모리 입력
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			memory[i] = Integer.parseInt(st.nextToken());
		}
		
		// 비용 입력 및 최대 비용 합 계산
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			int x = Integer.parseInt(st.nextToken());
			cost[i] = x;
			max += x; // 초기 탐색 범위 : 모든 앱을 껐을 때의 비용 총합
		}
		
		// dp[i] : 비용 i를 사용했을 때 확보 가능한 최대 메모리 크기
		dp = new int[max+1];
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 냅색 알고리즘 수행
		for(int i=0; i<n; i++) {
			int cnt = 0; // 이번 턴에서 목표 메모리 M을 달성한 인덱스의 개수(범위 단축용 카운트)
			
			// 뒤에서부터 반복(1차원 배열을 이용한 Knapsack 중복 방지)
			for(int j=max; j>= cost[i]; j--) {
				// 점화식: 현재 앱을 비활성화하는 경우와 하지 않는 경우 중 더 큰 메모리 선택
				dp[j] = Math.max(dp[j], dp[j-cost[i]] + memory[i]);
				
				// [최적화 로직]
				// 현재 비용 j로 목표 메모리 M을 확보했다면, 
				// 이보다 더 큰 비용 구간은 이미 충분한 메모리를 확보한 상태이므로 
				// 다음 턴부터는 탐색 범위를 줄일 수 있다.
				if(dp[j] >= m) cnt++;
			}
			
			// 목표를 달성한 만큼 탐색 범위(상한선) 절감
			// 이미 필요한 만큼의 메모리가 확보된 고비용 구간을 다음 루프부터 제외
			max -= cnt;
		}
		
		// 루프가 끝난 후 max는 '목표 메모리 M을 달성하지 못한 최대 비용' 구간을 가리키게 됨.
		// 로직상 max가 줄어든 최종 결과에서 +1을 한 값이 최소 비용이 된다.
		System.out.println(max+1);
	}

}