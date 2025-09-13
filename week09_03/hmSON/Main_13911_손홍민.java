import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_13911_손홍민 {
	
	// 알고리즘 분류 : 멀티 소스 다익스트라
	// 풀이 방식 :
	// 1. 무방향 그래프를 인접 리스트에 저장
	// 2. 맥도날드와 스타벅스인 건물 리스트를 각각의 셋에 저장
	// 3. 첫번째 다익스트라 : 맥도날드 -> 맥도날드 지점들을 시작점으로 하여 거리가 macLimit 이하인 건물들의 번호 및 이동 거리를 맵에 등록
	// 4. 두번째 다익스트라 : 스타벅스 -> 스타벅스 지점들을 시작점으로 하여 거리가 starLimit 이하인 건믈들을 탐색
	// 5. 두 조건에 모두 해당하는 건믈을 발견한 경우 건물로부터 양쪽 지점간의 거리 합을 구하고 최소값 갱신
	
	// 이번 B형 검정 3차시 문제는 집 구하기 문제를 그대로 가져가 입력값의 범위만 바꾼 채로 출제한 것으로 보임.
	
	// 정해 풀이가 아님!!!
	// 최단 경로를 구하는 것이 아닌, 일정 거리 이하인 모든 주택을 찾는 것이기 때문에 내 방식의 경우 Queue를 사용하면 자료구조의 차이로 시간이 많이 줄어듦
	// 이와 별개로 정해는 맥도날드와 스타벅스 지점 전체를 시작점으로 하는 다익스트라 한 번에 기저 조건을 강하게 걸어서 해결하는 것임.
	// 이 점이 내가 B형 불합격이라고 확신하는 이유
	
	// 정점과 간선의 개수, 맥도날드와의 거리 제한, 스타벅스와의 거리 제한
	static int v, e, macLimit, starLimit;
	// 맥도날드로부터 각 건물까지의 최소 거리, 스타벅스로부터 각 건물까지의 최소 거리
	static int[] macDist, starDist;
	// 인접 리스트
	static List<List<Node>> list = new ArrayList<>();
	// 맥도날드 지점 인덱스 리스트
	static HashSet<Integer> macList = new HashSet<>();
	// 스타벅스 지점 인덱스 리스트
	static HashSet<Integer> starList = new HashSet<>();
	
	// 건물의 인덱스와 이동 거리를 나타내는 노드 클래스
	static class Node {
		int id, cost;
		
		public Node(int id, int cost) {
			this.id = id;
			this.cost = cost;
		}
	}
	
	public static void main(String[] args) throws Exception {
		init();
		System.out.println(dijkstra());
	}
	
	static void init() throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		v = Integer.parseInt(st.nextToken());
		e = Integer.parseInt(st.nextToken());
		for(int i=0; i<v; i++) {
			list.add(new ArrayList<>());
		}
		macDist = new int[v];
		starDist = new int[v];
		
		for(int i=0; i<e; i++) {
			st = new StringTokenizer(br.readLine());
			int v1 = Integer.parseInt(st.nextToken())-1;
			int v2 = Integer.parseInt(st.nextToken())-1;
			int w = Integer.parseInt(st.nextToken());
			
			// 무방향 리스트
			list.get(v1).add(new Node(v2, w));
			list.get(v2).add(new Node(v1, w));
		}
		
		// 맥도날드로부터의 제한 거리 등록 및 최소 거리 배열 초기화
		st = new StringTokenizer(br.readLine());
		int macN = Integer.parseInt(st.nextToken());
		macLimit = Integer.parseInt(st.nextToken());
		for(int i=0; i<v; i++) {
			macDist[i] = macLimit+1;
		}
		
		// 맥도날드 지점 등록
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<macN; i++) {
			macList.add(Integer.parseInt(st.nextToken()) - 1);
		}
		
		// 스타벅스로부터의 제한 거리 등록 및 최소 거리 배열 초기화
		st = new StringTokenizer(br.readLine());
		int starN = Integer.parseInt(st.nextToken());
		starLimit = Integer.parseInt(st.nextToken());
		for(int i=0; i<v; i++) {
			starDist[i] = starLimit+1;
		}
		
		// 스타벅스 지점 등록
		st = new StringTokenizer(br.readLine());
		for(int i=0; i<starN; i++) {
			starList.add(Integer.parseInt(st.nextToken()) - 1);
		}
	}
	
	public static int dijkstra() {
		// 최소값 초기화 : 마지막까지 값이 동일하면 조건에 부합하는 건물이 없는 것.
		int min = macLimit + starLimit + 1;
		// 맥도날드로부터 제한 거리 이내에 있는 건물 번호 및 이동거리를 등록할 맵
		HashMap<Integer, Integer> nears = new HashMap<>();
		
		// 다익스트라 사용을 위한 우선순위 큐 생성(정렬 조건 : 현재까지의 이동 거리)
		PriorityQueue<Node> macQ = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
		PriorityQueue<Node> starQ = new PriorityQueue<>(Comparator.comparingInt(a -> a.cost));
		for(int m : macList) {
			macQ.add(new Node(m, 0));
			macDist[m] = 0;
		}
		for(int s : starList) {
			starQ.add(new Node(s, 0));
			starDist[s] = 0;
		}
		
		// 1. 맥도날드
		// 일정 거리 이내에 건물이 있다면 nears 맵에 건물 번호 및 총 이동 거리 등록
		while(!macQ.isEmpty()) {
			Node cur = macQ.poll();
			if(cur.cost > macDist[cur.id]) continue;
			for(Node next : list.get(cur.id)) {
				int newCost = macDist[cur.id] + next.cost;
				if(newCost > macLimit || newCost >= macDist[next.id]) continue;
				macDist[next.id] = newCost;
				if(!starList.contains(next.id)) nears.put(next.id, newCost);
				macQ.add(new Node(next.id, newCost));
			}
		}
		
		// 맥도날드 주변 건물이 없다면 탐색 종료.
		if(nears.isEmpty()) return -1;
		
		// 2. 스타벅스
		// 일정 거리 이내에 맥도날드와도 가까운 건물이 있다면 즉시 해당 건물로부터 양쪽 프랜차이즈까지 이동하는 최소 거리 연산 및 갱신
		while(!starQ.isEmpty()) {
			Node cur = starQ.poll();
			if(cur.cost > starDist[cur.id]) continue;
			if(nears.containsKey(cur.id)) {
				min = Math.min(min, cur.cost + macDist[cur.id]);
			}
			for(Node next : list.get(cur.id)) {
				int newCost = starDist[cur.id] + next.cost;
				if(newCost > starLimit || newCost >= starDist[next.id]) continue;
				starDist[next.id] = newCost;
				starQ.add(new Node(next.id, newCost));
			}
		}
		
		// 결국 min값이 변하지 않았다면 조건에 부합하는 건물이 없으므로 -1 반환
		if(min == macLimit + starLimit + 1) return -1;
		
		return min;
	}

}
