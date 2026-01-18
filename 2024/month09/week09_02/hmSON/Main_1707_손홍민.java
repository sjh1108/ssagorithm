import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1707 {
	
	// 정의 : 이분 그래프는 하나의 그래프를 선 하나로 그어 2분할했을 때 모든 정점이 독립 상태가 되는 그래프이다.
	// 해석 : 선을 긋는다 함은 다른 성질을 가진 두 정점 사이의 간선을 끊어내는 것이다.
	// 구상 : 이를 표현하기 위해 각 정점에 2가지 색 중 하나를 부여하고, 인접한 정점들의 색이 모두 다른 지 확인하는 방식을 사용한다.
	// 반례 : 사이클이 발생하더라도 이분 그래프의 규칙에 위배되지 않을 가능성이 존재한다.
	// 알고리즘 분류 : 따라서 해당 문제는 유니온 파인드를 사용할 수 없고, BFS를 통해 한 정점씩 색을 칠하면서 탐색해야 한다.
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 정점과 간선 수
	static int v, e;
	// 각 정점의 색을 나타내는 배열, w : 흰색(미방문), 'r' : 빨간색(방문), 'b' : 파란색(방문)
	static char[] color;
	// 인접 리스트
	static List<List<Integer>> list = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc --> 0) {
			st = new StringTokenizer(br.readLine());
			v = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			// 모든 정점의 초기 색을 흰색으로 표현. 미방문임을 표기하기 위함
			color = new char[v];
			Arrays.fill(color, 'w');
			for(int i=0; i<v; i++) {
				list.add(new ArrayList<>());
			}
			
			// 인접 리스트 작성
			for(int i=0; i<e; i++) {
				st = new StringTokenizer(br.readLine());
				int v1 = Integer.parseInt(st.nextToken())-1;
				int v2 = Integer.parseInt(st.nextToken())-1;
				
				list.get(v1).add(v2);
				list.get(v2).add(v1);
			}
			
			// 존재하는 모든 그래프를 탐색하고, 이분 그래프의 규칙에 위배되는 그래프가 존재하는지 확인
			// 이미 색이 칠해진 정점은 방문한 상태이므로 확인하지 않음
			// 만약 단 한 번이라도 이분 그래프가 될 수 없는 그래프가 존재하면 즉시 탐색 종료
			boolean flag = true;
			for(int i=0; i<v; i++) {
				if(color[i] != 'w') continue;
				flag = bfs(i);
				if(!flag) break;
			}
			
			sb.append(flag ? "YES" : "NO").append("\n");
			list.clear();
		}
		System.out.println(sb);
	}
	
	// BFS로 그래프 탐색
	// 현재 위치한 정점과 인접한 다른 정점에는 모두 현재 정점과 반대되는 색을 부여한다.
	// 1. 아직 흰색인 곳은 색을 칠한다.
	// 2. 인접한 정점에 이미 반대되는 색이 칠해진 곳은 무시하고 넘어간다.
	// 3. 인접한 정점에 같은 색이 칠해진 곳이 존재하면 규칙에 위배되므로 즉시 탐색을 종료한다.
	// 모든 그래프를 탐색하는 데 성공했다면 이분 그래프임을 증명한 것이다.
	static boolean bfs(int start) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(start);
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(int next : list.get(cur)) {
				// 이미 색이 칠해진 정점을 탐색하려는 경우
				// 같은 색이면 -> 즉시 탐색 종료, false 반환
				// 다른 색이면 -> 탐색 필요 없음
				if(color[next] != 'w') {
					if(color[next] == color[cur]) {
						return false;
					}
					continue;
				}
				// 자신과 반대되는 색을 칠한다
				if(color[cur] == 'r') {
					color[next] = 'b';
				} else {
					color[next] = 'r';
				}
				q.add(next);
			}
		}
		
		// 그래프의 모든 정점을 탐색했다면 이분 그래프인 것이다. true 반환
		return true;
	}
	
}
