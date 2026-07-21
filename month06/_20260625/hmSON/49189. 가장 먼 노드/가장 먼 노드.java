import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class Solution {

    /**
     * 자료구조 및 알고리즘 : BFS
     * 1번 노드로부터 가장 멀리 떨어진 노드들의 개수만 알면 됨
     * 최장거리(maxDist), 노드 수(cnt) 기록,
     * 기록된 최장거리보다 더 긴 거리의 노드가 존재할 때마다 카운트 1로 초기화
     * 기록된 최장거리와 동일한 노드가 존재할 때마다 카운트 1 추가
     */

    static int maxDist = 0, cnt = 0, v;
    static List<Integer>[] graph;

    public int solution(int n, int[][] edges) {
        v = n;
        graph = new ArrayList[n];
        for(int i=0; i<n; i++) graph[i] = new ArrayList<>();

        // 인접 리스트 초기화
        for(int[] e : edges) {
            int v1 = e[0] - 1, v2 = e[1] - 1;
            graph[v1].add(v2);
            graph[v2].add(v1);
        }

        bfs();
        return cnt;
    }

    static void bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        // { 노드 번호, 1번 노드로부터의 거리 }
        q.add(new int[]{0, 0});
        // 방문 처리 배열
        boolean[] visited = new boolean[v];
        visited[0] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            // 현재 노드의 거리가 기록된 최장거리보다 길면 최장거리 갱신 + 카운트 1로 초기화
            // 현재 노드의 거리가 기록된 최장거리와 동일하면 카운트 1 추가
            if(cur[1] > maxDist) {
                maxDist = cur[1];
                cnt = 1;
            } else if(cur[1] == maxDist) cnt++;

            for(int next : graph[cur[0]]) {
                if(visited[next]) continue;
                visited[next] = true;
                q.add(new int[]{next, cur[1] + 1});
            }
        }
    }
}