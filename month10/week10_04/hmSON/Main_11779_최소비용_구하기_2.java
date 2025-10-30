import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_11779_최소비용 구하기_2 {
	
	// BOJ_11779 : 최소비용 구하기 2
	// 자료구조 및 알고리즘 : 다익스트라, 역추적
	// BOJ_1916 최소비용 구하기의 상위 문제로, 최소비용을 구하고 그 경로도 함께 출력해야 한다.
	
	// 도시(정점) 수, 시작 위치와 목적지
	static int v, start, end;
	// 시작점으로부터 각 도시까지의 최단거리 배열, 최단 거리 이동시 각 도시별 직전 방문 도시 번호(prevId[start]는 반드시 -1)
	static int[] dist, prevId;
	// 버스 이동 경로 -> 단방향 인접 리스트
	static List<Node>[] list;
	
	static class Node implements Comparable<Node>{
		// 경로의 시작 위치, 목적지, 비용
		int from, to, cost;
		
		public Node(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
		
		// 비용 기준 오름차순 정렬
		@Override
		public int compareTo(Node o) { return cost - o.cost; }
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		v = Integer.parseInt(br.readLine());
		dist = new int[v];
		Arrays.fill(dist, Integer.MAX_VALUE);
		prevId = new int[v];
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		StringTokenizer st;
		int e = Integer.parseInt(br.readLine());
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			list[from].add(new Node(from, to, cost));
		}
		
		st = new StringTokenizer(br.readLine());
		start = Integer.parseInt(st.nextToken()) - 1;
		end = Integer.parseInt(st.nextToken()) - 1;
		
		// 시작 도시까지의 이동 비용은 0
		// 시작 도시 방문 직전의 도시 번호는 -1(없음)
		dist[start] = 0;
		prevId[start] = -1;
	}
	
	static void dijkstra() {
		// 정렬 순서는 Node 클래스에 정의됨(비용 기준 오름차순)
		PriorityQueue<Node> q = new PriorityQueue<>();
		// 시작점이므로 from은 -1을 입력하여 명시적으로 이전 지점이 없음을 표기
		q.add(new Node(-1, start, 0));
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			// 중복 방지
			if(dist[cur.to] < cur.cost) continue;
			// 현재 위치로 오기 직전에 방문한 도시 번호를 저장
			prevId[cur.to] = cur.from;
			
			for(Node next : list[cur.to]) {
				// 현재 위치에서 다음 도시로 이동하는 경우 총 소모 비용
				int newCost = cur.cost + next.cost;
				// 이미 더 짧은 경로가 있으면 현재의 경로는 무시
				if(dist[next.to] <= newCost) continue;
				
				q.add(new Node(cur.to, next.to, newCost));
				dist[next.to] = newCost;
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		dijkstra();
		
		// 시작 위치에서 목적지까지의 최소 비용
		StringBuilder sb = new StringBuilder();
		sb.append(dist[end] + "\n");
		
		// 역추적을 위한 스택 활용
		Deque<Integer> stack = new ArrayDeque<>();
		// 목적지 번호부터 시작해 다음 값이 -1일 때까지, 즉 시작점까지 역추적을 통해 저장
		int x = end;
		while(x != -1) {
			stack.addLast(x);
			x = prevId[x];
		}
		
		// 방문한 도시의 수(시작, 도착점 포함), 방문 도시 목록
		sb.append(stack.size() + "\n");
		while(!stack.isEmpty()) {
			sb.append((stack.removeLast()+1) + " ");
		}
		
		System.out.println(sb);
	}

}
