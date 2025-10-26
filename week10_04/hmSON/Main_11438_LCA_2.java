import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_11438_LCA_2 {
	
	// BOJ_11438 : LCA 2(Platinum_5)
	// 자료구조 및 알고리즘 : 트리, LCA(최소 공통 조상) 이진 점프(2^k 조상 테이블)
	// 트리가 주어지고, 이후 두 개의 정점 번호가 주어지면 두 정점의 가장 가까운 공통 조상을 출력해야 한다.
	
	// 정점 수, 조상 테이블의 로그 크기
	static int v, log;
	// 각 정점의 깊이
	static int[] depth;
	// 각 정점별 2^k번쨰 부모 노드 번호
	static int[][] parent;
	// 양방향 인접 리스트
	static List<Integer>[] list;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		v = Integer.parseInt(br.readLine());
		
		// 깊이 배열 초기화 : 방문 전 -1, 루트(0)는 0
		depth = new int[v];
		Arrays.fill(depth, -1);
		depth[0] = 0;
		
		// 인접 리스트 생성
		list = new ArrayList[v];
		for(int i=0; i<v; i++) list[i] = new ArrayList<>();
		for(int i=0; i<v-1; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken()) - 1;
			int v2 = Integer.parseInt(st.nextToken()) - 1;
			list[v1].add(v2);
			list[v2].add(v1);
		}
		
		// log 계산: 2^log > v 가 되는 최소 log를 구함
		log = 1;
		while((1 << log) <= v) log++;
		
		// parent 테이블 초기화 : 기본값 -1
		parent = new int[log][v];
		for(int i=0; i<log; i++) Arrays.fill(parent[i], -1);
		
		// 1) BFS로 루트(0)에서부터 트리 레벨(깊이)와 1단계 부모(parent[0][u])를 채움
		Queue<Integer> q = new ArrayDeque<>();
		q.add(0);
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(int next : list[cur]) {
				// 이미 방문한 정점(깊이 >= 0)은 건너뜀
				if(depth[next] >= 0) continue;
				q.add(next);
				// 깊이 갱신
				depth[next] = depth[cur] + 1;
				// 1번째(2^0) 부모 기록
				parent[0][next] = cur;
			}
		}
		
		// 2) Binary Lifting 전처리
		// parent[k][u] = parent[k-1][parent[k-1][u]]
		for(int k=1; k<log; k++) {
			for(int u=0; u<v; u++) {
				// u의 2^(k-1)번째 조상
				// 없으면 -1 유지
				int mid = parent[k-1][u];
				parent[k][u] = (mid == -1) ? -1 : parent[k-1][mid];
			}
		}
		
		// 질의 처리
		int query = Integer.parseInt(br.readLine());
		StringBuilder sb = new StringBuilder();
		for(int i=0; i<query; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken()) - 1;
			int b = Integer.parseInt(st.nextToken()) - 1;
			
			// 3) 깊이 맞추기: 더 깊은 정점을 위로 올려 두 정점의 깊이를 같게 맞춤
			if(depth[a] < depth[b]) { int t = a; a = b; b = t; } // a가 더 깊은 노드를 가리키도록 스왑
			int diff = depth[a] - depth[b];
			// diff의 각 비트가 1인 k에 대해 a를 2^k만큼 위로 이동
			for(int k=log-1; k>=0; k--) {
				if(((diff >> k) & 1) == 1) a = parent[k][a];
			}
			
			// 깊이를 맞춘 뒤 동일 정점이면 그 정점이 LCA
			if(a == b) {
				sb.append((a+1)).append('\n');
				continue;
			}
			
			// 4) 동시에 점프: a, b의 부모가 같아지기 직전까지 위로 올림
			for(int k=log-1; k>=0; k--) {
				if(parent[k][a] != parent[k][b]) {
					a = parent[k][a];
					b = parent[k][b];
				}
			}
			
			// 5) a와 b의 바로 위(1단계 부모)가 공통 조상
			sb.append((parent[0][a]+1)).append('\n');
		}
		
		System.out.println(sb);
	}

}
