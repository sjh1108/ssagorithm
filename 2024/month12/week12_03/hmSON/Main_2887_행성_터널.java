import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2887_행성_터널 {
	
	/*
	 * BOJ_2887 : 행성 터널(Platinum_5)
	 * 자료구조 및 알고리즘 : 크루스칼(MST), 정렬
	 *
	 * [문제 요약]
	 * - N개의 행성이 3차원 공간(x, y, z)에 위치한다.
	 * - 두 행성을 연결하는 터널의 비용은 min(|x1-x2|, |y1-y2|, |z1-z2|)이다.
	 * - 모든 행성을 연결하는 데 필요한 최소 비용을 구하라.
	 *
	 * [핵심 아이디어]
	 * - N이 최대 100,000이므로, 모든 행성 쌍에 대해 간선을 생성하면 O(N^2)으로 메모리/시간 초과가 발생한다.
	 * - 비용 결정 공식을 보면 각 좌표(x, y, z)의 차이 중 가장 작은 값이 비용이 된다.
	 * - 따라서, X좌표 기준 정렬 후 인접한 행성끼리, Y좌표 기준 정렬 후 인접한 행성끼리, 
	 * Z좌표 기준 정렬 후 인접한 행성끼리만 간선 후보로 등록해도 충분하다.
	 * - 이렇게 하면 간선 개수는 최대 3(N-1)개가 되어 효율적으로 MST를 구할 수 있다.
	 *
	 * [구현 메모]
	 * - planets 배열: [ID, X, Y, Z] 형태로 저장하여 정렬 시 원래 인덱스(ID)를 추적한다.
	 * - 3번의 정렬(Arrays.sort)과 간선 추출 후, 크루스칼 알고리즘을 수행한다.
	 *
	 * [시간 복잡도]
	 * - 행성 정렬: 3 * O(N log N)
	 * - 간선 정렬: 간선 수 E ≈ 3N 이므로 O(N log N)
	 * - Union-Find: O(N)
	 * - 전체: O(N log N)
	 */
	
	static int v; // 행성의 개수
	static int[] head; // Union-Find 부모 배열
	static int[][] planets; // 행성 정보: [idx, x, y, z]
	static List<Edge> edges = new ArrayList<>(); // 간선 리스트
	
	static class Edge implements Comparable<Edge> {
		int v1, v2, cost;
		
		public Edge(int v1, int v2, int cost) {
			this.v1 = v1;
			this.v2 = v2;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Edge e) {
			return Integer.compare(this.cost, e.cost);
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		v = Integer.parseInt(br.readLine());
		
		planets = new int[v][4];
		head = new int[v];
		
		for(int i=0; i<v; i++) {
			head[i] = i; // Union-Find 초기화
			
			StringTokenizer st = new StringTokenizer(br.readLine());
			planets[i][0] = i; // 원래 행성의 번호(ID) 저장
			planets[i][1] = Integer.parseInt(st.nextToken()); // X
			planets[i][2] = Integer.parseInt(st.nextToken()); // Y
			planets[i][3] = Integer.parseInt(st.nextToken()); // Z
		}
		
		// 1. X좌표 기준 정렬 및 인접 간선 추가
		Arrays.sort(planets, (a, b) -> a[1] - b[1]);
		for(int i=0; i<v-1; i++) {
			// 인접한 두 행성 간의 거리(X차이)를 비용으로 하는 간선 추가
			int cost = Math.abs(planets[i][1] - planets[i+1][1]);
			edges.add(new Edge(planets[i][0], planets[i+1][0], cost));
		}
		
		// 2. Y좌표 기준 정렬 및 인접 간선 추가
		Arrays.sort(planets, (a, b) -> a[2] - b[2]);
		for(int i=0; i<v-1; i++) {
			int cost = Math.abs(planets[i][2] - planets[i+1][2]);
			edges.add(new Edge(planets[i][0], planets[i+1][0], cost));
		}
		
		// 3. Z좌표 기준 정렬 및 인접 간선 추가
		Arrays.sort(planets, (a, b) -> a[3] - b[3]);
		for(int i=0; i<v-1; i++) {
			int cost = Math.abs(planets[i][3] - planets[i+1][3]);
			edges.add(new Edge(planets[i][0], planets[i+1][0], cost));
		}
	}
	
	static int find(int x) {
		if(x == head[x]) return x;
		return head[x] = find(head[x]);
	}
	
	static boolean union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		
		if(ra == rb) return false; // 이미 같은 집합(사이클)
		
		// 번호가 작은 쪽을 부모로 설정
		if(ra < rb) head[rb] = ra;
		else head[ra] = rb;
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 간선들을 비용 기준으로 오름차순 정렬 (Kruskal)
		Collections.sort(edges);
		
		int cnt = 0;
		long total = 0; // 총 비용은 int 범위를 넘을 수 있으므로 long 사용
		
		for(Edge e : edges) {
			// 사이클이 발생하지 않는 경우에만 간선 선택
			if(union(e.v1, e.v2)) {
				cnt++;
				total += e.cost;
				
				// MST 완성 조건: 간선이 V-1개 모이면 종료
				if(cnt == v-1) break;
			}
		}
		
		System.out.println(total);
	}

}