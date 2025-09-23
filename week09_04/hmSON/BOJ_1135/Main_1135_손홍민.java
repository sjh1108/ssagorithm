import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1135_손홍민 {
	
	static int n;
	static int[] dp;
	// 인접 리스트
	// 해당 문제에서는 이미 루트 노드를 알고 있고 부모 노드와 자식 노드의 번호가 명시되므로 단방향 그래프로 구성
	static List<List<Integer>> list = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		dp = new int[n];
		for(int i=0; i<n; i++) {
			list.add(new ArrayList<>());
		}
		
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			int x = Integer.parseInt(st.nextToken());
			// 입력받은 값 x는 i의 부모 노드
			// x가  -1인 경우 -> 루트 노드
			if(x != -1) list.get(x).add(i);
		}
		
		postorder(0);
		System.out.println(dp[0]);
	}
	
	public static void postorder(int root) {
		List<Integer> childs = list.get(root);
		
		// 리프 노드 -> 즉시 리턴
		if(childs.isEmpty()) return;
		
		for(int c : childs) postorder(c);
		childs.sort((a, b) -> Integer.compare(dp[b], dp[a]));
		int max = 0, time = 1;
		for(int c : childs) {
			// 자식 노드 하나를 선택할 떄마다 1분이 경과
			// 하위 트리의 소요 시간이 클 수록 빨리 전화해야 전체 소요 시간을 줄이게 됨.
			int elapsed = dp[c] + time;
			if(elapsed > max) max = elapsed;
			time++;
		}
		// 소요 시간 등록
		dp[root] = max;
	}

}