package Rank5.gold_4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_1647 {

	// 두 개의 분리된 마을을 요구한다. 즉, 정확히 두 개의 분리 집합을 요구한다.
	// 따라서 최소 신장 트리를 구현하되, n-2개의 간선만 선택해서 분리 집합이 정확히 2개만 남게 만드는 것이 목적이다.
	// 정점이 하나라도 존재하면 이것 또한 하나의 마을로 취급한다.
	// 만약 정점이 2개밖에 없다면? n-2 = 0, 간선을 추가하지 않아도 이미 2개의 분리된 마을이므로 0을 출력하면 된다.
	// 종합해봤을 때, 기존의 최소 신장 트리 코드에서 종료 조건만 n-1에서 n-2로 바꾸고 n = 2인 경우의 처리만 추가하면 된다.
	
	static int v, e;
	static int[] arr;
	// Edge 객체 배열 만들고 정렬하는 것 외에도 우선순위 큐에 집어넣고 하나씩 빼는 방법도 존재함
	// 오버헤드고 뭐고 정렬 방식의 차이 때문에 우선순위 큐가 더 빠른 실행 속도를 내는 듯함
	static PriorityQueue<Edge> q = new PriorityQueue<>(Comparator.comparingInt(e -> e.cost));

	// 간선 클래스. 간선을 구성하는 두 정점과 가중치 관리리
	static class Edge {
		int v1, v2, cost;

		public Edge(int v1, int v2, int cost) {
			this.v1 = v1;
			this.v2 = v2;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(makeTree());
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		arr = new int[v+1];
		for(int i=1; i<=v; i++) {
			arr[i] = i;
		}
		
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken());
			int v2 = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			q.add(new Edge(v1, v2, cost));
		}
	}
	
	static int makeTree() {
		// 집이 2개밖에 없으면 간선을 설치할 필요가 없음. 0 리턴
		if(v == 2) return 0;
		int totalCost = 0, cnt = 0;
		// 우선순위 큐에서 간선을 하나씩 받아서 설치
		while(!q.isEmpty()) {
			Edge e = q.poll();
			// 사이클 방지
			// union 메서드 자체를 boolean 타입으로 바꾸고, 사이클 발생시 false를 반환하는 방식이 더 효율적임.
			// 이 방식은 결국 사이클이 발생하지 않았다고 가정할 때 find 메서드를 두 번 더 호출하게 되는 거니까.
			if(find(e.v1) == find(e.v2)) continue;
			totalCost += e.cost;
			cnt++;
			// 간선 설치
			union(e.v1, e.v2);

			// 간선이 v-2개 설치되었을 때, 즉 2개의 마을만 존재할 때 종료, 간선 비용 합계 반환
			if(cnt == v-2) return totalCost;
		}

		// 가능성은 일절 없으나, 이는 간선 자체가 부족할 때를 대비한 것임.
		return -1;
	}
	
	static int find(int x) {
		if(arr[x] == x) return x;
		return arr[x] = find(arr[x]);
	}
	
	static void union(int a, int b) {
		int ra = find(a);
		int rb = find(b);
		// 사이클 발생시 리턴
		if(ra == rb) return;
		// 값이 더 작은 쪽으로 헤드 몰아주기
		if(ra > rb) arr[ra] = rb;
		else arr[rb] = ra;
	}

}
