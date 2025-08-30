package Rank4.gold_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1167 {
	
	static int n, max = 0;
	static List<List<Node>> list = new ArrayList<>();
	static boolean[] visited;
	
	static class Node {
		int to, cost;
		
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		visited[0] = true;
		dfs(0);
		System.out.println(max);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		for(int i=0; i<n; i++) {
			list.add(new ArrayList<>());
		}
		visited = new boolean[n];
		
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int root = Integer.parseInt(st.nextToken())-1;
			while(true) {
				int x = Integer.parseInt(st.nextToken());
				if(x == -1) break;
				int cost = Integer.parseInt(st.nextToken());
				list.get(root).add(new Node(x-1, cost));
			}
		}
	}
	
	static int dfs(int root) {
		int maxLen = 0, maxLen2 = 0;
		for(Node next : list.get(root)) {
			if(visited[next.to]) continue;
			visited[next.to] = true;
			int len = dfs(next.to) + next.cost;
			if(len > maxLen) {
				maxLen2 = maxLen;
				maxLen = len;
			} else if(len > maxLen2) maxLen2 = len;
		}
		int sum = maxLen + maxLen2;
		if(sum > max) max = sum;
		
		return maxLen;
	}

}
