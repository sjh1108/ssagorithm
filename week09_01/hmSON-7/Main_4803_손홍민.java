package gold_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_4803 {

	// 주어진 간선들을 모두 설치했을 때 총 몇 개의 집합이 만들어지는 지 물어보는 문제.
	// 그러나 사이클이 발생한 트리는 집합으로 취급하지 않는 것이 핵심이다.
	// 유니온 파인드를 이용해 각각의 분리 집합을 결합한 뒤, 각 분리 집합의 헤드 개수로 집합 수를 판단해야 한다.
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;

	// 정점과 간선의 개수
	static int v, e;
	// 각 정점의 대표자 기록
	static int[] arr;
	// 헤드 노드 목록을 기록할 해시셋. 세트 사이즈로 결과 출력
	static HashSet<Integer> set = new HashSet<>();
	
	public static void main(String[] args) throws Exception {
		int tc = 1;
		while(true) {
			init();
			// 0이 입력되면 루프를 종료하고 결과 출력
			if(v == 0) break;
			sb.append("Case " + tc + ": ");
			tc++;
			
			for(int i=0; i<e; i++) {
				st = new StringTokenizer(br.readLine());
				int v1 = Integer.parseInt(st.nextToken());
				int v2 = Integer.parseInt(st.nextToken());
				// 입력이 들어올 때마다 두 노드를 병합
				union(v1, v2);
			}
			
			// 각 정점의 헤드를 확인하고 이를 세트에 집어넣는다.
			// 단, 사이클이 발생한 집합의 경우 헤드를 아무 관련 없는 0으로 등록하여 사이클이 발생했음을 표현하였다.
			// find(x)의 결과가 0이라면 해당 정점은 사이클이 발생한 집합 안에 있다는 것.
			for(int i=1; i<=v; i++) {
				int head = find(i);
				if(head == 0) continue;
				set.add(head);
			}

			// 집합의 개수에 따라 출력이 달라진다.
			// 집합이 없는(전부 사이클인) 경우, 1개인 경우, 2개 이상인 경우우
			if(set.isEmpty()) {
				sb.append("No trees.\n");
			} else if(set.size() == 1) {
				sb.append("There is one tree.\n");
			} else {
				sb.append("A forest of " + set.size() + " trees.\n");
			}

			// 충돌 방지를 위해 매번 세트 정리
			set.clear();
		}
		
		System.out.println(sb);
	}
	
	static void init() throws Exception {
		st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		arr = new int[v+1];
		for(int i=1; i<=v; i++) {
			arr[i] = i;
		}
	}
	
	static int find(int x) {
		if(arr[x] == x) return x;
		return arr[x] = find(arr[x]);
	}
	
	static void union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		// 유니온 메서드를 일부 변경
		// 두 노드의 헤드가 동일 -> 사이클이므로 일부러 헤드 노드를 0으로 찍어서 사이클임을 표기
		if(ra == rb) {
			arr[ra] = 0;
			return;
		}
		
		// 위의 코드를 보조하기 위해 반드시 더 작은 수를 헤드 노드로 등록
		// 이렇게 하면 새로운 노드가 사이클이 걸린 노드에 합류할 때 0을 헤드로 찍게 됨
		if(ra < rb) arr[rb] = ra;
		else arr[ra] = rb;
	}

}
