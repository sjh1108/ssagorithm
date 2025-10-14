package bk.silver; //S1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 문제이해
 * - 1부터 N번까지의 컴퓨터
 * - A가 B를 신뢰 = B -> A
 * - 가장 연결된 것이 많은 노드를 찾기
 * 
 * 주의할 점
 * - 출력 : 오름차순 
 * - N = 10,000 (노드 1만)
 * - M = 100,000 (간선 10만)
 * - 시간복잡도 10억번이라는 뎁쇼 
 * 
 * 전략
 * - DFS하면서 카운팅. 매개변수는 현재 위치 => 시간초과 실패 
 * - BFS
 * 
 * 후기 
 * - 인접리스트 선언 및 생성 방법을 잊어버림
 * - dfs에서 방문처리를 잊어버려서 영원히 루프돌음 
 * - 시간초과 몇번나는 거임
 * - 상황이 심각하여 2시간 동안 안풀려서 실패코드 올리겠습니다 
 */
public class Main_1325_효율적인해킹 {
	
	static int N, M;
	static ArrayList<Integer>[] adj; // 인접리스트 
	static int[] count;
	static boolean[] visited;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		StringBuilder sb = new StringBuilder();
		
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		adj = new ArrayList[N+1]; // 인접리스트 
		for(int i = 0; i < N+1; i++) {
			adj[i] = new ArrayList<Integer>();
		}//초기화 
		count = new int[N+1];
		
		int from, to;
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			to = Integer.parseInt(st.nextToken());
			from = Integer.parseInt(st.nextToken());	
			
			adj[to].add(from); // B -> A
		}
		
		for(int i = 1; i <= N; i++) { //1~M까지의 컴퓨터
			dfs(i, new boolean[N+1]);
		}
		
		// 최대값 찾기 
		int max = 0;
		for(int i = 1; i <= N; i++) {
			max = Math.max(max, count[i]);
		}
		
		// 출력 
		for(int i = 1; i <= N; i++) {
			if(count[i] == max) {
				sb.append(i).append(" ");				
			}
		}
		System.out.println(sb.toString());
		
	}
	private static void dfs(int cur, boolean[] visited) {
		visited[cur] = true;
		for(int next : adj[cur]) {
			if(!visited[next]) {
				count[next]++;
				dfs(next, visited);
			}
		}

	}
}
