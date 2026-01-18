package week01_04.sjh1108.BOJ_22254;

import java.io.*;
import java.util.*;

// 백준 22254 - 공정 컨설턴트 호석 (Parametric Search + Priority Queue)
class Main {
    private static int N, X; // N: 선물 개수, X: 제한 시간
    private static int[] process_time; // 각 선물의 공정 소요 시간
 
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        X = Integer.parseInt(st.nextToken());
 
        st = new StringTokenizer(br.readLine());
        process_time = new int[N + 1];
        for(int i = 1; i <= N; i++) {
            process_time[i] = Integer.parseInt(st.nextToken());
        }
 
        // 이분 탐색 (Parametric Search)
        // 공정 라인의 개수 K를 1 ~ N 사이에서 탐색하여 최소값을 찾음
        int l = 1;
        int r = N;
        int ans = N; // 정답 초기화

        while(l <= r) {
            int mid = (l + r) / 2; // mid: 테스트할 공정 라인의 개수
 
            // mid개의 라인으로 제한 시간 X 내에 처리가 가능한지 확인
            if(can_process(mid)) {
                ans = mid;      // 가능하면 정답 후보로 저장
                r = mid - 1;    // 최소값을 찾기 위해 라인 개수를 줄여서 재시도
            } else {
                l = mid + 1;    // 불가능하면 라인 개수를 늘려야 함
            }
        }

        System.out.println(ans);
    }
 
    // 결정 함수: k개의 라인으로 모든 선물을 X 시간 내에 처리할 수 있는가?
    public static boolean can_process(int k) {
        // 우선순위 큐 (Min-Heap): 각 라인의 현재 '누적 작업 시간'을 저장
        // 가장 빨리 작업이 끝나는 라인에 다음 작업을 배정하기 위함 (Greedy)
        PriorityQueue<Integer> q = new PriorityQueue<>();
        
        // k개의 라인 초기화 (0초부터 시작)
        for(int i = 0; i < k; i++) {
            q.offer(0);
        }
 
        // 모든 선물(작업)을 순서대로 처리
        for(int i = 1; i <= N; i++) {
            // 현재 가장 여유로운(누적 시간이 가장 적은) 라인을 선택
            int time = q.poll();
            
            // 해당 라인에 작업을 추가했을 때 제한 시간 X를 초과하면 불가능
            if(time + process_time[i] > X) return false;
            
            // 작업 수행 후 갱신된 누적 시간을 큐에 다시 삽입
            q.offer(time + process_time[i]);
        }
        
        // 모든 작업을 제한 시간 내에 배정 완료했다면 성공
        return true;
    }
}