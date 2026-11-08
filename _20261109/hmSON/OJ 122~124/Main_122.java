import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : BFS, 역방향 그래프
     *
     * 모든 간선의 비용이 1이고 각 정류장부터 산 정상 정류장까지의 거리를 구하는 BFS 문제
     * 도착점이 하나로 고정되어 있는 상태이며 모든 간선의 방향이 고정됨
     *
     * 산 정상 정류장을 출발점으로 바꾸고 다른 정류장까지의 거리를 역추적하면 BFS 1회만에 해결할 수 있음
     * 방향이 바뀌었으므로 모든 간선의 방향 정보 또한 반대로 입력해야 함(u->v 이므로 인접 리스트는 v->u로 등록)
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        List<Integer>[] graph = new List[n]; // 인접 리스트
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }

        // 방향 그래프이므로 인접 리스트 또한 단방향으로 등록
        // 또한 도착점부터 시작하는 BFS이므로 간선 또한 역방향으로 등록
        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            graph[v].add(u);
        }

        // 각 정류장과 산 정상 정류장의 거리
        int[] dist = new int[n];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[n-1] = 0; // 산 정상

        Queue<Integer> q = new ArrayDeque<>();
        q.offer(n-1);

        while(!q.isEmpty()) {
            int cur = q.poll();
            int d = dist[cur];

            for(int next : graph[cur]) {
                if(dist[next] != Integer.MAX_VALUE) continue; // 이미 방문한 정류장
                dist[next] = d + 1;
                q.offer(next);
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++) {
            // 초기값 그대로면 미방문 상태 -> -1 출력
            sb.append(dist[i] == Integer.MAX_VALUE ? -1 : dist[i]).append(" ");
        }
        System.out.println(sb);
    }

}