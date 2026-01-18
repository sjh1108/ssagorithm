package bk.silver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 무방향그래프
 * - 연결요소의 개수 구하기(이게 무슨 말임???)
 * - => 모든 정점이 서로 직접/간접적으로 연결된 집합이 몇 개인지
 * 
 * 입력
 * - 정점의 개수 N, 간선의 개수 M
 * - 간선의 양 끝점 u,v
 * 
 * 출력 
 * - 연결 요소의 개수
 * 
 * 
 * 전략 
 * - 뭔가.. 유니온으로 풀어야할 것 같이 생겼는데 백준이 dfs로 풀 수 있다니까 해보겠음 
 *
 */
public class Main_11724_연결요소의개수 {

	static int V, E;
	static ArrayList<Integer>[] adj;
	static boolean[] visited;
	static int ans;
	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		adj = new ArrayList[V+1];
		for(int i = 1 ; i <= V; i++) { // 제발 여기서 for each문으로 복사하지말아라
			adj[i] = new ArrayList<>();
		}
		visited = new boolean[V+1];
		
		
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			int m = Integer.parseInt(st.nextToken());
			adj[n].add(m);
			adj[m].add(n); // 무방향 
		}
		// 입력끝
		
		for(int i = 1; i <= V; i++) {
			if(!visited[i]) {
				dfs(i);
				++ans;
			}	
		}
		
		
		System.out.println(ans);
	}
	
	private static void dfs(int cur) {
		if(visited[cur]) return;
		
		visited[cur] = true;
		for(int next : adj[cur]) {
			dfs(next);
		}
		
	}
}
