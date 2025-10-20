import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_23059 {
	
	// BOJ_23059 : 리그 오브 레게노(Gold_1)
	// 자료구조 및 알고리즘 : 위상 정렬, 해시맵
	
	static StringBuilder sb = new StringBuilder();
	
	// 정점 수. 수치가 입력으로 주어지지 않으므로, 새로운 아이템 이름이 등장할 때마다 카운트해야 함.
	static int v = 0;
	// 아이템 이름마다 ID를 매기는 해시맵
	static HashMap<String, Integer> nameToId = new HashMap<>();
	// 위의 해시맵을 기반으로 ID별 문자열을 저장하는 배열
	static String[] items;
	// 입력된 아이템 구매 순서를 저장하는 간선 배열
	static String[][] edges;
	// 각 아이템별 사전 구매해야 하는 아이템의 개수
	static int[] prev;
	// 인접 리스트
	static List<Integer>[] list;
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int e = Integer.parseInt(br.readLine());
		edges = new String[e][2];
		
		// 간선 정보(아이템 구매 순서) 저장
		// 아직 등록되지 않은 아이템이 주어진 경우 nameToId 해시맵에 등록하고 새 ID 부여
		for(int i=0; i<e; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			String item1 = st.nextToken();
			if(!nameToId.containsKey(item1)) nameToId.put(item1, v++);
			String item2 = st.nextToken();
			if(!nameToId.containsKey(item2)) nameToId.put(item2, v++);
			edges[i][0] = item1; edges[i][1] = item2;
		}
		
		
		// 배열의 각 인덱스에 문자열 저장
		items = new String[v];
		for(String key : nameToId.keySet()) {
			items[nameToId.get(key)] = key;
		}
		
		prev = new int[v];
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		// 입력받아둔 간선 정보를 가져와 인접 리스트에 적용 및 사전 구매 아이템 수 카운트
		for(int i=0; i<e; i++) {
			String[] edge = edges[i];
			int idx1 = nameToId.get(edge[0]);
			int idx2 = nameToId.get(edge[1]);
			
			list[idx1].add(idx2);
			prev[idx2]++;
		}
	}
	
	static boolean topologySort() {
		// 위상 정렬 종료 후 구매한 아이템의 개수
		int buyCnt = 0;
		// 각 인덱스에 해당하는 아이템 이름의 사전순으로 정렬하는 우선선위 큐
		PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> items[a].compareTo(items[b]));
		// 다음에 구매해야 할 아이템 인덱스를 준비시키는 대기 큐
		Queue<Integer> readyQ = new ArrayDeque<>();
		// 사전 구매해야 할 다른 아이템이 없는 아이템을 우선순위 큐에 추가
		for(int i=0; i<v; i++) {
			if(prev[i] == 0) {
				pq.add(i);
				buyCnt++;
			}
		}
		
		// 더 이상 구매 가능한 아이템이 없을 때까지
		while(!pq.isEmpty()) {
			// 일단 지금 당장 탐색된 모든 아이템을 구매
			while(!pq.isEmpty()) {
				int cur = pq.poll();
				sb.append(items[cur] + "\n");
				
				// 후속으로 구매해야 할 아이템에 대한 prev[i] 디카운트
				for(int next : list[cur]) {
					prev[next]--;
					if(prev[next] > 0) continue;
					
					// 사전 구매해야 할 아이템을 모두 구매했다면 대기 큐에 해당 아이템을 등록
					readyQ.add(next);
					buyCnt++;
				}
			}
			
			// 대기 큐에 등록된 모든 아이템을 우선순위 큐로 이동
			while(!readyQ.isEmpty()) {
				pq.add(readyQ.poll());
			}
		}
		
		// 구매한 아이템의 수와 전체 아이템의 수 v를 비교
		// 같지 않은 경우 사이클 발생으로 인해 모든 아이템을 구매하지 못한 것임
		return buyCnt == v;
	}
	
	public static void main(String[] args) throws Exception {
		init();
		
		// 위상 정렬 성공 -> 아이템 구매 순서 출력
		// 위상 정렬 실패 -> -1 출력
		System.out.println(topologySort() ? sb : -1);
	}

}
