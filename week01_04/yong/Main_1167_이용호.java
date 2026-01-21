package baek_week01_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1167_이용호 {
/*
 * 트리의 지름 -> 임의의 두 점사이 거리중 가장 긴
 * 1. 한 시작점에서 가장 먼 곳(B) 탐색
 * 2. B에서 가장 먼곳(A) 탐색(B -> A) 
 * 알게 된것: 트리 지름 구하는 방법
 */
	public static class Node {
		int to;
		int cost;
		
		public Node(int to, int cost){
			this.to = to;
			this.cost = cost;
		}
	}
	
	public static int V;
	public static boolean[] visited;
	public static LinkedList<Node>[] graph; // 인접 리스트
	static int maxDist; // 현재까지 최대 거리
	static int farNode; // 최대거리 갖는 노드(정점)
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		// 정점 개수
		V = Integer.parseInt(br.readLine());
		
		graph = new LinkedList[V+1];
		for(int i = 0; i <= V; i++) {
			graph[i] = new LinkedList<>();
		}
		
		// V개 줄에 걸쳐 간선 정보
		for(int i = 0; i < V; i++) {
			// 정점 번호(처음) / 다른 정점 번호, 정점 까지 거리 / 마지막 -1
			StringTokenizer st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			// 간선 저장
			while(true) {
				int to = Integer.parseInt(st.nextToken());
				if(to == -1) break; // 입력 종료
				else {
					int cost = Integer.parseInt(st.nextToken());
					graph[from].add(new Node(to,cost));
				}
			}
			
			
		}
		// 지름의 길이 A -> B 찾기
		// 시작점 기준 가장 먼곳 찾기(임의 -> B)
		bfs(1);
		maxDist = 0;
		// (B -> A)
		bfs(farNode);
		System.out.println(maxDist);
	}
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		visited = new boolean[V+1];
		int[] dist = new int[V+1]; // start로 부터 거리
		
		q.add(start);
		visited[start] = true;
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			// 방문 안한 노드 방문(현재 정점과 연결된 모든 노드)
			for(Node next : graph[cur]) {
				if(!visited[next.to]) {
					visited[next.to] = true;
					// 해당 노드까지 거리
					dist[next.to] = dist[cur] + next.cost;
					q.add(next.to);
					
					// 가장 먼 정점 갱신(farNode, maxDist)
					if(dist[next.to] > maxDist) {
						maxDist = dist[next.to];
						farNode = next.to;
					}
				}
			}
		}
	}

}
