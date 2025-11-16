import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_1419_등차수열의_합 {
	
	/*
	 * BOJ_1419 : 등차수열의 합
	 * 자료구조 및 알고리즘 : 수학(패턴 분석), 구간 누적(count(right) - count(left-1))
	 *
	 * [문제 요약]
	 * - 정수 구간 [L, R]과 길이 k(2 ≤ k ≤ 5)가 주어진다.
	 * - 첫항 a(≥1), 공차 d(≥1)인 길이 k의 등차수열 합 S = k/2 * (2a + (k-1)d) 로
	 *   표현 가능한 정수의 개수를 구간 [L, R]에서 세어라.
	 *
	 * [핵심 아이디어]
	 * - count(N, k) = { 1 ≤ S ≤ N 이면서 길이 k 등차수열 합으로 표현 가능한 S의 개수 } 를
	 *   k별로 닫힌형(폐형)으로 정리한 뒤,
	 *   정답 = count(R, k) - count(L-1, k) 로 계산한다.
	 *
	 * [k별 패턴 정리]
	 *  k=2: S = 2a + d, a≥1, d≥1 → 3 이상 모든 정수 표현 가능
	 *        ⇒ count(N) = max(0, N - 2)
	 *  k=3: S = 3(a + d), a≥1, d≥1 → 6, 9, 12, ... (3의 배수 중 6 이상)
	 *        ⇒ count(N) = max(0, ⌊N/3⌋ - 1)
	 *  k=4: S = 2(2a + 3d), a≥1, d≥1 → 짝수 중 10,14,16,18,... (단, 12는 불가능)
	 *        ⇒ count(N) = #짝수(≥10) - [12가 포함되면 1 제외]
	 *                     = max(0, ⌊N/2⌋ - 4 - 1{N≥12})
	 *  k=5: S = 5(a + 2d), a≥1, d≥1 → 15,20,25,... (5의 배수 중 15 이상)
	 *        ⇒ count(N) = max(0, ⌊N/5⌋ - 2)
	 *
	 * [시간 복잡도]
	 * - O(1) : k별 폐형 공식으로 직접 계산
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력: 구간 [left, right], 길이 k
		long left  = Integer.parseInt(br.readLine());
		long right = Integer.parseInt(br.readLine());
		int k      = Integer.parseInt(br.readLine());
		
		// 구간 합 = count(right) - count(left - 1)
		long res = calc(right, k) - calc(left - 1, k);
		System.out.println(Math.max(0L, res)); // 안전 가드(음수 방지)
	}

	/*
	 * calc(sum, k)
	 * - 1 이상 sum 이하에서, 길이 k 등차수열의 합으로 표현 가능한 수의 개수 반환
	 * - sum ≤ 0 이면 0
	 * - k ∈ {2,3,4,5} 에 대해 위의 패턴을 폐형으로 구현
	 */
	static long calc(long sum, int k) {
		if (sum <= 0) return 0; // [1..sum]이 비거나 무효인 경우
		
		long res = 0;
		switch (k) {
			case 2: // S = 2a + d (a,d≥1) → 3 이상 모든 정수
				// 개수 = max(0, sum - 2)
				res = sum - 2;
				break;
				
			case 3: // S = 3(a + d) → 3의 배수 중 6 이상
				// 개수 = max(0, floor(sum/3) - 1)  (6부터 시작이므로 3의 배수 개수에서 1 빼기)
				res = sum / 3 - 1;
				break;
				
			case 4: // S = 2(2a + 3d) → 짝수 중 10 이상, 단 12는 불가
				// 짝수(≥10) 개수 = floor(sum/2) - 4  (10,12,14,...이므로 2씩 나눠 5부터 → -4)
				// 여기서 12는 표현 불가라서 sum ≥ 12면 1개 제외
				if (sum < 10) res = 0;
				else res = sum / 2 - 4 - (sum >= 12 ? 1 : 0);
				break;
				
			case 5: // S = 5(a + 2d) → 5의 배수 중 15 이상
				// 개수 = max(0, floor(sum/5) - 2) (15=3*5부터 시작이므로 -2)
				res = sum / 5 - 2;
				break;
		}
		
		// 음수 보정
		if (res < 0L) res = 0L;
		return res;
	}
	
}
