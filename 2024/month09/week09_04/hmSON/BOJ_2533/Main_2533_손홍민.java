import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2533_손홍민 {
	
	// 알고리즘 분류 : Tree DP
	// 풀이 순서 : 
	// 1. 주어지는 트리의 노드 관계가 주어지면 이를 인접 리스트로 저장
	// 2. 임의의 한 노드를 루트 노드로 하는 트리의 후위 순회 시작
	// 3. 리프 노드 x에 방문시  dp[x][0] = 0, dp[x][1] = 1로 고정
	// 4. 그 외의 노드는 자식 노드의 방문 결과를 합산해야 함.
	// 4-1. 노드 x를 선택하지 않는 경우 => 자식 노드 전체를 얼리어답터로 지정했을 때의 총 얼리어답터 수를 합산해야 함.
	// 4-2. 노드 X를 선택한 경우 => 자식 노드마다 저장된 DP 배열의 값 중 더 작은 쪽을 선택하여 합산. 전부 끝나면 1을 더해줌(자신을 얼리어답터로 지정했으므로).
	// 5. 후위 순회가 종료되면 초기에 지정한 루트 노드 방문시 저장된 DP 배열의 값 중 더 작은 쪽을 선택하면 됨.
	
	// 인접 리스트
	static List<List<Integer>> list = new ArrayList<>();
	// 각 노드를 선택한 경우, 선택하지 않은 경우의 얼리어답터 최소값을 저장
	static int[][] dp;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		for(int i=0; i<n; i++) {
			list.add(new ArrayList<>());
		}
		
		for(int i=0; i<n-1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()) - 1;
			int v = Integer.parseInt(st.nextToken()) - 1;
			
			// 양방향 인접리스트로 저장
			list.get(u).add(v);
			list.get(v).add(u);
		}
		
		// dp[x][0] : 노드 자신이 얼리어답터가 아닐 때의 총 얼리어답터 수
		// dp[x][1] : 노드 자신이 얼리어답터일 때의 총 얼리어답터 수
		dp = new int[n][2];
		
		// 루트 : 0, 부모 노드 : -1(없다는 의미)
		postorder(0, -1);
		System.out.println(Math.min(dp[0][0], dp[0][1]));
	}
	
	static void postorder(int root, int parent) {
		// 리프 노드 방문시의 처리
		if(list.get(root).isEmpty()) {
			dp[root][0] = 0;
			dp[root][1] = 1;
			
			return;
		}
		
		// 리프 노드가 아닌 경우 자식 노드를 먼저 순회
		List<Integer> childList = list.get(root);
		
		int unselect = 0, select = 1;
		for(int child : childList) {
			// 양방향 리스트로 만든 점을 감안하여 부모 노드로 돌아가서 무한 루프가 발생하는 것을 방지
			if(child == parent) continue;
			// 후위 순회이므로 연산 전에 자식 노드부터 방문
			postorder(child, root);
			unselect += dp[child][1];
			select += Math.min(dp[child][0], dp[child][1]);
		}
		
		dp[root][0] = unselect;
		dp[root][1] = select;
	}

}