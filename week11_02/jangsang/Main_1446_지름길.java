package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1446_지름길 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N, straight, A, B, shortCut;
	static List<int[]>[] graph;
	static int[] dist;
	// 시작위치 도착위치 길의길이
	public static void main(String[] args) throws IOException {
		tokens = new StringTokenizer(input.readLine());
		N = Integer.parseInt(tokens.nextToken());
		straight = Integer.parseInt(tokens.nextToken());
		
		graph = new ArrayList[straight+1];
		for (int i = 0; i <= straight; i++) {
			graph[i] = new ArrayList<>();
			if(i == straight)	continue;
			graph[i].add(new int[] {i+1, 1});
		}
		
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			A = Integer.parseInt(tokens.nextToken());
			B = Integer.parseInt(tokens.nextToken());
			shortCut = Integer.parseInt(tokens.nextToken());
			if(B > straight)	continue;
			if(B - A <= shortCut)	continue;
			graph[A].add(new int[] {B, shortCut});
		} 
		
		System.out.println(djikstra());

	}
	
	static int djikstra() {
		PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[1], o2[1]));
		dist = new int[straight+1];
		Arrays.fill(dist, Integer.MAX_VALUE);
		dist[0] = 0;
		pq.offer(new int[] {0,0});
		
		
		while(!pq.isEmpty()) {
			int[] cur = pq.poll();
			int next = cur[0];
			int cost = cur[1];
			
			if (cost > dist[next]) continue;
			
			for (int i = 0; i < graph[next].size(); i++) {
                int next1 = graph[next].get(i)[0];
                int nCost = graph[next].get(i)[1];
                int total = cost + nCost;
                if (total < dist[next1]) {
                    dist[next1] = total;
                    pq.offer(new int[]{next1, total});
                }
            }
		}
		return dist[straight];
	}
}
