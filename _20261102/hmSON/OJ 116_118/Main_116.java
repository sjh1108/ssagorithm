import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 트리, BFS
     *
     * 소문이 한 턴에 한 칸씩 인접 마을로 퍼지므로, 각 마을이 소문을 듣는 시각은 시작 마을로부터의 최단 거리와 같음
     * 모든 이동 비용이 1이므로 BFS 한 번으로 전부 구할 수 있음
     *
     * 마을을 방문하는 순간이 곧 그 마을이 소문을 처음 듣는 시점이므로,
     * 큐에 넣을 때 해당 시각의 카운트를 증가시키면 시각별 마을 수가 자연히 집계됨
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int root = Integer.parseInt(st.nextToken()) - 1;

        // 오솔길의 방향이 주어지지 않으므로 무방향 인접 리스트
        List<Integer>[] graph = new List[n];
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            graph[v1].add(v2);
            graph[v2].add(v1);
        }

        // 마을 방문 처리
        boolean[] visited = new boolean[n];
        visited[root] = true;

        // 시각 t에 소문을 처음 듣는 마을의 수
        // 최악의 경우(경사 트리) 최대 거리는 n-1이므로 크기 n으로 선언
        int[] cntByTime = new int[n];
        cntByTime[0] = 1; // 시작 마을은 시각 0일 때 소문을 들음

        Queue<int[]> q = new ArrayDeque<>(); // { 마을 번호, 소문을 들은 시각 }
        q.offer(new int[]{root, 0});

        // BFS
        int max = 0; // 모든 마을이 소문을 듣게 되는 시각 T
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            if(cur[1] > max) max = cur[1];
            int nextTime = cur[1] + 1;

            for(int next : graph[cur[0]]) {
                if(visited[next]) continue; // 이미 들은 마을

                visited[next] = true;
                cntByTime[nextTime]++;
                q.offer(new int[]{next, nextTime});
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append(max).append("\n");

        for(int i=0; i<=max; i++) {
            sb.append(cntByTime[i]).append(" ");
        }
        System.out.println(sb);
    }

}