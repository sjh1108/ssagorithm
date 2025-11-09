package week_11_02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

// 트리의 부모찾기 
public class Main_11725 {
	
	static int N;
	static boolean[] visited;
	static List<Integer>[] arr; //<Integer>해줘야 for each에서 사용 가능. 
	static int[] parent;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		
		N = Integer.parseInt(br.readLine());
		
		arr = new ArrayList[N+1];
		
		parent = new int[N+1];
		
		
		for (int i = 1; i <= N; i++) {
			arr[i] = new ArrayList<>();
		}
		
		for (int i = 1; i < N; i++) { //간선 수 N-1, 실수 조심!
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			arr[a].add(b);
			arr[b].add(a);
		}
		
		visited = new boolean[N+1];
		
		bfs(1);
		
		for (int i = 2; i <= N; i++) {
			sb.append(parent[i]).append("\n");
		}
		
		System.out.println(sb);
		
		
	}

	private static void bfs(int start) {
		
		Queue<Integer> q = new ArrayDeque<>();
		q.offer(start);
		visited[start] = true; // 시작점 true해야 가능 중요. 
		
		while(!q.isEmpty()) {
			
			int cur = q.poll();
			for (int i: arr[cur]) { // cur와 연결된 애들
				if(!visited[i]) { // 아직 한 번도 안 본 애면
					visited[i] = true; // 그것은 cur의 자식임. 
					parent[i] = cur; // 이 코드 익숙해지는 게 중요함. 
					q.offer(i);
				}
			}
		}
		
	}
}
