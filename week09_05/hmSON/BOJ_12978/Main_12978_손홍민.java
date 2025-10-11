import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_12978_손홍민 {
	
	// 알고리즘 분류 : 트리 DP
	// 전형적인 DP 배열을 이용하여 각 항을 선택했을 때와 선택하지 않았을 때의 최소값을 구하는 문제
	// 후위 순회를 이용해 리프 노드에 기본값을 채우고, 상위 노드로 올라가면서 각 항의 최소 요구량을 구한다.
	
	static List<Integer>[] list;
	static int[][] dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		// dp[x][0] : x번 도시를 선택하지 않은 경우
		// dp[x][1] : x번 도시를 선택한 경우
		dp = new int[n][2];
		list = new ArrayList[n];
		// 인접 리스트
		for(int i=0; i<n; i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken())-1;
			int v2 = Integer.parseInt(st.nextToken())-1;
			// 양방향 도로
			list[v1].add(v2);
			list[v2].add(v1);
		}
		
		// 임의의 한 점을 루트 노드로 잡음
		postorder(0, -1);
		System.out.println(Math.min(dp[0][0], dp[0][1]));
	}
	
	static void postorder(int root, int parent) {
		List<Integer> childs = list[root];
		// 자식 노드가 없으면 리프 노드이므로 기본값 입력 후 리턴.
		// 단, 양방향 도로인 해당 문제에서는 어차피 모든 도시가 최소 하나의 연결점을 가지게 되므로, 아래 코드에서도 충분히 기저 조건을 걸러냄
		if(childs.size() == 0) {
			dp[root][0] = 0;
			dp[root][1] = 1;
			return;
		}
		
		// 선택한 경우와 선택하지 않은 경우
		int sel = 1, unsel = 0;
		for(int c : childs) {
			// 부모 노드 탐색 방지
			if(c == parent) continue;
			postorder(c, root);
			// 현재 도시에 경찰서를 세울 경우 -> 인접 도시는 세워도, 안세워도 상관없음. 대신 최소를 유지해야 함.
			sel += Math.min(dp[c][0], dp[c][1]);
			// 현재 도시에 경찰서를 안세울 경우 -> 자식 노드는 전부 세워야 함
			unsel += dp[c][1];
		}
		
		dp[root][0] = unsel;
		dp[root][1] = sel;
	}

}
