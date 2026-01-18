import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


public class Main_4485 {

	static int N;
	static int[][] arr;
	static int[][] dist;
	static int[] dx = {-1, 1, 0, 0};
	static int[] dy = {0, 0, -1, 1};
	static final int INF = 1000000000;

	static class Node implements Comparable<Node> {
		int x, y, cost;
		Node(int x, int y, int cost) { this.x = x; this.y = y; this.cost = cost; }
		public int compareTo(Node o) { return Integer.compare(this.cost, o.cost); }
	}


	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int tc = 1;

		while(true) {

			N = Integer.parseInt(br.readLine());
			if(N == 0) break;

			arr = new int[N][N];

			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					arr[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			dijkstra();
			System.out.println("Problem " + tc++ + ": " + dist[N-1][N-1]);
		}
	}

	private static void dijkstra() {

		dist = new int[N][N];
		for (int i = 0; i < N; i++) {
			Arrays.fill(dist[i], INF);
		}


		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[0][0] = arr[0][0];
		pq.offer(new Node(0,0,arr[0][0]));

		while(!pq.isEmpty()) {
			Node cur = pq.poll();
			if(cur.cost > dist[cur.x][cur.y]) continue;

			for (int dir = 0; dir < 4; dir++) {
				int nx = cur.x + dx[dir];
				int ny = cur.y + dy[dir];
				if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;

				int next = cur.cost + arr[nx][ny];
				if (next < dist[nx][ny]) {
					dist[nx][ny] = next;
					pq.offer(new Node(nx, ny, next));

				}
			}
		}
	}
}
