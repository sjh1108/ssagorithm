package d4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Solution_1249 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();

	// 맵 크기
	static int n;
	// 맵 정보 및 거리 저장 배열
	static int[][] map, dist;
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

	static class Node {
		int y, x, cost;
		
		public Node(int y, int x, int cost) {
			this.y = y;
			this.x = x;
			this.cost = cost;
		}
	}

	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		for(int i=1; i<=tc; i++) {
			init();
			sb.append("#" + i + " " + dijkstra() + "\n");
		}
		
		System.out.println(sb);
	}
	
	static void init() throws Exception{
		n = Integer.parseInt(br.readLine());
		map = new int[n][n];
		dist = new int[n][n];
		// 모든 정점으로 이동하는 거리를 최대값으로 초기화
		for(int i=0; i<n; i++) {
			Arrays.fill(dist[i], Integer.MAX_VALUE);
		}
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<n; j++) {
				map[i][j] = line.charAt(j) - '0';
			}
		}
	}


	// 최단거리 탐색 -> 다익스트라
	static int dijkstra() {
		// 우선순위 큐 생성 -> 정렬 기준 : 정점 방문시의 소비 비용
		PriorityQueue<Node> q = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
		q.add(new Node(0, 0, map[0][0]));
		// 초기 위치의 비용도 추가해야 함
		dist[0][0] = map[0][0];
		while(!q.isEmpty()) {
			Node cur = q.poll();
			for(int i=0; i<4; i++) {
				int ny = cur.y + dy[i];
				int nx = cur.x + dx[i];
				if(!isIn(ny, nx)) continue;
				int newCost = cur.cost + map[ny][nx];

				// 이미 저비용으로 방문한 상태라면 추가 방문하지 않음
				if(dist[ny][nx] <= newCost) continue;
				dist[ny][nx] = newCost;
				// 도착점에 방문한 경우 즉시 탐색 종료
				if(ny == n-1 && nx == n-1) return newCost;
				q.add(new Node(ny, nx, newCost));
			}
		}

		return -1;
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < n && x < n;
	}

}
