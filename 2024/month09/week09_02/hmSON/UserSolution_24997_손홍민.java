import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class UserSolution_24997 {
	int n;
	Taxi[] taxiList;
	List<Taxi>[][] map;
	
	TreeSet<Taxi> distLog;
	
	static class Taxi {
		int id, y, x, move, moveWithGuest;
		
		public Taxi(int id, int y, int x) {
			this.id = id;
			this.y = y;
			this.x = x;
			move = 0;
			moveWithGuest = 0;
		}
		
		public void resetDist() {
			move = 0;
			moveWithGuest = 0;
		}
	}
	
	public void init(int N, int M, int L, int[] mXs, int[] mYs)
	{
		n = N;
		taxiList = new Taxi[M+1];
		map = new ArrayList[10][10];
		for(int i=0; i<10; i++) {
			for(int j=0; j<10; j++) {
				map[i][j] = new ArrayList<>();
			}
		}
		distLog = new TreeSet<>((a, b) -> a.moveWithGuest != b.moveWithGuest ? 
				Integer.compare(b.moveWithGuest, a.moveWithGuest) : Integer.compare(a.id, b.id)
		);
		
		for(int i=0; i<M; i++) {
			Taxi taxi = new Taxi(i+1, mYs[i], mXs[i]);
			taxiList[i+1] = taxi;
			map[mYs[i]/L][mXs[i]/L].add(taxi);
		}
		
		return;
	}

	public int pickup(int mSX, int mSY, int mEX, int mEY)
	{
		// 주변 9개 구역의 택시 존재 여부 확인
		// 가장 가까운, 그 중 번호가 가장 빠른 택시만 호출
		// 출발지로 이동시 move 증가
		// 출발지에서 목적지로 이동시 move, moveWithGuest 증가. 거리 계산 필요
		
		// 주변 택시가 없으면 -1 반환
		
		// 추가로 dist에 손님을 태우고 이동한 택시의 이동 기록을 남길 것
		int minDist = n/10 + 1, minId = taxiList.length + 1, ty = -1, tx = -1;
		int curY = mSY/(n/10), curX = mSX/(n/10);
		for(int i=curY-1; i<=curY+1; i++) {
			for(int j=curX-1; j<=curX+1; j++) {
				if (i < 0 || i >= 10 || j < 0 || j >= 10) {
		            continue;
		        }
				
				for(Taxi t : map[i][j]) {
					int dist = Math.abs(t.y - mSY) + Math.abs(t.x - mSX);
					if(dist > minDist) continue;
					if(dist == minDist) {
						if(t.id < minId) {
							minId = t.id; ty = i; tx = j;
						}
					} else {
						minDist = dist;
						minId = t.id;
						ty = i; tx = j;
					}
				}
			}
		}
		// 주변에 택시 없음
		if(minDist == n/10 + 1) return -1;
		
		// 이동 및 거리 기록
		Taxi nearest = taxiList[minId];
		int curDist = Math.abs(mEY - mSY) + Math.abs(mEX - mSX);
		map[ty][tx].remove(nearest);
		if(distLog.contains(nearest)) distLog.remove(nearest);
		
		nearest.y = mEY;
		nearest.x = mEX;
		nearest.move += minDist + curDist;
		nearest.moveWithGuest += curDist;
		distLog.add(nearest);
		map[mEY/(n/10)][mEX/(n/10)].add(nearest);
		
		return nearest.id;
	}

	public Solution_24997.Result reset(int mNo)
	{
		// 지정된 택시의 결과를 res에 담은 뒤
		// 택시의 이동 거리를 전부 초기화
		// dist에서 해당 택시 제거
		
		Solution_24997.Result res = new Solution_24997.Result();
		
		Taxi target = taxiList[mNo];
		res.mX = target.x;
		res.mY = target.y;
		res.mMoveDistance = target.move;
		res.mRideDistance = target.moveWithGuest;
		
		// 이거 문장 순서 바꿔줬다고 해결됨. 왜???????????
		distLog.remove(target);
		target.resetDist();

		return res;
	}

	public void getBest(int[] mNos)
	{
		// 이동 로그가 남아있는 택시들 중 이동한 총 거리가 가장 큰 순서대로 상위 5대의 택시를 보여준다.
		// 저장 위치는 매개변수의 mNos
		// 만약 이동 로그가 남은 택시가 5대 미만이라면 남은 칸은 1부터 채우기
		
		List<Integer> bestList = new ArrayList<>();
		for(Taxi t : distLog) {
			bestList.add(t.id);
			
			if(bestList.size() == 5) break;
		}
		
		Set<Integer> alreadyIn = new HashSet<>();
		for(int id : bestList) {
		    alreadyIn.add(id);
		}
		
		int taxiId = 1;
		while(bestList.size() < 5) {
		    if(!alreadyIn.contains(taxiId)) {
		        bestList.add(taxiId);
		    }
		    taxiId++;
		}
		
		for(int i=0; i<5; i++) {
			mNos[i] = bestList.get(i);
		}
		
		return;
	}
}
