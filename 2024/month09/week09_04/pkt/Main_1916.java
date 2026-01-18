package week_study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


class Node implements Comparable<Node>{
	int v, w;

	public Node(int v, int w) {
		super();
		this.v = v;
		this.w = w;
	}

	@Override
	public int compareTo(Node o) {
		return this.w - o.w; // 가중치 오름차순
	}
}


public class Main_1916 {

	static int u;
	static int v;
	static int w;

	static final int INF = Integer.MAX_VALUE;
	static int V, E, K;
	static List<Node>[] graph;
	static int[] dist;



	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st; 
        V = Integer.parseInt(br.readLine()); // 정점의 갯수
		E = Integer.parseInt(br.readLine()); // 간선의 갯수


		graph = new ArrayList[V+1];

		for (int i = 1; i <= V; i++) {
			graph[i] = new ArrayList<>();
		}


		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			u = Integer.parseInt(st.nextToken());
			v = Integer.parseInt(st.nextToken());
			w = Integer.parseInt(st.nextToken());

			// 방향 그래프 <- 살짝 이해하기 어렵다. 
			graph[u].add(new Node(v,w));
		}

		st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());; // 시작점
		int end = Integer.parseInt(st.nextToken()); // 도착점 입력 받음
		
		dist = new int[V+1];
		Arrays.fill(dist,INF); // 비용 저장

		dijkstra(start);

		// 입출력
		System.out.println(dist[end]);

	}


	public static void dijkstra(int s) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		dist[s] = 0; // 시작점까지 거리는 0으로 설정 - 정답 배열 세팅, 위 아래 역할이 다름!
		pq.add(new Node(s,0)); //// 시작 정점을 큐에 넣음 - 탐색 시작 트리거
		
		
		while(!pq.isEmpty()) {
			Node cur = pq.poll(); // 가장 거리가 짧은 정점 꺼내기, 왜? 우선순위 큐니까,
			if(cur.w > dist[cur.v]) continue; // 이미 처리된 거리보다 크면 스킵..
			
			for (Node next : graph[cur.v]) { // dist[next.v] 기준이 되며 계속 갱신되는 녀석
				// 더 짧은 경로 발견 시 업데이트
				if(dist[next.v] > dist[cur.v] + next.w) {
					dist[next.v] = dist[cur.v] + next.w;
					pq.add(new Node(next.v, dist[next.v])); // 갱신된 거리 큐에 추가
				}
			}
			
			
		}
		
		
		
	}
}
