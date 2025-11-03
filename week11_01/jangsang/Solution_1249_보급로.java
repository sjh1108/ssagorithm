package algo2025_10_05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Solution_1249_보급로 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int tc,N;
	static int []map[];
	static int []dist[];
	static final int INF = Integer.MAX_VALUE;
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static class Node implements Comparable<Node> {
		int x, y, cost;

		public Node(int x, int y, int cost) {
			super();
			this.x = x;
			this.y = y;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
//			return Integer.compare(this.cost, o.cost);
			return cost - o.cost; // Integer의 비교 방법
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		tc = Integer.parseInt(input.readLine());
		for (int i = 1; i <= tc; i++) {
			N = Integer.parseInt(input.readLine());
			map = new int [N][N];
			dist = new int [N][N];
			// 단일가중치를 가지는 map배열과 합산 최소값을 가지는 dist배열은 항상 짝궁

			for (int j = 0; j < N; j++) { //map 입력
				String line = input.readLine();
				for (int k = 0; k < N; k++) {
					map[j][k] = line.charAt(k) - '0';
					dist[j][k] = INF;
				}
			}
			System.out.println("#" + i +" " +  dijkstra());

			
			
		}// tc
	}// main
	static int dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[0][0] = map[0][0]; // 시작 정점에 가중치 있을 수 있어서 넣고 시작
		
		pq.offer(new Node(0, 0, map[0][0])); // Node x,y,cost 시작점 비용
		while(!pq.isEmpty()) {
			
			Node cur = pq.poll();
			
			if(cur.cost > dist[cur.x][cur.y]) continue;
			for (int i = 0; i < 4; i++) {
				int nx = cur.x + dx[i];
				int ny = cur.y + dy[i];
				
				if (nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				
				int min = cur.cost + map[nx][ny]; // 현재누적값 + 현재
				if(min < dist[nx][ny]) {
					dist[nx][ny] = min; // 최저값을 만족하여 탐색한 노드 값에 추가(갱신)
					pq.offer(new Node(nx, ny, min)); // 삽입
				}
			}
			
		}
		
		return dist[N-1][N-1];
	}
}
