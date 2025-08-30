package Rank5.gold_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1647 {
	
	static int v, e;
	static int[] arr;
	// Edge 배열 만들고 정렬하는 것보다 그냥 우선순위 큐에 넣는 게 훨씬 빠른듯
	// 앞으로 이렇게 하는 게 맞을 것 같음
	static PriorityQueue<Edge> q = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));
	
	static class Edge {
		int v1, v2, cost;

		public Edge(int v1, int v2, int cost) {
			this.v1 = v1;
			this.v2 = v2;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(makeTree());
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		arr = new int[v+1];
		for(int i=1; i<=v; i++) {
			arr[i] = i;
		}
		
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			q.add(new Edge(v1, v2, cost));
		}
	}
	
	static int makeTree() {
		// 집이 2개밖에 없으면 
		if(v == 2) return 0;
		int totalCost = 0, cnt = 0;
		while(!q.isEmpty()) {
			Edge e = q.poll();
			if(find(e.v1) == find(e.v2)) continue;
			totalCost += e.cost;
			cnt++;
			union(e.v1, e.v2);
			
			if(cnt == v-2) return totalCost;
		}
		
		return -1;
	}
	
	static int find(int x) {
		if(arr[x] == x) return x;
		return arr[x] = find(arr[x]);
	}
	
	static void union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		if(ra == rb) return;
		if(ra > rb) arr[ra] = rb;
		else arr[rb] = ra;
	}

}
