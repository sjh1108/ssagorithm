import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17472_다리_만들기_2 {
	
	/*
	 * BOJ_17472 : 다리 만들기 2(Gold_1)
	 * 자료구조 및 알고리즘 : Flood Fill(BFS), Multi-Source BFS, MST(Kruskal)
	 *
	 * [문제 요약]
	 * - 격자 맵에 여러 개의 섬이 있다. 섬을 다리로 연결하여 모든 섬이 통행 가능하도록 만들어야 한다.
	 * - 다리는 직선으로만 놓을 수 있고, 길이는 2 이상이어야 한다. 다리는 겹칠 수 있다.
	 * - 모든 섬을 연결하는 다리 길이의 최솟값을 구하라. 불가능하면 -1을 출력한다.
	 *
	 * [핵심 아이디어]
	 * 1. 섬 구분 (Flood Fill): 각 섬에 고유 번호(ID)를 부여한다.
	 * 2. 다리 건설 (Multi-source BFS):
	 * - 모든 섬의 가장자리(물과 닿은 곳)를 시작점으로 큐에 넣는다.
	 * - BFS를 통해 매 턴마다 지정된 방향으로만 다리를 1칸씩 늘려나간다.
	 * - 이렇게 하면 '길이가 짧은 다리'부터 먼저 다른 섬에 닿게 되므로, 자연스럽게 가중치가 낮은 간선부터 탐색하는 효과가 있다.
	 * 3. MST 구성 (Kruskal 응용):
	 * - 다리가 다른 섬에 닿았을 때, 길이가 2 이상이고 두 섬이 아직 연결되지 않았다면(Union-Find) 다리를 설치한다.
	 * - 별도의 간선 리스트 정렬(Brute-Force & Sort) 없이 BFS 탐색 순서가 곧 길이 오름차순 정렬을 대체한다.
	 *
	 * [구현 메모]
	 * - Edge 클래스: 현재 뻗어나가고 있는 다리의 정보(위치, 출발 섬 ID, 방향, 현재 길이)를 담는다.
	 * - map[][]: 초기 입력값(-1)을 섬 번호(1~cnt)로 갱신하여 관리. 0은 바다.
	 *
	 * [시간 복잡도]
	 * - 섬 구분: O(R*C)
	 * - 다리 확장 BFS: 맵의 모든 빈칸을 4방향으로 거의 한 번씩 방문 -> O(R*C)
	 * - Union-Find: 거의 상수 시간
	 * - 전체 복잡도: O(R*C) (일반적인 간선 생성 후 정렬 방식보다 효율적임)
	 */
	
	static int r, c, cnt = 0; // 맵 크기, 섬의 개수
	static int[][] map;       // 맵 정보(0: 바다, 1~cnt: 섬 번호)
	static boolean[][] visited; // 섬 구분용 방문 배열
	static int[] head;        // Union-Find 부모 배열
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	// 다리 확장을 위한 큐(모든 섬의 가장자리에서 동시에 출발하는 BFS)
	static Queue<Edge> bridges = new ArrayDeque<>();
	
	static class Edge {
		int y, x;   // 현재 다리 끝의 위치
		int id;     // 출발한 섬의 번호
		int dir;    // 다리가 뻗어나가는 방향
		int len;    // 현재 다리의 길이
		
		public Edge(int y, int x, int id, int dir, int len) {
			this.y = y;
			this.x = x;
			this.id = id;
			this.dir = dir;
			this.len = len;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		
		map = new int[r][c];
		visited = new boolean[r][c];
		
		// 입력: 1은 땅, 0은 바다 -> 편의상 땅을 -1로 임시 저장
		for(int i=0; i<r; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<c; j++) {
				map[i][j] = Integer.parseInt(st.nextToken()) * -1;
			}
		}
		
		// 1. 섬 구분 (Flood Fill)
		// 모든 땅을 탐색하며 섬마다 고유 번호(1, 2, 3...) 부여
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(map[i][j] == 0 || visited[i][j]) continue;
				bfs(i, j, ++cnt);
			}
		}
		
		// Union-Find 초기화
		head = new int[cnt+1];
		for(int i=1; i<=cnt; i++) {
			head[i] = i;
		}
	}
	
	// 섬을 구분하고 번호를 매기는 BFS
	// 동시에 섬의 가장자리에서 바다로 뻗어나갈 '다리의 시작점(Edge)'을 큐에 등록함
	static void bfs(int sy, int sx, int idx) {
		Queue<int[]> q = new ArrayDeque<>();
		q.add(new int[] {sy, sx});
		visited[sy][sx] = true;
		map[sy][sx] = idx;
		
		while(!q.isEmpty()) {
			int[] cur = q.poll();
			
			for(int i=0; i<4; i++) {
				int y = cur[0] + dy[i];
				int x = cur[1] + dx[i];
				
				if(y < 0 || x < 0 || y >= r || x >= c || visited[y][x]) continue;
				
				// 인접한 곳이 바다(0)라면 -> 다리 건설 시작점
				// 길이 1인 상태로 해당 방향으로 뻗어나갈 준비
				if(map[y][x] == 0) bridges.add(new Edge(y, x, idx, i, 1));
				else {
					// 인접한 곳이 땅이라면 -> 같은 섬으로 체크하고 계속 탐색
					q.add(new int[] {y, x});
					visited[y][x] = true;
					map[y][x] = idx;
				}
			}
		}
	}
	
	static int find(int x) {
		if(x == head[x]) return x;
		return head[x] = find(head[x]);
	}
	
	static boolean union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		if(ra == rb) return false;
		
		if(ra < rb) head[rb] = ra;
		else head[ra] = rb;
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		int linkCnt = 0;  // 연결된 간선 수
		int totalLen = 0; // 총 다리 길이
		
		// 2. 다리 확장 및 3. MST 구성 (BFS + Kruskal)
		// BFS 특성상 길이가 짧은 다리(len=1)부터 차례대로 큐에서 나옴 (별도 정렬 불필요)
		while(!bridges.isEmpty() && linkCnt < cnt-1) {
			Edge cur = bridges.poll();
			
			// 현재 방향으로 한 칸 전진
			int ny = cur.y + dy[cur.dir];
			int nx = cur.x + dx[cur.dir];
			
			// 맵 밖으로 나가면 폐기
			if(ny < 0 || nx < 0 || ny >= r || nx >= c) continue;
			
			// 땅을 만난 경우 (다른 섬 도착)
			if(map[ny][nx] > 0) {
				// 출발지와 같은 섬이면 무시 (자기 자신으로 돌아온 경우 등) -> map[ny][nx] != cur.id 체크가 필요할 수 있으나,
				// 어차피 같은 섬인 땅 또는 이미 연결된 섬을 만나면 union을 통해 걸러짐
				
				// 다리 길이 조건: 2 이상이어야 함
				if(cur.len == 1) continue;
				
				// 서로 다른 두 섬을 연결 시도 (Union)
				// 같은 집합이 아니라면(사이클 X) 다리 설치 확정
				if(map[ny][nx] != cur.id) { // 출발지와 도착지가 다를 때만
					boolean res = union(cur.id, map[ny][nx]);
					if(res) {
						linkCnt++;
						totalLen += cur.len;
					}
				}
			}
			// 바다인 경우 -> 다리를 계속 연장
			else {
				bridges.add(new Edge(ny, nx, cur.id, cur.dir, cur.len + 1));
			}
		}
		
		// 모든 섬을 연결했으면(간선 수 == 정점 수 - 1) 길이 출력, 아니면 -1
		System.out.println(linkCnt == cnt-1 ? totalLen : -1);
	}

}