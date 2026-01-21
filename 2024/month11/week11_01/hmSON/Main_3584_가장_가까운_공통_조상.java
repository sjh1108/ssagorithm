import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_3584_가장_가까운_공통_조상 {
	
	/*
	 * BOJ_3584 : 가장 가까운 공통 조상(Gold_4)
	 * 자료구조 및 알고리즘 : 트리, LCA(최소 공통 조상)
	 * 
	 * 목표 : 어떤 트리 상의 두 정점이 주어질 때, 두 정점으로부터 가장 가까운 공통 조상을 찾아야 한다.
	 * 입력값 정보 : 간선 정보는 단방향으로 주어진다. (a, b)로 주어지면 a가 b의 부모 노드이다.
	 * 논리 1 : 그래프를 구현할 필요가 없어 보인다. 어차피 방향이 주어져 있으니 그대로 따라 올라가면 된다.
	 * 논리 2 : 트리를 구현하지 않았으니 대신 두 정점을 시작점으로 하고, 부모 노드를 거치면서 방문 처리를 할 예정이다.
	 * 논리 3 : 위에서 가장 먼저 중복되는 부분이 바로 LCA일 것이다.
	 */
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 정점의 수, 두 정점의 번호
	static int v, a, b;
	// 각 정점의 상위 노드 번호를 저장하는 배열
	static int[] parent;
	// 각 정점의 방문 처리 배열
	static boolean[] visited;
	
	static void init() throws Exception {
		v = Integer.parseInt(br.readLine());
		
		// 각 정점의 상위 노드 번호를 저장.
		// 0번 노드와 루트의 중복 방지를 위해 모든 값을 -1로 초기화
		parent = new int[v];
		Arrays.fill(parent, -1);
		visited = new boolean[v];
		
		// 간선 정보
		// v1과 v2가 주어지면 v1이 v2의 상위 노드라는 의미
		for(int i=0; i<v-1; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken()) - 1;
			int v2 = Integer.parseInt(st.nextToken()) - 1;
			parent[v2] = v1;
		}
		
		st = new StringTokenizer(br.readLine());
		a = Integer.parseInt(st.nextToken()) - 1;
		b = Integer.parseInt(st.nextToken()) - 1;
	}
	
	static void lca() {
		// 정점 a부터 탐색 시작. 루트 노드를 만날 때까지 방문 처리
		while(a != -1) {
			visited[a] = true;
			a = parent[a];
		}
		
		// 정점 b 탐색 시작. 이미 a가 방문한 곳을 만날 때까지 방문 처리
		// 루프가 종료되었을 때의 b가 바로 LCA임
		while(!visited[b]) b = parent[b];
		
		sb.append((b+1) + "\n");
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			lca();
		}
		
		System.out.println(sb);
	}

}
