import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Main_9205_맥주_마시면서_걸어가기 {

    /*
     * BOJ_9205 : 맥주 마시면서 걸어가기 (Gold_5)
     * 자료구조 및 알고리즘 : BFS
     *
     * [문제 요약]
     * - 상근이네 집에서 출발하여 맥주를 마시며 락 페스티벌에 가려고 한다.
     * - 맥주 한 박스에는 20병이 있고, 50미터에 한 병씩 마신다. (최대 이동 가능 거리 1000m)
     * - 중간에 편의점에 들르면 빈 병을 버리고 새 맥주를 살 수 있다. (다시 20병 채움 -> 1000m 리셋)
     * - 페스티벌에 도착할 수 있으면 "happy", 없으면 "sad"를 출력하라.
     *
     * [핵심 아이디어]
     * - 맥주의 개수를 구체적으로 카운트할 필요 없이, '두 지점 사이의 거리가 1000m 이하인가?'가 핵심이다.
     * - 거리가 1000m 이하라면 두 지점은 연결된 것(이동 가능)으로 간주하여 간선을 연결한다.
     * - 집(시작점), 편의점들, 페스티벌(도착점)을 노드로 보고, 연결된 그래프에서 시작점 -> 도착점 경로가 존재하는지 BFS로 확인한다.
     *
     * [구현 메모]
     * - MAX_DIST : 20병 * 50m = 1000m.
     * - point[][] : 각 장소의 x, y 좌표 저장 (0: 집, 1~cs: 편의점, cs+1: 페스티벌).
     * - graph : 맨해튼 거리가 1000 이하인 노드끼리 연결한 인접 리스트.
     * - 입력을 받으면서 동시에 이전에 입력받은 모든 노드와 거리를 계산하여 그래프를 구성한다.
     *
     * [시간 복잡도]
     * - 노드 수 V = 편의점 수(N) + 2 (최대 102개).
     * - 그래프 구성 : 이중 반복문을 돌며 모든 쌍의 거리 계산 O(V^2).
     * - BFS 탐색 : 모든 정점과 간선 확인 O(V + E). 여기서 E는 최대 V^2.
     * - V가 작으므로(<=102) 충분히 빠른 시간 내에 해결 가능.
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    static final int MAX_DIST = 1000; // 맥주 20병 * 50m
    static int cs; // 편의점 개수
    static int[][] point; // 좌표 저장 배열 (0: x, 1: y)
    static List<Integer>[] graph; // 인접 리스트

    static void init() throws Exception {
        cs = Integer.parseInt(br.readLine());
        point = new int[2][cs+2]; // 0: 집, 1~cs: 편의점, cs+1: 페스티벌
        graph = new ArrayList[cs+2];

        for(int i=0; i<cs+2; i++) {
            graph[i] = new ArrayList<>();

            StringTokenizer st = new StringTokenizer(br.readLine());
            point[0][i] = Integer.parseInt(st.nextToken());
            point[1][i] = Integer.parseInt(st.nextToken());

            // 현재 입력받은 노드(i)와 이전에 입력받은 모든 노드(j) 사이의 거리 계산
            for(int j=0; j<i; j++) {
                int dist = Math.abs(point[0][i] - point[0][j]) + Math.abs(point[1][i] - point[1][j]);

                // 거리가 1000m 이하라면 이동 가능하므로 양방향 간선 추가
                if(dist > MAX_DIST) continue;

                graph[i].add(j);
                graph[j].add(i);
            }
        }
    }

    static void bfs() {
        int end = cs+1; // 도착점(락 페스티벌) 인덱스
        boolean[] visited = new boolean[cs+2];
        visited[0] = true;

        Queue<Integer> q = new ArrayDeque<>();
        q.add(0); // 시작점(집) 큐에 삽입

        while(!q.isEmpty()) {
            int cur = q.poll();

            for(int next : graph[cur]) {
                if(visited[next]) continue;

                // 목적지에 도달하면 happy 출력 후 종료
                if(next == end) {
                    sb.append("happy").append("\n");
                    return;
                }

                visited[next] = true;
                q.add(next);
            }
        }

        // 큐가 빌 때까지 목적지에 도달하지 못하면 sad 출력
        sb.append("sad").append("\n");
    }

    public static void main(String[] args) throws Exception {
        int tc = Integer.parseInt(br.readLine());
        while(tc-- > 0) {
            init();
            bfs();
        }

        System.out.println(sb);
    }

}