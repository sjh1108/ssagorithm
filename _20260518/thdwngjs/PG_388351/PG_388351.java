package _20260518.thdwngjs.PG_388351;

class Solution {
    public int solution(int[] schedules, int[][] timelogs, int startday) {
        int answer = 0;
        int workers = schedules.length;

        // 각 직원의 출근 희망 시각에 10분을 더해 "출근 인정 시각"으로 변환
        // 시각은 HHMM 형태의 정수(예: 9:58 -> 958)
        for(int i = 0 ; i < workers; i++){
            int tmp = schedules[i] + 10;

            // 분(mm)이 60을 넘어가면 시(hh)로 올림 처리
            // ex) 9:58 + 10 -> 9:68 -> 10:08
            if(tmp % 100 > 59){
                int hh = tmp / 100;
                int mm = tmp % 100;
                hh += 1;
                mm = mm % 60;

                tmp = hh * 100 + mm;
            }

            schedules[i] = tmp;
        }

        // 7일 중 토요일/일요일에 해당하는 인덱스 계산
        // startday: 1(월) ~ 7(일), j = 0..6 은 이벤트 시작일로부터 며칠째인지
        // 토요일까지 남은 일수: (6 - startday + 7) % 7 == (13 - startday) % 7
        // 일요일까지 남은 일수: (7 - startday + 7) % 7 == 7 - startday (단, startday=7이면 0)
        int sat = (13 - startday) % 7;
        int sun = 7 - startday;

        // 각 직원별로 평일(5일) 모두 인정 시각 내 출근했는지 검사
        for(int i = 0; i < workers; i++){
            int cnt = 0;

            for(int j = 0; j < 7; j++){
                // 주말은 이벤트에 영향이 없으므로 건너뜀
                if(j == sat || j == sun) continue;

                // 실제 출근 시각이 인정 시각 이하라면 정상 출근으로 카운트
                if(timelogs[i][j] <= schedules[i]) cnt++;
            }

            // 평일 5일 모두 정상 출근한 직원만 상품 대상
            if(cnt == 5) answer++;
        }

        return answer;
    }
}
