package algostudy.baek.m9w4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
/*
 * 도시간 비용이 1이라서 bfs로도 최단거리 보장 가능
 */
public class Main_18352_이용호 {
	static ArrayList<Integer>[] graph; // ArrayList배열 선언
	static int[] dist;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken()); // 도시 개수
		int M = Integer.parseInt(st.nextToken()); // 도로 개수
		int K = Integer.parseInt(st.nextToken()); // 거리 정보
		int X = Integer.parseInt(st.nextToken()); // 출발 도시
		
		graph = new ArrayList[N+1]; // 1-based
		dist = new int[N+1];
		Arrays.fill(dist, -1);
		
		//배열에 ArrayList 추가
		for(int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			graph[from].add(to);
		}
		bfs(X);
		
		StringBuilder sb = new StringBuilder();
		boolean isExist = false; // K인 도시 존재하는지
		for(int i = 1; i <= N; i++) {
			if(dist[i] != K) continue;
			isExist = true;
			sb.append(i).append("\n");
		}
		System.out.print(isExist ? sb : -1);
	}
	static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		dist[start] = 0;
		
		while(!q.isEmpty()) {
			int now = q.poll();
			
			for(int next : graph[now]) {
				if(dist[next] == -1) {//방문 안한 도시면
					dist[next] = dist[now]+1; // 다음 도시 cost = 현재도시+1
					q.add(next);
				}
			}
		}
	}

}
