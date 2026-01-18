import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1707_이분_그래프 {
	
	/*
	 * BOJ_1707 : 이분 그래프(Gold_4)
	 * 자료구조 및 알고리즘 : BFS, 이분 그래프
	 * 
	 * 그래프의 정점을 두 개의 집합으로 분할하였을 때, 각 집합에 속한 정점끼리는 서로 인접하지 않도록 분할해야 한다.
	 * 우리가 기존에 알고 있던 그래프 탐색을 진행하되, 각 정점마다 인접한 정점임을 알 수 있도록 표현할 무언가가 있어야 한다.
	 * 
	 * 이 코드에서는 정점의 색으로 인접 관계를 표현한다. 임의의 정점 하나가 빨간색일 때, 인접한 모든 정점은 모두 파란색으로 표기한다.
	 * 단, 모순이 생기는 경우가 존재한다(ex: 삼각형 사이클 발생). 색을 통해 모순의 발생 여부도 함께 판단한다.
	 */
	
	// IO Handler
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 정점과 간선의 수
	static int v, e;
	// 각 정점의 색(미방문 : 흰색, 방문 : 빨간색 또는 파란색)
	static char[] color;
	// 양방향 인접 리스트
	static List<Integer>[] list;
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc --> 0) {
			st = new StringTokenizer(br.readLine());
			v = Integer.parseInt(st.nextToken());
			e = Integer.parseInt(st.nextToken());
			color = new char[v];
			
			// 모든 정점은 초기에 흰색(미방문)임
			Arrays.fill(color, 'w');
			list = new ArrayList[v];
			for(int i=0; i<v; i++) {
				list[i] = new ArrayList<>();
			}
			
			// 양방향 인접 리스트 생성
			for(int i=0; i<e; i++) {
				st = new StringTokenizer(br.readLine());
				int v1 = Integer.parseInt(st.nextToken())-1;
				int v2 = Integer.parseInt(st.nextToken())-1;
				
				list[v1].add(v2);
				list[v2].add(v1);
			}
			
			/* 각 테스트케이스마다 이분 그래프인지 판단.
			 그래프의 모든 정점이 하나의 그래프를 구성하고 있지 않을 수도 있다.
			 따라서 미방문한 정점이 발견되면 그 정점으로부터 BFS를 시작한다.
			 여러 그래프 중 단 하나라도, 모순이  발생한다면 NO를 출력해야 한다.*/
			boolean flag = true;
			for(int i=0; i<v; i++) {
				if(color[i] != 'w') continue;
				flag = bfs(i);
				if(!flag) break;
			}
			
			sb.append(flag ? "YES" : "NO").append("\n");
		}
		
		System.out.println(sb);
	}
	
	static boolean bfs(int start) {
		Queue<Integer> q = new ArrayDeque<>();
		q.add(start);
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(int next : list[cur]) {
				// 흰색 -> 미방문.
				// 흰색이 아닌 정점은 이미 방문한 곳이다.
				// 그런데 현재 정점과 색이 같다면 이는 인접한 정점이 같은 집합에 속한 것이므로 모순임을 증명한다.
				// 따라서 false를 반환한다.
				if(color[next] != 'w') {
					if(color[next] == color[cur]) {
						return false;
					}
					continue;
				}
				
				// 미방문 상태인 정점은 현재 정점과 반대되는 색을 칠한다.
				color[next] = color[cur] == 'r' ? 'b' : 'r';
				q.add(next);
			}
		}
		
		// 정상적으로 BFS가 수행되었다면 true를 반환한다.
		return true;
	}
	
}
