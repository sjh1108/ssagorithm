import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2263_트리의_순회 {
	
	/*
	 * BOJ_2263 : 트리의 순회(Gold_1)
	 * 자료구조 및 알고리즘 : 트리, 분할 정복
	 *
	 * [문제 요약]
	 * - 이진 트리의 중위 순회(Inorder)와 후위 순회(Postorder)가 주어졌을 때,
	 * 전위 순회(Preorder) 결과를 출력하라.
	 *
	 * [핵심 아이디어]
	 * - 프리오더는 [루트 -> 왼쪽 -> 오른쪽] 순서이다. 즉, 루트를 먼저 찾아 출력해야 한다.
	 * - 1. 포스트오더의 가장 마지막 원소가 해당 서브트리의 '루트 노드'이다.
	 * - 2. 인오더에서 이 루트 노드의 위치를 찾으면, 그 기준 왼쪽은 '왼쪽 서브트리', 오른쪽은 '오른쪽 서브트리'가 된다.
	 * - 3. 서브트리의 크기(노드 개수)를 이용해 포스트오더 배열도 왼쪽/오른쪽 영역으로 나눈다.
	 * - 이 과정을 재귀적으로 반복하며 루트를 출력하면 프리오더가 된다.
	 *
	 * [구현 메모]
	 * - 인오더에서 루트의 위치를 매번 탐색하면 O(N^2)이 되어 시간 초과가 날 수 있다.
	 * - 미리 `index` 배열에 인오더 값들의 위치를 저장해두어 O(1)에 찾도록 최적화했다.
	 * - 재귀 호출 시 인오더와 포스트오더의 시작/끝 인덱스 4개를 관리하여 범위를 좁혀간다.
	 *
	 * [시간 복잡도]
	 * - 각 노드는 한 번씩 방문하여 출력된다.
	 * - 루트 위치 찾기 O(1) * N번 호출 -> O(N)
	 */
	
	static int n;
	static int[] inorder, postorder;
	static int[] index; // 인오더에서의 값 위치를 저장할 배열(Key: 노드값, Value: 인오더 인덱스)
	static StringBuilder sb = new StringBuilder();
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		
		inorder = new int[n];
		postorder = new int[n];
		index = new int[n+1]; // 노드 번호가 1~n이므로 n+1 크기 할당
		
		// 인오더 입력 및 위치 기록
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			int x = Integer.parseInt(st.nextToken());
			inorder[i] = x;
			index[x] = i; // 값 x가 인오더의 i번째에 있음을 저장 (O(1) 탐색용)
		}
		
		// 포스트오더 입력
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			postorder[i] = Integer.parseInt(st.nextToken());
		}
	}
	
	// 분할 정복을 수행하는 재귀 함수
	// si, ei : 현재 처리할 인오더의 시작, 끝 인덱스
	// sp, ep : 현재 처리할 포스트오더의 시작, 끝 인덱스
	static void preorder(int si, int ei, int sp, int ep) {
		
		// 1. 포스트오더의 끝(ep)이 현재 서브트리의 루트이다.
		int root = postorder[ep];
		sb.append(root + " ");
		
		// 2. 인오더에서 루트의 위치를 찾음
		int rootIdx = index[root];
		
		// 3. 서브트리 크기 계산 및 범위 분할
		int rightAmount = ei - rootIdx;
		
		// 4. 포스트오더에서 왼쪽/오른쪽 경계 계산
		int midP = ep - rightAmount;
		
		// 5. 재귀 호출
		if(rootIdx > si) preorder(si, rootIdx - 1, sp, midP - 1);
		if(rootIdx < ei) preorder(rootIdx + 1, ei, midP, ep - 1);
	}
	
	public static void main(String[] args) throws Exception {
		init();
		preorder(0, n-1, 0, n-1);
		
		System.out.println(sb);
	}
	
}