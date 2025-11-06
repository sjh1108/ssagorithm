package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1753_최단경로 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static List<int[]>[] graph;
	static int V, E, K;
	static final int INF = Integer.MAX_VALUE;
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		V = Integer.parseInt(tokens.nextToken());
		E = Integer.parseInt(tokens.nextToken());
		K = Integer.parseInt(input.readLine());
		
		graph = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < E; i++) {
			tokens = new StringTokenizer(input.readLine());
			int a = Integer.parseInt(tokens.nextToken());
			int b = Integer.parseInt(tokens.nextToken());
			int c = Integer.parseInt(tokens.nextToken());
			graph[a].add(new int[] {b,c});
		}
		int[] res = dijkstra(K);
		
		for (int i = 1; i <= V; i++) {
			if(res[i] == INF) System.out.println("INF");
			else System.out.println(res[i]);
			
		}
		
		
	}
	static int[] dijkstra(int start) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
		int[] dist = new int[V + 1];
		Arrays.fill(dist, INF);
		dist[start] = 0;
		pq.offer(new int[] {start,0});
		
		while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int now = cur[0];
            int cost = cur[1];

            if (cost > dist[now]) continue;

            for (int[] next : graph[now]) {
                int nextNode = next[0];
                int nextCost = cost + next[1];

                if (nextCost < dist[nextNode]) {
                    dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
                }
            }
        }
		return dist;
	}
}
