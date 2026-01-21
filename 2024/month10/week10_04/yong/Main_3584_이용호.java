package algostudy.baek.week10_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
/*
 * 백준 3584 : 가장 가까운 공통 조상
 */
public class Main_3584_이용호 {
	static int[] parent, depth;
	static boolean[] visited;
	static List<Integer>[] tree;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		
		for(int tc = 0; tc < T; tc++) {
			int N = Integer.parseInt(br.readLine());
			depth = new int[N+1];
			parent = new int[N+1];
			tree = new ArrayList[N+1];
			visited = new boolean[N+1];
			
			for(int i = 1; i <= N; i++) {
				tree[i] = new ArrayList<>();
			}
			for(int i = 0; i < N-1; i ++) {
				//a가 b의 부모
				StringTokenizer st = new StringTokenizer(br.readLine());
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				tree[a].add(b);
				tree[b].add(a);
				parent[b] = a;
			}
			//루트 노드 찾기
			int root = findRoot(1);
//			System.out.println(root);
			dfs(root,0);
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			System.out.println(lca(a,b));
		}
	}

	//루트 노드 찾기
	private static int findRoot(int node) {
		if(parent[node] != 0) return findRoot(parent[node]);
		return node;
		
		
	}
	
	//depth 기록
	private static void dfs(int node, int depthV) {
		visited[node] = true;
		depth[node] = depthV;
		
		for(int next : tree[node]) {
			if(visited[next]) continue;
			dfs(next, depthV + 1);
		}
		
		
	}
	
	private static int lca(int a, int b) {
		while(depth[a] < depth[b]) b = parent[b];
		while(depth[b] < depth[a]) a = parent[a];
		
		while(a != b) {
			a = parent[a];
			b = parent[b];
		}
		return a;
	}
	
	
	
	

}
