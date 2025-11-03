import java.io.*;
import java.util.*;

class Main {

    static int V, E;
    static ArrayList<Integer>[] graph;
    static int[] visited; // 0: 미방문, 1: 그룹A, -1: 그룹B

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            visited = new int[V + 1];
            graph = new ArrayList[V + 1];
            for (int i = 1; i <= V; i++) 
                graph[i] = new ArrayList<>();

            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                graph[a].add(b);
                graph[b].add(a);
            }

            boolean isBipartite = true;
            // 그래프가 여러 컴포넌트로 나뉘어 있을 수 있으므로 모든 정점을 확인
            for (int i = 1; i <= V; i++) {
                // 아직 방문하지 않은 정점이 있다면 BFS 시작
                if (visited[i] == 0) {
                    if (!bfs(i)) {
                        isBipartite = false;
                        break;
                    }
                }
            }

            if (isBipartite)    System.out.println("YES");
            else                System.out.println("NO");
            
        }
    }

    // BFS를 이용한 색칠하기. 이분 그래프가 아니면 false 반환
    static boolean bfs(int start) {
        Queue<Integer> q = new ArrayDeque<>();
        
        q.add(start);
        visited[start] = 1; // 시작 노드를 1번 그룹으로 설정

        while (!q.isEmpty()) {
            int cur = q.poll();

            for (int next : graph[cur]) {
                // 인접한 노드가 아직 색칠되지 않았다면, 반대 색으로 칠하고 큐에 추가
                if (visited[next] == 0) {
                    visited[next] = -visited[cur]; // 1 -> -1, -1 -> 1
                    q.add(next);
                }

                // 인접한 노드가 이미 칠해져 있는데, 현재 노드와 같은 색이라면 이분 그래프가 아님
                else if (visited[next] == visited[cur]) 
                    return false; 
            }
        }
        return true;
    }
}