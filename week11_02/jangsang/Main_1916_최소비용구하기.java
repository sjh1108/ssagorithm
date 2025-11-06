package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1916_최소비용구하기 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N, M, start, end;
	static List<int[]>[] graph;
//	static int dist[];
	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());
		M = Integer.parseInt(input.readLine());
		
		graph = new ArrayList[N+1];
		for (int i = 0; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < M; i++) {
			tokens = new StringTokenizer(input.readLine());
			int a = Integer.parseInt(tokens.nextToken());
			int b = Integer.parseInt(tokens.nextToken());
			int cost = Integer.parseInt(tokens.nextToken());
			
			graph[a].add(new int[] {b ,cost});
		}
		tokens = new StringTokenizer(input.readLine());
		start = Integer.parseInt(tokens.nextToken());
		end = Integer.parseInt(tokens.nextToken());
		
		System.out.println(dijkstra(start,end));
	} //main
	
	static int dijkstra(int s, int e) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
		int[] dist = new int[N+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[start] = 0;
		pq.offer(new int[] {s,0});
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int now = cur[0];
			int cost = cur[1];
			
			if(cost > dist[now]) continue;
			
			for (int[] next : graph[now]) {
				int nextNode = next[0];
				int nextCost = next[1] + cost;
				
				if(nextCost < dist[nextNode]) {
					dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
				}
			}
		}
		
		return dist[e];
	}
} //class
