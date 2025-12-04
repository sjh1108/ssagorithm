import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1854_K번째_최단경로_찾기 {
	
	/*
	 * BOJ_1854 : K번째 최단경로 찾기 (Platinum_4)
	 * 자료구조 및 알고리즘 : 다익스트라, 우선순위 큐
	 *
	 * [문제 요약]
	 * - 1번 도시에서 다른 모든 도시로 가는 'K번째 최단 경로'의 소요 시간을 구하라.
	 * - 경로가 존재하지 않으면 -1을 출력한다.
	 *
	 * [핵심 아이디어]
	 * - 일반적인 다익스트라는 dist[i]에 '최단 거리(1번째)'만 저장하고 갱신한다.
	 * - 이 문제는 K번째까지 관리해야 하므로, dist[i]를 int형 변수가 아닌
	 * '우선순위 큐(PriorityQueue)'로 선언하여 상위 K개의 경로를 저장한다.
	 * - 이때, 큐는 '내림차순(Max-Heap)'으로 설정한다.
	 * 이유: 큐의 크기가 K일 때, 가장 큰 값(K번째 경로)과 새로운 경로를 비교하여
	 * 새로운 경로가 더 짧다면 가장 큰 값을 버리고 교체하기 위함이다.
	 *
	 * [구현 메모]
	 * - dist[i]: i번 정점까지 발견된 경로 중 짧은 순서대로 최대 K개를 저장하는 Max-Heap.
	 * - 갱신 로직:
	 * 1. 저장된 경로가 K개 미만이면 -> 무조건 추가.
	 * 2. 저장된 경로가 K개이고, 새로운 경로 < dist[i].peek() (현재 K번째) 이면 -> peek를 버리고 추가.
	 *
	 * [시간 복잡도]
	 * - 일반 다익스트라 O(E log V)에서 각 간선마다 큐 삽입/삭제가 일어나므로
	 * - 큐의 크기 K를 고려하면 대략 O(K * E * log V) 또는 O(E log K) 정도의 복잡도를 가짐.
	 */
	
	static int v, k;
	// 각 정점별로 발견된 경로비용을 저장하는 우선순위 큐 배열 (Max-Heap)
	static PriorityQueue<Integer>[] dist;
	static List<Node>[] graph;
	
	static class Node implements Comparable<Node>{
		int to, cost;
		
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}

		@Override
		public int compareTo(Node o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		// dist 배열 초기화: 각 요소가 PriorityQueue임
		dist = new PriorityQueue[v];
		graph = new ArrayList[v];
		for(int i=0; i<v; i++) {
			// [중요] 내림차순 정렬 (Max-Heap)
			// K개를 유지하면서, 새로운 작은 값이 들어올 때 '가장 큰 값(K번째)'을 밀어내기 위함
			dist[i] = new PriorityQueue<>(Comparator.reverseOrder());
			graph[i] = new ArrayList<>();
		}
		
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1; // 1-based -> 0-based
			int to = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			graph[from].add(new Node(to, cost));
		}
	}
	
	static void dijkstra() {
		PriorityQueue<Node> q = new PriorityQueue<>();
		// 시작점 초기화
		q.add(new Node(0, 0));
		dist[0].add(0);
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			// [가지치기]
			// 현재 경로 비용(cur.cost)이 해당 정점의 K번째 최단 경로(dist[cur.to].peek())보다 크다면
			// 더 이상 탐색할 가치가 없음 (K번째 순위권 밖이므로)
			// 단, 아직 K개가 안 찼다면 탐색해야 함
			if(dist[cur.to].size() == k && cur.cost > dist[cur.to].peek()) continue;
			
			for(Node next : graph[cur.to]) {
				int newCost = cur.cost + next.cost;
				
				// [갱신 조건]
				// 이미 K개를 찾았는데, 새 경로가 현재 K번째 경로(가장 큰 값)보다 크거나 같다면 무시
				if(dist[next.to].size() == k && dist[next.to].peek() <= newCost) continue;
				
				// 경로 추가
				dist[next.to].add(newCost);
				
				// K개를 초과했다면 가장 큰 값(가장 비효율적인 경로) 제거하여 size K 유지
				if(dist[next.to].size() > k) dist[next.to].poll();
				
				// 큐에 추가하여 다음 탐색 진행
				q.add(new Node(next.to, newCost));
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		dijkstra();
		
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<v; i++) {
			// K개의 경로를 못 찾았으면 -1, 찾았으면 K번째 경로(Max-Heap의 root) 출력
			sb.append((dist[i].size() < k ? -1 : dist[i].peek()) + "\n");
		}
		System.out.println(sb);
	}

}