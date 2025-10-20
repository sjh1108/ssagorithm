import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static List<List<Integer>> graph; 
	static boolean[] visited;
	static int[] result;
	static int cnt;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());
		
		graph = new ArrayList<>();
		for(int i = 0; i <= N; i++) { //1 base
			graph.add(new ArrayList<>());
		}
		
		
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			graph.get(from).add(to);
			graph.get(to).add(from);
		}
		//내림차순 정렬
		for(int i = 1; i <= N; i++) {
			Collections.sort(graph.get(i), Collections.reverseOrder());
		}
		
		visited = new boolean[N+1];
		cnt = 0;
		result = new int[N+1];
		StringBuilder sb = new StringBuilder();
		
		bfs(R);
		
		for(int i = 1; i <= N; i++) {
			sb.append(result[i] + "\n");
		}
		System.out.println(sb);
		
	}
	
	public static void bfs(int start) {
		Queue<Integer> q = new LinkedList<>();
		q.add(start);
		visited[start] = true;
		
		while(!q.isEmpty()) {
			int now = q.poll();
			
			result[now] = ++cnt;
			
			
			for(int next : graph.get(now)) {
				if(visited[next]) continue;
				visited[next] = true;
				q.add(next);
			}
		}
	}

}
