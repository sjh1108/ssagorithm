import java.util.Arrays;

class Solution {

    /**
     * 자료구조 및 알고리즘 : 이분 탐색, 매개변수 탐색
     * 징검다리의 돌을 del만큼만 빼서 만들 수 있는 최단 간격의 최대값을 구해야 함
     * 구해야 하는 해는 "최단 간격"이므로 이를 이용해 매개변수 탐색
     * -> 1. 임의의 거리값 mid를 징검다리의 최단 간격으로 지정
     * -> 2. 시작점인 0부터 시작해서 거리가 mid 미만인 돌을 제거
     * -> 3-1. 간격이 mid 이상인 다리를 만들기 위해 제거해야 할 돌이 del개 초과 -> 구성 불가. right 감소
     * -> 3-2. 간격이 mid 이상인 다리를 만들기 위해 제거해야 할 돌이 del개 이하 -> 구성 가능. left 증가
     */

    public int solution(int dist, int[] rocks, int del) {
        // 배열 내 값이 정렬된 상태로 주어지지 않음
        Arrays.sort(rocks);

        // 최소 간격은 1, 최대 간격은 시작점 - 도착점 간 거리인 dist
        // 매 번 임의의 최단 간격을 지정할 때마다 제거되는 돌의 개수를 카운트해야 함
        int left = 1, right = dist, delCnt = 0;
        while(left <= right) {
            int mid = left + (right - left) / 2;
            delCnt = 0;
            // 간격 측정을 위해 직전 지점의 위치 기록. 초기 값은 시작점인 0
            int prev = 0;
            for(int rock : rocks) {
                // 두 지점 간 간격이 임의의 지정 간격(mid)보다 작으면 해당 돌 제거 및 카운트
                if(rock - prev < mid) {
                    delCnt++;
                    // 이미 제한된 돌 제거 횟수를 초과했다면 더 볼 필요 없음
                    if(delCnt > del) break;
                    continue;
                }

                // 직전 지점 갱신
                prev = rock;
            }

            // 마지막 돌 - 도착점 간 거리도 확인해야 함
            // 두 지점 간 간격이 mid 미만이면 마지막 돌까지 제거하는 것으로 취급
            if(dist - prev < mid) delCnt++;

            // 제거한 돌의 개수가 지정 횟수 del을 초과했는가?
            if(delCnt > del) right = mid - 1;
            else left = mid + 1;
        }

        return right;
    }

}