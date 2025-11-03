package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

//SWEA 보급로
public class Solution_보급로 {

	static int N;
	static int[][] map; 
	static int[][] dist; // 비용을 저장하는 배열
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	static final int INF = Integer.MAX_VALUE; // 상수
			
	static class Node implements Comparable<Node>{
		int x, y, cost;

		Node(int x, int y, int cost) {
			this.x = x;
			this.y = y;
			this.cost = cost;
		}
		
		public int compareTo(Node O) {
			return Integer.compare(this.cost, O.cost); // this - 노드객체 : cost 값이 작은거부터. 
			// return Integer.compare(o.cost, this.cost); // cost 값이 큰거부터 나오게 하기. 
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br  = new BufferedReader(new InputStreamReader(System.in));
		//  st 필요없으면 지우기. 오류 뜸. 
		
		int T = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder(); // 여기 있어서 sb를 출력가능
		
		// 테케안에서 계속 갱신되어야 함. - 갱신되어야 할 값 안되는지 체크 중요. 
		for (int tc = 1; tc <= T; tc++) {
		
			N =Integer.parseInt(br.readLine());
			
			map = new int[N][N];
			dist = new int[N][N];
			
			for (int i = 0; i < N; i++) {
				String input = br.readLine(); // 한줄로 읽기. 
				for (int j = 0; j < N; j++) {
					//String input = st.nextToken(); // 실수: 한글자씩읽기
					map[i][j] = input.charAt(j) - '0'; // 숫자 변환
					dist[i][j] = INF; // 세트로 함께 해주기. 모든 칸의 초기 비용은 무한대로 설정 -- 다익가서 다 Arrays.fill()채워도 될듯. 
				}
			} // 0,0 ~ N-1,N-1까지.. 
			
			
			// 다익스트라 알고리즘 수행
			int result = dijkstra();
			
			sb.append("#").append(tc).append(" ").append(result).append("\n");
		}// 테스트케이스 돌아가기. 
		System.out.println(sb);
		// 출력코드 자리 
		
	}

	private static int dijkstra() {
		PriorityQueue<Node> pq = new PriorityQueue<>(); // 우선순위큐 사용. 
		
		dist[0][0] = map[0][0];
		
		pq.offer(new Node(0,0,map[0][0])); // 시작점, 비용
		
		while(!pq.isEmpty()) {
			
			Node cur = pq.poll(); //  우선순위큐에서 꺼냄.  최소비용. 
			
			if(cur.cost > dist[cur.x][cur.y]) continue; // 이미 더 짧은 경로로 방문된 노드면 건너뛰어라. 
			// 짧은 경로를 찾는거니까. 
			
			
			// 4방 확인
			for (int dir = 0; dir < 4; dir++) {
				int nx = cur.x + dx[dir];
				int ny = cur.y + dy[dir];
				
				if(nx < 0 || ny < 0 || nx >= N || ny >= N) continue;
				// ki 행열이 M,N인 경우도 확인하기. 중요. 
				
				int next = cur.cost + map[nx][ny]; // 누적, 현재누적 + 현재값
				if(next < dist[nx][ny]) { // 들어온게 더 짧은 경로일 때 실행
					dist[nx][ny] = next; // 갱신해주기. 
					pq.offer(new Node(nx,ny,next));
				}
			}
		}
		
		return dist[N-1][N-1]; // 도착점 N-1,N-1,, 작은애들 도착점이 달라지는 것. 
		
	}
}
