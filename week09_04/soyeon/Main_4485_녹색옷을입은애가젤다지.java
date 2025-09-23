package test.a;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 다익스트라 풀기
// 다익맞지..?ㅠㅠ 
// 각 격자하나하나가 노드... 

// N * N 격자
// 0,0 시작 -> n-1, n-1 까지 이동해야함 
// 각 칸에 도둑루피가 있고 지나가려면 도둑 루피 크기만큼 소지금을 잃게됨
// 루피 크기 : 0<= k <= 9 
// 상하좌우 1칸씩 이동 가능
// 잃는 최소 금액을 구하시오 
public class Main_4485_녹색옷을입은애가젤다지 {

	static int N;
	static int map[][];
	static boolean check[][]; // 해당 위치를 고려했는가 
	static int dist[][]; // 출발지에서 해당위치까지의 최단비용 (누적)
	static int[] dr = {-1, 1, 0, 0};
	static int[] dc = {0, 0, -1, 1}; 
	
	// pq를 사용해 cost 최소의 
	// pq에 출발지에서 특정 노드까지 가는 코스트를 저장해야함
	static class Point implements Comparable<Point>{
		int r;
		int c;
		int cost; // 출발지에서 r,c까지 오는 비용 
		public Point(int r, int c, int cost) {
			super();
			this.r = r;
			this.c = c;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(Point o) {
			return Integer.compare(this.cost, o.cost);
		}
	}
	static StringBuilder sb = new StringBuilder();
		
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int cnt = 1;
		while(true) {
			N = Integer.parseInt(br.readLine());
			if(N == 0) break; // 종료조건 
			
			map = new int[N][N];
			check = new boolean[N][N];
			dist = new int[N][N];
			
			for(int r = 0; r < N; r++) {
				st = new StringTokenizer(br.readLine());
				for(int c = 0; c < N; c++) {
					map[r][c] = Integer.parseInt(st.nextToken());
				}
			} //입력 끝
			
			sb.append("Problem ").append(cnt++).append(": ");
			dijkstra();
			
			
		}
		System.out.println(sb.toString());
		
		
	}

	private static void dijkstra() {

		for(int[] d : dist) {
			Arrays.fill(d, Integer.MAX_VALUE);
		} //거리 배열 무한대로 초기화 
		
		dist[0][0] = map[0][0]; // 시작위치에도 돈뺏김 (오답노트) 
		
		PriorityQueue<Point> pq = new PriorityQueue<>();
		pq.offer(new Point(0,0,dist[0][0])); // 가중치 0부터 시작하는 거 아님 (오답노트)
		
		while(!pq.isEmpty()) { //탐색해보자 
			
			Point now = pq.poll(); // 하나 꺼내 
			int r = now.r;
			int c = now.c;
			int cost = now.cost;
//			System.out.println("poll: " + r + ", " + c + ", " + cost);
			
			if(r == N-1 && c == N-1) continue; // 원하는 출구까지가면 끝 
			if(check[r][c]) continue; //이미 확인했으면 끝
			check[r][c] = true; // 방문처리 
			
			
			
			for(int d = 0; d < 4; d++) {
				
				int nr = r + dr[d];
				int nc = c + dc[d];
				if(!inMap(nr, nc)) continue; // 범위체크 
//				System.out.println("nr: " + nr + ", nc: " + nc);
				
				// 기록된(dist) 출발->nr,nc까지의 비용보다
				// 출발 -> 지금 위치 + nr,nc비용 이 더 적으면
				// 그 값으로 기록(dist)를 바꿈 
				if(dist[nr][nc] > dist[r][c] + map[nr][nc]) {
					dist[nr][nc] = dist[r][c] + map[nr][nc];
					//그리고 pq에 넣어
					pq.offer(new Point(nr,nc,dist[nr][nc]));
				}
			}	
		}
		
		sb.append(dist[N-1][N-1]).append("\n");
	}
	
	private static boolean inMap(int nr, int nc) {
		return nr>=0 && nc>=0 && nr<N && nc<N;
	}
	
}
