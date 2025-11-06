package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_10282_해킹 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int tc, n,d,c, a,b,s;
	static List<int[]>[] graph;
	static int[] dist;
	public static void main(String[] args) throws NumberFormatException, IOException {
		tc = Integer.parseInt(input.readLine());
		for (int i = 0; i < tc; i++) {
			tokens = new StringTokenizer(input.readLine());
			n = Integer.parseInt(tokens.nextToken()); // 컴퓨터수
			d = Integer.parseInt(tokens.nextToken()); // 의존성 개수 
			c = Integer.parseInt(tokens.nextToken()); // 해킹당한 컴 번호(시작점)
			
			graph = new ArrayList[n+1];
			for (int j = 0; j <= n; j++) {
				graph[j] = new ArrayList<>();
			}
			for (int j = 0; j < d; j++) {
				tokens = new StringTokenizer(input.readLine());
				a = Integer.parseInt(tokens.nextToken());
				b = Integer.parseInt(tokens.nextToken());
				s = Integer.parseInt(tokens.nextToken());
				
				graph[b].add(new int[] {a, s});
			}
			int[] res = dijkstra(c);
			System.out.println(res[0] +" "+ res[1]);
		}// tc
		
	}//main
	static int[] dijkstra(int s) {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
		dist = new int[n+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[s] = 0; //시작점 초기화
		pq.offer(new int[] {s,0}); //시작점부터 offer s,0 cost는 당연히 0
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int now = cur[0];
			int cost = cur[1];
			
			if (cost > dist[now]) continue;
			
			for (int[] next : graph[now]) {
				int nextNode = next[0];
				int nextCost = next[1] + cost;
				
				if (nextCost < dist[nextNode]) {
					dist[nextNode] = nextCost;
                    pq.offer(new int[]{nextNode, nextCost});
				}
			}
		}
		 int cnt = 0;
	     int maxTime = 0;
	        for (int i = 1; i <= n; i++) {
	            if (dist[i] != Integer.MAX_VALUE) { // max값 아닌게 해킹당한거 그래서 이거만큼 cnt++
	                cnt++;
	                maxTime = Math.max(maxTime, dist[i]); // 오래걸리는시간 갱신해서 return해주기
	            }
	        }
	        return new int[]{cnt, maxTime};
	}
}// class
