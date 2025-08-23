import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_4485_손홍민 {

	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// n: 맵 크기, map: 동굴 정보, lost: 각 구역까지 이동하는 데 드는 손실 비용 저장 배열
	static int n;
	static int[][] map, lost;
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	public static void main(String[] args) throws Exception {
		int tc = 1;
		while(true) {
			init();
			if(n == 0) break;
			sb.append("Problem ").append(tc++).append(": ");
			dijkstra();
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		n = Integer.parseInt(br.readLine());
		if(n == 0) return;
		map = new int[n][n];
		for(int i=0; i<n; i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<n; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
			}
		}
		// 각 구역별 최소 손실비용 측정
		// 최소 비용 측정을 위해 초기값 최대로 설정
		lost = new int[n][n];
		for(int i=0; i<n; i++) {
			Arrays.fill(lost[i], Integer.MAX_VALUE);
		}
	}
	
	static void dijkstra() {
		// 우선순위 큐 생성. 정렬 기준은 각 구역까지 이동하는 데 소모한 비용
		PriorityQueue<Cave> q = new PriorityQueue<>(Comparator.comparingInt(o -> o.cost));
		q.add(new Cave(0, 0, map[0][0]));
		
		// 시작 위치의 코인 수도 손실 비용에 포함임
		lost[0][0] = map[0][0];
		while(!q.isEmpty()) {
			Cave cur = q.poll();
			for(int i=0; i<4; i++) {
				int ny = cur.y + dy[i];
				int nx = cur.x + dx[i];
				// 배열 범위 초과 방지
				if(!isIn(ny, nx)) continue;
				
				int totalCost = cur.cost + map[ny][nx];
				// 목적지까지 도착했다면 즉시 탐색 종료
				// 가장 먼저 도착한 경우가 곧 최소 비용인 경우임
				if(ny == n-1 && nx == n-1) {
					sb.append(totalCost).append("\n");
					return;
				}
				
				// 다음 구역이 이미 최소 비용으로 탐색한 상태라면 무시
				// 그렇지 않다면 손실 비용을 기록하고 큐에 등록
				if(lost[ny][nx] <= totalCost) continue;
				lost[ny][nx] = totalCost;
				q.add(new Cave(ny, nx, totalCost));
			}
		}
	}
	
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < n && x < n;
	}
	
	// 다익스트라에 사용할 클래스
	// 각각 현 지역의 좌표, 현재까지 손실한 비용
	static class Cave {
		int y, x, cost;

		public Cave(int y, int x, int cost) {
			this.y = y;
			this.x = x;
			this.cost = cost;
		}
	}
	
}
