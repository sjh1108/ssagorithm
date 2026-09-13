// 문제: 상위 기지 판정 (SILVER)
// 접근: 1번을 뿌리로 반복문 DFS 를 돌며 방문 시각 tin/tout 을 기록한다.
//       u 가 v 의 조상 <=> tin[u] < tin[v] 이고 tout[v] < tout[u].
import java.io.*;

public class A3_Solution {

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
        int[] tin = new int[n + 1];
        int[] tout = new int[n + 1];
        int[] it = new int[n + 1];
        int[] par = new int[n + 1];
        int[] stack = new int[n + 1];
        System.arraycopy(head, 0, it, 0, n + 1);
        int top = 0, timer = 1;
        stack[top] = 1;
        tin[1] = 1;
        while (top >= 0) {
            int v = stack[top];
            int e = it[v];
            boolean moved = false;
            while (e != 0) {
                int u = dst[e];
                e = nxt[e];
                if (u != par[v] && tin[u] == 0) {
                    it[v] = e;
                    par[u] = v;
                    tin[u] = ++timer;
                    stack[++top] = u;
                    moved = true;
                    break;
                }
            }
            if (moved) continue;
            it[v] = 0;
            tout[v] = ++timer;
            top--;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < q; i++) {
            int u = readInt(), v = readInt();
            boolean ok = u != v && tin[u] < tin[v] && tout[v] < tout[u];
            sb.append(ok ? "YES" : "NO").append('\n');
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
