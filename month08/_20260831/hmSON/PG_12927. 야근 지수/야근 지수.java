import java.util.Comparator;
import java.util.PriorityQueue;

class Solution {

    /*
     * 자료구조 및 알고리즘 : 우선순위 큐, 그리디, 정렬
     * 피로도는 남은 각 작업 잔여량의 제곱의 합임
     * 따라서 전체 피로도를 최소화하려면 큰 값부터 1시간씩 처리해서 남은 작업들의 잔여량이 최대한 동일 선상에 있게끔 만들어야 함
     *
     * 따라서 우선순위 큐에 모든 잔여량을 넣고, 제일 큰 값만 지속적으로 1씩 깎는다
     * 야근 종료 이후 잔여량을 하나씩 빼서 피로도 합산
     */

    public long solution(int n, int[] works) {
        // 큰 값부터 빼야 하므로 역순 정렬
        PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.reverseOrder());
        for(int work : works) q.add(work);

        for(int i=0; i<n && !q.isEmpty(); i++) {
            // 잔여량 1씩 깍은 뒤 다시 큐에 넣기
            int remain = q.poll() - 1;
            // 잔여량이 0이면 추가하지 않음
            if(remain > 0) q.add(remain);
        }

        long res = 0L;

        while(!q.isEmpty()) {
            long cur = q.poll();
            // 피로도 == 잔여량의 제곱
            res += (cur * cur);
        }

        return res;
    }

}