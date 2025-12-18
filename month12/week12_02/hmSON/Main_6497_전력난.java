import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_6497_전력난 {
	
	/*
	 * BOJ_6497 : 전력난 (Gold_4)
	 * 자료구조 및 알고리즘 : 최소 신장 트리(MST), 크루스칼
	 *
	 * [문제 요약]
	 * - 모든 집을 연결하면서 가로등을 켜두되, 불필요한 가로등은 소등하여 전력을 절약하려 한다.
	 * - 모든 집이 서로 오갈 수 있도록 연결성을 유지하면서 절약할 수 있는 '최대 액수'를 구하라.
	 * - 입력은 여러 테스트 케이스로 주어지며, m=0, n=0일 때 종료된다.
	 *
	 * [핵심 아이디어]
	 * - "절약할 수 있는 최대 비용" = "전체 가로등 비용" - "최소한으로 켜야 하는 가로등 비용(MST)"
	 * - 즉, 전체 간선 비용의 합을 미리 구해두고, 크루스칼 알고리즘을 통해 MST를 구성하는 최소 비용을 구하여 뺀다.
	 *
	 * [구현 메모]
	 * - 전체 비용(total)은 입력 단계에서 모든 간선의 비용을 더해 계산한다.
	 * - MST 구성 비용(reqCost)은 크루스칼 알고리즘 수행 시 선택된 간선의 비용만 누적한다.
	 * - Union-Find를 통해 사이클을 판별하며 간선을 선택한다.
	 *
	 * [시간 복잡도]
	 * - 간선 정렬: O(E log E)
	 * - Union-Find 연산: O(E * α(V)) (거의 상수)
	 * - 전체 복잡도: O(E log E)
	 */
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 간선 정보를 담는 클래스 (비용 기준 오름차순 정렬 지원)
	static class Edge implements Comparable<Edge> {
		int v1, v2, cost;
		
		public Edge(int v1, int v2, int cost) {
			this.v1 = v1;
			this.v2 = v2;
			this.cost = cost;
		}

		@Override
		public int compareTo(Edge e) {
			return this.cost - e.cost;
		}
	}
	
	static int v, total; // 집의 수(정점), 모든 가로등 비용의 합
	static int[] head;   // Union-Find 부모 배열
	static Edge[] edges; // 간선 리스트
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken()); // 집의 수 (m)
		int e = Integer.parseInt(st.nextToken()); // 길의 수 (n)
		
		// 0 0 입력 시 종료 조건 처리를 위해 리턴 (main에서 break 처리됨)
		if(v == 0) return;
		
		head = new int[v];
		edges = new Edge[e];
		
		// Union-Find 초기화
		for(int i=0; i<v; i++) {
			head[i] = i;
		}
		
		total = 0; // 전체 비용 초기화
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			total += cost; // 모든 가로등을 켰을 때의 비용 누적
			edges[i] = new Edge(v1, v2, cost);
		}
		
		// 크루스칼 알고리즘을 위해 간선을 비용 오름차순으로 정렬
		Arrays.sort(edges);
	}
	
	// Find : 경로 압축(Path Compression)
	static int find(int x) {
		if(head[x] == x) return x;
		return head[x] = find(head[x]);
	}
	
	// Union : 두 집합 병합. 병합 성공 시 true 반환
	static boolean union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		
		// 이미 같은 집합(사이클 발생)이면 false
		if(ra == rb) return false;
		
		// 번호가 작은 쪽을 부모로 설정 (일관성 유지)
		if(ra > rb) {
			int temp = ra; ra = rb; rb = temp;
		}
		head[rb] = ra;
		return true;
	}
	
	// 크루스칼 알고리즘을 수행하여 MST 비용 반환
	static int makeTree() {
		int reqCost = 0; // MST 구성에 필요한 최소 비용
		int cnt = 0;     // 선택된 간선 수
		
		for(Edge e : edges) {
			// 두 정점이 연결되어 있지 않다면(사이클 X) 간선 선택
			boolean res = union(e.v1, e.v2);
			if(!res) continue;
			
			cnt++;
			reqCost += e.cost;
			
			// MST의 간선 개수는 정점 - 1개이면 충분하므로 조기 종료 가능
			if(cnt == v - 1) break;
		}
		
		return reqCost;
	}
	
	public static void main(String[] args) throws Exception {
		while(true) {
			init();
			// 정점 개수가 0이면 종료 (입력의 끝)
			if(v == 0) break;
			
			// 필수 유지 비용(MST 비용) 계산
			int reqCost = makeTree();
			
			// 절약 가능한 최대 비용 = 전체 비용 - 필수 비용
			sb.append((total - reqCost) + "\n");
		}
		
		System.out.println(sb);
	}

}