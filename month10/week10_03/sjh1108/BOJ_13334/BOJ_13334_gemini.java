package week10_03.sjh1108.BOJ_13334;

import java.io.*;
import java.util.*;

/*
 * 스위핑 알고리즘 문제였습니다
 * 
 * 문제를 풀고 보니 스위핑 알고리즘 문제였는데요
 * 스위핑 알고리즘에 대해서 아는 것이 없어서
 * 제미나이한테 풀어달라고 했습니다
 * 
 * 그래서 그냥 이것도 같이 올리겠읍니다
 */
class Main {

    // 1. 스위핑을 위한 Event 클래스를 정의합니다. (x: 좌표, v: 값 +1 또는 -1)
    static class Event {
        final int x, v;

        Event(int x, int v) {
            this.x = x;
            this.v = v;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] arr = new int[N][2];
        StringTokenizer st;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());

            int h = Integer.parseInt(st.nextToken());
            int o = Integer.parseInt(st.nextToken());

            if (h <= o) {
                arr[i][0] = h;
                arr[i][1] = o;
            } else {
                arr[i][0] = o;
                arr[i][1] = h;
            }
        }


        int L = Integer.parseInt(br.readLine());

        // 2. 이벤트를 좌표(x) 기준으로 정렬할 PriorityQueue를 생성합니다.
        PriorityQueue<Event> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.x));

        // 3. 모든 구간을 순회하며 이벤트를 생성합니다.
        for (int i = 0; i < N; i++) {
            int start = arr[i][0];
            int end = arr[i][1];

            // 길이가 L 이하인 구간만 처리
            if (end - start <= L) {
                // 이 구간을 덮는 선분의 끝점 범위는 [end, start + L] 입니다.
                
                // 시작 이벤트: end 위치에서 +1
                pq.offer(new Event(end, 1));
                
                // 끝 이벤트: (start + L)의 *다음* 위치에서 -1
                pq.offer(new Event(start + L + 1, -1));
            }
        }

        int max = 0;
        int cnt = 0;
        
        // 4. 스윕 라인을 진행합니다.
        while (!pq.isEmpty()) {
            // 다음 이벤트를 가져옵니다.
            Event e = pq.poll();
            
            // 현재 겹침 개수를 갱신합니다.
            cnt += e.v;

            // !! 중요: 같은 좌표에 여러 이벤트가 있을 수 있으므로 모두 처리합니다.
            while (!pq.isEmpty() && pq.peek().x == e.x) {
                cnt += pq.poll().v;
            }

            // 현재 좌표의 모든 이벤트 처리가 끝난 후, max 값을 갱신합니다.
            max = Math.max(max, cnt);
        }

        System.out.println(max);
    }
}