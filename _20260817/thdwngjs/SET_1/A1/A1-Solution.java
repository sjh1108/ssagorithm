// 문제: 가장 위험한 터널 (GOLD)
// 접근: 희소 배열에 조상 번호뿐 아니라 그 구간의 (최대 위험도, 최대와 같은 터널 개수)를 함께 올린다.
//       두 조각을 합칠 때 최댓값이 같으면 개수를 더한다. 질의당 O(log N).
import java.io.*;

public class Solution {

    static long bestMax;
    static int bestCnt;

    static void take(long m, int c) {
        if (m > bestMax) { bestMax = m; bestCnt = c; }
        else if (m == bestMax) bestCnt += c;
    }

    public static void main(String[] args) throws IOException {
        int n = readInt();
        int q = readInt();
        int[] head = new int[n + 1];
        int[] nxt = new int[2 * n];
        int[] dst = new int[2 * n];
        long[] wt = new long[2 * n];
        int ec = 1;
        for (int i = 0; i < n - 1; i++) {
            int a = readInt(), b = readInt();
            long w = readLong();
            ec++; dst[ec] = b; wt[ec] = w; nxt[ec] = head[a]; head[a] = ec;
            ec++; dst[ec] = a; wt[ec] = w; nxt[ec] = head[b]; head[b] = ec;
        }
        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;
        int[][] up = new int[LOG][n + 1];
        long[][] mx = new long[LOG][n + 1];
        int[][] cn = new int[LOG][n + 1];
        int[] dep = new int[n + 1];
        boolean[] vis = new boolean[n + 1];
        int[] stack = new int[n + 1];
        int top = 0;
        stack[0] = 1;
        vis[1] = true;
        while (top >= 0) {
            int v = stack[top--];
            for (int e = head[v]; e != 0; e = nxt[e]) {
                int u = dst[e];
                if (!vis[u]) {
                    vis[u] = true;
                    up[0][u] = v;
                    mx[0][u] = wt[e];
                    cn[0][u] = 1;
                    dep[u] = dep[v] + 1;
                    stack[++top] = u;
                }
            }
        }
        for (int k = 1; k < LOG; k++) {
            int[] pu = up[k - 1], cu = up[k], pc = cn[k - 1], cc = cn[k];
            long[] pm = mx[k - 1], cm = mx[k];
            for (int v = 1; v <= n; v++) {
                int mid = pu[v];
                cu[v] = pu[mid];
                long a = pm[v], b = pm[mid];
                if (a > b) { cm[v] = a; cc[v] = pc[v]; }
                else if (b > a) { cm[v] = b; cc[v] = pc[mid]; }
                else { cm[v] = a; cc[v] = pc[v] + pc[mid]; }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int u = readInt(), v = readInt();
            if (u == v) { sb.append("0 0\n"); continue; }
            if (dep[u] < dep[v]) { int t = u; u = v; v = t; }
            bestMax = 0; bestCnt = 0;
            int d = dep[u] - dep[v];
            for (int k = 0; d != 0; k++, d >>= 1) {
                if ((d & 1) != 0) { take(mx[k][u], cn[k][u]); u = up[k][u]; }
            }
            if (u != v) {
                for (int k = LOG - 1; k >= 0; k--) {
                    if (up[k][u] != up[k][v]) {
                        take(mx[k][u], cn[k][u]);
                        take(mx[k][v], cn[k][v]);
                        u = up[k][u];
                        v = up[k][v];
                    }
                }
                take(mx[0][u], cn[0][u]);
                take(mx[0][v], cn[0][v]);
            }
            sb.append(bestMax).append(' ').append(bestCnt).append('\n');
        }
        System.out.print(sb);
    }

    private static final int BUFSZ = 1 << 16;
    private static final byte[] buf = new byte[BUFSZ];
    private static int bufLen = 0, bufPtr = 0;
    private static final InputStream is = System.in;

    private static int readByte() throws IOException {
        if (bufPtr == bufLen) {
            bufLen = is.read(buf, 0, BUFSZ);
            bufPtr = 0;
            if (bufLen <= 0) return -1;
        }
        return buf[bufPtr++];
    }

    private static long readLong() throws IOException {
        int c = readByte();
        while (c == ' ' || c == '\n' || c == '\r' || c == '\t') c = readByte();
        boolean neg = false;
        if (c == '-') { neg = true; c = readByte(); }
        long x = 0;
        while (c >= '0' && c <= '9') { x = x * 10 + (c - '0'); c = readByte(); }
        return neg ? -x : x;
    }

    private static int readInt() throws IOException {
        return (int) readLong();
    }
}
