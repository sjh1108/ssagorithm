package gold_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_4803 {
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringBuilder sb = new StringBuilder();
	static StringTokenizer st;
	
	static int v, e;
	static int[] arr;
	// 헤드 노드 목록을 기록할 해시셋. 세트 사이즈로 결과 출력
	static HashSet<Integer> set = new HashSet<>();
	
	public static void main(String[] args) throws Exception {
		int tc = 1;
		while(true) {
			init();
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
			
			// 트리의 개수 확인. 헤드 노드가 0이면 사이클이 발생한 그래프이므로 무시
			for(int i=1; i<=v; i++) {
				int head = find(i);
				if(head == 0) continue;
				set.add(head);
			}
			
			if(set.isEmpty()) {
				sb.append("No trees.\n");
			} else if(set.size() == 1) {
				sb.append("There is one tree.\n");
			} else {
				sb.append("A forest of " + set.size() + " trees.\n");
			}
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
			arr[rb] = 0;
			return;
		}
		
		// 위의 코드를 보조하기 위해 반드시 더 작은 수를 헤드 노드로 등록
		if(ra < rb) arr[rb] = ra;
		else arr[ra] = rb;
	}

}
