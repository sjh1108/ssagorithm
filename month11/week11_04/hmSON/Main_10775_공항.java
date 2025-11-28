import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_10775_공항 {
	
	/*
	 * BOJ_10775 : 공항 (Gold_2)
	 * 자료구조 및 알고리즘 : 그리디 + 유니온파인드(분리집합, 경로 압축)
	 *
	 * [문제 요약]
	 * - 게이트가 1..G까지 있고, i번째 비행기는 1..g_i 중 하나의 게이트에만 도킹할 수 있다.
	 * - 각 비행기를 순서대로 가능한 가장 큰 번호의 **빈** 게이트에 도킹시키되,
	 *   도킹할 게이트가 없으면 즉시 중단한다.
	 * - 도킹 가능한 비행기 수의 최댓값을 출력.
	 *
	 * [핵심 아이디어]
	 * - "가능한 가장 큰 게이트"로 배정하는 것이 최적(그리디).
	 * - 이미 배정된 게이트는 사용할 수 없으므로, 그보다 "바로 작은" 게이트로 연결시키는
	 *   유니온파인드 트릭을 사용:
	 *     - parent[x]는 "x 게이트가 비었을 때, 배정 가능한 최댓값 게이트 대표"를 가리킨다.
	 *     - x가 배정되면 parent[x]를 x-1로 연결(= 다음에 x를 찾으면 자동으로 x-1로 안내).
	 *     - find(x)가 -1이면 더 이상 배정할 게이트가 없다는 뜻.
	 *
	 * [구현 메모]
	 * - 0-based로 처리:
	 *     게이트: 0..G-1,  입력 g_i는 (g_i-1)로 변환
	 * - find(x): x == -1 이거나 parent[x] == x면 대표.
	 * - union(x): 대표 r = find(x), r == -1이면 불가, 아니면 parent[r] = r-1로 연결하고 true 반환.
	 *
	 * [시간 복잡도]
	 * - 각 비행기마다 find/union이 거의 상수(경로 압축) → O(P)
	 */
	
	// parent 배열(여기서는 head): head[x] = x가 속한 집합의 대표(가용 최댓값 게이트 인덱스)
	static int[] head;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int g = Integer.parseInt(br.readLine()); // 게이트 수(0..G-1)
		int p = Integer.parseInt(br.readLine()); // 비행기 수
		
		// 유니온파인드 초기화: 처음엔 각 게이트가 자기 자신을 대표
		head = new int[g];
		for (int i = 0; i < g; i++) head[i] = i;
		
		// 비행기 입력: 1-based를 0-based로 바꿔 저장
		int[] planes = new int[p];
		for (int i = 0; i < p; i++) {
			planes[i] = Integer.parseInt(br.readLine()) - 1;
		}
		
		int cnt = 0; // 도킹 성공한 비행기 수
		for (int x : planes) {
			boolean dock = union(x); // x 이하에서 가장 큰 가용 게이트에 배정 시도
			if (!dock) break;        // 불가하면 즉시 중단
			cnt++;
		}
		
		System.out.println(cnt);
	}
	
	/* find(x)
	 * - x 이하에서 배정 가능한 "가장 큰" 게이트의 대표를 반환.
	 * - x == -1 이면 더 이상 배정 불가를 의미.
	 * - 경로 압축으로 다음 탐색을 빠르게.
	 */
	static int find(int x) {
		if (x == -1 || head[x] == x) return x;
		return head[x] = find(head[x]);
	}
	
	/* union(x)
	 * - x 이하에서 가능한 가장 큰 게이트 r = find(x)를 찾는다.
	 * - r == -1이면 배정 불가 → false
	 * - r에 배정하고, 다음 번엔 r-1로 안내되도록 head[r] = r-1 로 연결 → true
	 */
	static boolean union(int x) {
		int r = find(x);
		if (r == -1) return false;   // 배정할 게이트가 없음
		head[r] = r - 1;             // r 게이트 사용 처리: 다음엔 r-1로 안내
		return true;
	}
}
