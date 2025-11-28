import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_3197_백조의_호수 {
	
	/*
	 * BOJ_3197 : 백조의 호수 (Platinum_5)
	 * 자료구조 및 알고리즘 : BFS(Flood Fill), 유니온 파인드
	 *
	 * [문제 요약]
	 * - 호수에 얼음('X')과 물('.'), 두 마리의 백조('L')가 있다. 매일 물과 접촉한 얼음은 녹아 물이 된다.
	 * - 두 마리의 백조가 물 위를 이동해 서로 만나려면 최소 며칠이 걸리는지 구하라.
	 *
	 * [핵심 아이디어]
	 * - 매일 백조가 이동 가능한지 BFS를 돌리면 O(T * R * C)로 시간 초과가 발생한다.
	 * - 대신, '영역(Component)' 관점으로 접근한다:
	 * 1. 초기 상태에서 연결된 물들을 각각 하나의 그룹(번호)으로 묶는다 (Flood Fill).
	 * 2. 백조가 속한 두 그룹(s1, s2)이 같은 집합에 속하는지 Union-Find로 확인한다.
	 * 3. 매일 가장자리의 얼음을 녹이며(BFS), 새로 녹은 물이 다른 그룹의 물과 접촉하면 Union 연산으로 두 그룹을 합친다.
	 *
	 * [구현 메모]
	 * - 2차원 좌표 (y, x)를 큐에 넣을 때 객체 생성 오버헤드를 줄이기 위해 `y * c + x`의 1차원 정수로 변환하여 관리.
	 * - edge 큐: 현재 물의 가장자리(내일 녹을 얼음 옆)를 관리하여 불필요한 탐색을 방지.
	 *
	 * [시간 복잡도]
	 * - 각 칸은 최대 한 번 큐에 들어가고 처리된다.
	 * - Union-Find 연산은 거의 상수 시간이므로 전체 복잡도는 O(R * C).
	 */
	
	static int r, c;
	static int s1 = 0, s2 = 0; // 두 백조가 속한 그룹 번호
	static int n = 0; // 발견된 물 그룹(Component)의 총 개수(ID 부여용)
	
	// 맵 정보: -1(얼음), 1~N(물 그룹 번호), 0(아직 그룹이 지정되지 않은 물), -2(백조)
	static int[][] map;
	// Union-Find를 위한 부모 배열
	static int[] head;
	// 상하좌우 델타
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	// 얼음과 맞닿아 있는 물의 위치를 저장하는 큐 (다음에 녹일 기준점)
	static Queue<Integer> edge = new ArrayDeque<>();
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		r = Integer.parseInt(st.nextToken());
		c = Integer.parseInt(st.nextToken());
		map = new int[r][c];
		
		// 입력 처리
		for(int i=0; i<r; i++) {
			String line = br.readLine();
			for(int j=0; j<c; j++) {
				char ch = line.charAt(j);
				if(ch == 'X') map[i][j] = -1; // 얼음
				if(ch == '.') map[i][j] = 0;  // 물 (아직 그룹핑 전)
				if(ch == 'L') map[i][j] = -2; // 백조 (물과 동일 취급하되 위치 식별용 임시 값)
			}
		}
		
		// 초기 물 그룹핑 (Flood Fill)
		// 아직 방문하지 않은 물(0)이나 백조(-2)를 찾으면 새로운 그룹 ID(n)를 부여
		for(int i=0; i<r; i++) {
			for(int j=0; j<c; j++) {
				if(map[i][j] == -1 || map[i][j] > 0) continue;
				n++; // 그룹 번호 증가
				floodFill(i, j);
			}
		}
		
		// Union-Find 배열 초기화
		head = new int[n+1];
		for(int i=1; i<=n; i++) {
			head[i] = i;
		}
	}
	
	// 초기 상태에서 연결된 물들을 하나의 그룹으로 묶고, 가장자리(edge)를 큐에 삽입
	static void floodFill(int sy, int sx) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(sy * c + sx);
		
		// 시작점이 백조 위치였다면 해당 그룹 번호를 저장
		if(map[sy][sx] == -2) {
			if(s1 == 0) s1 = n;
			else s2 = n;
		}
		map[sy][sx] = n; // 현재 그룹 번호 할당
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			int y = cur / c, x = cur % c;
			
			boolean edgeFlag = false; // 현재 칸이 얼음과 맞닿아 있는지 체크
			for(int i=0; i<4; i++) {
				int ny = y + dy[i];
				int nx = x + dx[i];
				
				// 범위 벗어남 or 이미 같은 그룹
				if(ny < 0 || nx < 0 || ny >= r || nx >= c || map[ny][nx] == n) continue;
				
				// 인접한 곳이 얼음인 경우 -> 현재 칸은 가장자리(edge)임
				if(map[ny][nx] == -1) {
					if(!edgeFlag) {
						edge.add(y * c + x); // 다음에 여기서부터 얼음을 녹임
						edgeFlag = true;
					}
					continue;
				}
				
				// 인접한 곳이 백조인 경우 그룹 번호 기록
				if(map[ny][nx] == -2) {
					if(s1 == 0) s1 = n;
					else s2 = n;
				}
				
				// 물로 연결된 곳 계속 탐색
				q.add(ny * c + nx);
				map[ny][nx] = n;
			}
		}
	}
	
	// Union-Find
	static int find(int x) {
		if(head[x] == x) return x;
		return head[x] = find(head[x]);
	}
	
	static void union(int a, int b) {
		int ra = find(a), rb = find(b);
		if(ra == rb) return;
		
		// 번호가 작은 쪽을 부모로 설정 (임의 기준)
		if(ra < rb) head[rb] = ra;
		else head[ra] = rb;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 처음부터 두 백조가 같은 물 구역에 있을 경우 0일 출력
		if(s1 == s2) {
			System.out.println(0);
			return;
		}
		
		int date = 0;
		while(true) {
			date++;
			int size = edge.size(); // 이번 날짜에 처리할 물의 가장자리 개수 (Level-order BFS)
			
			while(!edge.isEmpty() && size-- > 0) {
				int cur = edge.poll();
				int y = cur / c, x = cur % c;
				
				for(int i=0; i<4; i++) {
					int ny = y + dy[i];
					int nx = x + dx[i];
					
					// 범위 벗어남 or 이미 같은 그룹인 경우 패스
					if(ny < 0 || nx < 0 || ny >= r || nx >= c || map[ny][nx] == map[y][x]) continue;
					
					// 1. 인접한 곳이 다른 그룹의 물인 경우 -> 두 그룹을 병합(Union)
					if(map[ny][nx] > 0) {
						union(map[y][x], map[ny][nx]);
					} 
					// 2. 인접한 곳이 얼음인 경우 -> 녹여서 물로 만들고 확장
					else {
						map[ny][nx] = map[y][x]; // 얼음이 녹아 현재 그룹의 물이 됨
						edge.add(ny * c + nx);   // 새로 생긴 물도 가장자리가 됨
						
						// 중요: 새로 녹은 물 주변에 '또 다른 그룹의 물'이 있는지 확인하여 즉시 병합
						// 이를 통해 다음 날까지 기다리지 않고 연결 여부를 바로 갱신
						for(int j=0; j<4; j++) {
							int nny = ny + dy[j];
							int nnx = nx + dx[j];
							if(nny < 0 || nnx < 0 || nny >= r || nnx >= c) continue;
							
							if(map[nny][nnx] > 0 && map[nny][nnx] != map[ny][nx]) {
								union(map[ny][nx], map[nny][nnx]);
							}
						}
					}
				}
			}
			
			// 이번 턴에 얼음을 녹이고 그룹 병합을 마친 후, 두 백조가 만날 수 있는지 확인
			if(find(s1) == find(s2)) {
				System.out.println(date);
				return;
			}
		}
	}

}