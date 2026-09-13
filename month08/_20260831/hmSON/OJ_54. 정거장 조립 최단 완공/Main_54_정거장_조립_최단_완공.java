import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 위상 정렬, DP, 임계 경로
     * 기존 위상 정렬 문제에 각 공정별 진행 시간이 추가로 주어지는 문제
     * 모든 공정이 끝났을 때의 시간과 각 공정의 시작 시각을 출력해야 함
     *
     * 각 공정의 시작 시각을 기록하는 배열을 추가하여 선행 공정들 중 제일 늦게 끝나는 공정의 종료 시각을 기록
     * 별도로 매 공정을 시작할 때마다 전체 공정의 종료 시각을 별도로 기록해야 함
     */

    static int v; // 공정 수
    static int[] prev, cost; // 각 공정별 선행 공정 수(0이면 즉시 진행 가능), 각 공정별 소요 시간
    static long[] started; // 각 공정별 시작 시각
    static List<Integer>[] graph; // 인접 리스트

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        prev = new int[v];
        cost = new int[v];
        started = new long[v];
        graph = new ArrayList[v];

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<v; i++) {
            graph[i] = new ArrayList<>();
            cost[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;

            graph[v1].add(v2);
            prev[v2]++;
        }

        // 반환값 : 전체 공정의 총 진행 시간
        // 위상 정렬중 사이클이 발생하면 -1 출력 후 종료
        long res = topologicalSort();
        if(res == -1) {
            System.out.println("-1");
            return;
        }

        // 전체 공정의 총 진행 시간 및 각 공정별 시작 시각 출력
        StringBuilder sb = new StringBuilder();
        sb.append(res).append("\n");
        for(int i=0; i<v; i++) {
            sb.append(started[i]).append(" ");
        }
        System.out.println(sb);
    }

    // 위상 정렬 메서드
    // 위상 정렬 후 전체 공정의 총 진행 시간 반환
    static long topologicalSort() {
        long endTime = 0; // 전체 공정의 총 진행 시간
        int cnt = 0; // 진행 공정 수

        // 직전 문제와 달리 사전순 처리를 요구하지 않으므로 단순 큐로 처리
        Queue<Integer> q = new ArrayDeque<>();
        // 선행 공정이 없는 공정 번호를 큐에 등록
        for(int i=0; i<v; i++) {
            if(prev[i] == 0) q.add(i);
        }

        while(!q.isEmpty()) {
            int cur = q.poll();
            cnt++; // 진행 공정 수 카운트

            // 전체 공정의 총 진행 시간 갱신 : 매 순간 제일 늦게 끝난 공정의 종료 시각을 기록
            long elapsed = started[cur] + cost[cur]; // 현 공정의 종료 시각
            if(endTime < elapsed) endTime = elapsed;

            for(int next : graph[cur]) {
                if(elapsed > started[next]) started[next] = elapsed; // 가장 늦게 끝난 선행 공정의 종료 시각 기록
                prev[next]--; // 다음 공정의 선행 공정 수 감소
                if(prev[next] == 0) q.add(next); // 선행 공정 수가 0이 된 공정을 큐에 등록
            }
        }

        // cnt == v라면 모든 공정을 진행했음.
        // 아니라면 사이클 발생으로 인해 모든 공정을 진행하지 못했음.
        return cnt == v ? endTime : -1;
    }

}