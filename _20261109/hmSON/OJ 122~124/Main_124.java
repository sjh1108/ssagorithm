import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 다익스트라, 우선순위 큐, 역방향 그래프
     *
     * 케이블카 정류장으로 구성된 산에서 각 등산객이 산 정상까지 제 시간에 도착하려면 언제 출발해야 하는지 알아내야 한다.
     * 각 케이블카 노선은 다음 정보들을 가진다 -> u(출발점), v(도착점), s(첫차 출발 시각), p(배차 간격), w(도착점까지의 이동 시간)
     * 특정 정류장에 도착하더라도 당장 탑승할 케이블카가 없다면 대기해야 한다. 이 또한 소요 시간에 포함된다.
     *
     * 정류장의 개수는 최대 50000, 등산객의 수도 최대 50000이므로
     * 매 질의마다 등산객 -> 산 정상 다익스트라를 수행하면 최악의 경우 25억 회의 탐색을 요구한다.
     * 따라서 산 정상부터 역방향 다익스트라를 수행하여 각 정류장별로 언제 출발해야 제 시간에 도착하는지 기록하는 전처리를 수행한다.
     * 이를 위해 우선순위 큐의 정렬 또한 시각 내림차순을 적용한다.
     *
     * 등산객의 초기 위치(정류장 번호)가 질의로 주어지므로, 전처리가 수행되면 각 질의를 단순 조회로 처리할 수 있게 된다.
     */

    // 케이블카 노선 클래스
    // 각 케이블카의 출발지, 첫차 출발 시각, 배차 간격, 목적지까지의 이동 시간 관리
    static class CableCar {
        int from, first, interval, moveTime;

        public CableCar(int from, int first, int interval, int moveTime) {
            this.from = from;
            this.first = first;
            this.interval = interval;
            this.moveTime = moveTime;
        }

        // 현재 정류장까지 제 시간에 도착할 수 있는 마지막 케이블카 탑승 시각 반환
        public long getLastBoardingTime(long current) {
            long expected = current - moveTime; // 아무리 늦어도 언제 출발해야 하는가?
            if(expected < first) return -1;     // 첫차를 타도 제 시간에 도착할 수 없는 상황

            long passed = (expected - first) / interval; // 지나간 케이블카 수
            return first + (passed * interval);          // 제 시간에 도착하기 위해 타야 하는 마지막 케이블카 탑승 시각
        }
    }

    // 다익스트라에 사용할 정류장 클래스
    // 각 정류장 번호 및 각 정류장까지의 제한 소요 시간 관리
    // 소요 시간 내림차순 정렬 적용
    static class Station implements Comparable<Station> {
        int idx;
        long time;

        public Station(int idx, long time) {
            this.idx = idx;
            this.time = time;
        }

        @Override
        public int compareTo(Station o) {
            return Long.compare(o.time, this.time);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        long t = Long.parseLong(st.nextToken());

        // 케이블카 정류장 인접 리스트
        List<CableCar>[] graph = new List[n];
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 도착점부터 시작하는 다익스트라이므로 케이블카 노선도 역방향으로 등록
        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int first = Integer.parseInt(st.nextToken());
            int interval = Integer.parseInt(st.nextToken());
            int moveTime = Integer.parseInt(st.nextToken());

            graph[to].add(new CableCar(from, first, interval, moveTime));
        }

        // 다익스트라를 위한 우선순위 큐. Station 클래스의 정렬 기준 적용
        Queue<Station> pq = new PriorityQueue<>();
        pq.offer(new Station(n-1, t)); // 초기값은 산 정상 정류장 번호와 목표 시각 t

        // 각 정류장별 산 정상에 도달하기 위해 출발해야 하는 제한 시각
        long[] leaveTime = new long[n];
        Arrays.fill(leaveTime, -1); // -1 : 제 시간 내 도착 불가능
        leaveTime[n-1] = t;         // 산 정상(이미 도착) = t

        while(!pq.isEmpty()) {
            Station cur = pq.poll();

            // 이미 처리한 정류장
            if(cur.time < leaveTime[cur.idx]) continue;

            for(CableCar prev : graph[cur.idx]) {
                long boardingTime = prev.getLastBoardingTime(cur.time);
                if(boardingTime <= leaveTime[prev.from]) continue;

                leaveTime[prev.from] = boardingTime;
                pq.offer(new Station(prev.from, boardingTime));
            }
        }

        int query = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        // 각 정류장별 반드시 출발해야 하는 시각을 전처리 해뒀으므로 단순 조회로 질의 처리 가능
        for(int i=0; i<query; i++) {
            int start = Integer.parseInt(br.readLine()) - 1;
            sb.append(leaveTime[start]).append("\n");
        }

        System.out.print(sb);
    }

}