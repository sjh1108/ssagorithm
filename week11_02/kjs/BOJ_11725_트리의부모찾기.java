package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class BOJ_11725_트리의부모찾기 {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		List<Integer>[] graph = new ArrayList[N + 1]; // 그래프 표현을 위해 list 만들기
		for (int i = 1; i <= N; i++) {
			// 1이 트리의 루트로 설정 i~N개의
			graph[i] = new ArrayList<>();
			// graph 배열 1칸에 list 생성
		}

		for (int i = 0; i < N - 1; i++) { // N-1 정점 넣기
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			// 양방향 연결
			graph[a].add(b);
			graph[b].add(a);
		}

		int[] parent = new int[N + 1];
		boolean[] visited = new boolean[N + 1];

		Queue<Integer> q = new ArrayDeque<>();
		q.add(1);
		visited[1] = true;
		// 1번 루트 넣기

		while (!q.isEmpty()) {
			int current = q.poll();

			for (int next : graph[current]) {
				if (!visited[next]) {
					visited[next] = true;
					parent[next] = current;
					q.add(next);
				}
			}

		}
		for(int i=2; i<=N; i++) {
			System.out.println(parent[i]);
		}

	}

}
