import java.util.*;

class Solution {

    // 작업 클래스
    // 작업 id, 작업 요청 시각, 소요 시간
    // 정렬 순서 : 소요 시간 -> 작업 요청 시각 -> 작업 id
    static class Node implements Comparable<Node> {
        int idx, call, cost;

        public Node(int idx, int call, int cost) {
            this.idx = idx;
            this.call = call;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node o) {
            if(this.cost == o.cost) {
                if(this.call == o.call) return this.idx - o.idx;
                return this.call - o.call;
            }
            return this.cost - o.cost;
        }
    }

    public int solution(int[][] jobs) {
        // time은 현재 시각
        int sum = 0, time = 0, len = jobs.length;

        // 작업 목록은 요청 시점 오름차순으로 정렬
        Arrays.sort(jobs, Comparator.comparingInt(o -> o[0]));
        PriorityQueue<Node> pq = new PriorityQueue<>();

        int idx = 0;
        while(!pq.isEmpty() || idx < len) {
            // 현재 시각을 기준으로 요청된 모든 작업을 대기 큐에 추가
            while(idx < len && jobs[idx][0] <= time) {
                pq.add(new Node(idx, jobs[idx][0], jobs[idx][1]));
                idx++;
            }

            // 만약 위 작업을 거치고도 대기 큐에 아무것도 없다면
            // 그 다음으로 가장 빨리 요청될 예정인 작업의 요청 시각으로 현재 시각 갱신
            if(pq.isEmpty()) {
                time = jobs[idx][0];
                continue;
            }

            Node cur = pq.poll();
            // sum에 더해야 할 것 : endTime(작업 종료 시각) - Node.call(작업 호출 시각)
            // endTime(작업 종료 시각) = time(현재 시각) + Node.cost(소요 시간)
            int endTime = time + cur.cost;
            sum += endTime - cur.call;

            // 작업 하나가 종료되는 시점까지 다른 작업이 불가능함
            // 현재 시각을 작업 종료 시각으로 갱신
            time = endTime;
        }

        return sum / len;
    }

}