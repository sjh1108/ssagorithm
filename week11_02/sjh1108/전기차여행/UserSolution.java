package week11_02.sjh1108.전기차여행;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

class UserSolution {
    // Road 클래스: 도로 정보를 저장.
    // Comparable은 구현되어 있지만, 이 문제의 cost() 함수에서는 사용되지 않음.
    static class Road implements Comparable<Road>{
        int sCity, eCity, mTime, mPower;
        public Road(int sCity, int eCity, int mTime, int mPower){
            this.sCity = sCity; // 출발 도시
            this.eCity = eCity; // 도착 도시
            this.mTime = mTime; // 소요 시간
            this.mPower = mPower; // 소모 전력
        }

        // mTime 오름차순, mTime이 같으면 mPower 내림차순 (cost()에서는 사용 안 함)
        @Override
        public int compareTo(Road o) {
            if(this.mTime == o.mTime){
                return o.mPower - this.mPower;
            }
            return this.mTime - o.mTime;
        }
    }

    // 인접 리스트: roadsFromCities[i] = i번 도시에서 출발하는 도로들의 ID(mId) 집합
    ArrayList<HashSet<Integer>> roadsFromCities;
    // 도로 정보 맵: Key(mId) -> Value(Road 객체). 도로 ID로 도로의 상세 정보(시간, 전력)를 빠르게 조회.
    HashMap<Integer, Road> roadMap;
    // 도시별 충전량: mCharge[i] = i번 도시에서 1의 시간동안 충전되는 양
    int[] mCharge;
    // 총 도시 수
    int N;

    /**
     * 초기화 함수
     * @param N 도시의 수
     * @param mCharge 도시별 시간당 충전량 배열
     * @param K 초기 도로의 수
     * @param mId 도로 ID 배열
     * @param sCity 도로 출발 도시 배열
     * @param eCity 도로 도착 도시 배열
     * @param mTime 도로 소요 시간 배열
     * @param mPower 도로 소모 전력 배열
     */
    public void init(int N, int mCharge[], int K, int mId[], int sCity[], int eCity[], int mTime[], int mPower[]) {
        this.N = N;
        this.mCharge = new int[N];
        // mCharge 배열 복사
        System.arraycopy(mCharge, 0, this.mCharge, 0, N);

        // 인접 리스트 초기화 (N개의 도시)
        roadsFromCities = new ArrayList<>();
        for(int i = 0; i < N; i++){
            roadsFromCities.add(new HashSet<>());
        }
        // 도로 정보 맵 초기화
        roadMap = new HashMap<>();

        // K개의 초기 도로 정보를 그래프에 추가
        for(int i = 0; i < K; i++){
            add(mId[i], sCity[i], eCity[i], mTime[i], mPower[i]);
        }
        return;
    }

    /**
     * 도로 추가 함수
     */
    public void add(int mId, int sCity, int eCity, int mTime, int mPower) {
        Road road = new Road(sCity, eCity, mTime, mPower);
        // 도로 ID -> 도로 정보 매핑
        roadMap.put(mId, road);
        // 출발 도시(sCity)의 인접 리스트에 도로 ID 추가
        roadsFromCities.get(sCity).add(mId);
        return;
    }

    /**
     * 도로 제거 함수
     */
    public void remove(int mId) {
        // roadMap에서 mId로 Road 객체(정보)를 조회
        Road road = roadMap.get(mId);
        // 해당 도로의 출발 도시(road.sCity)의 인접 리스트에서 mId 제거
        roadsFromCities.get(road.sCity).remove(mId);
        // roadMap에서도 mId 제거
        roadMap.remove(mId);
        return;
    }

    /**
     * 최소 시간(비용) 계산 함수
     * @param B 최대 배터리 용량
     * @param sCity 출발 도시
     * @param eCity 도착 도시
     * @param M 바이러스 시작 도시 수
     * @param mCity 바이러스 시작 도시 배열
     * @param mTime 바이러스 시작 시간 배열
     * @return 출발지에서 도착지까지 가는 최소 시간 (불가능하면 -1)
     */
    public int cost(int B, int sCity, int eCity, int M, int mCity[], int mTime[]) {
        
        // --- 1부: 다익스트라를 이용한 바이러스 전파 시간 계산 ---
        
        // virus[i] = i번 도시에 바이러스가 도달하는 '최소 시간'
        int[] virus = new int[N];
        Arrays.fill(virus, Integer.MAX_VALUE);

        // 바이러스 전파용 우선순위 큐 (시간 기준 Min-Heap)
        Queue<int[]> virusQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));

        // 초기 바이러스 시작 지점들을 큐와 virus 배열에 등록
        for(int i = 0; i < M; i++){
            virusQueue.add(new int[]{mCity[i], mTime[i]}); // {도시, 시간}
            virus[mCity[i]] = mTime[i];
        }

        // 바이러스 전파 다익스트라
        while (!virusQueue.isEmpty()) {
            int[] curr = virusQueue.poll();
            int u = curr[0]; // 현재 도시
            int time = curr[1]; // 바이러스 도달 시간

            if (virus[u] < time) continue; // 이미 더 빠른 경로로 바이러스가 도달했다면 스킵

            // 현재 도시(u)에서 갈 수 있는 모든 도로(mId) 탐색
            for (int mId : roadsFromCities.get(u)) {
                Road road = roadMap.get(mId);
                int v = road.eCity; // 다음 도시
                int nextTime = time + road.mTime; // 다음 도시 바이러스 도달 시간

                // 다음 도시에 더 빨리 바이러스가 도달할 수 있다면 갱신
                if (virus[v] > nextTime) {
                    virus[v] = nextTime;
                    virusQueue.offer(new int[]{v, nextTime});
                }
            }
        }
        // --- 1부 종료: virus[] 배열에 각 도시별 바이러스 최소 도달 시간 계산 완료 ---


        // --- 2부: 다익스트라를 이용한 최소 이동 시간 계산 ---

        // dist[i][j] = i번 도시에 배터리 j를 가지고 도착하는 '최소 시간'
        int[][] dist = new int[N][B + 1];
        for (int[] row : dist) {
            Arrays.fill(row, Integer.MAX_VALUE);
        }

        // 메인 다익스트라용 우선순위 큐 (시간 기준 Min-Heap)
        // 상태: {도시, 시간, 배터리}
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingLong(a -> a[1]));

        // 시작 상태: 출발 도시에(sCity), 0의 시간으로, 최대 배터리(B)를 가지고 도착
        dist[sCity][B] = 0;
        pq.offer(new int[]{sCity, 0, B}); // {도시, 시간, 배터리}

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int to = cur[0]; // 현재 도시
            int time = cur[1]; // 현재까지 걸린 시간
            int battery = cur[2]; // 현재 배터리

            // 이미 더 빠른 경로로 이 상태(도시, 배터리)에 도달했다면 스킵
            if (dist[to][battery] < time) continue;
            
            // [도착 조건] 도착 도시(eCity)에 도달했다면, 즉시 시간 반환
            // (다익스트라(Min-Heap)이므로, 처음 eCity를 꺼냈을 때가 최소 시간)
            if (to == eCity) return time;

            // --- [선택 1] 현재 도시(to)에서 다음 도시(nextTo)로 '이동' ---
            for (int mId : roadsFromCities.get(to)) {
                Road road = roadMap.get(mId);
                
                // 1-1. 배터리가 충분한지 확인
                if (battery >= road.mPower) {
                    int nextTo = road.eCity; // 다음 도시
                    int nextTime = time + road.mTime; // '도착' 예상 시간

                    // 1-2. [바이러스 체크] 도착 예상 시간(nextTime)이
                    //      다음 도시의 바이러스 도달 시간(virus[nextTo])보다 빠르거나 같으면 이동 불가
                    if (virus[nextTo] <= nextTime) continue;

                    int nextBattery = battery - road.mPower; // 이동 후 남은 배터리
                    
                    // 1-3. [다익스트라 갱신] (다음 도시, 다음 배터리) 상태에 더 빨리 도달했다면
                    if (dist[nextTo][nextBattery] > nextTime) {
                        
                        // [최적화]
                        // "nextTo" 도시에 "nextBattery" 배터리로 "nextTime"에 도착할 수 있다면,
                        // "nextTo" 도시에 "nextBattery"보다 *적은* 배터리(i)로 "nextTime"에 도착하는
                        // 모든 (이전의, 더 느렸던) 상태들도 이 "nextTime"으로 갱신해준다.
                        // (i < nextBattery인 상태는 (nextBattery) 상태보다 항상 안좋으므로)
                        // (만약, i 배터리로 nextTime보다 *더 빨리* 도착한 경로(dist[nextTo][i] < nextTime)가
                        //  이미 존재한다면 갱신하지 않고 break)
                        for (int i = nextBattery; i >= 0; --i) {
                            if (dist[nextTo][i] < nextTime) break;
                            dist[nextTo][i] = nextTime;
                        }
                        
                        // 큐에 (다음 도시, 다음 시간, 다음 배터리) 상태 추가
                        pq.offer(new int[]{nextTo, nextTime, nextBattery});
                    }
                }
            }

            // --- [선택 2] 현재 도시(to)에서 '충전' (1의 시간 소요) ---
            
            /*
             * (사용자 주석) 이 부분을 위에 for문 안에 넣으려고 고민하다가 오래걸림
             * (분석) '이동'과 '충전'은 현재 상태(to, time, battery)에서 할 수 있는
             * 서로 다른 *선택지*이므로, '이동' 루프(for mId)와
             * '충전' 로직(if battery < B)은 동일한 레벨(while문)에 있어야 합니다.
             */
            
            // 2-1. 배터리가 최대(B)가 아닐 때만 충전 가능
            if (battery < B) {
                int nextTime = time + 1; // 충전은 1의 시간이 걸림

                // 2-2. [바이러스 체크] 충전이 *끝나는* 시간(nextTime)이
                //      현재 도시의 바이러스 도달 시간(virus[to])보다 빠르거나 같으면 충전 불가
                if (virus[to] > nextTime) { // (virus[to] <= nextTime 이면 continue)
                    int nextBattery = battery + mCharge[to]; // 충전 후 배터리
                    if (nextBattery > B) nextBattery = B; // 최대 용량(B)으로 제한

                    // 2-3. [다익스트라 갱신] (현재 도시, 다음 배터리) 상태에 더 빨리 도달했다면
                    if (dist[to][nextBattery] > nextTime) {
                        
                        // [최적화] (이동 시와 동일한 로직)
                        // (to) 도시에 (nextBattery) 배터리 상태로 (nextTime)에 도달 가능함을 갱신.
                        // 이보다 안좋은 (i < nextBattery) 상태들도 갱신.
                        for (int i = nextBattery; i >= 0; --i) {
                            if (dist[to][i] < nextTime) break;
                            dist[to][i] = nextTime;
                        }
                        
                        // 큐에 (현재 도시, 다음 시간, 다음 배터리) 상태 추가
                        pq.offer(new int[]{to, nextTime, nextBattery});
                    }
                }
            }
        }
        
        // 큐가 비었는데 도착 도시(eCity)에 도달하지 못했다면, 경로가 없는 것
        return -1;
    }
}