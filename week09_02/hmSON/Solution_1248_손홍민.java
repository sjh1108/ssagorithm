import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Solution_1248 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	// 정점과 간선 수, 공통 조상을 찾으려는 두 정점의 번호, 공통 조상을 루트 노드로 하는 서브 트리의 정점 수
	static int v, e, n1, n2, treeCnt;
	// 정점 객체의 번호를 인덱스별로 저장
	static Node[] nodes;
	// 두 리프 노드 중 하나의 조상 목록을 한 단계씩 올라가면서 리스트에 저장
	static List<Integer> parentList = new ArrayList<>();
	
	static class Node {
		Node left, right, parent;
		int id;
		
		public Node(int id) {
			this.id = id;
		}
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		for(int i=1; i<=tc; i++) {
			init();
			int idx = find();
			cntChild(nodes[idx]);
			sb.append("#" + i + " " + (idx+1) + " " + treeCnt + "\n");
			// 각 테스트 케이스마다 리스트 초기화
			parentList.clear();
		}
		
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		n1 = Integer.parseInt(st.nextToken()) - 1;
		n2 = Integer.parseInt(st.nextToken()) - 1;
		
		nodes = new Node[v];
		for(int i=0; i<v; i++) {
			nodes[i] = new Node(i);
		}
		
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<e; i++) {
			int v1 = Integer.parseInt(st.nextToken()) - 1;
			int v2 = Integer.parseInt(st.nextToken()) - 1;

			// 자식 노드가 없는 경우 왼쪽 서브트리부터 채움
			// 왼쪽 자식이 존재하면 오른쪽 서브트리에 채움
			if(nodes[v1].left == null) nodes[v1].left = nodes[v2];
			else nodes[v1].right = nodes[v2];
			nodes[v2].parent = nodes[v1];
		}
		treeCnt = 0;
	}

	// 두 지정 노드의 공통 조상을 찾는 과정
	static int find() {
		// n1으로부터 한 칸씩 올라가면서 조상 노드 목록을 리스트에 등록
		Node cur = nodes[n1];
		while(cur.parent != null) {
			parentList.add(cur.parent.id);
			cur = cur.parent;
		}

		// n2도 한 칸씩 올라가면서 자신의 부모 노드가 n1의 조상 노드 리스트에 존재하는 지 확인
		// 가능성은 존재하지 않으나, 루트 노드까지 올라가는 상황을 고려함
		cur = nodes[n2];
		while(cur.parent != null) {
			if(parentList.contains(cur.parent.id)) {
				return cur.parent.id;
			}
			cur = cur.parent;
		}
		
		return 0;
	}

	// 공통 조상에 해당하는 노드를 루트 노드로 하는 서브 트리의 정점 개수를 카운트
	static void cntChild(Node root) {
		if(root == null) return;
		
		treeCnt++;
		
		cntChild(root.left);
		cntChild(root.right);
	}

}
