package _20260413.thdwngjs.BOJ_2593;

import java.io.*;
import java.util.*;

// BOJ 2593 - 엘리베이터
// N층 건물에 M개의 엘리베이터가 있을 때, A층에서 B층까지 최소 환승 횟수로 이동하는 경로를 구하는 문제
// 엘리베이터를 노드로 보고 BFS를 수행하여 최소 환승 경로를 탐색
class Main {
    static int N, M;        // N: 총 층 수, M: 엘리베이터 수
    static int[] xs, ys;    // xs[i]: i번 엘리베이터의 시작 층, ys[i]: 정차 간격
    static int[] parent;    // 경로 역추적용 부모 엘리베이터 번호
    static int[] dist;      // 출발 층에서 해당 엘리베이터까지 탑승 횟수

    // i번 엘리베이터가 floor층에 정차하는지 확인
    static boolean stopsAt(int i, int floor) {
        if (floor < xs[i] || floor > N) return false;
        return (floor - xs[i]) % ys[i] == 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        xs = new int[M + 1];
        ys = new int[M + 1];
        parent = new int[M + 1];
        dist = new int[M + 1];
        Arrays.fill(parent, -1);
        Arrays.fill(dist, -1);

        // 각 엘리베이터의 시작 층과 정차 간격 입력
        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            xs[i] = Integer.parseInt(st.nextToken());
            ys[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken()); // 출발 층
        int B = Integer.parseInt(st.nextToken()); // 도착 층

        // BFS 초기화: A층에 정차하는 모든 엘리베이터를 큐에 삽입
        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= M; i++) {
            if (stopsAt(i, A)) {
                q.add(i);
                parent[i] = 0;  // 시작점 표시 (부모 없음)
                dist[i] = 1;    // 첫 번째 탑승
            }
        }

        // BFS: 현재 엘리베이터가 정차하는 모든 층에서 환승 가능한 엘리베이터 탐색
        while (!q.isEmpty()) {
            int cur = q.poll();
            // 현재 엘리베이터의 모든 정차 층을 순회
            for (int floor = xs[cur]; floor <= N; floor += ys[cur]) {
                // 해당 층에서 환승할 수 있는 다른 엘리베이터 탐색
                for (int j = 1; j <= M; j++) {
                    if (dist[j] != -1) continue;       // 이미 방문한 엘리베이터 스킵
                    if (!stopsAt(j, floor)) continue;   // 이 층에 정차하지 않으면 스킵
                    parent[j] = cur;
                    dist[j] = dist[cur] + 1;
                    q.add(j);
                }
            }
        }

        // B층에 정차하는 엘리베이터 중 최소 탑승 횟수인 것을 선택
        int best = -1;
        for (int i = 1; i <= M; i++) {
            if (dist[i] == -1) continue;         // 도달 불가능한 엘리베이터 스킵
            if (!stopsAt(i, B)) continue;         // B층에 정차하지 않으면 스킵
            if (best == -1 || dist[i] < dist[best]) {
                best = i;
            }
        }

        // 도달 불가능한 경우
        if (best == -1) {
            System.out.println(-1);
            return;
        }

        // parent 배열을 따라 경로 역추적 (스택으로 순서 뒤집기)
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int cur = best;
        while (cur != 0) {
            stack.push(cur);
            cur = parent[cur];
        }

        // 결과 출력: 탑승 횟수 + 탑승 순서대로 엘리베이터 번호
        StringBuilder sb = new StringBuilder();
        sb.append(dist[best]).append('\n');
        while (!stack.isEmpty()) {
            sb.append(stack.pop()).append('\n');
        }
        System.out.print(sb);
    }
}