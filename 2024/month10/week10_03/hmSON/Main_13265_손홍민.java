import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_13265 {
	
	// BOJ_13265 : 색칠하기(Gold_5)
	// 사용한 알고리즘 : BFS, 이분 그래프
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 정점 수
	static int v;
	// 각 정점의 색(미방문 -> 흰색, 방문 -> 빨간색 또는 파란색)
	static char[] colors;
	// 인접 리스트
	static List<Integer>[] list;
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			// 분리된 그래프일 가능성이 존재할 수 있음
			// 단 한 번이라도 2가지 색상으로 색칠할 수 없는 그래프라면 false가 됨
			boolean flag = true;
			for(int i=0; i<v; i++) {
				if(colors[i] != 'w') continue;
				flag = bfs(i);
				if(!flag) break;
			}
			
			sb.append(flag ? "possible" : "impossible").append("\n");
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		// 정점과 간선
		v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		
		// 각 정점의 색상 저장 배열 -> 초기값은 흰색
		colors = new char[v];
		Arrays.fill(colors, 'w');
		
		// 인접 리스트. 양방향으로 등록
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken())-1;
			int v2 = Integer.parseInt(st.nextToken())-1;
			
			list[v1].add(v2);
			list[v2].add(v1);
		}
	}
	
	static boolean bfs(int start) {
		Queue<Integer> q = new ArrayDeque<>();
		
		// 초기값은 항상 빨간색으로 고정
		q.add(start);
		colors[start] = 'r';
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			char curColor = colors[cur];
			
			for(int next : list[cur]) {
				char nextColor = colors[next];
				
				// 다음 정점이 흰색인가? -> 미방문
				// 현재 정점과 반대되는 색을 칠함
				if(nextColor == 'w') {
					q.add(next);
					colors[next] = curColor == 'r' ? 'b' : 'r';
					continue;
				}
				
				// 다음 정점이 현재 정점과 동일한 색을 가졌는가? -> 2가지 색상만으로 모든 정점을 색칠할 수 없음
				// 즉시 false 반환
				if(nextColor == curColor) return false;
			}
		}
		
		// 모든 정점을 문제 없이 순회했다면 true 반환
		return true;
	}

}
