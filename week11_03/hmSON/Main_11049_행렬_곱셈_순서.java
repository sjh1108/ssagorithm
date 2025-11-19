import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11049_행렬_곱셈_순서 {
	
	/*
	 * BOJ_11049 : 행렬 곱셈 순서 (Gold_3)
	 * 자료구조 및 알고리즘 : DP(구간 DP, Matrix Chain Multiplication)
	 *
	 * [문제 요약]
	 * - 크기가 각각 (r1×c1), (r2×c2), ... 인 n개의 행렬 A0..A{n-1}가 주어진다.
	 * - 모든 행렬을 주어진 순서대로 곱하되, 괄호치는 순서를 적절히 정해,
	 *   총 스칼라 곱셈 횟수(연산 수)를 최소화하라.
	 *
	 * [핵심 아이디어]
	 * - 행렬 곱의 결합법칙에 따라 곱셈 순서만 바꿔도 연산 수가 달라진다.
	 * - dp[i][j] : 연속한 행렬 Ai..Aj 를 곱하는 데 필요한 최소 곱셈 횟수
	 *   - 구간 길이를 1부터 n-1까지 늘려가며(짧은 구간 → 긴 구간) 채운다.
	 *   - 분할점 k(i ≤ k < j)를 기준으로 (Ai..Ak)와 (A{k+1}..Aj)를 먼저 계산하고 마지막에 두 결과를 곱한다.
	 *   - 이때 마지막 곱의 비용은 p_i * p_{k+1} * p_{j+1}
	 *     (여기서 p_t는 차원 배열, 코드에선 p_i=arr[i][0], p_{t+1}=arr[t][1])
	 *
	 * [전이식]
	 *   dp[i][j] = min_{i ≤ k < j} ( dp[i][k] + dp[k+1][j] + p_i * p_{k+1} * p_{j+1} )
	 *
	 * [시간/공간 복잡도]
	 * - 시간 O(n^3), 공간 O(n^2)
	 */
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int n = Integer.parseInt(br.readLine()); // 행렬의 개수
		int[][] arr = new int[n][2];             // arr[i] = {rows, cols} for Ai
		
		// 차원 입력: Ai = arr[i][0] × arr[i][1]
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			arr[i][0] = Integer.parseInt(st.nextToken()); // 행
			arr[i][1] = Integer.parseInt(st.nextToken()); // 열
		}
		
		// dp[i][j] : Ai..Aj를 곱하는 최소 연산 수 (대각선은 0: 단일 행렬은 곱셈 필요 없음)
		int[][] dp = new int[n][n];
		
		// len = j - i : 구간 길이(행렬 개수 - 1)
		// i=1부터 시작하는 바깥 루프는 "구간의 실제 길이(len+1)"를 의미
		for(int len = 1; len < n; len++) {              // 구간 길이 2..n
			for(int j = len; j < n; j++) {             // 구간의 오른쪽 끝 j
				int i = j - len;                       // 구간의 왼쪽 끝 i
				int min = Integer.MAX_VALUE;
				
				// 분할점 k : (Ai..Ak) | (A{k+1}..Aj)
				for(int k = i; k < j; k++) {
					// 왼쪽 구간 dp[i][k] + 오른쪽 구간 dp[k+1][j]
					// 마지막 두 결과 행렬의 곱 비용: arr[i][0] * arr[k][1] * arr[j][1]
					//  - p_i   = arr[i][0]
					//  - p_{k+1} = arr[k][1]
					//  - p_{j+1} = arr[j][1]
					int cost = dp[i][k] + dp[k+1][j]
					         + (arr[i][0] * arr[k][1] * arr[j][1]);
					min = Math.min(min, cost);
				}
				
				dp[i][j] = min; // i..j 구간의 최소 비용 확정
			}
		}
		
		// 전체 구간 A0..A{n-1}의 최소 곱셈 횟수
		System.out.println(dp[0][n-1]);
	}

}
