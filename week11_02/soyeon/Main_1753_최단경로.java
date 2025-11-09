package week;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 골드 4

/**
 * 문제 이해
 * - 정점 개수 V (1~V까지 번호)
 * - 간선 개수 E
 * - (u,v,w) u->v가는 가중치 w 간선   : 단방향 
 * - w는 10 이하 자연수
 *
 * 출력 
 * - i번정점으로의 최단 경로의 경로값 순차적으로 (총 V개)
 * - 경로가 존재하지 않는 경우 INF
 */
public class Main_1753_최단경로{
	
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

	static int V, E; // 정점수 간선수
	static int S;
	static ArrayList<Node>[] adj;
	static int[] dist;
	public static void main(String[] args) throws Exception{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(br.readLine());

		// 인접리스트 
		adj = new ArrayList[V+1]; //1~V까지 
		for(int i = 1; i <= V; i++) {
			adj[i] = new ArrayList<>();
		}// 초기화
		
		// dist 배열
		dist = new int[V+1];
		Arrays.fill(dist, Integer.MAX_VALUE); // 무한대 초기화
		
		
		for(int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			adj[from].add(new Node(to, cost));
		} // 입력끝
		
		
		
		dijkstra();
		
		for(int i = 0; i < dist.length;i ++) {
			if(i == 0) continue;
			if(dist[i] == Integer.MAX_VALUE) {
				sb.append("INF");
				
			}else {
				sb.append(dist[i]);				
			}
			
			sb.append("\n");
		}
		
		System.out.println(sb.toString());
		
		
	}
	
	private static void dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		//출발점 준비
		pq.offer(new Node(S, 0));
		dist[S] = 0;
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			int index = now.index;
			int cost = now.cost;
			
			// 지금까지의 최단 거리보다 이미 더 최단인 루트가 존재함. -> out
			if(cost > dist[index]) continue;
			
			// 갈수 있는 곳 찾기
			for(int i = 0; i < adj[index].size(); i++) {
				Node next = adj[index].get(i);
				
				// 갈수있는 node까지의 최단거리와
				// now 위치까지의 최단거리 + now -> node까지의 거리 를 비교
				if(dist[next.index] > cost + next.cost) {
					dist[next.index] = cost + next.cost;
					pq.offer(new Node(next.index, dist[next.index]));
				}
			}
		}
	}
}
