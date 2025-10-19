import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_9470 {
	
	// BOJ_9470 : Strahler 순서(Gold_3)
	// 자료구조 및 알고리즘 : 우선선위 큐, 위상 정렬
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 테스트 케이스 번호, 정점(강의 근원 또는 교차점) 수
	static int tc, v;
	// 각 정점별 먼저 방문해야 할 다른 정점의 수, 각 정점을 향하는 다른 정점의 최대 Strahler 순서
	static int[] prev, prevLevels;
	// 인접 리스트
	static List<Integer>[] list;
	
	static class Node implements Comparable<Node> {
		// 각 정점의 번호와 Strahler 순서
		int id, level;
		
		public Node(int id, int level) {
			this.id = id;
			this.level = level;
		}
		
		@Override
		public int compareTo(Node o) {
			return level - o.level;
		}
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		// 테스트케이스 번호, 정점 수, 간선 수
		tc = Integer.parseInt(st.nextToken());
		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		prev = new int[v];
		prevLevels = new int[v];
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 간선 입력은 반드시 A to B로 주어짐
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			
			list[from].add(to);
			prev[to]++;
		}
	}
	
	static int topologySort() {
		// 어떤 강이든 최소 Strahler 순서는 1로 시작함
		int maxLevel = 1;
		
		// Strahler 순서가 적은 것부터 방문하기 위해 우선순위 큐 사용
		PriorityQueue<Node> q = new PriorityQueue<>();
		for(int i=0; i<v; i++) {
			// 강의 근원에 해당하는 정점을 큐에 추가. 해당 정점의 Strahler 순서는 모두 1임.
			if(prev[i] > 0) continue;
			q.add(new Node(i, 1));
		}
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			// 강 전체의 Strahler 레벨 최대값 갱신
			if(cur.level > maxLevel) maxLevel = cur.level;
			
			for(int next : list[cur.id]) {
				// 다음 정점들의 prev[i] 디카운트
				// 동시에 먼저 방문해야 할 다른 정점이 남아있다면 현재 정점의 Strahler 레벨을 저장
				prev[next]--;
				if(prev[next] > 0) {
					prevLevels[next] = cur.level;
					continue;
				}
				
				// 더 이상 먼저 방문해야 할 다른 정점이 없다면 큐에 정점 추가 및 Strahler 레벨 기록
				// Strahler 순서 규칙
				// 1. 정점 x로 향하는 다른 정점들 중 가장 높은 Strahler 레벨 i를 기록
				// 2-1. Strahler 레벨이 i인 이전 정점이 1개라면 정점 x의 Strahler 레벨은 i
				// 2-2. Strahler 레벨이 i인 이전 정점이 2개 이상이라면 정점 x의 Strahler 레벨은 i+1
				int newLevel = cur.level + (cur.level == prevLevels[next] ? 1 : 0);
				q.add(new Node(next, newLevel));
			}
		}
		
		return maxLevel;
	}
	
	public static void main(String[] args) throws Exception {
		int t = Integer.parseInt(br.readLine());
		
		// 각 테스트케이스별 번호 및 강 전체의 Strahler 레벨 출력
		for(int i=0; i<t; i++) {
			init();
			sb.append(tc + " " + topologySort() + "\n");
		}
		
		System.out.println(sb);
	}

}
