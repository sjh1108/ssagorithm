import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_16437_양_구출_작전 {
	
	/*
	 * BOJ_16437 : 양 구출 작전(Gold_3)
	 * 자료구조 및 알고리즘 : 트리, DFS
	 *
	 * [문제 요약]
	 * - N개의 섬이 트리 형태로 연결되어 있고, 1번 섬이 루트(구명보트가 있는 곳)이다.
	 * - 각 섬에는 양('S') 또는 늑대('W')가 살고 있다.
	 * - 양들은 부모 노드(1번 섬 방향)로 이동하며, 이동 중에 늑대가 있는 섬을 지나면 
	 * 늑대 한 마리당 양 한 마리가 잡아먹힌다.
	 * - 1번 섬에 도달할 수 있는 양의 최대 마리 수를 구하라.
	 *
	 * [핵심 아이디어]
	 * - 루트에서 시작해 리프 노드까지 내려갔다가, 다시 올라오면서 살아남은 양의 수를 계산한다.(Post-order)
	 * - 현재 노드에서의 생존 양 = (자식 노드들에서 올라온 양의 합) + (현재 노드의 상태)
	 * 1. 현재 노드가 양('S'): 자식에서 올라온 양 + 현재 노드의 양
	 * 2. 현재 노드가 늑대('W'): (자식에서 올라온 양 - 현재 노드의 늑대 수), 단 0보다 작아질 수 없음(Math.max)
	 *
	 * [구현 메모]
	 * - 양의 수와 늑대의 수가 많아 합산 시 int 범위를 초과할 수 있으므로 `long` 타입을 사용해야 한다.
	 * - 단방향 연결(부모 -> 자식)만으로도 충분하므로, 인접 리스트(`child`)를 이용해 트리를 구성한다.
	 *
	 * [시간 복잡도]
	 * - 모든 노드를 한 번씩 방문하는 DFS 탐색 : O(N)
	 */
	
	static class Node {
		boolean state; // true: 양(S), false: 늑대(W)
		long cnt;      // 해당 섬에 있는 동물의 수
		List<Node> child; // 자식 노드(연결된 하위 섬) 리스트
		
		public Node() {
			child = new ArrayList<>();
		}
	}
	
	static int v;
	static Node[] tree; // 노드 객체 배열
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		v = Integer.parseInt(br.readLine());
		
		tree = new Node[v];
		for(int i=0; i<v; i++) {
			tree[i] = new Node();
		}
		
		// 1번 섬(루트) 초기화: 문제 조건상 1번 섬에는 동물이 없음(어떤 상태로 가정해도 무관)
		tree[0].state = true; 
		tree[0].cnt = 0;
		
		// 2번 섬부터 N번 섬까지 정보 입력
		for(int i=1; i<v; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			boolean state = st.nextToken().equals("S"); // 'S'면 true(양), 'W'면 false(늑대)
			long cnt = Long.parseLong(st.nextToken());
			int parent = Integer.parseInt(st.nextToken()) - 1; // 1-based -> 0-based
			
			tree[i].state = state;
			tree[i].cnt = cnt;
			
			// 부모 노드의 자식 리스트에 현재 노드 추가 (단방향 트리 구성)
			tree[parent].child.add(tree[i]);
		}
	}
	
	// DFS 탐색 (후위 순회)
	// 반환값: 해당 서브트리에서 현재 노드 위로 올려보낼 수 있는 생존한 양의 수
	static long search(Node root) {
		if(root == null) return 0;
		
		long total = 0; // 자식 노드들로부터 살아 돌아온 양의 합
		
		// 1. 자식 노드들을 먼저 탐색하여 올라온 양들을 모두 더함 (Bottom-up)
		for(Node next : root.child) {
			total += search(next);
		}
		
		// 2. 현재 노드의 상태에 따라 처리
		if(root.state) {
			// 현재 섬이 양('S')인 경우: 자식들의 양 + 내 섬의 양
			total += root.cnt;
		} else {
			// 현재 섬이 늑대('W')인 경우: 자식들의 양 - 내 섬의 늑대 수
			// 단, 잡아먹힌 후 남은 양이 음수가 될 수는 없음 (0으로 처리)
			total = Math.max(total - root.cnt, 0);
		}
		
		return total;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		// 루트(1번 섬)부터 탐색 시작
		System.out.println(search(tree[0]));
	}
}