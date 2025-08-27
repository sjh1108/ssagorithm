package algostudy.baek.b1916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1916_이용호 {
/*
 * 주어지는 M의 정보는 (출발도시, 도착도시, 비용) -> 다익스트라
 * 최소비용 구하기 
 * 마지막줄에 출발점도시와 도착도시
 * 출발 도시에서 도착 도시까지 가는데 드는 최소비용 출력
 */
	static class node implements Comparable<node> {
		int vertex;
		int cost;
		
		node(int vertex, int cost){
			this.vertex = vertex;
			this.cost = cost;
		}
		
		@Override
		public int compareTo(node o) {
			return this.cost - o.cost; // cost 기준 오름차순
		}
		
	}
	public static void dijkstra(int start) {
		Arrays.fill(dist, Integer.MAX_VALUE); //최단거리 배열 처음에 무한대로
		dist[start] = 0; //출발도시 비용 0
		
		PriorityQueue<node> pq = new PriorityQueue<>();
		pq.add(new node(start,0));
		
		while(!pq.isEmpty()) {
			node now = pq.poll();
			int u = now.vertex; //출발 정점
			int c = now.cost;
			
			if(c > dist[u]) continue; //pq에서 꺼낸 비용이 이미 기록된 최단거리보다 크면 건너뜀
			
			//u에서 나가는 모든 간선 확인 : 완화
			for(node edge : graph.get(u)) {
				int v = edge.vertex;
				int w = edge.cost;
				int newDist = c + w; //u까지 최단거리 d + (u -> v)
				if(newDist < dist[v]) {//경로 갱신
					dist[v] = newDist;
					pq.add(new node(v,newDist)); //갱신된 상태 PQ에 삽입
				}
			}
			
		}
	}
	static List<List<node>> graph;
	static int[] dist;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine()); //정점 개수
		int M = Integer.parseInt(br.readLine());
		
		graph = new LinkedList<>();
		dist = new int[N+1];
		
		for(int i = 0 ; i <= N;i++) {//1base
			graph.add(new LinkedList<>());
		}
		
		for(int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			graph.get(u).add(new node(v,w));
		}
		
		//시작도시, 도착도시
		StringTokenizer st = new StringTokenizer(br.readLine());
		int start = Integer.parseInt(st.nextToken());
		int end = Integer.parseInt(st.nextToken());
		dijkstra(start);
		System.out.println(dist[end]);
		
	}
	

}
