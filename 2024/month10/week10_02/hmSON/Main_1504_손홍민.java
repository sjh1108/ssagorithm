import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1504_손홍민 {
	
	// 이론상 만들어질 수 없는 값을 최대값으로 설정(200_000 * 1_000)
	static final int INF = 200_000_000;
	// 정점, 반드시 통과해야 하는 두 점 a와 b, 두 점간 최단거리, 1~N까지의 최단거리
	static int v, pa, pb, distPTP = INF;
	// 특정 시작점으로부터 다른 점까지의 최단 거리 기록
	static int[] dist;
	// 인접 리스트
	static List<Node>[] list;
	
	static class Node implements Comparable<Node> {
		int id, cost;
		
		public Node(int id, int cost) {
			this.id = id;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Node o) {
			return cost - o.cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 루트 A는 1 -> a -> b -> N
		// 루트 B는 1 -> b -> a -> N
		// 두 경로의 합을 기록하고, 둘 중 최소값을 결과로 출력
		int sumA = 0, sumB = 0;
		
		// 시작점을 초기 좌표로 설정한 다익스트라
		dijkstra(0);
		// 만약 a와 b중 어느 한 점이라도 이동 불가하다면 어떤 방식으로도 이동이 불가능함
		// 즉시 -1 출력 후 종료
		if(dist[pa] == INF || dist[pb] == INF) {
			System.out.println(-1);
			return;
		}
		sumA += dist[pa]; sumB += dist[pb];
		
		// 도착점을 초기 좌표로 설정한 다익스트라
		dijkstra(v-1);
		// 만약 a와 b중 어느 한 점이라도 이동 불가하다면 어떤 방식으로도 이동이 불가능함
		// 즉시 -1 출력 후 종료
		if(dist[pa] == INF || dist[pb] == INF) {
			System.out.println(-1);
			return;
		}
		sumA += dist[pb]; sumB += dist[pa];
		
		// 두 점 사이의 최단 거리를 구하는 다익스트라
		dijkstra(pa);
		sumA += dist[pb]; sumB += dist[pb];
		
		// 두 경로의 이동거리 중 최소값을 출력
		System.out.println(Math.min(sumA,  sumB));
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader( new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 정점과 간선
		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		dist = new int[v];
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 양방향 리스트
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken()) - 1;
			int v2 = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			list[v1].add(new Node(v2, cost));
			list[v2].add(new Node(v1, cost));
		}
		
		// 반드시 지나야 하는 두 점 a와 b
		st = new StringTokenizer(br.readLine());
		pa = Integer.parseInt(st.nextToken()) - 1;
		pb = Integer.parseInt(st.nextToken()) - 1;
	}
	
	static void dijkstra(int start) {
		// 여러 번 다익스트라를 실행해야 하므로 매번 거리 배열을 초기화
		Arrays.fill(dist, INF);
		PriorityQueue<Node> q = new PriorityQueue<>();
		q.add(new Node(start, 0));
		dist[start] = 0;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			// 중복 방문에 대한 처리
			if(dist[cur.id] < cur.cost) continue;
			
			for(Node next : list[cur.id]) {
				int newCost = cur.cost + next.cost;
				// 이미 더 짧은 경로가 존재한다면 취급하지 않음
				if(dist[next.id] <= newCost) continue;
				q.add(new Node(next.id, newCost));
				dist[next.id] = newCost;
			}
		}
	}

}
