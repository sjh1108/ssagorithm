import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1766_손홍민 {
	
	// 기존 위상 정렬 문제에서 항목의 순위 보장이 추가된 형태
	// 번호가 낮은 문제일 수록 쉬운 문제이며, 쉬운 문제를 우선적으로 푸는 것을 기준으로 하고 있음
	// 따라서 우선순위 큐를 이용해 위상 정렬을 구현하면서도 쉬운 문제부터 접근하도록 설계

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		// 정점과 간선의 수
		int v = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());
		// 자신을 참조하는 간선의 개수 카운트 배열
		int[] prevCnt = new int[v];
		
		// 다음 경로를 모아두는 인접 리스트
		List<Integer>[] nextList = new ArrayList[v];
		for(int i=0; i<v; i++) {
			nextList[i] = new ArrayList<>();
		}
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int prev = Integer.parseInt(st.nextToken()) - 1;
			int next = Integer.parseInt(st.nextToken()) - 1;
			nextList[prev].add(next);
			prevCnt[next]++;
		}
		
		
		// 위상 정렬을 하면서도, 쉬운 문제 우선 접근을 위해 우선순위 큐를 사용
		// 오름차순(기본값)으로 설정하여 숫자가 낮은(즉, 쉬운) 문제부터 접근
		PriorityQueue<Integer> q = new PriorityQueue<>();
		for(int i=0; i<v; i++) {
			// 자신을 참조하는 간선이 없으면 시작점이 될 수 있음
			if(prevCnt[i] == 0) q.add(i);
		}
		
		while(!q.isEmpty()) {
			int cur = q.poll();
			// 접근한 문제 번호 출력
			sb.append(cur+1).append(" ");
			
			// 인접 리스트를 돌면서 각 정점의 prevCnt 디카운트
			// 디카운트 결과가 0이면 접근 가능함을 의미. 큐에 추가
			for(int next : nextList[cur]) {
				prevCnt[next]--;
				if(prevCnt[next] == 0) {
					q.add(next);
				}
			}
		}
		
		System.out.println(sb);
	}
	
}
