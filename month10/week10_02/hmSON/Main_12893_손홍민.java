import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_12893_손홍민 {
	
	// 정점과 간선 수(정점은 사람, 간선은 적대 관계)
	static int v, e;
	// 각 정점의 관계를 색으로 표현 (미방문 -> 흰색, 방문 -> 빨간색 혹은 파란색)
	static char[] colors;
	// 인접 리스트
	static List<Integer>[] list;
	
	public static void main(String[] args) throws Exception {
		init();
		// 결과  출력용 플래그. 한 번이라도 적의 적 관계가 무너지면 즉시 0으로 변경
		// 그래프가 여러 개로 분할되었을 가능성이 존재하므로 나눠진 만큼 각각의 그래프를 탐색하면서 적의 적 관계를 증명해야 함
		int flag = 1;
		for(int i=0; i<v; i++) {
			if(colors[i] == 'w') {
				int res = bfs(i);
				flag &= res;
			}
			if(flag == 0) break;
		}
		
		System.out.println(flag);
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		
		colors = new char[v];
		// 초기값은 'w' -> 흰색 ; 미방문을 의미
		Arrays.fill(colors, 'w');
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 양방향 그래프
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken())-1;
			int v2 = Integer.parseInt(st.nextToken())-1;
			list[v1].add(v2);
			list[v2].add(v1);
		}
	}
	
	static int bfs(int start) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(start);
		// 초기 정점의 색을 둘 중 하나의 색으로 아무거나 선택
		colors[start] = 'r';
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			
			for(int next : list[cur]) {
				char nextColor = colors[next];
				// 흰색인가? -> 미방문 : 현재 위치와 반대되는 색을 칠함
				if(nextColor == 'w') {
					q.add(next);
					colors[next] = colors[cur] == 'r' ? 'b' : 'r';
					continue;
				}
				// 흰색이 아닌가? -> 같은 색인지 확인
				// 같은 색인가? -> 적의 적 = 친구 관계를 증명할 수 없음. 즉시 0 반환
				// 다른 색인가? -> 적대관계
				if(nextColor == colors[cur]) return 0;
			}
		}
		
		// 그래프 내 모든 정점을 방문했다면 적의 적 관계를 증명할 수 있는 그래프임
		// 1 반환
		return 1;
	}

}
