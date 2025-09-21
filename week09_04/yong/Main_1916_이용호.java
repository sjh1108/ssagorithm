package algostudy.baek.m9w4;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1916_이용호 {
	static List<node>[] graph;
	static int[] dist;
	static int N;
	static class node implements Comparable<node>{
		int vertex;
		int cost;
		
		public node(int vertex, int cost) {
			this.vertex = vertex;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		graph = new ArrayList[N+1];
		dist = new int[N+1];
		//초기화(마을에서 나가는 버스(엣지), 최소 비용)
		for(int i = 1;i <= N; i++) {
			graph[i] = new ArrayList<>();
			dist[i] = Integer.MAX_VALUE;
		}
		
		for(int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			//버스 왕복가능 해서 양방향인줄 알았는데 단방향임;; 깜빡속았네
			graph[from].add(new node(to,cost));
//			graph[to].add(new node(from,cost));
		}
		StringTokenizer st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		
		//다익스트라
		dij(start);
		System.out.println(dist[end]);
	}

	static void dij(int start){
		PriorityQueue<node> pq = new PriorityQueue<>(); 
		dist[start] = 0;
		pq.offer(new node(start,0));
		
		while(!pq.isEmpty()) {
			node now = pq.poll();
			//가지치기( 현재 마을까지 비용 > 갱신되어 있는 현재 마을까지 최소비용 )
			if(now.cost > dist[now.vertex]) continue;

			for(node next : graph[now.vertex]) {
				//다음 마을까지 비용 계산
				int newCost = dist[now.vertex] + next.cost;
				//최소 비용 갱신
				if(newCost >= dist[next.vertex]) continue;
				dist[next.vertex] = newCost;
				pq.offer(new node(next.vertex, newCost));
			}
		}
	}
}
