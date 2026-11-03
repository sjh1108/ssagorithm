import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 그리디, BFS, 정렬
     *
     * 모든 마을이 거리 K 이내의 중계탑에게 전파를 받도록 할 때 필요한 탑의 최소 개수를 구해야 함
     *
     * 핵심은 "아직 덮이지 않은 마을 중 가장 깊은 곳"부터 처리하는 것
     * 그 마을이 전파 범위에 걸치게 하기 위해 탑을 세울 최선의 위치는
     * -> 그 마을에서 루트 방향으로 정확히 K칸 올라간 지점
     * 자기보다 깊은 마을은 이미 모두 처리되었으므로 아래쪽을 신경 쓸 필요가 없음
     *
     * 전체 흐름
     * 1. 루트 기준 각 마을의 깊이를 구함 (BFS)
     * 2. 깊이 내림차순으로 마을 정렬
     * 3. 배열 순회하면서 아직 전파받지 못한 마을을 만나면 K칸 위에 탑을 세우고, 그 지점에서 거리 K까지 전파 (BFS)
     *
     * 전파 여부와 BFS 방문 여부를 하나의 int 배열(spread)로 통합 관리하며 총 3개 상태가 존재
     * - 0            : 아직 어떤 탑의 전파도 받지 못함
     * - towerIdx와 일치 : 이번 탑의 BFS에서 이미 방문함
     * - 그 외 0이 아닌 값 : 이전 탑에게 전파받았으나 이번 BFS에서는 아직 미방문
     * 이렇게 하면 매 BFS마다 방문 배열을 초기화할 필요가 없어짐
     */

    static int n, power;
    static int[] parent, spread; // 각 마을의 상위 번호, 전파 여부 기록
    static int[][] distByRoot;
    static List<Integer>[] graph;
    static Queue<int[]> q = new ArrayDeque<>(); // BFS용 큐

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        power = Integer.parseInt(st.nextToken());

        // 오솔길의 방향이 주어지지 않으므로 무방향 인접 리스트
        graph = new List[n];
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

        // 마을이 하나 뿐이면 반드시 그 마을에 탑을 세워야 함
        // 출력이 충분히 크면 어디에 세워도 전체를 덮을 수 있음
        if(n == 1 || power >= n-1) {
            System.out.println(1);
            return;
        }

        // 출력이 0이면 전파가 자기 마을에만 닿으므로 모든 마을에 탑이 필요
        if(power == 0) {
            System.out.println(n);
            return;
        }

        // 정렬 후에도 마을 번호를 알아야 하므로 깊이와 함께 저장
        distByRoot = new int[n][2]; // { 마을 번호, 루트로부터의 깊이 }
        for(int i=0; i<n; i++) {
            distByRoot[i][0] = i;
        }

        parent = new int[n];
        Arrays.fill(parent, -1); // 루트는 상위 마을이 없음을 -1로 표시
        measureDistBFS();

        // 깊이 내림차순 : 가장 깊은 마을부터 처리해야 그리디 성립
        Arrays.sort(distByRoot, (a, b) -> Integer.compare(b[1], a[1]));

        spread = new int[n];
        int cnt = 0;
        for(int[] village : distByRoot) {
            if(spread[village[0]] != 0) continue; // 이미 전파 범위에 있는 마을
            cnt++;
            spreadBFS(village[0], cnt); // 탑 번호를 1부터 부여해 0(미전파)과 구분
        }

        System.out.println(cnt);
    }

    // 거리 측정 BFS
    // 루트(0번)를 기준으로 각 마을의 깊이와 상위 마을을 기록
    static void measureDistBFS() {
        q.offer(new int[]{0, 0});

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int nextDist = cur[1] + 1;

            for(int next : graph[cur[0]]) {
                if(parent[cur[0]] == next) continue; // 상위 마을로의 역류 방지
                parent[next] = cur[0];
                distByRoot[next][1] = nextDist;
                q.offer(new int[]{next, nextDist});
            }
        }
    }

    // 중계탑 전파 BFS
    // 덮이지 않은 마을에서 K칸 위에 탑을 세우고, 그 지점에서 거리 K까지 전파
    static void spreadBFS(int villageIdx, int towerIdx) {
        // 제일 깊은 마을로부터 루트 방향으로 거리가 K인 마을 찾기
        // 루트에 도달하면 더 올라갈 수 없으므로 그 지점에 세움
        for(int i=0; i<power && villageIdx>0; i++) {
            villageIdx = parent[villageIdx];
        }

        spread[villageIdx] = towerIdx; // 탑이 선 마을도 전파 대상
        q.offer(new int[]{villageIdx, 0});
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int nextDist = cur[1] + 1;
            if(nextDist > power) continue; // 출력 범위를 벗어나면 더 퍼지지 않음

            for(int next : graph[cur[0]]) {
                // 이번 탑의 BFS에서 이미 방문한 마을만 건너뜀
                // 이전 탑에게 전파받은 마을도 경로상 거쳐 가야 할 수 있으므로 다시 방문
                if(spread[next] == towerIdx) continue;
                spread[next] = towerIdx;
                q.offer(new int[]{next, nextDist});
            }
        }
    }

}