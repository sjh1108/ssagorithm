package _20260803.thdwngjs.B3;

// 문제: 연구소 회랑 답사 (SILVER)
// 접근: CSR 인접 리스트 + 명시적 스택 DFS 로 1번 방에서의 거리를 모두 구한 뒤 최댓값을 찾는다.
import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int n = nextInt(in);
        if (n == 1) { System.out.print("0 1\n"); return; }

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

        // 거리 합이 최대 1.2e5 * 1e9 = 1.2e14 이므로 long
        long[] dist = new long[n + 1];
        boolean[] vis = new boolean[n + 1];
        int[] stack = new int[n];
        int sp = 0;
        stack[sp++] = 1; vis[1] = true;
        while (sp > 0) {
            int v = stack[--sp];
            long dv = dist[v];
            for (int j = start[v]; j < start[v + 1]; j++) {
                int u = adjv[j];
                if (!vis[u]) { vis[u] = true; dist[u] = dv + adjw[j]; stack[sp++] = u; }
            }
        }
        long best = -1; int bi = 1;
        // 번호 오름차순으로 훑으면서 > 비교만 하면 동점일 때 자동으로 작은 번호가 남는다
        for (int v = 1; v <= n; v++) if (dist[v] > best) { best = dist[v]; bi = v; }
        System.out.print(best + " " + bi + "\n");
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0, b;
        do { b = in.read(); } while (b < '0');
        while (b >= '0') { ret = ret * 10 + (b - '0'); b = in.read(); }
        return ret;
    }
}
