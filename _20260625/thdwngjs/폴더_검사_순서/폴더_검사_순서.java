package _20260625.thdwngjs.폴더_검사_순서;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int target = Integer.parseInt(st.nextToken());
        int deadline = Integer.parseInt(st.nextToken());

        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
            adj.get(a).add(b); adj.get(b).add(a);
        }

        // BFS로 parent / 방문순서 (재귀 X — 스택오버플로 방지)
        int[] parent = new int[n], order = new int[n];
        boolean[] vis = new boolean[n];
        int idx = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(0); vis[0] = true; parent[0] = -1;
        while (!q.isEmpty()) {
            int cur = q.poll();
            order[idx++] = cur;
            for (int nx : adj.get(cur)) if (!vis[nx]) { vis[nx] = true; parent[nx] = cur; q.add(nx); }
        }
        // 서브트리 크기 (역방향 누적)
        int[] size = new int[n];
        Arrays.fill(size, 1);
        for (int i = idx - 1; i >= 1; i--) size[parent[order[i]]] += size[order[i]];

        // root -> target 경로
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) path.add(v);
        Collections.reverse(path);
        int k = path.size() - 1; // 깊이

        // 경로 노드별 base_B = 번호가 더 작은 형제들의 서브트리 크기 합
        List<Long> red = new ArrayList<>();
        long naturalPos = 1 + k;
        for (int i = 1; i <= k; i++) {
            int node = path.get(i), par = path.get(i - 1);
            long baseB = 0;
            for (int ch : adj.get(par)) {
                if (ch == parent[par]) continue;   // 조부모 제외 -> 나머지는 par의 자식
                if (ch < node) baseB += size[ch];   // (내림차순이면 ch > node 로 변경)
            }
            naturalPos += baseB;
            if (baseB > 0) red.add(baseB);
        }

        if (naturalPos <= deadline) { System.out.println(0); return; }
        if (1 + k > deadline) { System.out.println(-1); return; } // 최소 위치도 초과

        red.sort(Collections.reverseOrder());
        long pos = naturalPos; int marks = 0;
        for (long r : red) {
            if (pos <= deadline) break;
            pos -= r; marks++;
        }
        System.out.println(marks);
    }
}