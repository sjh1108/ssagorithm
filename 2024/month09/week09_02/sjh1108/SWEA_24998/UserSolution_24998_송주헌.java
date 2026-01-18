package week09_02.sjh1108.SWEA_24998;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

class UserSolution_24998_송주헌 {
    int idx;
    int size;
    int L;

    class BaseCamp implements Comparable<BaseCamp>{
        int id;
        int x, y;
        int mQuantity;
        int totalQuantity;

        public BaseCamp(int id, int X, int Y, int mQuantity) {
            this.id = id;
            this.x = X;
            this.y = Y;
            this.mQuantity = mQuantity;
            this.totalQuantity = mQuantity;
        }

        @Override
        public int compareTo(BaseCamp o){
            if(this.totalQuantity == o.totalQuantity){
                if(this.x == o.x){
                    return Integer.compare(this.y, o.y);
                }

                return Integer.compare(this.x, o.x);
            }

            return Integer.compare(this.totalQuantity, o.totalQuantity);
        }

        public int compareTwo(BaseCamp o) {
            if(this.mQuantity == o.mQuantity){
                if(this.x == o.x){
                    return Integer.compare(this.y, o.y);
                }

                return Integer.compare(this.x, o.x);
            }

            return Integer.compare(this.mQuantity, o.mQuantity);
        }

        @Override
        public int hashCode(){
            return Integer.hashCode(id);
        }

        @Override
        public boolean equals(Object o){
            if(o instanceof BaseCamp){
                return ((BaseCamp)o).id == this.id;
            }
            return false;
        }
    }

    HashMap<Integer, Integer> IDtoIDX;
    
    // key : idx / value : parentID
    int[] parent;
    BaseCamp[] camps;
    List<Integer>[][] bucket;

    // heap
    TreeSet<BaseCamp> rootCamps;

    // find
    private int find(int a) {
        if (parent[a] == a) {
            return a;
        }
        return parent[a] = find(parent[a]);
    }

    // union
    private void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        if (rootA != rootB) {
            BaseCamp repA = camps[rootA];
            BaseCamp repB = camps[rootB];

            // 기존 parents TreeSet에서 제거
            rootCamps.remove(repA);
            rootCamps.remove(repB);
            
            // 루트 선정
            if (repA.compareTwo(repB) < 0) {
                parent[rootB] = rootA;
                repA.totalQuantity += repB.totalQuantity;
                rootCamps.add(repA);
            } else {
                parent[rootA] = rootB;
                repB.totalQuantity += repA.totalQuantity;
                rootCamps.add(repB);
            }
        }
    }

    // get distance
    private int getDistance(BaseCamp o1, BaseCamp o2){
        return Math.abs(o1.x - o2.x) + Math.abs(o1.y - o2.y);
    }

    @SuppressWarnings("unchecked")
	void init(int L, int N){
        idx = 0;
        size = (N + L - 1) / L;
        this.L = L;

        parent = new int[20001];
        for(int i = 1; i < 20001; i++){
            parent[i] = i;
        }
        camps = new BaseCamp[20001];
        IDtoIDX = new HashMap<>();

        bucket = new List[size][size];
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                bucket[i][j] = new ArrayList<>();
            }
        }

        rootCamps = new TreeSet<>();
	}
	
	int addBaseCamp(int mID, int mRow, int mCol, int mQuantity){
        // BaseCamp 객체 생성
        int curIdx = idx++;
        BaseCamp tmp = new BaseCamp(mID, mRow, mCol, mQuantity);

        IDtoIDX.put(mID, curIdx);
        camps[curIdx] = tmp;
        rootCamps.add(tmp);

        // union - find
        int bucketX = mRow / L;
        int bucketY = mCol / L;

        // find camp from closed buckets
        for(int i = Math.max(0, bucketX - 1); i <= Math.min(size - 1, bucketX + 1); i++){
            for(int j = Math.max(0, bucketY - 1); j <= Math.min(size - 1, bucketY + 1); j++){
                for(int neighbor: bucket[i][j]){
                    if(getDistance(camps[neighbor], tmp) <= L){
                        union(neighbor, curIdx);
                    }
                }
            }
        }

        // 버킷에 추가
        bucket[bucketX][bucketY].add(curIdx);

        // 최종 그룹의 총 광물량 반환
        int rootIdx = find(curIdx);
		return camps[rootIdx].totalQuantity;
	}
	
	int findBaseCampForDropping(int K) {
        BaseCamp bestCamp = null;

        for (BaseCamp leader : rootCamps) {
            if (leader.totalQuantity >= K) {
                if (bestCamp == null || leader.compareTwo(bestCamp) < 0) {
                    bestCamp = leader;
                }
            }
        }

        return bestCamp == null ? -1 : bestCamp.id;
    }
}
