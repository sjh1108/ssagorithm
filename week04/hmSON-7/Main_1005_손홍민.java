import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_1005_손홍민 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	// 건물 수, 빌드 수, 목표 건물, 전체 소모한 시간
	static int n, k, target;
	// 각 건물의 건설 시간, 각 건물별 선행 빌드 수, 각 건물을 건설 가능하게 하는 최소 시간
	static int[] cost, prevCnt, dist;
	static List<List<Integer>> list = new ArrayList<>();
	
	public static void main(String[] args) throws Exception {
		int tc = Integer.parseInt(br.readLine());
		while(tc-- > 0) {
			init();
			topologicalSort();
			// 출력값 : 건물 건설이 가능한 시간 + 목표 건물 건설 시간
			sb.append(dist[target] + cost[target]).append("\n");
			// 충돌 방지를 위해 매 케이스마다 리스트 초기화
			list.clear();
		}
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		// 건물 수 및 빌드 수 입력
		n = Integer.parseInt(st.nextToken());
		k = Integer.parseInt(st.nextToken());
		for(int i=0; i<n; i++) {
			list.add(new ArrayList<>());
		}
		cost = new int[n];
		prevCnt = new int[n];
		dist = new int[n];
		
		// 각 건물별 건설 시간, 건설 순서, 목표 건물 번호 입력
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<n; i++) {
			cost[i] = Integer.parseInt(st.nextToken());
		}
		
		for(int i=0; i<k; i++) {
			st = new StringTokenizer(br.readLine());
			int before = Integer.parseInt(st.nextToken())-1;
			int after = Integer.parseInt(st.nextToken())-1;
			list.get(before).add(after);
			prevCnt[after]++;
		}
		
		target = Integer.parseInt(br.readLine())-1;
	}
	
	static void topologicalSort() {
		Queue<Integer> q = new ArrayDeque<>();
		// 선행 건설 조건이 없는 건물들부터 건설
		for(int i=0; i<n; i++) {
			if(prevCnt[i] == 0) q.add(i);
		}
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			for(int next : list.get(cur)) {
				// 선행 조건 완료 디카운트
				// 추가로 같은 레벨의 건물을 동시에 건설 시작했다고 가정하고, 가장 오래 걸린 케이스를 등록해둬야 함
				prevCnt[next]--;
				dist[next] = Math.max(dist[next], dist[cur] + cost[cur]);
				
				// 만약 선제 조건을 모두 완료한 경우
				// 목표 건물 건설이 가능하면 즉시 정렬 종료
				// 아니라면 큐에 다음 건물 삽입
				if(prevCnt[next] == 0) {
					if(next == target) return;
					q.add(next);
				}
			}
		}
	}

}
