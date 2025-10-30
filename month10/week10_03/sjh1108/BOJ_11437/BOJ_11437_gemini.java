package week10_03.sjh1108.BOJ_11437;

import java.io.*;
import java.util.*;

class Main {

    static int n, m;
    // adj: 인접 리스트
    static ArrayList<Integer>[] adj;
    // parent[i][j]: i번 노드의 2^j번째 조상
    static int[][] parent;
    // level[i]: i번 노드의 깊이
    static int[] level;
    // maxlevel: 트리의 최대 깊이에 따른 2^k의 k의 최댓값 (log2(N))
    static int maxlevel;

    /**
     * DFS를 수행하며 각 노드의 깊이(level)와 
     * 부모 테이블(parent)을 채웁니다.
     * @param node 현재 노드
     * @param pnode 부모 노드
     * @param lv   현재 노드의 깊이
     */
    static void set_tree(int node, int pnode, int lv) {
        level[node] = lv;
        parent[node][0] = pnode; // 2^0번째 조상 =
                                // 
                                // 부모

        // 2^i번째 조상을 채웁니다.
        // parent[node][i] = parent[parent[node][i-1]][i-1]
        // (node의 2^(i-1)번째 조상의 2^(i-1)번째 조상)
        for (int i = 1; i <= maxlevel; i++) {
            parent[node][i] = parent[parent[node][i - 1]][i - 1];
        }

        // DFS 재귀 호출
        for (int childnode : adj[node]) {
            if (childnode == pnode) continue;
            set_tree(childnode, node, lv + 1);
        }
    }

    /**
     * 두 노드 a와 b의 최소 공통 조상(LCA)을 찾습니다.
     * @param a 노드 a
     * @param b 노드 b
     * @return a와 b의 LCA 노드 번호
     */
    static int LCA(int a, int b) {
        // 1번 노드(루트)가 포함된 경우 항상 1
        if (a == 1 || b == 1) return 1;

        // 1. target이 항상 더 깊은 노드가 되도록 조정
        int target = a, compare = b;
        if (level[a] < level[b]) {
            target = b;
            compare = a;
        }

        // 2. 두 노드의 level이 같아지도록 target을 올립니다.
        // (이진 리프팅)
        if (level[target] != level[compare]) {
            for (int i = maxlevel; i >= 0; i--) {
                // target을 2^i만큼 올렸을 때, 
                // 그 레벨이 compare의 레벨보다 크거나 같다면
                if (level[parent[target][i]] >= level[compare]) {
                    target = parent[target][i]; // 
                                                // target을 
                                                // 2^i만큼 
                                                // 올립니다.
                }
            }
        }

        // 3. 레벨을 맞춘 후, 공통 조상을 찾습니다.
        int ret = target;
        if (target != compare) {
            // 두 노드가 같지 않다면, 
            // 공통 조상을 만날 때까지(부모가 같아질 때까지) 
            // 함께 올립니다.
            for (int i = maxlevel; i >= 0; i--) {
                if (parent[target][i] != parent[compare][i]) {
                    target = parent[target][i];
                    compare = parent[compare][i];
                }
            }
            // 이 루프가 끝나면 target과 compare는 
            // LCA의 바로 아래 자식 노드가 됩니다.
            ret = parent[target][0]; // 따라서 
                                     // 
                                     // LCA는 
                                     // target의 
                                     // 부모입니다.
        }

        return ret;
    }

    public static void main(String[] args) throws IOException {
        // Java의 빠른 입출력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // C++의 init() 함수 부분
        n = Integer.parseInt(br.readLine());

        // N의 최댓값(100000)을 기준으로 maxlevel 계산
        maxlevel = (int) Math.floor(Math.log(100000) / Math.log(2)); // 
                                                                    // 16

        // 자료구조 초기화
        adj = new ArrayList[n + 1];
        level = new int[n + 1];
        // parent[node][maxlevel + 1] -> [n+1][17]
        parent = new int[n + 1][maxlevel + 1]; 
        for (int i = 0; i <= n; i++) {
            adj[i] = new ArrayList<>();
        }

        // 트리 간선 정보 입력
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int p = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            adj[p].add(c);
            adj[c].add(p);
        }

        // 트리 전처리 (DFS 수행)
        // 1번 노드를 루트, 부모는 0 (dummy), 
        // 레벨은 1로 시작
        set_tree(1, 0, 1);

        // 쿼리(M) 입력
        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int f = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            // 결과를 StringBuilder에 추가
            sb.append(LCA(f, s)).append("\n");
        }

        // 모든 결과 한 번에 출력
        System.out.print(sb.toString());
    }
}