// 문제: 공통 상위 기지 (GOLD)
// 접근: 반복문 BFS/DFS 로 부모와 깊이를 구한 뒤 희소 배열(binary lifting) up[k][v] 를 만든다.
//       깊이를 맞추고, 부모가 갈라지는 지점까지 함께 올라간다. 질의당 O(log N).
import java.io.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        int n = readInt();
        int q = readInt();
        int[] head = new int[n + 1];
        int[] nxt = new int[2 * n];
        int[] dst = new int[2 * n];
        int ec = 1;
        for (int i = 0; i < n - 1; i++) {
            int a = readInt(), b = readInt();
            ec++; dst[ec] = b; nxt[ec] = head[a]; head[a] = ec;
            ec++; dst[ec] = a; nxt[ec] = head[b]; head[b] = ec;
        }
        int LOG = 1;
        while ((1 << LOG) <= n) LOG++;
        int[][] up = new int[LOG][n + 1];
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
                    dep[u] = dep[v] + 1;
                    stack[++top] = u;
                }
            }
        }
        for (int k = 1; k < LOG; k++) {
            int[] prev = up[k - 1], cur = up[k];
            for (int v = 1; v <= n; v++) cur[v] = prev[prev[v]];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int u = readInt(), v = readInt();
            if (dep[u] < dep[v]) { int t = u; u = v; v = t; }
            int d = dep[u] - dep[v];
            for (int k = 0; d != 0; k++, d >>= 1) {
                if ((d & 1) != 0) u = up[k][u];
            }
            if (u != v) {
                for (int k = LOG - 1; k >= 0; k--) {
                    if (up[k][u] != up[k][v]) { u = up[k][u]; v = up[k][v]; }
                }
                u = up[0][u];
            }
            sb.append(u).append('\n');
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
