package algostudy.baek.week10_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_11437_이용호 {
	/*
	 * 백준 11437: LCA
	 * 1. 완탐으로 노드들 깊이와 직전 부모 노드를 구한다. dfs
	 * 2. 임의의 두 노드가 깊이가 서로 다르면 평탄화 작업을 진행한다. 
	 * 3. 깊이가 동일 함에도, 부모노드가 다른경우, 동일한 부모노드가 나올때까지 상위 노드를 탐색한다
	 */
	static int parent[], depth[];
	static boolean[] visited;
	static List<Integer>[] tree;
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		tree = new ArrayList[N+1];
		depth = new int[N+1];
		parent = new int[N+1];
		visited = new boolean[N+1];
		StringBuilder sb = new StringBuilder();
		
		for(int i = 1; i <= N; i++) {
			tree[i] = new ArrayList<>();
		}
		
		// 간선 정보 입력(무방향 그래프)
		for(int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			tree[n1].add(n2);
			tree[n2].add(n1);
		}
		
		// 각 노드 깊이와 부모 설정
		dfs(1,0);
		
		int M  = Integer.parseInt(br.readLine());
		
		for(int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n1 = Integer.parseInt(st.nextToken());
			int n2 = Integer.parseInt(st.nextToken());
			sb.append(lca(n1,n2)).append("\n");
		}
		System.out.println(sb);
	}
	/* node depth계산 -> O(N)
	 * 현재 노드 깊이 기록
	 * 인접 노드중 방문하지 않은곳 재귀 호출
	 * 각 자식노드 부모 설정
	 */
	private static void dfs(int node, int depthV) {
		visited[node] = true;
		depth[node] = depthV;
		
		for(int next : tree[node]) { // 인접 노드 중
			if(visited[next]) continue; 
			parent[next] = node; // 자식의 부모는 현재노드
			dfs(next, depthV + 1); // 깊이 증가 후 재귀 호출
		}
	}
	// O(H)
	private static int lca(int a, int b) {
		// 깊이가 다르면 맞춰줌
		while(depth[a] > depth[b]) a = parent[a];
		while(depth[b] > depth[a]) b = parent[b];
		// 깊이가 같으면 올라가면서 공통조상 찾기
		while(a != b) {
			a = parent[a];
			b = parent[b];
		}
		return a;
	}

}

