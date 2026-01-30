import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_1167_트리의_지름 {

    /*
     * BOJ_1167 : 트리의 지름 (Gold_2)
     * 자료구조 및 알고리즘 : 트리, DFS, 트리의 지름
     *
     * [문제 요약]
     * - 임의의 두 점 사이의 거리 중 가장 긴 것(트리의 지름)을 구하는 문제.
     * - 각 간선에는 가중치(거리)가 있다.
     *
     * [핵심 아이디어]
     * - 트리의 지름은 특정 노드를 중심으로 '가장 긴 자식 가지'와 '두 번째로 긴 자식 가지'의 합으로 결정될 수 있다.
     * - 임의의 루트(0번)에서 DFS를 수행하며, 리프 노드에서부터 거리를 누적해 올라온다(Post-order).
     * - 각 노드(root)에서:
     * 1. 자식 노드들로부터 뻗어나가는 경로 중 가장 긴 값(maxLen)과 두 번째로 긴 값(maxLen2)을 찾는다.
     * 2. (maxLen + maxLen2)가 현재 노드를 꺾임 점으로 하는 지름 후보이므로, 전체 max값과 비교해 갱신한다.
     * 3. 부모 노드에게는 자신이 가진 가장 긴 경로(maxLen)만을 전달하여 경로를 잇는다.
     *
     * [구현 메모]
     * - 입력이 '정점 번호' -> '연결된 정점' -> '거리' ... -> '-1' 순서로 주어진다.
     * - 0-based index로 변환하여 저장하므로, 종료 조건인 -1은 -2가 된다.
     * - visited 배열을 사용하여 부모 방향으로 역류하는 것을 방지한다.
     *
     * [시간 복잡도]
     * - 트리 순회와 동일하게 모든 노드와 간선을 한 번씩 방문하므로 O(V).
     */

    static class Node {
        int id, cost;

        public Node(int id, int cost) {
            this.id = id;
            this.cost = cost;
        }
    }

    static int v, max = 0; // max: 트리의 지름(글로벌 최대값)
    static List<Node>[] tree;
    static boolean[] visited;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        v = Integer.parseInt(br.readLine());

        visited = new boolean[v];
        tree = new ArrayList[v];
        for(int i=0; i<v; i++) {
            tree[i] = new ArrayList<>();
        }

        for(int i=0; i<v; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int from = Integer.parseInt(st.nextToken()) - 1; // 시작 정점 (0-based)

            while(true) {
                int to = Integer.parseInt(st.nextToken()) - 1; // 도착 정점
                if(to == -2) break; // 입력의 끝인 -1을 읽으면 (-1 -1 = -2) 종료

                int cost = Integer.parseInt(st.nextToken());
                tree[from].add(new Node(to, cost));
            }
        }
    }

    // 현재 노드(root)를 기준으로 하위 트리에서 가장 긴 경로의 길이를 반환
    static int dfs(int root) {
        int maxLen = 0;  // 가장 긴 경로
        int maxLen2 = 0; // 두 번째로 긴 경로

        for(Node next : tree[root]) {
            if(visited[next.id]) continue;

            visited[next.id] = true;
            // 자식 노드로부터 가장 긴 경로를 받아와 현재 간선 비용을 더함
            int len = dfs(next.id) + next.cost;

            // 가장 긴 경로와 두 번째로 긴 경로 갱신 로직
            if(len > maxLen) {
                maxLen2 = maxLen; // 기존 1등이 2등으로 밀려남
                maxLen = len;     // 새로운 1등
            } else if(len > maxLen2) {
                maxLen2 = len;    // 새로운 2등
            }
        }

        // 현재 노드(root)에서 갈라지는 두 경로의 합이 지름 후보가 됨
        int sum = maxLen + maxLen2;
        if(sum > max) max = sum;

        // 부모에게는 가장 긴 경로 하나만 전달 (선형으로 이어져야 하므로)
        return maxLen;
    }

    public static void main(String[] args) throws Exception {
        init();

        visited[0] = true;
        dfs(0); // 임의의 루트(0번)에서 탐색 시작

        System.out.println(max);
    }

}