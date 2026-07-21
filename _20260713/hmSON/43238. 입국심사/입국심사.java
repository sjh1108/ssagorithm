class Solution {

    /**
     * 자료구조 및 알고리즘 : 이분 탐색, 매개변수 탐색
     * 이 문제에서 찾아야 할 변수는 모든 입국자가 심사대를 통과하는 최단 시간
     * 매개변수 탐색을 이용해 "특정 시간이 주어졌을 때 각 심사대에서 최대 몇 명을 통과시킬 수 있는가"를 판단한다
     * 위 과정을 통해 각 심사대의 수용 가능 인원수 합이 입국자 수 n 이상인가?
     * yes -> 주어진 시간 내 입국 심사 가능. right를 감소시켜 더 적은 시간 안에도 가능한 지 확인
     * no -> 주어진 시간 내 입국 심사 불가능. left를 증가시켜 수용 가능 범위를 줄여나감
     * @param n : 입국자 수
     * @param times : 각 심사대의 입국 심사 인당 소요 시간
     * @return : 모든 입국자가 심사대를 통과하는 최단 시간
     */

    public long solution(int n, int[] times) {
        // min : 입국 심사가 제일 빠른 심사대의 심사 소요 시간
        // 구하는 이유 : 모든 입국자가 가장 빠른 심사대만 거쳐가는 경우를 최대값으로 전제하기 위함
        int min = Integer.MAX_VALUE;
        for(int time : times) {
            if(time < min) min = time;
        }

        // 상기한 대로 right는 모든 입국자가 가장 빠른 심사대만 거쳐가는 상황의 소요 시간임
        long left = 1, right = (long)n * min, cnt = 0;
        while(left <= right) {
            long mid = left + (right - left) / 2;
            cnt = 0;

            // 각 심사대별 주어진 시간 내 수용 가능한 입국자 수 합계를 구함
            // 이후 입국자 수 n과 비교하여 입국자를 모두 수용 가능한 지 확인
            for(int time : times) {
                cnt += mid / time;
                if(cnt >= n) break;
            }

            if(cnt >= n) right = mid - 1;
            else left = mid + 1;
        }

        return left;
    }

}