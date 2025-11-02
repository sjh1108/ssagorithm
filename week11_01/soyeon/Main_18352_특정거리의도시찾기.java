package week;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 다익스트라
 * - 그런데 가중치가 1로 동일함
 * - 하지만 다익스트라르 쓰는 이유는 한 출발점에서 나머지 노드로 가는 모든 최단거리를 구하기 떄문
 * - 다익스트라는 위의 내용이 한번에 구해지면서 시간 복잡도가 O((V+E)log V)
 * 
 */

public class Main_18352_특정거리의도시찾기 {

	static int N, M, K, X; // V 30만 / E 100만  / 원하는거리 / 출발도시
	static List<Integer>[] adj;
	static int[] dist; // 거리배열
	
	static class Node implements Comparable<Node>{
		int city;
		int cost;

		public Node(int city, int cost) {
			super();
			this.city = city;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());
		
		adj = new ArrayList[N+1];
		for(int i = 1; i <= N; i++) {
			adj[i] = new ArrayList<>();
		}
		dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		// 초기화 끝 
		
		for(int m = 0; m < M; m++) {
			 st = new StringTokenizer(br.readLine());
			 int from = Integer.parseInt(st.nextToken());
			 int to = Integer.parseInt(st.nextToken());
			 
			 adj[from].add(to);
		}// 입력끝
		
		dijkstra();
		
		
		boolean haveAns = false;
		for(int i = 1; i < dist.length; i++) {
			if(dist[i] == K) {
				System.out.println(i);
				haveAns = true;
			}
		}
		if(!haveAns) System.out.println(-1);
		
	}

	private static void dijkstra() {
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.offer(new Node(X, 0));
		dist[X] = 0;
		
		
		while(!pq.isEmpty()) {
			Node node = pq.poll();
			int now = node.city;
			int cost = node.cost;
			
			if(cost > dist[now]) continue; //잊지말기
			
			for(int next : adj[now]) {
				if(dist[next] > cost + 1) { // 가중치 1이므로 기존 다익과 코드가 다름 
					dist[next] = cost + 1; // 기록된 최단경로와 지금cost+가중치 를 비교하는 거임!!
					pq.offer(new Node(next, dist[next]));
				}
			}
			
		}
		
	}
}
