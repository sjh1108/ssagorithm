import java.io.*;
import java.util.*;

public class Main_25195_Yes_or_yes {

    /*
     * BOJ_25195 : Yes or yes (Gold_4)
     * 자료구조 및 알고리즘 : DFS(BFS로도 풀이 가능하나, DFS 우위)
     *
     * [문제 요약]
     * - 방향 그래프가 주어지고, 일부 정점은 팬클럽이 있는 정점이다.
     * - 시작 정점 1번에서 출발했을 때,
     *   팬클럽 정점을 한 번도 만나지 않고 도달할 수 있는 "막다른 경로(리프)"가 존재하면 "yes"를 출력한다.
     * - 그런 경로가 하나도 없으면 "Yes"를 출력한다.
     *
     * [핵심 아이디어]
     * - 사이클이 없는 방향 그래프(DAG)이므로, DFS로 한 경로를 끝까지 따라가며 판정할 수 있다.
     * - 리프 정점(더 이상 갈 곳이 없는 정점)에 도달했다는 것은
     *   그 경로가 팬클럽을 만나지 않고 끝까지 살아남았다는 뜻이므로 바로 "yes"를 출력하고 종료하면 된다.
     * - 반대로, 가능한 모든 경로가 팬클럽 정점에서 막히거나 팬클럽으로만 이어진다면 최종 답은 "Yes"이다.
     * - 팬클럽 정점은 "방문 불가 정점"처럼 처리하면 자연스럽게 제외할 수 있다.
     *
     * [구현 메모]
     * - visited[]는 두 역할을 함께 가진다.
     *   1) 팬클럽 정점 표시
     *   2) 현재 DFS 경로에서의 방문 처리
     * - init()에서 팬클럽 정점을 먼저 visited=true로 표시해 둔다.
     * - main()에서 시작점(0번 정점)이 팬클럽이면 바로 "Yes" 출력.
     * - dfs(cur):
     *   - 현재 정점의 인접 리스트가 비어 있으면 리프 도달 -> "yes" 출력 후 종료
     *   - 다음 정점이 visited면 스킵
     *   - 아니면 방문 처리 후 재귀, 돌아오면 false로 되돌려 다른 경로 탐색
     * - 문제에서 DAG가 보장되므로, 리프 판정은 graph[cur].isEmpty()로 충분하다.
     *
     * [시간 복잡도]
     * - 최악의 경우 모든 정점/간선을 따라 탐색하므로 O(V + E)
     */

    static int v;
    static List<Integer>[] graph;
    static boolean[] visited;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        graph = new ArrayList[v];
        visited = new boolean[v];
        for(int i=0; i<v; i++) graph[i] = new ArrayList<>();

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            graph[v1].add(v2);
        }

        int s = Integer.parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<s; i++) {
            int pt = Integer.parseInt(st.nextToken()) - 1;
            visited[pt] = true; // 팬클럽 정점은 방문 불가처럼 처리
        }
    }

    static void dfs(int cur) {
        // 더 갈 곳이 없는 리프에 도달했다는 것은
        // 팬클럽을 만나지 않고 끝까지 도달 가능한 경로가 존재한다는 뜻
        if(graph[cur].isEmpty()) {
            System.out.println("yes");
            System.exit(0);
        }

        for(int next : graph[cur]) {
            if(visited[next]) continue;
            visited[next] = true;
            dfs(next);
            visited[next] = false;
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        // 시작점이 팬클럽이면 애초에 출발 불가
        if(visited[0]) {
            System.out.println("Yes");
            return;
        }

        visited[0] = true;
        dfs(0);
        System.out.println("Yes");
    }

}