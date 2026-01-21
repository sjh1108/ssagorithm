package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1753_최단경로 {
	
	static class  Node implements Comparable<Node> {
		int v , w;

		
		public Node(int v, int w) {
			this.v = v;
			this.w = w;
		}


		public int compareTo(Node O) {
			return Integer.compare(this.w, O.w);
		}
		
		
	}
	
	
	static int V, E, start;
	static int[] dist;
	static final int INF = Integer.MAX_VALUE;
	static List<Node>[] graph;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		start = Integer.parseInt(br.readLine());
		
		graph = new ArrayList[V+1]; 
		
		for (int i = 1; i <= V; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for (int i = 1; i <= E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			graph[u].add(new Node(v,w));
		}
		
		
		dist = new int[V+1];
		
		Arrays.fill(dist, INF);
		
		dijkstra(start);
		
		for (int i = 1; i <= V; i++) {
			sb.append(dist[i] == INF ? "INF" : dist[i]).append("\n");
		}
		
		System.out.println(sb);
		
		
	}

	private static void dijkstra(int s) {
		
		PriorityQueue<Node> pq = new PriorityQueue<>();
		
		dist[s] = 0;
		
		pq.offer(new Node(s,0));
		
		while(!pq.isEmpty()) {
		
			Node cur = pq.poll();
			
			if(cur.w > dist[cur.v]) continue;
			
			for (Node next : graph[cur.v]) {
				if(dist[next.v] > cur.w + next.w) {
					dist[next.v] = cur.w + next.w;
					pq.offer(new Node(next.v, dist[next.v]));
				}
			}	
		}
	}
}

// static List<Node>[] graph; import util로 선언하기.
// private static void dijkstra(int s) { int
// 생성자 까먹지 말기. 
// dist[s] = 0; start 아님. 나 디버깅 연습이 매우 중요 일단 익힌다음에..
// 아니 미친 입력을 안했네 실환가.. 
