import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_13023_ABCDE {
	
	/*
	 * BOJ_13023 : ABCDE(Gold_5)
	 * 자료구조 및 알고리즘 : 백트래킹
	 * 
	 * A-B, B-C, C-D, D-E 친구 관계를 가진 5명 A, B, C, D, E가 존재하는 지 구해야 한다.
	 * 친구 관계 정보가 주어지므로, 백트래킹을 이용해 중복 없이 깊이 5까지 들어갈 수 있는지 확인한다.
	 */

	// 정점(사람 수 )
	static int v;
	// 친구 관계가 존재하는지 확인하기 위한 플래그
	static boolean flag = false;
	// 양방향 인접 리스트(친구 관계)
	static List<Integer>[] list;
	// 방문 여부 확인
	static boolean[] visited;
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 정점과 간선 수
		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 양방향 인접 리스트 구현
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			list[v1].add(v2);
			list[v2].add(v1);
		}
	}
	
	static void backtrack(int id, int cnt) {
		// 조건에 부합하는 친구 관계가 있음이 증명되면 더 이상 탐색할 필요 없음
		if(flag) return;
		
		// 깊이 5까지 도달하면 A-B-C-D-E 친구 관계가 증명됨
		if(cnt == 5) {
			flag = true;
			return;
		}
		
		// 현재 방문한 친구와 인접한 다른 친구 확인
		for(int next : list[id]) {
			if(visited[next]) continue;
			visited[next] = true;
			backtrack(next, cnt+1);
			visited[next] = false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		for(int i=0; i<v && !flag; i++) {
			visited = new boolean[v];
			visited[i] = true;
			backtrack(i, 1);
		}
		
		// 친구 관계가 증명되면 1, 아니면 0 반환
		System.out.println(flag ? 1 : 0);
	}
	
}
