package Pro_24998;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class UserSolution_24998 {

	static int l, n;
	
	static HashMap<Integer, BaseCamp> basecampList;
	static TreeMap<BaseCamp, Integer> groupHeads;
	static List<BaseCamp>[][] map;
	
	static class BaseCamp {
		// 베이스 캠프 ID, 좌표, 채굴량, 그룹 대표자 번호
		// 버킷 배열 사용할 예정이기 때문에 좌표도 들고 있어야 함
		// 유니온 파인드를 위해 그룹 대표자 ID도 같이 들고 있어야 함
		int id, y, x, quantity, head;
		
		public BaseCamp(int id, int y, int x, int quantity) {
			this.id = id;
			this.y = y;
			this.x = x;
			this.quantity = quantity;
			head = id;
		}
	}

	void init(int L, int N){
		// 변수 및 자료구조 초기화
		l = L; n = N;
		basecampList = new HashMap<>();
		groupHeads = new TreeMap<>((a, b) -> a.quantity != b.quantity ?
				Integer.compare(a.quantity, b.quantity) : a.y != b.y ?
						Integer.compare(a.y, b.y) : Integer.compare(a.x, b.x)
		);
		
		// 배열의 크기는 섬의 한 변의 길이 / 에너지 크기
		// 버킷 배열에 각 구역마다 베이스캠프를 리스트로 설치
		map = new ArrayList[n/l+1][n/l+1];
		for(int i=0; i<=n/l; i++) {
			for(int j=0; j<=n/l; j++) {
				map[i][j] = new ArrayList<>();
			}
		}
	}
	
	int addBaseCamp(int mID, int mRow, int mCol, int mQuantity){
		// 베이스캠프 객체 생성 후 인접한 9개 구역 탐색
		// L 거리 내부에 있는 베이스캠프는 유니온
		// 이 과정에서 단일 채굴량이 가장 적고, 행열 값이 제일 작은 베이스캠프가 대표자가 되어야 함.
		// 반환값은 결국 소속 그룹의 총 채집량이 됨
		BaseCamp bc = new BaseCamp(mID, mRow, mCol, mQuantity);
		basecampList.put(mID, bc);
		int sectorY = mRow/l, sectorX = mCol/l;
		map[sectorY][sectorX].add(bc);

		// 현재 베이스캠프는 하나의 별개 그룹이므로 그룹 대표자로 등록
		groupHeads.put(bc, bc.quantity);

		// 연결 여부를 확인하기 위한 플래그
		// false: 주변에 거리 L 이하인 다른 베이스캠프가 없음, true : 주변의 다른 베이스캠프와 그룹 연결 성공
		boolean flag = false;
		for(int i=sectorY-1; i<=sectorY+1; i++) {
			for(int j=sectorX-1; j<=sectorX+1; j++) {
				if(i < 0 || j < 0 || i > n/l || j > n/l) continue;
				List<BaseCamp> campList = map[i][j];
				for(BaseCamp near : campList) {
					int dist = Math.abs(near.y - bc.y) + Math.abs(near.x - bc.x);
					if(dist > l) continue;
					// 주변에 다른 베이스캠프가 존재하면 그룹 연합
					union(near, bc);
					flag = true;
				}
			}
		}

		// 사실 플래그 여부 상관없이 현재 베이스캠프의 그룹 대표 베이스캠프 ID 받아서 총 채굴량 반환해도 됨
		// 그러나 메서드와 컬렉션 방문시의 오버헤드를 고려해 명시적으로 코드 작성
		if(!flag) {
			return bc.quantity;
		} else {
			int headId = find(bc);
			return groupHeads.get(basecampList.get(headId));
		}
	}
	
	int findBaseCampForDropping(int K) {
		// 각 베이스캠프 그룹별 총 채굴량이 K 이상이면서 단일 채굴량이 가장 적은 베이스캠프를 선택하여 반환
		// 단일 채굴량이 가장 적은 베이스캠프가 여러 개인 경우 행이 작은, 행도 동일하다면 열이 작은 베이스 캠프를 선택
		// 이미 트리셋을 이용해 정렬하고 있으므로 가장 먼저 접근한 그룹 대표 베이스캠프의 번호를 반환하면 됨
		for(BaseCamp head : groupHeads.keySet()) {
			if(groupHeads.get(head) < K) continue;
			return head.id;
		}

		// 만약 조건에 맞는 베이스캠스가 없으면 -1 반환
		return -1;
	}
	
	int find(BaseCamp bc) {
		if(bc.head == bc.id) return bc.id;
		return bc.head = find(basecampList.get(bc.head));
	}
	
	void union(BaseCamp bc1, BaseCamp bc2) {
		int h1 = find(bc1);
		int h2 = find(bc2);
		if(h1 == h2) return;
		
		// 다음 규칙에 따라 대표자가 정해져야 함
		// 1. 단일 채굴량이 제일 작은 베이스캠프
		// 2. 위의 조건이 동일한 경우 행 번호가 더 작은 베이스캠프
		// 3. 위의 조건도 동일한 경우 열 번호가 더 작은 베이스캠프
		BaseCamp headBC1 = basecampList.get(h1);
		BaseCamp headBC2 = basecampList.get(h2);
		boolean flag = false;
		if(headBC1.quantity != headBC2.quantity) {
			if(headBC2.quantity < headBC1.quantity) flag = true;
		} else if(headBC1.y != headBC2.y) {
			if(headBC2.y < headBC1.y) flag = true;
		} else {
			if(headBC2.x < headBC1.x) flag = true;
		}
		
		if(flag) {
			headBC1.head = headBC2.id;
			int cnt = groupHeads.get(headBC1);
			groupHeads.put(headBC2, cnt + groupHeads.get(headBC2));
			groupHeads.remove(headBC1);
		}
		else {
			headBC2.head = headBC1.id;
			groupHeads.put(headBC1, groupHeads.get(headBC1) + groupHeads.get(headBC2));
		}
	}

}
