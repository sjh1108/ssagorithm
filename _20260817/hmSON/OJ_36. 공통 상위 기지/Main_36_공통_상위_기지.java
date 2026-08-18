import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 트리, BFS, LCA(최소 공통 조상)
     * 공통 상위 기지 : 기지 u와 v의 상위 기지에 모두 속하는 기지 중 본부로부터 가장 멀리 떨어진 기지
     * -> 기지 u와 v의 최소 공통 조상을 구해야 함
     *
     * 주어지는 기지의 수는 최대 10만, 질의의 수도 최대 10만
     * 상위 기지를 추적하기 위해 한 칸씩 올라가는 행위가 허용되지 않는 수치임
     * 이진 점프를 이용해 각 기지의 2^k번째 상위 기지 번호만 저장하여 메모리 절약 및 매 질의의 탐색 횟수 절감
     *
     * 1. 최악의 경우(경사 트리)에 대한 k값으로 상위 기지 테이블 생성
     * 2. BFS - 각 기지의 깊이(본부 : 0 기준) 및 상위 기지 번호 기록
     * 3. 이진 점프 - 상위 기지 테이블 전처리
     * 4. LCA - 각 질의에 대한 처리
     * 4-a. 두 경로의 깊이 일치시키기
     * 4-b. k값 내림차순으로 반복문을 돌리면서 큰 단위부터 상위 기지로 점프
     */

    static int n, k = 1;
    static int[][] up; // 각 기지의 상위 기지 테이블
    static int[] depth; // 각 기지의 깊이(본부 0 기준)
    static List<Integer>[] graph; // 인접 리스트

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
            graph[v1].add(v2);
            graph[v2].add(v1);
        }

        // 상위 기지 테이블
        // 행(r) : 각 기지별 2^r 번째 상위 기지
        // 열(c) : 각 기지 번호
        up = new int[k+1][n];
        depth = new int[n]; // 각 기지별 깊이 테이블
        for(int[] ex : up) Arrays.fill(ex, -1);
        Arrays.fill(depth, Integer.MAX_VALUE);

        // 상위 기지 테이블 전처리
        bfs();
        buildTable();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<q; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1;
            int v = Integer.parseInt(st.nextToken()) - 1;
            sb.append(lca(u, v) + 1).append("\n");
        }

        System.out.println(sb);
    }

    // 트리 탐색을 위한 BFS 메서드
    // 각 기지의 깊이 파악 및 1단계(2^0) 상위 기지 등록
    static void bfs() {
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        depth[0] = 0;
        // 본부의 상위 기지는 없으므로 -1 유지

        while(!q.isEmpty()) {
            int cur = q.poll();
            int d = depth[cur]; // 현재 기지의 깊이

            for(int next : graph[cur]) {
                if(depth[next] <= d) continue; // 역주행 방지, 이미 깊이가 등록된 경우 무시
                depth[next] = d + 1; // 깊이 기록
                up[0][next] = cur; // 상위 기지 기록
                q.add(next);
            }
        }
    }

    // 이진 점프를 이용한 상위 기지 테이블 전처리 메서드
    // 기지 a의 x단계 상위 기지가 b이고, b의 x단계 상위 기지가 c라면,
    // 기지 a의 2*x 단계 상위 기지가 c임을 이용
    static void buildTable() {
        for(int i=1; i<=k; i++) {
            for(int j=0; j<n; j++) {
                int mid = up[i-1][j];
                if(mid != -1) up[i][j] = up[i-1][mid];
            }
        }
    }

    // 두 노드의 최소 공통 조상 반환 메서드
    static int lca(int u, int v) {
        // 기지 u를 더 깊은 쪽으로 고정
        if(depth[u] < depth[v]) {
            int temp = u; u = v; v = temp;
        }

        // 기지 u쪽 경로의 깊이를 기지 v와 동일한 깊이로 맞춤
        // 깊이 차가 5인 경우 2진수 101로 변환되므로 i = {0, 2}일 때 기지 u쪽 경로의 깊이를 v 쪽으로 끌어올림
        int diff = depth[u] - depth[v];
        for(int i=0; i<=k; i++) {
            if(((diff >> i) & 1) == 1) u = up[i][u];
        }

        // 높이를 맞춘 직후 이미 기지 번호가 같을 수 있음
        if(u == v) return u;

        // k를 내림차순으로 반복하며 큰 단위부터 점프
        // 필요없는 깊이의 비교를 굳이 하지 않기 위함
        for(int i=k; i>=0; i--) {
            if(up[i][u] != up[i][v]) {
                u = up[i][u];
                v = up[i][v];
            }
        }

        // 반복문이 끝날을 때에는 두 기지 u, v의 1단계 상위 기지까지 같아서 더 이상 점프가 불가능할 때임
        // 두 기지 중 아무거나 골라 1단계 상위 기지 번호를 반환
        return up[0][u];
    }

}