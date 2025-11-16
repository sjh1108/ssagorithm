package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class BOJ_2606_바이러스 {

	static List<Integer>[] list; // 인접 리스트
	static boolean[] visited; // 방문 체크
	static int count = 0; // 방문한 컴퓨터 수 (1번 포함)

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine()); // 컴퓨터 수
		int M = Integer.parseInt(br.readLine()); // 연결 수

		// 인접 리스트 초기화
		list = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			list[i] = new ArrayList<>();
		}

		// 간선 정보 입력 (무방향 그래프)
		for (int i = 0; i < M; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			list[a].add(b);
			list[b].add(a);
		}

		visited = new boolean[N + 1];

		// 1번 컴퓨터에서 DFS 시작
		dfs(1);


		System.out.println(count - 1);
	}

	// 깊이 우선 탐색
	private static void dfs(int node) {
		visited[node] = true;
		count++; 
		for (int next : list[node]) {
			if (!visited[next]) {
				dfs(next);
			}
		}
	}
}