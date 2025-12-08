import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11505_구간_곱_구하기 {
	
	/*
	 * BOJ_11505 : 구간 곱 구하기 (Gold_1)
	 * 자료구조 및 알고리즘 : 세그먼트 트리 (Segment Tree)
	 *
	 * [문제 요약]
	 * - N개의 수가 주어지고, 중간에 수의 변경이 빈번하게 일어난다.
	 * - 특정 구간의 곱을 구하는 명령과 수를 변경하는 명령을 처리해야 한다.
	 * - 계산 결과는 1_000_000_007로 나눈 나머지를 출력한다.
	 *
	 * [핵심 아이디어]
	 * - 데이터의 변경과 구간 연산이 빈번하므로 세그먼트 트리를 사용한다. (시간복잡도 O(logN))
	 * - '구간 합'과 달리 '구간 곱' 문제이므로, 트리의 빈 노드나 초기값은 곱셈의 항등원인 '1'로 설정해야 한다.
	 * - 숫자가 급격히 커지므로 모든 곱셈 연산 직후에 모듈러(MOD) 연산을 수행하여 오버플로우를 방지한다.
	 *
	 * [구현 메모]
	 * - 모든 연산에 % MOD가 적용되어야 한다.
	 *
	 * [시간 복잡도]
	 * - 트리 구축(Init): O(N)
	 * - 업데이트(Update): O(logN)
	 * - 구간 곱 계산(Calc): O(logN)
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	
	static int n, cmds; // 수의 개수, 명령의 총 개수
	static int p;       // 리프 노드가 시작되는 인덱스 (2^k 형태, 트리의 크기 결정)
	static long[] tree; // 세그먼트 트리 배열
	static final int MOD = 1_000_000_007; // 나머지 연산을 위한 상수
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		cmds = Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken()); // 변경 횟수 + 구간 곱 횟수
		
		// 1. 트리의 크기(p) 결정
		// N보다 크거나 같은 가장 작은 2의 거듭제곱 값을 찾음
		p = 1;
		while(p < n) p <<= 1;
		
		tree = new long[2 * p]; // 전체 노드 개수는 약 2*p개
		
		// 2. 리프 노드 초기화
		for(int i=0; i<p; i++) {
			if(i < n) tree[p+i] = Integer.parseInt(br.readLine());
			else tree[p+i] = 1; // N개 이후의 남는 리프 노드는 곱셈에 영향 없는 1로 채움
		}
		
		// 3. 내부 노드(부모 노드) 구축
		// 부모 = (왼쪽 자식 * 오른쪽 자식) % MOD
		for(int i=p-1; i>0; i--) {
			tree[i] = (tree[i << 1] * tree[i << 1 | 1]) % MOD;
		}
	}
	
	// 특정 인덱스(idx)의 값을 num으로 업데이트
	static void update(int idx, int num) {
		int node = p + idx; // 실제 트리 배열에서의 인덱스
		
		// 값이 같다면 갱신 불필요
		if(tree[node] == (long)num) return;
		
		tree[node] = num;
		
		// 리프 노드부터 루트까지 올라가며 부모 노드 갱신
		while(node > 1) {
			node >>= 1; // 부모 노드로 이동
			// 부모 = (왼쪽 자식 * 오른쪽 자식) % MOD
			tree[node] = (tree[node << 1] * tree[node << 1 | 1]) % MOD;
		}
	}
	
	// 구간[start, end] 곱 계산
	static long calc(int start, int end) {
		// 트리 배열 인덱스로 변환
		start += p; 
		end += p;
		long res = 1;
		
		while(start <= end) {
			// start가 오른쪽 자식(홀수)이면, 해당 값을 결과에 곱하고 범위를 좁힘(오른쪽으로 이동)
			if((start & 1) == 1) res = (res * tree[start++]) % MOD;
			
			// end가 왼쪽 자식(짝수)이면, 해당 값을 결과에 곱하고 범위를 좁힘(왼쪽으로 이동)
			if((end & 1) == 0) res = (res * tree[end--]) % MOD;
			
			// 부모 레벨로 이동
			start >>= 1; 
			end >>= 1;
		}
		
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		StringBuilder sb = new StringBuilder();
		
		for(int i=0; i<cmds; i++) {
			st = new StringTokenizer(br.readLine());
			int cmd = Integer.parseInt(st.nextToken());
			
			if(cmd == 1) { // 업데이트 명령
				int idx = Integer.parseInt(st.nextToken()) - 1; // 인덱스는 반드시 0-based로
				int num = Integer.parseInt(st.nextToken());
				update(idx, num);
				
			} else { // 구간 곱 명령
				int start = Integer.parseInt(st.nextToken()) - 1;
				int end = Integer.parseInt(st.nextToken()) - 1;
				sb.append(calc(start, end) + "\n");
			}
		}
		
		System.out.println(sb);
	}

}