import java.io.*;
import java.util.*;

public class Main_2252_줄_세우기 {

	/*
	 * BOJ_2252 : 줄 세우기 (Gold_3)
	 * 자료구조 및 알고리즘 : 위상 정렬
	 *
	 * [문제 요약]
	 * - N명의 학생들을 키 순서대로 줄을 세우려고 한다.
	 * - 두 학생의 키를 비교한 결과(A가 B보다 앞에 서야 함)가 M번 주어진다.
	 * - 주어진 비교 결과를 모두 만족하는 줄 세우기 순서를 출력하라.
	 * - 답이 여러 개인 경우 아무거나 출력해도 된다.
	 *
	 * [핵심 아이디어]
	 * - 순서가 정해져 있는 작업(비교)을 차례대로 수행해야 하므로 위상 정렬을 사용한다.
	 * - '자신을 향하는 간선의 개수'인 진입 차수(In-degree)가 0인 노드는 선행 조건이 없으므로 바로 줄을 설 수 있다.
	 * - 1. 진입 차수가 0인 노드를 큐에 넣는다.
	 * - 2. 큐에서 노드를 꺼내 결과(StringBuilder)에 저장하고, 연결된 노드들의 진입 차수를 1씩 감소시킨다.
	 * - 3. 감소된 진입 차수가 0이 되면 그 노드도 큐에 넣는다.
	 *
	 * [구현 메모]
	 * - v, e : 학생 수(N)와 비교 횟수(M).
	 * - prev[] : 각 노드의 진입 차수(In-degree)를 저장하는 배열.
	 * - graph : 특정 학생 바로 뒤에 서야 하는 학생들의 목록을 저장하는 인접 리스트.
	 * - ArrayDeque : LinkedList보다 메모리 효율적이고 빠른 큐 연산을 위해 사용.
	 * - StringBuilder : 빈번한 출력을 줄이고 한 번에 출력하여 I/O 시간을 단축.
	 *
	 * [시간 복잡도]
	 * - 정점의 수 V(N)와 간선의 수 E(M)에 대해, 모든 정점과 간선을 한 번씩 확인하므로 O(V + E).
	 */

	static int v;
	static int[] prev;
	static List<Integer>[] graph;
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws Exception {
		init();
		topologicalSort();
		System.out.println(sb);
	}

	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());

		prev = new int[v];
		graph = new ArrayList[v];
		for(int i=0; i<v; i++) {
			graph[i] = new ArrayList<>();
		}

		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			// 입력은 1번 학생부터 시작하지만, 배열 인덱스 편의를 위해 -1 하여 0번부터 시작하도록 변환
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;

			prev[to]++; // 진입 차수 증가
			graph[from].add(to); // 방향 그래프 연결
		}
	}

	static void topologicalSort() {
		Queue<Integer> q = new ArrayDeque<>();

		// 1. 진입 차수가 0인(자신 앞에 설 사람이 없는) 학생들을 큐에 삽입
		for(int i=0; i<v; i++) {
			if(prev[i] == 0) q.add(i);
		}

		while(!q.isEmpty()) {
			int cur = q.poll();
			sb.append(cur + 1).append(" "); // 결과 저장 (1-based 복구)

			// cur 학생 바로 뒤에 설 수 있는 학생들 탐색
			for(int next : graph[cur]) {
				prev[next]--; // 선행 조건 하나 해결

				// 더 이상 앞에 설 사람이 없다면 큐에 추가
				if(prev[next] == 0) {
					q.add(next);
				}
			}
		}
	}

}