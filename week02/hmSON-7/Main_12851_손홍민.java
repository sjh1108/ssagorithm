package Rank4.gold_4;

import java.io.*;
import java.util.*;

public class Main_12851 {

    // 각 인덱스별 방문한 최소시간 기록
    static int[] visited = new int[100_001];

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 시작 지점과 도착 지점
        // 이미 두 지점의 위치가 같다면 즉시 종료
        int start = Integer.parseInt(st.nextToken());
        int end = Integer.parseInt(st.nextToken());
        if(start == end) {
            System.out.println(0 + "\n" + 1);
            System.exit(0);
        }
        Arrays.fill(visited, Integer.MAX_VALUE);
        visited[start] = 0;

        // 동일한 시간대에 end 위치로 도착한 경로의 횟수를 기록
        // BFS를 통해 방문 시간 순서대로 방문 처리
        int endCnt = 0;
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{start, 0});
        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int curIdx = cur[0], curTime = cur[1];
            int[] next = {curIdx-1, curIdx+1, curIdx*2};
            for(int x : next) {
                if(x > 100_000 || x < 0 || visited[x] < curTime+1) continue;
                visited[x] = curTime+1;
                if(x == end) {
                    endCnt++;
                    continue;
                }
                q.add(new int[]{x, curTime+1});
            }
        }
        System.out.println(visited[end] + "\n" + endCnt);
    }

}
