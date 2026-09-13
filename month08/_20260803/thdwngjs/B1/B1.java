package _20260803.thdwngjs.B1;

// 문제: 모든 방의 대피 시간 (GOLD)
// 접근: 리루팅(rerooting) 트리 DP. down[v]=서브트리 아래로 최장, up[v]=부모 쪽으로 최장, 답=max(down,up).
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();
        int n = nextInt(in);
        if (n == 1) { System.out.print("0\n"); return; }

        int m = n - 1;
        int[] eu = new int[m], ev = new int[m], ew = new int[m];
        int[] deg = new int[n + 2];
        for (int i = 0; i < m; i++) {
            int a = nextInt(in), b = nextInt(in), w = nextInt(in);
            eu[i] = a; ev[i] = b; ew[i] = w;
            deg[a]++; deg[b]++;
        }
        int[] start = new int[n + 2];
        for (int v = 1; v <= n; v++) start[v + 1] = start[v] + deg[v];
        int[] pos = start.clone();
        int[] adjv = new int[2 * m], adjw = new int[2 * m];
        for (int i = 0; i < m; i++) {
            adjv[pos[eu[i]]] = ev[i]; adjw[pos[eu[i]]] = ew[i]; pos[eu[i]]++;
            adjv[pos[ev[i]]] = eu[i]; adjw[pos[ev[i]]] = ew[i]; pos[ev[i]]++;
        }

        int[] par = new int[n + 1];
        int[] order = new int[n];
        boolean[] vis = new boolean[n + 1];
        int[] stack = new int[n];
        int sp = 0, cnt = 0;
        stack[sp++] = 1; vis[1] = true;
        while (sp > 0) {
            int v = stack[--sp];
            order[cnt++] = v;
            for (int j = start[v]; j < start[v + 1]; j++) {
                int u = adjv[j];
                if (!vis[u]) { vis[u] = true; par[u] = v; stack[sp++] = u; }
            }
        }

        long[] down = new long[n + 1];
        // 역순으로 훑으면 자식이 항상 먼저 계산되어 있다 (재귀 없이 후위 순회)
        for (int i = n - 1; i >= 0; i--) {
            int v = order[i], pv = par[v];
            long b = 0;
            for (int j = start[v]; j < start[v + 1]; j++) {
                int u = adjv[j];
                if (u != pv) { long c = down[u] + adjw[j]; if (c > b) b = c; }
            }
            down[v] = b;
        }

        long[] up = new long[n + 1];
        for (int i = 0; i < n; i++) {
            int v = order[i], pv = par[v];
            // 자식 방향 최장 1위·2위를 구해 두면, 자기 자신을 제외한 최댓값을 O(1)에 얻는다
            long b1 = 0, b2 = 0; int i1 = 0;
            for (int j = start[v]; j < start[v + 1]; j++) {
                int u = adjv[j];
                if (u != pv) {
                    long c = down[u] + adjw[j];
                    if (c > b1) { b2 = b1; b1 = c; i1 = u; }
                    else if (c > b2) b2 = c;
                }
            }
            long uv = up[v];
            for (int j = start[v]; j < start[v + 1]; j++) {
                int u = adjv[j];
                if (u != pv) {
                    long alt = (u == i1) ? b2 : b1;
                    up[u] = Math.max(uv, alt) + adjw[j];
                }
            }
        }

        for (int v = 1; v <= n; v++) sb.append(Math.max(down[v], up[v])).append('\n');
        System.out.print(sb);
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0, c;
        do { c = in.read(); } while (c < '0');
        while (c >= '0') { ret = ret * 10 + (c - '0'); c = in.read(); }
        return ret;
    }
}
