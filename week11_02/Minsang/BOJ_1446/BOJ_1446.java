package week11_02.Minsang.BOJ_1446;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class BOJ_1446 {
	/**
	 * 모든 지름길은 일방통행, 고속도로를 역주행할 수 없다 (-> 양방향 그래프 x)
	 * 
	 */
	static int N, D;
	static ArrayList<Node>[] list; // for문으로 초기화
	static int INF = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		N = Integer.parseInt(st.nextToken());
		D = Integer.parseInt(st.nextToken());
		
		// 초기화
		list = new ArrayList[D+1];
		for(int i = 0; i <= D; i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			if(to > D) continue;
			if(to - from <= cost) continue;
			
			// 단방향 간선 추가
			list[from].add(new Node(to, cost));
		}
		
		for(int i = 0; i < D; i++) {
			list[i].add(new Node(i+1, 1));
		}
		
		int result = dij(0);
		System.out.println(result);
	}
		
		// 다익 메서드
		static int dij(int start) {
			int[] dist = new int[D+1]; // 다익은 최소 거리를 구해야하기 때문에 -> dist 배열 만들기 
			Arrays.fill(dist, INF);
			dist[start] = 0;
			
			PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
			pq.add(new Node(start, 0));
			
			while(!pq.isEmpty()) {
				Node cur = pq.poll();
				
				if(dist[cur.to] < cur.cost)
					continue;
				
				for(Node next : list[cur.to]) {
					int newDist = dist[cur.to] + next.cost;
					if(newDist < dist[next.to]) {
						dist[next.to] = newDist;
						pq.add(new Node(next.to, newDist));
					}
				}
			}
			
			return dist[D];

	}
	
	static class Node{
		int to, cost;
		
		Node(int to, int cost){
			this.to = to;
			this.cost = cost;
		}
	}

}

