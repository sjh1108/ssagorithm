package bk.gold;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 다익스트라에 필요한 것
 * Node class
 * 인접리스트 : Arraylist<Node>[] adj
 * 거리갱신배열 : int[] dist 
 * 
 * 
 * 아이디어
 * - 역방향 그래프를 그리면 거기까지 가는 비용이 나옴
 */
public class Main_1238_파티 {

	static int N, M, X; // 1 <= X <= N
	static int ans;
	static class Node implements Comparable<Node>{
		int index;
		int cost;

		public Node(int index, int cost) {
			super();
			this.index = index;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
		
	}
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		// 인접리스트
		ArrayList<Node>[] adj  = new ArrayList[N+1]; // 초기화 
		ArrayList<Node>[] revAdj = new ArrayList[N+1]; // 초기화 
		for(int i = 0; i < adj.length; i++) { // 내부 초기화
			adj[i] = new ArrayList<>();
		}
		for(int i = 0; i < revAdj.length; i++) { // 내부 초기화
			revAdj[i] = new ArrayList<>();
		}
		
		int[] dist = new int[N+1];
		int[] revDist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(revDist, Integer.MAX_VALUE);
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			adj[from].add(new Node(to, cost)); // 정방향 인접리스트 입력
			revAdj[to].add(new Node(from, cost)); // 역방향 인접리스트 입력
		}
		
		dijkstra(adj, dist);
		dijkstra(revAdj, revDist);
		
		int ans = 0;
		// 최단거리 계산 후 그중 최장 거리인 사람의 소요시간
		for(int i = 1; i <= N; i++) {
			ans = Math.max(dist[i] + revDist[i], ans);
		}
		System.out.println(ans);
	}
	
	
	private static void dijkstra(ArrayList<Node>[] adj, int[] dist) {
		// pq선언 및 출발점 offer
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(X, 0));
		dist[X] = 0;
		
		while(!pq.isEmpty()) {
			// pq에서 탐색할 노드를 꺼냄
			Node now = pq.poll();
			int index = now.index;
			int cost = now.cost;
			
			// 꺼내고 보니까 이미 여기까지 오는 경로중에 더 최소 비용이 있었음 
			if(cost > dist[index]) continue; 
			
			// 탐색할 노드에서 갈 수 있는 곳을 찾음
			for(Node next : adj[index]) {
				
				if(dist[next.index] > cost + next.cost) {
					dist[next.index] = cost + next.cost;
					pq.offer(new Node(next.index, dist[next.index]));
				}
			}
			
		}
		
	}
}
