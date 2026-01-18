import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1865_웜홀 {
	
	// BOJ_1865 : 웜홀(Gold_3)
	// 자료구조 및 알고리즘 : 벨만 포드
	// 양수의 가중치를 가지는 양방향 도로와, 음수의 가중치를 가지는 단방향 웜홀이 존재한다.
	// 어떤 지점에서 시작하였을 때 총 비용이 음수인 상태로 시작 지점에 돌아올 수 있는가를 요구한다. 즉, 음수 사이클 발생 여부를 판단해야 한다.
	// 시작 지점은 정해지지 않았고, 분리된 여러 개의 그래프가 주어질 수도 있어 아직 접근하지 않은 정점이 있다면, 벨만 포드를 또 돌려야 한다.
	// 벨만 포드 알고리즘을 통해 음수 사이클이 발견되면 YES를, 아니라면 NO를 출력한다.
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 정점 수, 간선 수(도로 + 웜홀)
	static int v, e;
	// 각 정점별 시작 지점으로부터의 최단 거리
	static long[] dist;
	// 간선 정보(도로는 양방향, 웜홀은 단방향)
	static Edge[] edges;
	// 초기값
	static final int INF = Integer.MAX_VALUE;
	
	static class Edge {
		// 각 간선의 시작점, 도착점, 비용
		int from, to, cost;
		
		public Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int w = Integer.parseInt(st.nextToken());
		
		// edges 배열에 도로를 추가할 때, 양방향임을 표현하기 위해 2번 입력해야 하므로 간선의 총 개수는 r*2 + w이다.
		e = r*2 + w;
		
		dist = new long[v];
		Arrays.fill(dist, INF);
		edges = new Edge[e];
		
		for(int i=0; i<r*2; i+=2) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			// 도로는 양방향이므로 반대방향도 추가
			edges[i] = new Edge(from, to, cost);
			edges[i+1] = new Edge(to, from, cost);
		}
		
		for(int i=0; i<w; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int cost = Integer.parseInt(st.nextToken());
			
			// 웜홀은 반드시 음수 가중치를 가지므로 비용에 -1을 곱함
			edges[r*2+i] = new Edge(from, to, cost*-1);
		}
		
	}
	
	static boolean bellmanFord(int start) {
		// 시작 지점은 거리가 0
		dist[start] = 0L;
		
		// 최단 경로 - 벨만 포드 알고리즘
		// v-1회만큼 최단 거리 계산 작업을 반복
		// 이후 다시 간선들을 순회하며, v번쨰 순회하는 경우에도 값 변경이 일어난다면 이는 무한히 감소하는 음수 사이클이 존재하는 것임.
		// 그렇지 않다면 정상적인 그래프
		
		for(int i=0; i<v-1; i++) {
			for (Edge e : edges) {
                if (dist[e.from] != INF && dist[e.to] > dist[e.from] + e.cost) {
                    dist[e.to] = dist[e.from] + e.cost;
                }
            }
		}
		
		// 음수 사이클 판정
		// 한 번이라도, 최단거리 추가 감소가 일어난다면 음수 사이클이 존재한다는 것을 증명
		for(Edge e : edges) {
			if (dist[e.from] != INF && dist[e.to] > dist[e.from] + e.cost) {
                return false;
            }
		}
		
		return true;
	}
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			// 그래프는 분리된 상태일 수 있음
			// 따라서 아직 접근하지 못한 지점을 찾아 시작점으로 지정한 후 벨만 포드 알고리즘 시작
			// 단 한 번이라도, 음수 사이클이 발생하면 즉시 탐색 종료
			boolean res = true;
			for(int i=0; i<v; i++) {
				if(dist[i] != INF) continue;
				res = bellmanFord(i);
				if(!res) break;
			}
			
			// 벨만 포드 알고리즘을 통해 음수 사이클 발견시 YES 출력
			sb.append(res ? "NO" : "YES").append("\n");
		}
		
		System.out.println(sb);
	}

}
