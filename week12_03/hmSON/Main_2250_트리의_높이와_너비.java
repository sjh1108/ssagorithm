import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2250_트리의_높이와_너비 {
	
	/*
	 * BOJ_2250 : 트리의 높이와 너비(Gold_2)
	 * 자료구조 및 알고리즘 : 깊이 우선 탐색, 트리 - 중위 순회(Inorder Traversal)
	 *
	 * [문제 요약]
	 * - 이진 트리를 격자판에 그릴 때, 다음 규칙을 따른다.
	 * 1. 같은 레벨의 노드는 같은 행에 위치한다.
	 * 2. 한 열에는 한 노드만 존재한다. 즉, 각 노드마다 열 번호를 가진다.
	 * - 각 레벨(행)별로 너비(오른쪽 끝 노드 - 왼쪽 끝 노드 + 1)를 계산했을 때,
	 * 가장 넓은 너비를 가진 레벨과 그 너비를 출력하라.
	 *
	 * [핵심 아이디어]
	 * - 문제의 조건(왼쪽 자식 -> 현재 -> 오른쪽 자식 순으로 열 번호 부여)이 정확히 '중위 순회(Inorder Traversal)'와 일치한다.
	 * - 따라서 트리를 중위 순회하며 방문 순서(cnt)를 매기면 그것이 곧 해당 노드의 열 번호(X좌표)가 된다.
	 * - DFS를 돌면서 각 레벨별로 '가장 작은 열 번호(start[i])'와 '가장 큰 열 번호(end[i])'를 갱신해둔다.
	 *
	 * [구현 메모]
	 * - 루트 찾기: 입력에서 부모가 없는 노드가 루트이므로, `isParentExist` 배열로 자식들을 체크하여 루트를 식별한다.
	 * - start[] / end[]: 각 레벨의 최소/최대 열 번호를 저장.
	 * (초기값 0을 이용해 해당 레벨의 첫 방문인지 확인)
	 *
	 * [시간 복잡도]
	 * - 트리 구성 및 루트 찾기: O(N)
	 * - 중위 순회(DFS): 모든 노드를 한 번씩 방문하므로 O(N)
	 * - 레벨별 너비 계산: 최대 깊이 N까지 순회하므로 O(N)
	 * - 전체 복잡도: O(N)
	 */
	
	static int v, cnt = 0; // 노드 개수, 열 번호 카운터(방문 순서)
	static boolean[] isParentExist; // 루트 노드를 찾기 위한 배열(부모 노드가 있으면 true)
	static int[] start, end; // 각 레벨별 [최소 열 번호, 최대 열 번호]
	static Node[] tree; // 노드 객체 배열(인덱스로 접근)
	
	static class Node {
		int id;
		Node left, right;
		
		public Node(int id) {
			this.id = id;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		v = Integer.parseInt(br.readLine());
		
		// 루트 찾기 및 트리 생성을 위한 초기화
		isParentExist = new boolean[v+1];
		isParentExist[0] = true; // 0번 인덱스는 사용 안 함
		tree = new Node[v+1];
		
		for(int i=0; i<v; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int root = Integer.parseInt(st.nextToken());
			
			// 노드 객체 생성 (이미 생성되었을 수도 있으므로 null 체크)
			if(tree[root] == null) tree[root] = new Node(root);
			
			int leftIdx = Integer.parseInt(st.nextToken());
			int rightIdx = Integer.parseInt(st.nextToken());
			
			// 왼쪽 자식 연결
			if(leftIdx != -1) {
				isParentExist[leftIdx] = true; // 부모가 존재함 표시
				if(tree[leftIdx] == null) tree[leftIdx] = new Node(leftIdx);
				tree[root].left = tree[leftIdx];
			}
			
			// 오른쪽 자식 연결
			if(rightIdx != -1) {
				isParentExist[rightIdx] = true; // 부모가 존재함 표시
				if(tree[rightIdx] == null) tree[rightIdx] = new Node(rightIdx);
				tree[root].right = tree[rightIdx];
			}
		}
		
		start = new int[v+1];
		end = new int[v+1];
	}
	
	// 중위 순회 (Inorder Traversal): Left -> Root -> Right
	// root: 현재 노드 객체
	// level: 현재 노드의 깊이
	static void inorder(Node root, int level) {
		if(root == null) return;
		
		// 1. 왼쪽 서브트리 방문
		inorder(root.left, level+1);
		
		// 2. 현재 노드 처리 (방문 순서 == 열 번호)
		cnt++;
		// 해당 레벨을 처음 방문했다면 현재 열 번호가 '시작점(최소값)'
		if(start[level] == 0) start[level] = cnt;
		// 해당 레벨을 재방문했다면 현재 열 번호가 '끝점(최대값)'
		else end[level] = cnt;
		
		// 3. 오른쪽 서브트리 방문
		inorder(root.right, level+1);
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 루트 노드 찾기 (부모가 없는 노드)
		int root = -1;
		for(int i=1; i<=v; i++) {
			if(!isParentExist[i]) {
				root = i;
				break;
			}
		}
		
		// 루트부터 중위 순회 시작 (레벨 1부터)
		inorder(tree[root], 1);
		
		int max = 0;        // 최대 너비
		int targetLevel = 1; // 최대 너비를 가진 레벨 (조건: 너비가 같으면 작은 레벨 우선)
		
		// 각 레벨별 너비 계산
		for(int i=1; i<=v; i++) {
			// 해당 레벨에 노드가 하나도 없으면 탐색 종료 (트리 깊이 초과)
			if(start[i] == 0) break;
			
			int gap = 1; // 기본 너비 1 (노드가 하나일 경우)
			// 노드가 2개 이상일 경우 (끝점 - 시작점 + 1)
			if(end[i] != 0) gap = end[i] - start[i] + 1;
			
			// 최대 너비 갱신 (strictly greater를 사용하여 레벨이 낮은 것을 우선 유지)
			if(gap > max) {
				max = gap; 
				targetLevel = i;
			}
		}
		
		System.out.println(targetLevel + " " + max);
	}
	
}