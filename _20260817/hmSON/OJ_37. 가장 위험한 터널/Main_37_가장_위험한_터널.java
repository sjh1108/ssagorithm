import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 트리, BFS, LCA(최소 공통 조상)
     * 주어지는 기지의 수는 최대 10만, 질의의 수도 최대 10만
     * 질의에서 주어지는 두 기지 간 이동 경로 내 최대 위험도와, 최대 위험 구간 수를 구해야 함
     * 트리 구조 상에서 두 기지 간 이동 경로는 반드시 공통 상위 기지를 거쳐야 함
     * 따라서 최소 공통 조상 알고리즘은 그대로 유지
     *
     * 대신 경로 내 위험도 최대값과 최대 위험 구간 수 또한 전처리 및 병합 과정이 추가되어야 함
     * 최대 위험도 및 구간 수 또한 상위 기지 정보와 동일하게 이진 점프를 이용한 배열 전처리 수행
     * 각 터널의 위험도는 최대 10^12이므로 long 타입 사용해야 함
     */

    // 목표 방향 기지의 번호와 터널의 위험도를 관리하는 노드 클래스
    static class Node {
        int id;
        long risk;

        public Node(int id, long risk) {
            this.id = id;
            this.risk = risk;
        }
    }

    static int n, k = 1, curCnt;
    static long curMax;
    static int[][] up, cnt; // 상위 기지 테이블, 각 경로별 최대 위험 구간 수 테이블
    static long[][] max; // 각 경로별 최대 위험도 테이블
    static int[] depth; // 각 기지별 깊이 테이블
    static List<Node>[] graph;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());
        // 노드 수가 n인 트리의 최대 깊이 == n임(경사 트리)
        // 따라서 2^k >= n이 되는 k값을 미리 구해두기
        while((1 << k) < n) k++;

        graph = new List[n];
        for(int i=0; i<n; i++) graph[i] = new ArrayList<>();

        for(int i=0; i<n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            long risk = Long.parseLong(st.nextToken());
            graph[v1].add(new Node(v2, risk));
            graph[v2].add(new Node(v1, risk));
        }

        // 상위 기지 테이블, 구간별 최대 위험도 테이블, 구간별 최대 위험 구간 수 테이블
        // 행(r) : 각 기지별 2^r 번째 상위 기지
        // 열(c) : 각 기지 번호
        up = new int[k+1][n];
        max = new long[k+1][n];
        cnt = new int[k+1][n];
        depth = new int[n]; // 각 기지별 깊이 테이블
        for(int i=0; i<=k; i++) Arrays.fill(up[i], -1);
        Arrays.fill(depth, Integer.MAX_VALUE);

        // 상위 기지 테이블 전처리
        bfs();
        buildTable();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<q; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            // 입력받는 두 기지 번호가 같을 수 있음
            // 경로 자체가 없으므로 0 0 출력
            if(u == v) {
                sb.append("0 0").append("\n");
                continue;
            }

            lca(u, v);
            sb.append(curMax).append(" ").append(curCnt).append("\n");
        }

        System.out.println(sb);
    }

    // 트리 탐색을 위한 BFS 메서드
    // 각 기지의 깊이 파악 및 1단계(2^0) 상위 기지와 위험도 등록
    static void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        depth[0] = 0;
        // 본부의 상위 기지는 없으므로 -1 유지

        while(!q.isEmpty()) {
            int cur = q.poll();
            int d = depth[cur]; // 현재 기지의 깊이

            for(Node next : graph[cur]) {
                if(depth[next.id] <= d) continue; // 역주행 방지, 이미 깊이가 등록된 경우 무시
                depth[next.id] = d + 1; // 깊이 기록
                up[0][next.id] = cur; // 상위 기지 기록
                max[0][next.id] = next.risk; // 상위 기지로 향하는 터널의 위험도 기록
                cnt[0][next.id] = 1; // 바로 위에 존재하는 상위 기지로의 위험 경로 수는 반드시 1임
                q.add(next.id);
            }
        }
    }

    // 이진 점프를 이용한 상위 기지 테이블 전처리 메서드
    // 기지 a의 x단계 상위 기지가 b이고, b의 x단계 상위 기지가 c라면,
    // 기지 a의 2*x 단계 상위 기지가 c임을 이용
    // 최대 위험도와 최대 위험 경로 수의 전처리도 함께 처리
    static void buildTable() {
        for(int i=1; i<=k; i++) {
            for(int j=0; j<n; j++) {
                int mid = up[i-1][j];
                if(mid != -1 && up[i-1][mid] != -1) {
                    up[i][j] = up[i-1][mid];

                    // 최대 위험도 및 최대 위험 경로 수의 전처리 기준은 다음과 같음
                    // 위험도 : 더 큰 쪽의 위험도 적용
                    // 위험 경로 수 :
                    // - 두 구간의 최대 위험도가 동일하면 두 최대 위험 경로 수를 합산
                    // - 두 구간의 최대 위험도가 다르면 최대 위험도가 더 큰쪽의 경로 수만 적용
                    long a = max[i-1][j];
                    long b = max[i-1][mid];

                    if(a == b) {
                        max[i][j] = a;
                        cnt[i][j] = cnt[i-1][j] + cnt[i-1][mid];
                    } else if(a > b) {
                        max[i][j] = a;
                        cnt[i][j] = cnt[i-1][j];
                    } else {
                        max[i][j] = b;
                        cnt[i][j] = cnt[i-1][mid];
                    }
                }
            }
        }
    }

    // 두 노드의 최소 공통 조상을 이용하여 입력받은 두 노드 간 경로의 최대 위험도 및 최대 위험 경로 수 기록
    static void lca(int u, int v) {
        // 기지 u를 더 깊은 쪽으로 고정
        if(depth[u] < depth[v]) {
            int temp = u; u = v; v = temp;
        }

        // 기지 u쪽 경로의 깊이를 기지 v와 동일한 깊이로 맞춤
        // 깊이 차가 5인 경우 2진수 101로 변환되므로 i = {0, 2}일 때 기지 u쪽 경로의 깊이를 v 쪽으로 끌어올림
        // 최대 위험도 및 위험 경로 수도 함께 갱신
        curMax = 0; curCnt = 0;
        int diff = depth[u] - depth[v];
        for(int i=0; i<=k; i++) {
            if(((diff >> i) & 1) == 1) {
                merge(max[i][u], cnt[i][u]);
                u = up[i][u];
            }
        }

        // 높이를 맞춘 직후 이미 기지 번호가 같을 수 있음
        if(u == v) return;

        // k를 내림차순으로 반복하며 큰 단위부터 점프
        // 필요없는 깊이의 비교를 굳이 하지 않기 위함
        // 마찬가지로 최대 위험도 및 위험 경로 수 갱신
        for(int i=k; i>=0; i--) {
            if(up[i][u] != up[i][v]) {
                merge(max[i][u], cnt[i][u]);
                u = up[i][u];
                merge(max[i][v], cnt[i][v]);
                v = up[i][v];
            }
        }

        // 이진 점프가 종료된 이후의 u, v는 최소 공통 조상의 바로 아래에 있는 기지들임
        // 두 하위 기지와 공통 상위 기지 간 최대 위험도 및 위험 경로 수도 적용해야 함
        merge(max[0][u], cnt[0][u]);
        merge(max[0][v], cnt[0][v]);
    }

    // 이동 경로 간 최대 위험도 및 위험 경로 수 갱신 메서드
    // m과 c는 추가되는 경로의 위험도 및 경로 수임
    // 새 경로의 최대 위험도가 더 큰 경우 : 최대 위험도 갱신 및 신규 경로의 위험 경로 수 적용
    // 새 경로의 최대 위험도가 현재와 동일한 경우 : 위험 경로 수 합산
    private static void merge(long m, int c) {
        if(m > curMax) {
            curMax = m; curCnt = c;
        } else if(m == curMax) curCnt += c;
    }

}