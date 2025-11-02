package algostudy.baek.week11_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1238_이용호 {
/*
 * 각 마을은 단방향 도로로 연결
 * 자신 마을 -> 파티 -> 자신 마을 중 최대 비용 
 */
	static int[] cost;
	static List<Edge>[] graph, reverseGraph;
	static int N;
	public static class Edge implements Comparable<Edge>{
		int to; int c;
		public Edge(int to, int c) {
			this.to = to;
			this.c = c;
		}
		@Override
		public int compareTo(Edge o) {
			return this.c - o.c;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken()); // 파티 장소
		/*
		 * 양방향 그래프아님
		 * 정방향 다익: 갈수있는 길로 갱신 -> 역방향 다익:올수있는 길로 갱신
		 */
		graph = new ArrayList[N+1]; // from -> to
		reverseGraph = new ArrayList[N+1]; // to -> from
		
		for(int i = 0; i <= N; i++) {
			graph[i] = new ArrayList<>();
			reverseGraph[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int c = Integer.parseInt(st.nextToken());
			
			graph[from].add(new Edge(to ,c));
			reverseGraph[to].add(new Edge(from, c));
		}
		
		int[] distTo = dij(graph, X); // 파티 장소 -> 각 마을까지 최소 거리   
        int[] distFrom = dij(reverseGraph, X); // 각 마을  -> 파티 장소까지 최소 거리
        
        int result = 0;
        for(int i = 1; i <= N; i++) {
        	if(i == X) continue;
        	result = Math.max(result, distTo[i] + distFrom[i]);
        }
		System.out.println(result);
	}
	
	public static int[] dij(List<Edge>[] list, int start) {
		PriorityQueue<Edge> pq = new PriorityQueue<>();
		cost = new int[N+1];
		Arrays.fill(cost, Integer.MAX_VALUE);
		pq.add(new Edge(start, 0));
		cost[start] = 0;
		
		while(!pq.isEmpty()) {
			Edge now = pq.poll();
			// 이미 더 짧은 경로로 방문된 적 있다면 패스
			if(now.c > cost[now.to]) continue;
			
			for(Edge next : list[now.to]) {
				int newCost = now.c + next.c;
				if(newCost >= cost[next.to]) continue;
				cost[next.to] = newCost;
				pq.add(new Edge(next.to, newCost));
			}
		}
		return cost;
	}

}
