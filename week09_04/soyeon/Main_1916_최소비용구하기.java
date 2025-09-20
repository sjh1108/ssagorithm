package ssagorithm.bk.s;

	import java.io.BufferedReader;
	import java.io.InputStreamReader;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.PriorityQueue;
	import java.util.StringTokenizer;

	// 큰일났다 다익이다 
	// 시간이 0.5
	// 빡빡하다. 가지쳐야한다. 아마도.
	public class Main_1916_최소비용구하기 {

		static class Node implements Comparable<Node>{
			int index;
			int cost;
			public Node(int index, int cost) {
				super();
				this.index = index;
				this.cost = cost;
			}
			
			@Override
			public int compareTo(Node o) {
				return Integer.compare(this.cost, o.cost);
			}
		}
		
		static int N, M; // 도시 개수. 버스 개수 (1부터 시작)
		static int start, end; // 출발도시번호, 도착도시번호
		static int[] dist; // 출발지에서 각 인덱스까지의 최소거리 ?
		static boolean[] check; // 확인처리  // 아... 제한시간 0.5라서 해야할 것 같은데 
		static ArrayList<Node>[] list; // 각 인덱스에서... 의... 최소... 비용? 그런게 있음
		public static void main(String[] args) throws Exception{
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			StringTokenizer st;
			
			N = Integer.parseInt(br.readLine());
			M = Integer.parseInt(br.readLine());
			dist = new int[N+1];
			check = new boolean[N+1];
			list = new ArrayList[N+1];
			
			for(int i = 1; i <= N; i++) {
				list[i] = new ArrayList<>();
			}
			
			for(int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine());
				int from = Integer.parseInt(st.nextToken());
				int to = Integer.parseInt(st.nextToken());
				int c = Integer.parseInt(st.nextToken());
				
				list[from].add(new Node(to, c));
			}
			st = new StringTokenizer(br.readLine());
			start = Integer.parseInt(st.nextToken());
			end = Integer.parseInt(st.nextToken());
			// 입력 끝
			
			 Arrays.fill(dist, Integer.MAX_VALUE); // 무한대로 덮어쓰기
			 
			 bfs(); // 다익스트라
			 
			 System.out.println(dist[end]);

		}
		
		private static void bfs() {
			
			dist[start] = 0;

			PriorityQueue<Node> pq = new PriorityQueue<>();
			
			pq.add(new Node(start, 0));// 초기값
			
			while(!pq.isEmpty()) {
				Node cur = pq.poll();
				
				if(cur.index == end) return;// 가지치기
				if(check[cur.index]) continue;
				check[cur.index] = true;
				
				for(Node next : list[cur.index]) {
					if(dist[next.index] > cur.cost + next.cost) {
						dist[next.index] = cur.cost + next.cost;
						pq.add(new Node(next.index, dist[next.index]));
					}
				}
				
			}
			
		}

	}

