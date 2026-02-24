import java.io.*;
import java.util.*;

public class Main_13913_숨바꼭질_4 {

    /*
     * BOJ_13913 : 숨바꼭질 4 (Gold_4)
     * 자료구조 및 알고리즘 : BFS, 역추적
     *
     * [문제 요약]
     * - 수빈이는 start에서 시작해 end로 이동한다.
     * - 1초에 할 수 있는 이동은 3가지: x-1, x+1, 2*x
     * - 최소 시간과, 그때의 이동 경로(방문한 위치들)를 출력한다.
     *
     * [핵심 아이디어]
     * - 모든 이동의 비용이 1이므로 최단거리는 BFS로 구할 수 있다.
     * - 최단 시간뿐 아니라 "경로"도 출력해야 하므로,
     *   각 정점(위치)에 처음 도달했을 때의 이전 위치를 prev[]에 저장해 둔다.
     * - BFS에서 목적지(end)에 처음 도달한 순간이 최단거리이므로 즉시 종료하고,
     *   prev[]를 따라 역추적하여 경로를 복원한다.
     *
     * [구현 메모]
     * - 탐색 범위는 0 ~ 100000 (INF = 100001)로 제한한다.
     * - 예외 처리:
     *   1) start == end : 시간 0, 경로는 start 하나.
     *   2) start > end  : 뒤로만 이동(x-1)하는 것이 최단이므로
     *      (start, start-1, ..., end) 를 그대로 출력한다. (BFS 불필요)
     * - prev[start] = -1 로 시작점을 표시하고,
     *   역추적은 end부터 prev를 따라가며 -1을 만날 때까지 진행한다.
     * - 출력 시에는 역순으로 쌓이는 값을 Deque에 담아 뒤에서부터 출력한다.
     *
     * [시간 복잡도]
     * - BFS: 각 위치를 최대 1번 방문 -> O(INF) = O(100000)
     * - 역추적: 최단 경로 길이만큼 -> O(최단거리)
     */

    static final int INF = 100_001;
    static int[] prev;
    static int start, end;
    static StringBuilder sb = new StringBuilder();

    static void printResult(int time) {
        sb.append(time).append("\n");

        Deque<Integer> stack = new ArrayDeque<>();
        int cur = end;
        // prev를 따라가며 end -> start 역순으로 수집 (start의 prev는 -1)
        while(cur >= 0) {
            stack.addLast(cur);
            cur = prev[cur];
        }

        // 뒤집어서 start -> end 순으로 출력
        while(!stack.isEmpty()) {
            sb.append(stack.pollLast()).append(" ");
        }

        System.out.println(sb);
        System.exit(0);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        start = sc.nextInt();
        end = sc.nextInt();

        // 시작과 끝이 같으면 이동 필요 없음
        if(start == end) {
            System.out.println(0 + "\n" + start);
            return;
        }

        // start > end면 -1만 반복하는 것이 최단
        if(start > end) {
            sb.append(start - end).append("\n");
            int cur = start;
            while(cur >= end) {
                sb.append(cur--).append(" ");
            }

            System.out.println(sb);
            return;
        }

        boolean[] visited = new boolean[INF];
        prev = new int[INF];
        visited[start] = true;
        prev[start] = -1; // 시작점 표식

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{start, 0}); // {현재 위치, 시간}

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int id = cur[0], time = cur[1];

            // 1) id - 1
            if(id-1 >= 0 && !visited[id-1]) {
                prev[id-1] = id;               // 경로 복원용 이전 위치 기록
                if(id-1 == end) printResult(time+1);
                visited[id-1] = true;
                q.add(new int[]{id-1, time+1});
            }

            // 2) id + 1
            if(id+1 < INF && !visited[id+1]) {
                prev[id+1] = id;
                if(id+1 == end) printResult(time+1);
                visited[id+1] = true;
                q.add(new int[]{id+1, time+1});
            }

            // 3) id * 2
            if(id*2 < INF && !visited[id*2]) {
                prev[id*2] = id;
                if(id*2 == end) printResult(time+1);
                visited[id*2] = true;
                q.add(new int[]{id*2, time+1});
            }
        }
    }

}