import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1162_도로포장 {
	
	/*
	 * BOJ_1162 : 도로포장(Platinum_5)
	 * 자료구조 및 알고리즘 : DP, 다익스트라
	 * 
	 * 포장된 도로는 걸리는 시간이 0초로 단축된다. 최대 K개의 도로를 포장해서 1부터 V번째 도시까지 이동하는 최단 거리를 구해야 한다.
	 * 
	 * 각 도시마다 도로 몇 개를 포장했는지에 대한 최단거리를 갱신해야 한다.
	 * 다익스트라를 기본 틀로 하되, 포장한 도로 수를 카운트하고, 현재의 카운트에 따라 포장을 추가로 하거나, 포장하지 않고 그 도로를 이용해야 한다.
	 * 거리 계산 및 도로 포장에 관한 점화식을 구해 DP 방식으로 최단거리 배열을 관리해야 한다.
	 * */
	
	// 도시 수, 포장 가능한 도로 수
	static int v, paved;
	// 각 도시별 포장된 도로 수에 따른 최단 거리
	static long[][] dist;
	// 양방향 연결 리스트
	static List<Edge>[] list;
	
	// 인접 리스트에 사용할 클래스. 정점 번호와 해당 도로의 비용만 관리
	static class Edge {
		int to;
		long cost;
		
		public Edge(int to, long cost) {
			this.to = to;
			this.cost = cost;
		}
	}
	
	// 다익스트라에 사용할 클래스. 지금까지 포장한 도로의 개수도 관리 + cost 오름차순 정렬 적용
	static class Node implements Comparable<Node>{
		int to, paveCnt;
		long cost;
		
		public Node(int to, long cost, int paveCnt) {
			this.to = to;
			this.cost = cost;
			this.paveCnt = paveCnt;
		}
		
		@Override
		public int compareTo(Node o) {
			return Long.compare(this.cost, o.cost);
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 입력 : 정점 수, 간선 수, 포장 가능한 도로 수
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		paved = Integer.parseInt(st.nextToken());
		
		// 각 도시별 최단 거리 배열. 최대 도로 수(50_000) * 각 도로별 최대 소요 시간(1_000_000)이 int 범위 초과 가능
		dist = new long[paved+1][v];
		for(int i=0; i<=paved; i++) {
			Arrays.fill(dist[i], Long.MAX_VALUE);
		}
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 간선 정보(양방향)
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			long cost = Long.parseLong(st.nextToken());
			
			list[from].add(new Edge(to, cost));
			list[to].add(new Edge(from, cost));
		}
	}
	
	static void dijkstra() {
		PriorityQueue<Node> q = new PriorityQueue<>();
		// 출발 도시는 항상 0
		q.add(new Node(0, 0L, 0));
		dist[0][0] = 0;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			// 중복 방문 방지
			if(dist[cur.paveCnt][cur.to] < cur.cost) continue;
			
			for(Edge next : list[cur.to]) {
				// 추가 포장 없이 도로를 통과할 경우
				// paveCnt를 유지하고 현재 도로의 통과 시간을 추가한다.
				long newCost = cur.cost + next.cost;
				if(dist[cur.paveCnt][next.to] > newCost) {
					q.add(new Node(next.to, newCost, cur.paveCnt));
					dist[cur.paveCnt][next.to] = newCost;
				}
				
				// 현재 도로를 포장하고 통과할 경우
				// paveCnt를 1 증가시키고 현재까지의 통과 시간을 유지한다.
				if(cur.paveCnt < paved && dist[cur.paveCnt+1][next.to] > cur.cost) {
					q.add(new Node(next.to, cur.cost, cur.paveCnt+1));
					dist[cur.paveCnt+1][next.to] = cur.cost;
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		dijkstra();
		
		// 마지막 도시(v-1)의 각 경우에 따른 최단 거리 중 최소값을 구해 출력
		long min = Long.MAX_VALUE;
		for(int i=0; i<=paved; i++) {
			min = Math.min(min, dist[i][v-1]);
		}
		System.out.println(min);
	}

}
