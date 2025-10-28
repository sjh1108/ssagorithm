import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Main_2665_미로 만들기 {
	
	// BOJ_2665 : 미로만들기(Gold_4)
	// 자료구조 및 알고리즘 : Deque(덱), 다익스트라, 0-1 BFS
	
	// 흰 방과 검은 방으로 구성된 미로가 있다. 단, 검은 방으로는 이동할 수 없다.
	// 따라서 검은 방을 흰 방으로 바꿔서 시작점부터 도착점까지 이동 가능한 하나의 경로를 만들어내야 한다.
	// 최소한의 횟수만 사용하여 검은 방을 흰 방으로 바꿔야 한다. 이미 통로가 뚫려있다면 바꿀 필요가 없다.
	
	// 맵의 크기
	static int n;
	// 맵 정보(true : 흰 방(통과 가능), false : 검은 방(통과 불가능))
	static boolean[][] map;
	// 시작 지점부터 각 좌표까지 이동할 때 통과한 검은 방 수
	static int[][] dist;
	// 4방향
	static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
	
	static class Node {
		// 각 위치의 좌표 및 비용
		int y, x, cost;
		
		public Node(int y, int x, int cost) {
			this.y = y;
			this.x = x;
			this.cost = cost;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		map = new boolean[n][n];
		dist = new int[n][n];
		for(int i=0; i<n; i++) {
			String line = br.readLine();
			for(int j=0; j<n; j++) {
				// 입력된 값이 1이면 true, 0이면 false 기록
				map[i][j] = line.charAt(j) == '1';
			}
			
			// 이론상 각 테스트케이스의 결과는 n*2 미만이므로 이를 초기값으로 사용
			Arrays.fill(dist[i], n*2);
		}
	}
	
	static int zeroOneBfs() {
		// 가중치가 0과 1뿐인 그래프의 최단 경로를 구할 때에는 다익스트라 대신 0-1 BFS 사용
		// 우선순위 큐 대신 Deque를 사용하여 우선순위 큐를 모방
		// 다음 정점으로 이동하기 위한 비용이 0이면 addFirst, 비용이 1이면 addLast를 사용하여 데이터 추가
		Deque<Node> deq = new ArrayDeque<>();
		
		// 시작점은 반드시 (0, 0)
		deq.addLast(new Node(0, 0, 0));
		dist[0][0] = 0;
		
		while(!deq.isEmpty()) {
			// 노드 반환시에는 반드시 앞부터
			Node cur = deq.removeFirst();
			
			// 4방향 탐색
			for(int i=0; i<4; i++) {
				int y = cur.y + dy[i];
				int x = cur.x + dx[i];
				// 배열 범위 초과 방지
				if(!isIn(y, x)) continue;
				
				// 도착 지점인 (n-1, n-1)로 도착한 경우
				if(y == n-1 & x == n-1) return cur.cost;
				
				// 다음 방이 흰 방 -> 비용 추가 없음 -> addFirst
				// 다음 방이 검은 방 -> 비용 1 추가 -> addLast
				if(map[y][x]) {
					if(dist[y][x] <= cur.cost) continue;
					deq.addFirst(new Node(y, x, cur.cost));
					dist[y][x] = cur.cost;
				} else {
					if(dist[y][x] <= cur.cost+1) continue;
					deq.addLast(new Node(y, x, cur.cost+1));
					dist[y][x] = cur.cost+1;
				}
			}
		}
		
		// 해당 문제에서는 절대 이 곳까지 오지 않음
		return -1;
	}
	
	// 배열 범위 초과 방지용 메서드
	static boolean isIn(int y, int x) {
		return y >= 0 && x >= 0 && y < n && x < n;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(zeroOneBfs());
	}

}
