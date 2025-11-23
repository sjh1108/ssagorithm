import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_16562_친구비 {
	
	/*
	 * BOJ_16562 : 친구비 (Gold_4)
	 * 자료구조 및 알고리즘 : 분리 집합 (Union-Find), 그리디 (Greedy)
	 *
	 * [문제 요약]
	 * - 학생 N명이 있고, 각 학생과 친구가 되기 위한 비용 A_i가 주어진다.
	 * - "친구의 친구는 친구다"라는 규칙에 따라, 한 명과 친구가 되면 그 그룹의 모든 학생과 친구가 된다.
	 * - 주어진 예산 K원 이하로 모든 학생과 친구가 될 수 있는지 판별하고, 최소 비용을 출력한다.
	 *
	 * [핵심 아이디어]
	 * - 친구 관계를 그래프로 보았을 때, 연결된 컴포넌트(친구 그룹) 중 
	 * 가장 '친구비가 싼' 사람 한 명에게만 돈을 내면 그 그룹 전체와 친구가 될 수 있다.
	 * - Union-Find를 사용하여 친구 그룹을 묶되, 
	 * 두 그룹을 합칠 때(Union) **비용이 더 적은 사람을 부모(대표)**로 설정한다.
	 * - 최종적으로 각 그룹의 대표(Root)들의 비용 합이 총비용이 된다.
	 *
	 * [구현 메모]
	 * - req[]: 각 학생의 친구비 정보.
	 * - HashSet: 모든 처리가 끝난 후 중복 없이 각 그룹의 루트(대표)만 추출하기 위해 사용.
	 *
	 * [시간 복잡도]
	 * - Union-Find 연산은 경로 압축 적용 시 거의 상수 시간(Ackermann 역함수).
	 * - 전체 복잡도: O(M * α(N) + N)
	 */
	
	static int n, k;
	static int[] req;  // 각 학생별 친구비 비용
	static int[] head; // 유니온 파인드 부모 배열
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken()); // 학생 수
		int rel = Integer.parseInt(st.nextToken()); // 친구 관계 수
		k = Integer.parseInt(st.nextToken()); // 가진 돈
		
		req = new int[n];
		head = new int[n];
		
		// 친구비 입력 및 초기화
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			head[i] = i; // 처음엔 자기 자신이 대표
			req[i] = Integer.parseInt(st.nextToken());
		}
		
		// 친구 관계 입력 및 Union 수행
		for(int i=0; i<rel; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1; // 0-based 변환
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			union(a, b);
		}
	}
	
	// Find: 경로 압축(Path Compression) 적용
	static int find(int x) {
		if(head[x] == x) return x;
		return head[x] = find(head[x]);
	}
	
	// Union: 비용이 더 적은 쪽이 부모가 되도록 병합 (Greedy)
	static void union(int a, int b) {
		int ra = find(a), rb = find(b);
		if(ra == rb) return; // 이미 같은 그룹이면 패스
		
		// 비용이 작은 노드가 대표가 되어야 최소 비용을 구할 수 있음
		if(req[ra] > req[rb]) head[ra] = rb; // rb가 더 싸므로 rb를 부모로
		else head[rb] = ra; // ra가 더 싸거나 같으므로 ra를 부모로
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 모든 학생에 대해 find를 수행하여 최종적인 루트를 확인하고 Set에 담아 중복 제거
		// (이미 union 되었어도 경로 압축이 완전히 안 되었을 수 있으므로 find 호출 권장)
		Set<Integer> set = new HashSet<>();
		for(int i=0; i<n; i++) set.add(find(i));
		
		int total = 0;
		// 각 그룹 대표들의 친구비 비용 합산
		for(int h : set) total += req[h];
		
		// 예산 초과 여부 확인
		System.out.println(total > k ? "Oh no" : total);
	}

}