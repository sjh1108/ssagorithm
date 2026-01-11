import java.util.*;

class UserSolution_25430 {

	// Heap 구조에 사용할 배열의 최대 크기는 배열 범위 초과 문제를 발생시키지 않을 정도의 크기로 잡아야 함.
	static final int MAX_HEAP = 100000;
	// 건물 수, 다익스트라 진행 중 Heap의 현재 크기
	static int n, heapSize;
	// 각 건물별 커피점 & 제과점과의 거리, Heap의 id와 cost
	static int[] cDist, bDist, heapCost, heapId;
	// Heap의 출발점 타입(c: 커피점, b: 제과점)
	static char[] heapType;
	// 인접 리스트
	static List<Street>[] graph;

	// 인접 리스트의 각 도로별 목적지 id와 이동거리 관리
	static class Street{
		int id, cost;

		public Street(int id, int cost) {
			this.id = id;
			this.cost = cost;
		}
	}

	// 다익스트라에 사용할 Heap의 현재 정보 반환 클래스.
	// 각각 현재 위치의 건물 id, 지금까지 이동한 총 거리, 출발점의 타입
	static class Node {
		int id, cost;
		char type;
	}
	// 지속적인 객체 반환으로 인한 오버헤드 방지
	static Node node = new Node();

	// Min. Heap 데이터 입력 메서드
	static void insert(int id, int cost, char type) {
		if(heapSize >= MAX_HEAP - 1) return;

		int idx = ++heapSize;
		heapId[idx] = id;
		heapCost[idx] = cost;
		heapType[idx] = type;

		while(idx > 1) {
			int parentIdx = idx/2;
			if(heapCost[parentIdx] <= heapCost[idx]) break;

			swap(parentIdx, idx);
			idx = parentIdx;
		}
	}

	// Min. Heap 데이터 제거 메서드
	static boolean poll() {
		if(heapSize == 0) return false;

		node.id = heapId[1];
		node.cost = heapCost[1];
		node.type = heapType[1];

		heapId[1] = heapId[heapSize];
		heapCost[1] = heapCost[heapSize];
		heapType[1] = heapType[heapSize];
		heapSize--;

		int idx = 1;
		while(idx*2 <= heapSize) {
			int child = idx*2;
			if(child+1 <= heapSize && heapCost[child] > heapCost[child+1]) child++;
			if(heapCost[idx] <= heapCost[child]) break;

			swap(idx, child);
			idx = child;
		}
		return true;
	}

	// Min. Heap 위치 변경 메서드. 정렬 상태 유지를 위해 부모 노드와 선택된 자식 노드의 위치 변경
	static void swap(int p, int c) {
		int tempId = heapId[p];
		heapId[p] = heapId[c];
		heapId[c] = tempId;

		int tempCost = heapCost[p];
		heapCost[p] = heapCost[c];
		heapCost[c] = tempCost;

		char tempType = heapType[p];
		heapType[p] = heapType[c];
		heapType[c] = tempType;
	}

	public void init(int N, int K, int[] sBuilding, int[] eBuilding, int[] mDistance) {
		n = N;
		cDist = new int[N];
		bDist = new int[N];

		heapId = new int[MAX_HEAP];
		heapCost = new int[MAX_HEAP];
		heapType = new char[MAX_HEAP];
		graph = new ArrayList[N];

		for(int i=0; i<N; i++) {
			graph[i] = new ArrayList<>();
		}

		for(int i=0; i<K; i++) {
			add(sBuilding[i], eBuilding[i], mDistance[i]);
		}
	}

	public void add(int sBuilding, int eBuilding, int mDistance) {
		// 양방향 이동 가능한 도로
		graph[sBuilding].add(new Street(eBuilding, mDistance));
		graph[eBuilding].add(new Street(sBuilding, mDistance));
	}

	public int calculate(int M, int[] mCoffee, int P, int[] mBakery, int R) {
		// 반환 가능한 값은 최대 2 * R(커피점 & 제과점과의 거리가 모두 R인 경우)
		// 그보다 1 큰 값으로 초기화하여 변화가 없으면 -1을 반환하도록 함.
		int minDist = 2 * R + 1;

		// 각 건물별 커피점 & 제과점과의 거리 또한 R+1로 초기화하여 미확인 또는 이동 불가 상태임을 표현
		Arrays.fill(cDist, R+1);
		Arrays.fill(bDist, R+1);

		// Heap 크기는 항상 다익스트라 시작 전에 초기화
		heapSize = 0;

		// 커피점이 된 건물을 Heap에 추가
		for(int i=0; i<M; i++) {
			int idx = mCoffee[i];
			cDist[idx] = 0;

			insert(idx, 0, 'c');
		}

		// 제과점이 된 건물을 Heap에 추가
		for(int i=0; i<P; i++) {
			int idx = mBakery[i];
			bDist[idx] = 0;

			insert(idx, 0, 'b');
		}

		// 직접 구현한 Heap을 이용한 다익스트라
		// boolean 타입 메서드인 poll()에 의해 Heap이 빈 상태라면 false를 반환하여 즉시 루프 종료
		while(poll()) {
			// 필요한 현재 위치 정보는 Node 타입 인스턴스를 이용해 가져온다
			// 메서드로부터 객체를 반환받는 과정에서 발생하는 오버헤드 방지
			int cur = node.id;
			int cost = node.cost;
			char type = node.type;

			// 핵심. 현재 이동 거리가 기록된 커피점 & 제과점과의 거리 합계보다 크거나 같으면 더 확인할 필요 없음
			// 마찬가지로 현재 이동 거리가 R보다 크면 더 확인할 필요 없음
			if(cost >= minDist || cost > R) break;

			// 이미 더 짧은 거리로 방문한 곳은 패스
			if((type == 'c' && cost > cDist[cur]) || (type == 'b' && cost > bDist[cur])) continue;

			// 이동 가능한 다음 건물 목록
			List<Street> nextBuildings = graph[cur];
			for(int i=0; i<nextBuildings.size(); i++) {
				Street next = nextBuildings.get(i);

				// 다음 건물의 id와 그 건물까지 이동할 경우의 총 이동거리
				// R을 초과하거나, 현재 기록된 커피점 & 제과점과의 거리 합계 이상이면 무시
				int nextBuilding = next.id;
				int newCost = cost + next.cost;
				if(newCost > R || newCost >= minDist) continue;

				// 출발점 건물 타입이 무엇인지에 따라 minDist 계산을 다르게 해야 함.
				if(type == 'c') {
					// 이미 더 짧은 이동 경로로 도착한 주택이라면
					if(newCost >= cDist[nextBuilding]) continue;
					cDist[nextBuilding] = newCost;
					if(bDist[nextBuilding] > 0 && bDist[nextBuilding] <= R) {
						minDist = Math.min(minDist, newCost + bDist[nextBuilding]);
					}
					insert(nextBuilding, newCost, 'c');
				} else {
					if(newCost >= bDist[nextBuilding]) continue;
					bDist[nextBuilding] = newCost;
					if(cDist[nextBuilding] > 0 && cDist[nextBuilding] <= R) {
						minDist = Math.min(minDist, newCost + cDist[nextBuilding]);
					}
					insert(nextBuilding, newCost, 'b');
				}
			}
		}

		// 다익스트라 이후에도 여전히 minDist 값이 2 * R + 1인가? 조건에 부합하는 건물이 없음. -> -1 반환
		// minDist 값이 한 번이라도 갱신되었는가? 조건에 부합하는 건물이 하나라도 존재함 -> minDist 반환
		return minDist <= 2 * R ? minDist : -1;
	}

}