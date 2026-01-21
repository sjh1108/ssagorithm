package week09_02.sjh1108.SWEA_24997;

import java.util.*;

class UserSolution_24997_송주헌
{
    // N: 섬의 한 변 길이, M: 택시의 수, L: 택시 호출 가능 최대 거리
    int N, M, L;

    /**
     * 택시의 정보를 저장하는 클래스.
     * TreeSet에서 정렬되기 위해 Comparable 인터페이스를 구현.
     */
    static class Taxi implements Comparable<Taxi>{
        int id; // 택시 고유 ID
        int x, y; // 현재 택시 위치 (행, 열)
        int mMoveDistance; // 총 이동 거리 (승객 탑승 전 + 후)
        int mRideDistance; // 승객 탑승 후 이동 거리

        public Taxi(int id, int x, int y, int mMoveDistance, int mRideDistance) {
            this.id = id;
            this.x = x;
            this.y = y;
            this.mMoveDistance = mMoveDistance;
            this.mRideDistance = mRideDistance;
        }

        /**
         * TreeSet 정렬을 위한 비교 로직.
         * 1. mRideDistance (승객 탑승 후 이동 거리)가 긴 순서 (내림차순)
         * 2. mRideDistance가 같다면 id가 작은 순서 (오름차순)
         */
        @Override
        public int compareTo(Taxi o) {
            // 승객 탑승 후 이동 거리가 같다면
            if(o.mRideDistance == this.mRideDistance){
                // ID가 작은 순서대로 정렬
                return Integer.compare(this.id, o.id);
            }
            // 승객 탑승 후 이동 거리가 긴 순서대로 정렬
            return Integer.compare(o.mRideDistance, this.mRideDistance);
        }
    }

    // 택시 ID를 키로 하여 Taxi 객체를 빠르게 찾기 위한 HashMap
    HashMap<Integer, Taxi> taxiMap;
    // 공간을 분할하여 특정 구역의 택시를 빠르게 찾기 위한 2차원 리스트 (버킷)
    List<Taxi>[][] bucket;
    // 택시를 mRideDistance 기준으로 항상 정렬된 상태로 유지하는 TreeSet (우선순위 큐 역할)
    TreeSet<Taxi> heap;

    /**
     * 두 지점 간의 맨해튼 거리를 계산하는 메소드
     * @param sX 시작점 행
     * @param sY 시작점 열
     * @param eX 도착점 행
     * @param eY 도착점 열
     * @return 맨해튼 거리
     */
    private int getDistance(int sX, int sY, int eX, int eY){
        return Math.abs(sX - eX) + Math.abs(sY - eY);
    }

    /**
     * 테스트 케이스 시작 시 호출되어 변수들을 초기화하는 메소드.
     */
    @SuppressWarnings("unchecked")
	public void init(int N, int M, int L, int[] mXs, int[] mYs)
	{
        this.N = N;
        this.M = M;
        this.L = L;
        
        // 공간을 10x10 버킷으로 분할하여 초기화
        bucket = new List[10][10];
        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                bucket[i][j] = new ArrayList<>();
            }
        }

        // 자료구조 초기화
        taxiMap = new HashMap<>();
        heap = new TreeSet<>();

        // 초기 M개의 택시 정보를 설정
        for(int i = 0; i < M; i++){
            int x = mXs[i], y = mYs[i];
            Taxi tmp = new Taxi(i+1, x, y, 0, 0); // 택시 객체 생성 (ID는 1부터 시작)

            // 위치에 맞는 버킷에 택시 추가
            bucket[x / L][y / L].add(tmp);

            // TreeSet과 HashMap에도 택시 추가
            heap.add(tmp);
            taxiMap.put(i+1, tmp);
        }

		return;
	}

    /**
     * 승객을 태울 택시를 배차하고, 택시의 상태를 갱신하는 메소드.
     * @param mSX 승객 출발지 행
     * @param mSY 승객 출발지 열
     * @param mEX 승객 목적지 행
     * @param mEY 승객 목적지 열
     * @return 배차된 택시의 ID, 배차 가능한 택시가 없으면 -1
     */
	public int pickup(int mSX, int mSY, int mEX, int mEY) {
        // 승객의 위치를 기준으로 버킷 인덱스 계산
        int x = mSX / L;
        int y = mSY / L;

        int min = Integer.MAX_VALUE; // 승객과의 최소 거리를 저장할 변수
        Taxi called = null; // 배차될 택시를 저장할 변수
        
        // 승객이 위치한 버킷과 그 주변 8개 버킷(총 9개)만 탐색하여 효율성 증대
        for(int i = Math.max(0, x-1); i <= Math.min(9, x+1); i++){
            for(int j = Math.max(0, y-1); j <= Math.min(9, y+1); j++){
                // 해당 버킷에 있는 모든 택시에 대해 검사
                for(Taxi t: bucket[i][j]){
                    int dist = getDistance(mSX, mSY, t.x, t.y);

                    // 호출 가능 최대 거리 L을 초과하면 무시
                    if(dist > L){
                        continue;
                    }
                    
                    // 더 가까운 택시를 찾은 경우
                    if(dist < min){
                        called = t;
                        min = dist;
                    }
                    // 거리가 같으면 ID가 더 작은 택시를 선택
                    else if(dist == min && (called == null || called.id > t.id)){
                        called = t;
                    }
                }
            }
        }
        
        // 배차 가능한 택시가 없는 경우
        if(called == null){
            return -1;
        }

        // 택시 상태 갱신 전, 기존 정보로 TreeSet과 버킷에서 제거
        heap.remove(called);
        int oldTaxiBucketX = called.x / L;
        int oldTaxiBucketY = called.y / L;
        bucket[oldTaxiBucketX][oldTaxiBucketY].remove(called);

        // 택시 상태 갱신
        int rideDist = getDistance(mSX, mSY, mEX, mEY); // 승객 이동 거리
        called.mMoveDistance += min + rideDist; // 총 이동 거리 갱신
        called.mRideDistance += rideDist; // 승객 탑승 이동 거리 갱신
        called.x = mEX; // 택시 위치를 목적지로 갱신
        called.y = mEY;

        // 갱신된 정보로 TreeSet과 버킷에 다시 추가
        int newTaxiBucketX = called.x / L;
        int newTaxiBucketY = called.y / L;
        bucket[newTaxiBucketX][newTaxiBucketY].add(called);
        heap.add(called);

        return called.id;
    }
    
    /**
     * 특정 택시의 운행 기록을 초기화하는 메소드.
     * @param mNo 초기화할 택시의 ID
     * @return 초기화 전 택시의 상태 정보
     */
	public Solution_24997_송주헌.Result reset(int mNo)
	{
		Solution_24997_송주헌.Result res = new Solution_24997_송주헌.Result();

        // HashMap에서 ID로 택시 객체를 찾음
        Taxi t = taxiMap.get(mNo);

        // 갱신 전 정보로 TreeSet에서 제거
        heap.remove(t);

        // 반환할 Result 객체에 초기화 전 정보 저장
        res.mX = t.x;
        res.mY = t.y;
        res.mMoveDistance = t.mMoveDistance;
        res.mRideDistance = t.mRideDistance;

        // 운행 기록 초기화
        t.mMoveDistance = 0;
        t.mRideDistance = 0;

        // 초기화된 정보로 TreeSet에 다시 추가
        heap.add(t);

		return res;
	}

    /**
     * 우수 택시 상위 5대의 ID를 찾는 메소드.
     * @param mNos 상위 5대 택시 ID를 저장할 배열
     */
	public void getBest(int[] mNos)
	{
        // TreeSet은 항상 정렬 상태이므로, 가장 첫 번째 원소가 최우수 택시
        Taxi output = heap.first();
        mNos[0] = output.id;
        
        // higher() 메소드를 이용해 순서대로 다음 우수 택시를 찾음
        for(int i = 1; i < 5; i++){
            output = heap.higher(output);
            mNos[i] = output.id;
        }
		return;
	}
}