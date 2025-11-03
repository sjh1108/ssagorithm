import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2637 {
	
	// BOJ_2637 : 장난감 조립(Gold_2)
	// 자료구조 및 알고리즘 : DP, 위상 정렬
	
	// 정점 수
	static int v;
	// 각 부품을 요구하는 완제품 및 중간 부품의 개수, 완제품 하나를 만들기 위해 필요한 각 부품의 개수
	static int[] prev, cnts;
	// 단방향 인접 리스트(상위 부품 -> 하위 부품)
	static List<Node>[] list;
	// 더 이상 분해할 수 없는 기본 부품의 번호 리스트
	static List<Integer> base = new ArrayList<>();
	
	static class Node {
		// 각 부품의 번호와 개수
		int id, cnt;
		
		public Node(int id, int cnt) {
			this.id = id;
			this.cnt = cnt;
		}
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		v = Integer.parseInt(br.readLine());
		prev = new int[v];
		cnts = new int[v];
		list = new ArrayList[v];
		for(int i=0; i<v; i++) {
			list[i] = new ArrayList<>();
		}
		
		int e = Integer.parseInt(br.readLine());
		for(int i=0; i<e; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int id = Integer.parseInt(st.nextToken()) - 1;
			int subId = Integer.parseInt(st.nextToken()) - 1;
			int subCnt = Integer.parseInt(st.nextToken());
			
			// 조립은 분해의 역순
			// 상위 부품 x개를 만들기 위해 필요한 하위 부품 = x * y 개를  cnts 배열에 저장하는 방식을 사용할 것임
			// 따라서 상위 부품 -> 하위 부품으로 이어지는 단방향 인접 리스트 구성
			// 또한 각 하위 부품을 요구하는 상위 부품의 개수 기록 (v번째 부품, 즉 완제품은 반드시 0)
			list[id].add(new Node(subId, subCnt));
			prev[subId]++;
		}
	}
	
	static void topologySort() {
		Queue<Node> q = new ArrayDeque<>();
		
		// 시작 지점은 완제품 번호. 개수는 1
		q.add(new Node(v-1, 1));
		cnts[v-1] = 1;
		
		while(!q.isEmpty()) {
			Node cur = q.poll();
			
			// 하위 부품이 없으면 기본 부품이므로 기본 부품 리스트에 번호 저장
			if(list[cur.id].size() == 0) {
				base.add(cur.id);
				continue;
			}
			
			// 하위 부품 목록
			for(Node next : list[cur.id]) {
				// 요구하는 상위 부품 개수 디카운트
				// 해당 상위 부품 cur.cnt개를 만들기 위해 필요한 하위 부품의 개수 = cur.cnt * next.cnt개
				prev[next.id]--;
				cnts[next.id] += (cur.cnt * next.cnt);
				
				// 더 이상 요구하는 상위 부품이 없는 경우
				// 해당 하위 부품의 번호 및 필요한 총 부품 개수를 큐에 입력
				if(prev[next.id] > 0) continue;
				q.add(new Node(next.id, cnts[next.id]));
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		topologySort();
		
		StringBuilder sb = new StringBuilder();
		
		// 오름차순 출력
		// 각 기본 부품의 개수 출력
		Collections.sort(base);
		for(int id : base) {
			sb.append((id+1) + " " + cnts[id] + "\n");
		}
		System.out.println(sb);
	}

}
