import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 다익스트라, 우선순위 큐
     *
     * 1번 정류장부터 N번 정류장까지의 최단 경로를 구하는 다익스트라 문제
     * 각 노선은 다음 정보들을 가진다 -> u(출발점), v(도착점), s(첫차 출발 시각), p(배차 간격), w(도착점까지의 이동 시간)
     * 특정 정류장에 도착하더라도 당장 탑승할 케이블카가 없다면 대기해야 한다. 이 또한 소요 시간에 포함된다.
     * 따라서 각 노선마다 다음 케이블카를 탑승할 수 있는 시각을 구하는 로직이 별도로 요구된다.
     */

    // 케이블카 노선 클래스
    // 각 케이블카의 목적지, 첫차 출발 시각, 배차 간격, 목적지까지의 이동 시간 관리
    static class CableCar {
        int to, first, interval, moveTime;

        public CableCar(int to, int first, int interval, int moveTime) {
            this.to = to;
            this.first = first;
            this.interval = interval;
            this.moveTime = moveTime;
        }

        // 현재 시각을 기준으로 다음 정류장까지의 예상 도착 시각을 계산하여 반환
        public long getNextArrivalTime(long current) {
            if(current < first) return first + moveTime; // 첫차 출발 시각 이전인 경우

            long passed = (current - first + interval - 1) / interval; // 지나간 케이블카 수
            return first + (passed * interval) + moveTime;
        }
    }

    // 다익스트라에 사용할 정류장 클래스
    // 각 정류장 번호 및 도착하는 데 걸린 총 소요 시간 관리
    // 소요 시간 오름차순 정렬 적용
    static class Station implements Comparable<Station> {
        int idx;
        long time;

        public Station(int idx, long time) {
            this.idx = idx;
            this.time = time;
        }

        @Override
        public int compareTo(Station o) {
            return Long.compare(this.time, o.time);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        // 정류장이 하나 -> 출발점이자 도착점. 이동 시간 없음.
        if(n == 1) {
            System.out.println(0);
            return;
        }

        // 케이블카 정류장 인접 리스트
        List<CableCar>[] graph = new List[n];
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1;
            int to = Integer.parseInt(st.nextToken()) - 1;
            int first = Integer.parseInt(st.nextToken());
            int interval = Integer.parseInt(st.nextToken());
            int moveTime = Integer.parseInt(st.nextToken());

            graph[from].add(new CableCar(to, first, interval, moveTime));
        }

        // 다익스트라를 위한 우선순위 큐. Station 클래스의 정렬 기준 적용
        Queue<Station> pq = new PriorityQueue<>();
        pq.offer(new Station(0, 0));

        // 각 정류장 도착까지 걸린 최단 소요 시간
        long[] cost = new long[n];
        Arrays.fill(cost, Long.MAX_VALUE);
        cost[0] = 0; // 출발점 -> 0

        while(!pq.isEmpty()) {
            Station cur = pq.poll();
            // 현재 위치가 도착점이면 소요 시간 출력 후 종료
            if(cur.idx == n-1) {
                System.out.println(cur.time);
                return;
            }

            // 이미 처리한 정류장
            if(cur.time > cost[cur.idx]) continue;

            for(CableCar next : graph[cur.idx]) {
                long nextTime = next.getNextArrivalTime(cur.time);
                if(cost[next.to] <= nextTime) continue;

                cost[next.to] = nextTime;
                pq.offer(new Station(next.to, nextTime));
            }
        }

        // 도착점까지 도달할 수 없음
        System.out.println(-1);
    }

}