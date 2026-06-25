import java.util.*;

class Solution {

    /**
     * 자료구조 및 알고리즘 : BFS(DFS로도 동일하게 풀이 가능)
     * 각 컴퓨터 간 연결 관계를 그래프에 등록한 후 BFS를 통해 네트워크 탐색
     * BFS 메서드 호출 횟수 == 네트워크 수
     */

    static List<Integer>[] graph;
    // BFS 호출이 1회 이상이므로 큐 객체 재활용
    static Queue<Integer> q = new ArrayDeque<>();
    // 방문 처리 배열
    static boolean[] visited;

    public int solution(int n, int[][] coms) {
        int cnt = 0;

        graph = new ArrayList[n];
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
            for(int j=0; j<n; j++) {
                if(i == j || coms[i][j] == 0) continue;
                graph[i].add(j);
            }
        }

        // 각 컴퓨터를 시작점으로 하는 BFS
        // 이미 다른 네트워크를 통해서 방문한 컴퓨터는 시작점으로 잡지 않음
        // BFS 메서드 호출 횟수가 곧 존재하는 네트워크 수
        visited = new boolean[n];
        for(int i=0; i<n; i++) {
            if(visited[i]) continue;
            bfs(i);
            cnt++;
        }

        return cnt;
    }

    static void bfs(int start) {
        q.clear();
        q.add(start);
        visited[start] = true;

        while(!q.isEmpty()) {
            int cur = q.poll();

            for(int next : graph[cur]) {
                if(visited[next]) continue;
                q.add(next);
                visited[next] = true;
            }
        }
    }

}