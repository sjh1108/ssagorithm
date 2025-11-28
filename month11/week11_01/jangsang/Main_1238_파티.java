package algo2025_10_05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1238_파티 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N, M, X;
	static int[][] map, dist;
	static final int INF = Integer.MAX_VALUE;

	static class Node implements Comparable<Node> {
		int v, cost;
		public Node(int v, int cost) {
			this.v = v;
			this.cost = cost;
		}
		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}

	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		M = Integer.parseInt(tokens.nextToken());
		X = Integer.parseInt(tokens.nextToken());

		map = new int[N + 1][N + 1];
		dist = new int[N + 1][N + 1];

		for (int i = 0; i < M; i++) {
			tokens = new StringTokenizer(input.readLine());
			int from = Integer.parseInt(tokens.nextToken());
			int to = Integer.parseInt(tokens.nextToken());
			int w = Integer.parseInt(tokens.nextToken());
			map[from][to] = w;
		}

		for (int i = 1; i <= N; i++) {
			for (int j = 1; j <= N; j++) {
				dist[i][j] = INF;
			}
		}

		int res = 0;
		for (int i = 1; i <= N; i++) {
			if (i == X) continue;
			res = Math.max(res, dist[i][X] + dist[X][i]);
		}
		System.out.println(res);
	}

	static void dijkstra(int start, int end) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[start][start] = 0;
		pq.offer(new Node(start, 0));

		while (!pq.isEmpty()) {
			Node cur = pq.poll();

			if (cur.cost > dist[start][cur.v]) continue;

			for (int i = 1; i <= N; i++) {
				if (map[cur.v][i] == 0) continue;

				int newCost = cur.cost + map[cur.v][i];
				if (newCost < dist[start][i]) {
					dist[start][i] = newCost;
					pq.offer(new Node(i, newCost));
				}
			}
		}
	}
}
