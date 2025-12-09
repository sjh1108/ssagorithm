package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BOJ_13023_ABCDE {
	
	
	static int n, m;
	static boolean found = false;
	static ArrayList<Integer>[] graph;
	static boolean[] visited;
	
	
	public static void dfs(int depth, int current) {
		if(found) return;
		if(depth == 4) {
			found = true;
			return;
		}
		
		for(int next : graph[current]) {
			if(visited[next]) continue;
			
			visited[next] = true;
			dfs(depth + 1, next);
			visited[next] = false;
		}
		
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList[n];
		
		for(int i=0; i<n; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			graph[a].add(b);
			graph[b].add(a);
			
			
		}
		
		for(int i=0; i<n; i++) {
			visited = new boolean[n];
			visited[i] = true;
			dfs(0,i);
			
			if(found) break;
			
		}
		 System.out.println(found ? 1 : 0);
	}

}

