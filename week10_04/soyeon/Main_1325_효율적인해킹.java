package bk.silver; //1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;

import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - N (10,000) - 정점 최대수 
 * - M (100,000) - 간선 최대수
 * - 인접리스트 완탐시 시간복잡도 
 * - N x (N + M) = 11억 
 * 
 * - 1부터 N까지 번호의 정점 
 *  
 * - A B
 * - A가 B를 신뢰한다. 
 * - B를 해킹하면 A도 해킹할 수 있다.
 * - B -> A 방향의 그래프
 *
 */
public class Main_1325_효율적인해킹 {
	
	static int N, M;
	static ArrayList<Integer>[] adj;
	static int[] visited; // visitToken 방식
	static int[] cnt;
	static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		adj = new ArrayList[N+1]; //제발 선언이랑 초기화좀 제대로 하기
		for(int i = 1; i <= N; i++) { 
			adj[i] = new ArrayList<>();
		}
		visited = new int[N+1];
		
		for(int i = 0 ; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			
			adj[B].add(A);
		}// 입력끝
		
		
		cnt = new int[N+1];
		for(int i = 1 ; i <= N; i++) {// 노드번호는 1부터 시작함
			bfs(i);
		}
		
		//최대값 찾기 
		int max = cnt[1];
		sb.append(1).append(" ");
		
		for(int i = 2; i <= N; i++) {
			if(max < cnt[i]) {
				max = cnt[i];
				sb.setLength(0);
				sb.append(i).append(" ");
			}else if(max == cnt[i]) sb.append(i).append(" ");
		}
		
		System.out.println(sb.toString().trim());
	}

	private static void bfs(int start) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(start); 
		visited[start] = start; 
		int count = 1;
		
		
		while(!q.isEmpty()) {
			
			int node = q.poll(); 
			for(int next :  adj[node]) { 
				if(visited[next] == start) continue;  
				q.add(next); 
				visited[next] = start;
				++count;
			}
		}
		
		cnt[start] = count;

	}
}