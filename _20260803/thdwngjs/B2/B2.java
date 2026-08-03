package _20260803.thdwngjs.B2;

// 문제: 연구소에서 가장 먼 두 방 (GOLD)
// 접근: 아무 정점에서 가장 먼 정점 A 를 찾고, A 에서 가장 먼 거리가 곧 지름이다 (탐색 두 번).
import java.io.*;

class Main {
    static int n;
    static int[] start, adjv, adjw;
    static long[] dist;
    static int[] stack;

    // 반환값: 가장 먼 정점 번호, 거리는 dist 배열에 남는다
    static int farthest(int src) {
        java.util.Arrays.fill(dist, 1, n + 1, -1);
        dist[src] = 0;
        int sp = 0;
        stack[sp++] = src;
        int bi = src;
        long best = 0;
        while (sp > 0) {
            int v = stack[--sp];
            long dv = dist[v];
            if (dv > best || (dv == best && v < bi)) { best = dv; bi = v; }
            for (int j = start[v]; j < start[v + 1]; j++) {
                int u = adjv[j];
                if (dist[u] < 0) { dist[u] = dv + adjw[j]; stack[sp++] = u; }
            }
        }
        return bi;
    }

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        n = nextInt(in);
        if (n == 1) { System.out.print("0\n"); return; }
        int m = n - 1;
        int[] eu = new int[m], ev = new int[m], ew = new int[m];
        int[] deg = new int[n + 2];
        for (int i = 0; i < m; i++) {
            int a = nextInt(in), b = nextInt(in), w = nextInt(in);
            eu[i] = a; ev[i] = b; ew[i] = w;
            deg[a]++; deg[b]++;
        }
        start = new int[n + 2];
        for (int v = 1; v <= n; v++) start[v + 1] = start[v] + deg[v];
        int[] pos = start.clone();
        adjv = new int[2 * m]; adjw = new int[2 * m];
        for (int i = 0; i < m; i++) {
            adjv[pos[eu[i]]] = ev[i]; adjw[pos[eu[i]]] = ew[i]; pos[eu[i]]++;
            adjv[pos[ev[i]]] = eu[i]; adjw[pos[ev[i]]] = ew[i]; pos[ev[i]]++;
        }
        dist = new long[n + 1];
        stack = new int[n];

        int a = farthest(1);
        int b = farthest(a);
        System.out.print(dist[b] + "\n");
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0, c;
        do { c = in.read(); } while (c < '0');
        while (c >= '0') { ret = ret * 10 + (c - '0'); c = in.read(); }
        return ret;
    }
}
