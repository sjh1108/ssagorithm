import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

/**
 * SWEA_25005 (Pro) : 전기차여행
 *
 * 핵심 아이디어
 * 1) 전염병 전파 시간 계산 (도시 단위 최단시간)  -> dijkVirus()
 * 2) 전염병 시간 제약을 고려한 전기차 경로 탐색 -> dijkCar()
 *
 * - 감염 도시는 진입/체류 불가 (감염 시각과 '동일'하게 도착/체류하는 것도 불가)
 * - 충전은 1시간 단위: (시간 +1, 배터리 + 도시 충전량) 를 상태 전이로 모델링
 * - 간선 제거는 실제 삭제 대신 Node.removed 플래그로 비활성화 처리
 *
 * 시간 복잡도(개략): dijkVirus = O(K log N), dijkCar = O((N * B + K) log (N * B))
 */
class UserSolution_25005_전기차여행 {
    
    static final int INF = Integer.MAX_VALUE; // 충분히 큰 최댓값
    static int v;                              // 도시 수
    static int[] amountCharge, distVirus;      // 도시별 시간당 충전량, 도시별 감염 도달 최소 시각
    static HashMap<Integer, Node> nodes;       // 도로ID -> Node 매핑(동적 add/remove 대응)
    static List<Node>[] graph;                 // 인접 리스트(단방향)

    /**
     * 전기차 상태(다익스트라용)
     * to    : 현재 도시
     * time  : 현재까지 경과 시간 (우선순위 큐 정렬 기준)
     * power : 현재 남은 배터리
     */
    static class Car implements Comparable<Car> {
        int to, time, power;
        
        public Car(int to, int time, int power) {
            this.to = to;
            this.time = time;
            this.power = power;
        }

        @Override
        public int compareTo(Car o) {
            return time - o.time; // 최소 시간 우선
        }
    }
    
    /**
     * 전염병 상태(다익스트라/BFS 유사)
     * to   : 감염된 도시
     * time : 해당 도시가 감염되는 시각
     */
    static class Virus implements Comparable<Virus> {
        int to, time; 
        
        public Virus(int to, int time) {
            this.to = to;
            this.time = time;
        }
        
        @Override
        public int compareTo(Virus o) {
            return time - o.time; // 더 빨리 감염되는 시각 우선
        }
    }
    
    /**
     * 간선(도로) 정보
     * to      : 도착 도시
     * time    : 도로 통과 소요 시간
     * power   : 도로 통과 시 소모되는 전력
     * removed : true면 비활성화(삭제 처리)
     */
    static class Node {
        int to, time, power;
        boolean removed;
        
        public Node(int to, int time, int power) {
            this.to = to;
            this.time = time;
            this.power = power;
            removed = false;
        }
    }
    
    /**
     * 초기화
     * - 도시 수/충전량 저장
     * - 인접 리스트 생성
     * - 초기 도로 K개 등록 (nodes 해시맵 + graph 인접리스트)
     */
    public void init(int N, int mCharge[], int K, int mId[], int sCity[], int eCity[], int mTime[], int mPower[]) {
        v = N;
        amountCharge = mCharge;
        graph = new ArrayList[v];
        distVirus = new int[v];
        for (int i = 0; i < v; i++) {
            graph[i] = new ArrayList<>();
        }
        nodes = new HashMap<>();
        
        for (int i = 0; i < K; i++) {
            Node node = new Node(eCity[i], mTime[i], mPower[i]);
            nodes.put(mId[i], node);       // 도로 ID로 직접 접근 가능
            graph[sCity[i]].add(node);     // 단방향 간선 등록
        }
    }

    /**
     * 도로 추가
     * - 새 Node를 만들고 해시맵과 인접리스트에 등록
     */
    public void add(int mId, int sCity, int eCity, int mTime, int mPower) {
        Node newNode = new Node(eCity, mTime, mPower);
        nodes.put(mId, newNode);
        graph[sCity].add(newNode);
    }

    /**
     * 도로 제거
     * - 실제 삭제 대신 removed 플래그만 세팅 (탐색 시 스킵)
     */
    public void remove(int mId) {
        Node target = nodes.get(mId);
        target.removed = true;
    }

    /**
     * 비용(최소 시간) 계산
     * 1) dijkVirus : 감염 도달 최소 시각을 구한다.
     * 2) dijkCar   : 감염 시각 제약 하에 전기차의 최소 시간 경로를 구한다.
     */
    public int cost(int B, int sCity, int eCity, int M, int mCity[], int mTime[]) {
        dijkVirus(M, mCity, mTime);
        return dijkCar(B, sCity, eCity);
    }
    
    /**
     * 전기차 다익스트라
     * 상태: (도시, 남은 배터리)
     * 전이:
     *  - 충전: (시간 +1, 배터리 + 충전속도) 단, 배터리는 최대용량 B를 초과할 수 없음
     *  - 이동: (시간 + 간선시간, 배터리 - 소모전력)
     *
     * 감염 제약:
     *  - 도시 '체류/도착' 시각이 distVirus[도시]보다 엄Strict하게 작아야 함
     *    (동일 시각 도착/체류 불가)
     */
    public int dijkCar(int maxCharged, int start, int end) {
        // distCar[u][p] : 도시 u에 배터리 p로 도달하는 최소 시간
        int[][] distCar = new int[v][maxCharged + 1];
        for (int i = 0; i < v; i++) {
            Arrays.fill(distCar[i], INF);
        }
        
        PriorityQueue<Car> q = new PriorityQueue<>();
        distCar[start][maxCharged] = 0;                 // 초기: 출발 도시는 완충 상태
        q.add(new Car(start, 0, maxCharged));
        
        while (!q.isEmpty()) {
            Car cur = q.poll();

            // 이미 더 좋은 경로가 있거나, 해당 시각에 도시가 감염되었거나(=감염 시각 이하) 체류 불가
            if (distCar[cur.to][cur.power] < cur.time || cur.time >= distVirus[cur.to]) continue;
            
            // 목표 도시에 도달하면 그 시간이 최소
            if (cur.to == end) return cur.time;
            
            // (1) 충전 전이: 1시간 뒤, 배터리 + 충전속도(도시별), 단 최대용량 cap
            if (cur.power < maxCharged) {
                int newCharge = Math.min(maxCharged, cur.power + amountCharge[cur.to]);
                int newTime = cur.time + 1;
                // 충전 완료 시점에도 감염되면 안 됨 (newTime < distVirus[cur.to])
                if (newTime < distVirus[cur.to] && newTime < distCar[cur.to][newCharge]) {
                    distCar[cur.to][newCharge] = newTime;
                    q.add(new Car(cur.to, newTime, newCharge));
                }
            }
            
            // (2) 이동 전이: 간선 시간/전력 반영
            for (Node next : graph[cur.to]) {
                if (next.removed) continue; // 제거된 도로 스킵
                
                int nextTime = cur.time + next.time;
                // 도착 도시도 감염 시각 이전에만 도달 가능 (동일 시각 불가)
                if (distVirus[next.to] <= nextTime) continue;
                
                int nextCharge = cur.power - next.power;
                if (nextCharge < 0 || nextTime >= distCar[next.to][nextCharge]) continue;
                
                distCar[next.to][nextCharge] = nextTime;
                q.add(new Car(next.to, nextTime, nextCharge));
            }
        }
        
        // 도달 불가
        return -1;
    }
    
    /**
     * 전염병 다익스트라(간선 가중치 = 도로 소요 시간)
     * - 여러 시작점(초기 감염 도시)에서 시작하는 멀티소스 최단거리
     * - distVirus[u] : 도시 u가 감염되는 '최소 시각'
     */
    public void dijkVirus(int m, int city[], int time[]) {
        Arrays.fill(distVirus, INF);
        
        PriorityQueue<Virus> q = new PriorityQueue<>();
        // 초기 감염 도시들을 시드로 설정
        for (int i = 0; i < m; i++) {
            distVirus[city[i]] = time[i];
            q.add(new Virus(city[i], time[i]));
        }
        
        while (!q.isEmpty()) {
            Virus cur = q.poll();
            if (distVirus[cur.to] < cur.time) continue; // 더 이른 감염 시각으로 확정된 경우 스킵
            
            for (Node next : graph[cur.to]) {
                if (next.removed) continue; // 제거된 도로 스킵
                
                int newTime = cur.time + next.time; // 간선 시간만큼 뒤에 다음 도시 감염
                if (distVirus[next.to] <= newTime) continue; // 기존이 더 이르면 갱신 불필요
                distVirus[next.to] = newTime;
                q.add(new Virus(next.to, newTime));
            }
        }
    }
    
}
