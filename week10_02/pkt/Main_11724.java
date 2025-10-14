package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main_11724 {

	static int N, M;
	static ArrayList<Integer>[] A;
	static boolean[] visited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		

		A = new ArrayList[N+1];
		visited = new boolean[N+1]; 

		for (int i = 1; i < N+1; i++) {
			A[i] = new ArrayList<>();
		}

		for (int i = 1; i < M+1; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			A[u].add(v);
			A[v].add(u);
		}

		// 방문
		int count = 0;
		for(int i = 1; i < N+1; i++) {
			if(!visited[i]) {
				count++;
				DFS(i);
			}			

		} System.out.println(count);
	}

	private static void DFS(int i) {
		if(visited[i]) {
			return;
		}
		visited[i] = true;
		for(int j : A[i]) {
			if(!visited[j]) {
				DFS(j);
			}
		}
	}
}

