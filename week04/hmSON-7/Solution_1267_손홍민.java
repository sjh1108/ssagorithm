import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_1267_손홍민 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// v, e: 정점과 간선 수
	// list: 각 작업별 이어지는 다음 작업 리스트
	// prevCnt: 각 정점별 이전 작업 개수 카운트
	static int v, e;
	static List<List<Integer>> list = new ArrayList<>();
	static int[] prevCnt;
	
	public static void main(String[] args) throws Exception {
		int tc = 10;
		for(int i=1; i<=tc; i++) {
			init();
			sb.append("#").append(i).append(" ");
			topologicalSort();
			list.clear();
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		for(int i=0; i<v; i++) {
			list.add(new ArrayList<>());
		}
		prevCnt = new int[v];
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<e; i++) {
			int start = Integer.parseInt(st.nextToken())-1;
			int end = Integer.parseInt(st.nextToken())-1;
			list.get(start).add(end);
			prevCnt[end]++;
		}
	}
	
	// 위상 정렬
	static void topologicalSort() {
		Queue<Integer> q = new ArrayDeque<>();
		
		// 선수 작업이 없는 경우 먼저 처리해야 할 작업으로 분류 -> 큐에 삽입
		for(int i=0; i<v; i++) {
			if(prevCnt[i] == 0) {
				q.add(i);
			}
		}
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			// 큐에서 반환된 작업 -> 완료한 작업이므로 출력
			sb.append(cur+1).append(" ");
			for(int next : list.get(cur)) {
				// 선수 작업 하나가 완료되었으므로 디카운트
				// 만약 선수 작업 개수가 0이 되면 즉시 할 수 있는 작업임 -> 큐에 삽입
				prevCnt[next]--;
				if(prevCnt[next] == 0) {
					q.add(next);
				}
			}
		}
		sb.append("\n");
	}
	
}
