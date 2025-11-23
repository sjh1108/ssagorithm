import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_20303_할로윈의_양아치 {

	/*
	 * BOJ_20303 : 할로윈의 양아치 (Gold_3)
	 * 자료구조 및 알고리즘 : 유니온 파인드, DP(Knapsack)
	 *
	 * [문제 요약]
	 * - 아이들은 친구 그룹으로 묶여 있다. 한 아이의 사탕을 뺏으면 그 친구들의 사탕도 모두 뺏어야 한다.
	 * - 어른들에게 들키지 않으려면 뺏은 아이들의 총 인원수가 K명 미만이어야 한다.
	 * - 이때 뺏을 수 있는 사탕의 최대 개수를 구하라.
	 *
	 * [핵심 아이디어]
	 * 1. 그룹화 (Union-Find): 친구 관계인 아이들을 하나의 집합으로 묶는다.
	 * 이때, 루트 노드에 해당 그룹의 '총 인원 수(friends)'와 '총 사탕 수(candy)'를 합쳐서 관리한다.
	 * 2. 최대 가치 계산 (Knapsack DP):
	 * - 각 친구 그룹을 하나의 '물건'으로 본다.
	 * - 무게(Weight) = 그룹의 인원 수, 가치(Value) = 그룹의 사탕 수.
	 * - 배낭의 용량(Capacity) = K - 1 (K명 미만이어야 하므로).
	 * - 1차원 배열을 이용한 0/1 배낭 문제 점화식으로 해결한다.
	 *
	 * [구현 메모]
	 * - DP 배열 크기: K명 미만이어야 하므로 인덱스 K-1까지 사용 가능한 크기 K로 선언.
	 * - 중복 방지: Union-Find 후 루트 노드(root[i] == i)인 경우만 DP 연산을 수행한다.
	 *
	 * [시간 복잡도]
	 * - Union-Find: 거의 상수 시간 (경로 압축) -> O(M)
	 * - Knapsack DP: O(N * K)
	 * - 전체적으로 N, K가 최대 30,000 / 3,000 수준
	 */

	static int n, k;
	static int[] root;    // 각 노드의 부모(루트) 번호
	static int[] candy;   // 각 그룹(루트)이 가진 사탕의 총합
	static int[] friends; // 각 그룹(루트)에 속한 친구의 총 인원수
	
	public static void main(String[] args) throws Exception {
		init();
		
		// DP 배열: dp[i] = i명의 아이들에게서 뺏을 수 있는 최대 사탕 수
		// 조건이 'K명 미만'이므로 인덱스는 0부터 K-1까지 유효
		int[] dp = new int[k];
		
		// 모든 아이들을 순회하며 각 그룹(루트 노드)을 Knapsack의 물건으로 처리
		for(int i=0; i<n; i++) {
			// 1. 루트 노드가 아닌 경우(그룹의 구성원)는 건너뜀 (루트에서 합산된 정보만 사용)
			// 2. 해당 그룹의 인원수가 이미 K 이상이면 뺏을 수 없으므로 건너뜀
			if(root[i] != i || friends[i] >= k) continue;
			
			// 0/1 배낭 문제 (1차원 배열 최적화: 뒤에서부터 채움)
			// j: 현재 허용 가능한 인원 수 (무게)
			for(int j=k-1; j>=friends[i]; j--) {
				dp[j] = Math.max(dp[j], dp[j-friends[i]] + candy[i]);
			}
		}
		
		// K명 미만(최대 K-1명)일 때 얻을 수 있는 최대 사탕 수 출력
		System.out.println(dp[k-1]);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); // 아이 수
		int m = Integer.parseInt(st.nextToken()); // 친구 관계 수
		k = Integer.parseInt(st.nextToken()); // 울음이 터지는 인원 기준
		
		root = new int[n];
		candy = new int[n];
		friends = new int[n];
		
		// 사탕 정보 입력 및 초기화
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			root[i] = i; // 초기엔 자기 자신이 루트
			candy[i] = Integer.parseInt(st.nextToken());
			friends[i] = 1; // 초기엔 그룹 인원이 자기 자신 1명
		}
		
		// 친구 관계 입력 및 그룹 병합
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int c1 = Integer.parseInt(st.nextToken()) - 1; // 1-based -> 0-based
			int c2 = Integer.parseInt(st.nextToken()) - 1;
			union(c1, c2);
		}
	}
	
	// Find: 경로 압축 적용
	static int find(int x) {
		if(root[x] == x) return x;
		return root[x] = find(root[x]);
	}
	
	// Union: 두 그룹을 합치면서 사탕 수와 인원 수를 한쪽으로 몰아줌
	static void union(int a, int b) {
		int ra = find(a), rb = find(b);
		if(ra == rb) return;
		
		// 최적화: 친구 수가 더 많은 쪽(혹은 임의 기준)으로 병합하여 트리의 균형 유지 시도
		if(friends[ra] >= friends[rb]) {
			root[rb] = ra; // rb를 ra 밑으로
			friends[ra] += friends[rb]; // 인원 합산
			candy[ra] += candy[rb];     // 사탕 합산
		}
		else {
			root[ra] = rb; // ra를 rb 밑으로
			friends[rb] += friends[ra];
			candy[rb] += candy[ra];
		}
	}
	
}