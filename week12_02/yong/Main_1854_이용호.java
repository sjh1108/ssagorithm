package algostudy.baek.week12_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
/*
 * 1 -> i 번째 도시로 가는 k번째 최단경로
 * 일반 다익 + i번쨰로 가는 cost 관리 필요 -> 도시 i의 cost들을 pq로 관리
 */
public class Main_1854_이용호 {
	public static class Node implements Comparable<Node>{
		int to; int cost;
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
		@Override
		public int compareTo(Node o) {
			return this.cost - o.cost;
		}
	}
	public static List<List<Node>> graph;
	public static List<PriorityQueue<Integer>> kCost;
	public static int n, m, k;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList<>();
        kCost = new ArrayList<>();
		
		for(int i  = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
			kCost.add(new PriorityQueue<>(Collections.reverseOrder()));
		}
		
		for(int i = 0; i < m; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			
			graph.get(from).add(new Node(to,cost));
		}
		
		dij(1);
		StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            if (kCost.get(i).size() < k) sb.append(-1).append("\n");
            else sb.append(kCost.get(i).peek()).append("\n"); // k번째 최단거리
        }
        System.out.print(sb.toString());
	}
	public static void dij(int start) {
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(start,0));
		kCost.get(start).add(0); // 시작점 0
		
		while(!pq.isEmpty()) {
			Node now = pq.poll();
			
			for(Node next : graph.get(now.to)) {
				int newCost = now.cost + next.cost;
				
				PriorityQueue<Integer> bag = kCost.get(next.to);
				
				// k개 미만이면 그냥 add
				if(bag.size() < k) {
					bag.add(newCost);
					pq.add(new Node(next.to, newCost));
				}
				// 최댓값(k개 중 가장 큰 값)보다 더 짧은 경로가 오면 교체
				else if (bag.peek() > newCost) {
                    bag.poll();         // 가장 큰 값 제거
                    bag.add(newCost);   // 새 값 삽입
                    pq.add(new Node(next.to, newCost));
                }
			}
		}
	}
}
