package Rank4.gold_2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1167 {

	// 트리의 지름 구하기
	// 먼저 트리의 지름을 구성하는 두 노드 중 하나를 찾을 것이다. 임의의 점으로부터 탐색을 시작하여 가장 거리가 먼 노드 v1을 찾는다.
	// 이후 노드 v1에서 다시 탐색을 시작하여 가장 거리가 먼 노드 v2를 찾는다.
	// 두 노드 v1, v2간의 거리가 트리에서 가장 먼 거리에 있는, 즉 트리를 펼쳤을 때의 지름이 된다. 이를 위해 총 2번의 DFS를 사용해야 한다.
	
	// 이를 살짝 응용하여 해당 문제에서는 DFS를 한 번만 수행하여 각 노드가 자신의 각 서브트리별로 가장 먼 노드와의 거리를 구한 후 그 중 2개를 합하는 방식을 사용한다.
	// 즉, 노드 자기 자신의 하위 노드들 중 가장 거리가 먼 두 노드간의 거리를 저장하면서 최대값을 기록하고 복귀한다.
	// 풀어보니 시간

	// 정점의 개수, 정점 간 거리 최대값(트리의 지름)
	static int n, max = 0;
	// 인접리스트
	static List<List<Node>> list = new ArrayList<>();
	// DFS 탐색시 사용할 방문 처리 배열
	static boolean[] visited;
	
	static class Node {
		int to, cost;
		
		public Node(int to, int cost) {
			this.to = to;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 임의의 정점을 기준으로 첫번째 노드를 찾을 것이다. 따라서 루트 노드를 시작점으로 잡는다.
		visited[0] = true;
		dfs(0);
		System.out.println(max);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		n = Integer.parseInt(br.readLine());
		for(int i=0; i<n; i++) {
			list.add(new ArrayList<>());
		}
		visited = new boolean[n];
		
		for(int i=0; i<n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			// 입력 순서는 각 노드의 번호, 그리고 이 노드를 부모로 하는 서브 노드의 번호 및 거리이다.
			// 입력값의 마지막에 -1이 존재하고, 이는 더 이상 서브 노드가 없음을 의미한다.
			int root = Integer.parseInt(st.nextToken())-1;
			while(true) {
				int x = Integer.parseInt(st.nextToken());
				if(x == -1) break;
				int cost = Integer.parseInt(st.nextToken());
				list.get(root).add(new Node(x-1, cost));
			}
		}
	}
	
	static int dfs(int root) {
		// 현재 노드로부터 가장 거리가 먼 두 점과의 거리
		int maxLen = 0, maxLen2 = 0;

		// 각 서브노드를 탐색하며, 가장 먼 점을 찾는다.
		for(Node next : list.get(root)) {
			if(visited[next.to]) continue;
			visited[next.to] = true;
			int len = dfs(next.to) + next.cost;
			// 그 중 각 서브노드 별로 가장 먼 점들 중 가장 멀리 있는 2개만 선택하여 길이를 기록한다.
			if(len > maxLen) {
				maxLen2 = maxLen;
				maxLen = len;
			} else if(len > maxLen2) maxLen2 = len;
		}
		// 두 점의 거리를 합한다. 이것이 현재 노드를 루트노드로 하면서 자신을 거치는 서브트리의 지름이다.
		// 최대값을 갱신한다. 루트 노드를 지나는 거리가 가장 길다는 법은 없기 때문이다.
		int sum = maxLen + maxLen2;
		if(sum > max) max = sum;

		// 반환값은 현재 노드와 가장 걸리 있는 노드와의 거리이다. 그래야 상위 노드에서 같은 방식으로 거리를 구하니까.
		return maxLen;
	}

}
