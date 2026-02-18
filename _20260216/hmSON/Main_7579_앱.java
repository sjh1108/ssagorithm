import java.io.*;
import java.util.*;

public class Main_7579_앱 {

	/*
	 * BOJ_7579 : 앱 (Gold_3)
	 * 자료구조 및 알고리즘 : Knapsack
	 *
	 * [문제 요약]
	 * - N개의 앱이 있고 i번째 앱을 비활성화하면 memory[i]만큼 메모리를 확보하고 cost[i]만큼 비용이 든다.
	 * - 총 확보 메모리가 M 이상이 되도록 할 때, 필요한 최소 비용을 구하라.
	 *
	 * [핵심 아이디어]
	 * - "확보 메모리"를 기준으로 DP를 만들면 메모리 합이 최대 10,000,000까지 커질 수 있어 배열이 부담된다.
	 * - 반대로 "비용"을 기준으로 DP를 만들면 비용의 총합(= Σcost)은 최대 100 * 100 = 10,000 수준이라 관리 가능.
	 * - dp[j] = 비용 j를 사용했을 때 확보 가능한 최대 메모리(Knapsack).
	 * - dp[j] >= M이 되는 최소 j를 찾으면 정답.
	 *
	 * [구현 메모]
	 * - max = 모든 cost의 합(비용 DP 배열 크기).
	 * - 같은 앱을 중복 선택하지 않도록 j를 뒤에서 앞으로(역순) 갱신한다.
	 * - 본 코드는 dp 갱신 중 dp[j]가 reqMemory 이상이 되면 max를 감소시키는 방식으로
	 *   "정답 후보 상한"을 줄여가며 마지막에 max+1을 출력한다.
	 *   (즉, dp가 목표를 만족하는 비용 인덱스들이 생기면 그 이상의 영역을 전부 보지 않으려는 가지 치기)
	 *
	 * [시간 복잡도]
	 * - 비용 합을 C = Σcost라 하면,
	 * - DP: O(N * C), 메모리: O(C)
	 */

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int n = Integer.parseInt(st.nextToken());
		int reqMemory = Integer.parseInt(st.nextToken());

		int[] memory = new int[n], cost = new int[n];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			memory[i] = Integer.parseInt(st.nextToken());
		}

		st = new StringTokenizer(br.readLine());
		int max = 0; // 비용 총합(= DP 배열 크기)
		for(int i=0; i<n; i++) {
			int x = Integer.parseInt(st.nextToken());
			max += x;
			cost[i] = x;
		}

		// dp[j] = 비용 j로 확보 가능한 최대 메모리
		int[] dp = new int[max+1];

		for(int i=0; i<n; i++) {
			// Knapsack: 같은 앱을 중복 선택하지 않도록 역순 탐색
			for(int j=max; j>=cost[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j-cost[i]] + memory[i]);

				// 목표 메모리를 달성한 비용 구간이 나오면,
				// 이후의 구간은 볼 필요가 없으므로 상한 축소 가지 치기
				if(dp[j] >= reqMemory) max--;
			}
		}

		// 최종적으로 max는 조건 달성이 불가능한 가장 높은 비용이며, max+1은 조건 달성이 가능한 가장 낮은 비용이다.
		System.out.println(max+1);
	}

}
