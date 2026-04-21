package _20260420.thdwngjs.BOJ_22254;

import java.io.*;
import java.util.*;

// 이분 탐색 + 그리디 풀이
// 생산 라인 수를 이분 탐색하고, 각 라인 수에 대해
// 우선순위 큐로 가장 여유 있는 라인에 작업을 배정하여
// 제한 시간 X 내에 모든 공정을 처리할 수 있는지 확인한다.
class Main {
    private static int N, X; // N: 공정 수, X: 제한 시간
    private static int[] process_time; // 각 공정의 소요 시간

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

        // 이분 탐색: 최소 생산 라인 수를 찾는다
        int l = 1;
        int r = N;
        while(l <= r) {
            int mid = (l + r) / 2;

            if(can_process(mid)) {
                r = mid - 1; // 더 적은 라인으로 가능한지 시도
            } else {
                l = mid + 1; // 라인이 부족하므로 늘린다
            }
        }

        System.out.println(l);
    }

    // mid개의 생산 라인으로 모든 공정을 X 시간 내에 처리할 수 있는지 판별
    public static boolean can_process(int mid) {
        // 최소 힙: 각 라인의 현재 누적 작업 시간을 관리
        PriorityQueue<Integer> q = new PriorityQueue<>();

        // mid개의 라인을 0시간으로 초기화
        for(int i = 0; i < mid; i++) {
            q.offer(0);
        }

        // 각 공정을 현재 가장 여유 있는(누적 시간이 가장 작은) 라인에 배정
        for(int i = 1; i <= N; i++) {
            int time = q.poll();
            if(time + process_time[i] > X) return false; // 제한 시간 초과
            q.offer(time + process_time[i]);
        }
        return true;
    }
}