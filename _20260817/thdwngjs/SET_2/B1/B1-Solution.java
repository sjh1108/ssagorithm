// 문제: 유물 창고의 금액 (GOLD)
// 접근: 개수 c 를 그대로 늘어놓지 않고 1, 2, 4, ... 묶음으로 이진 분할한다.
//       각 묶음은 "한 번만 쓸 수 있는 동전"이므로 역방향 0/1 배낭으로 처리한다.
//       S/v 개를 넘게 쓰면 어차피 S 를 넘으므로 개수는 min(c, S/v) 로 잘라도 된다.
//       O(S * sum(log(min(c_i, S/v_i)))).
import java.io.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        int m = readInt();
        int s = readInt();
        boolean[] dp = new boolean[s + 1];
        dp[0] = true;
        for (int i = 0; i < m; i++) {
            long v = readLong();
            long c = readLong();
            if (v > s) continue;
            long k = Math.min(c, s / v);
            long step = 1;
            while (step <= k) {
                int shift = (int) (v * step);
                for (int x = s; x >= shift; x--) {
                    if (dp[x - shift]) dp[x] = true;
                }
                k -= step;
                step <<= 1;
            }
            if (k > 0) {
                int shift = (int) (v * k);
                for (int x = s; x >= shift; x--) {
                    if (dp[x - shift]) dp[x] = true;
                }
            }
        }
        long cnt = 0, tot = 0;
        for (int x = 1; x <= s; x++) {
            if (dp[x]) { cnt++; tot += x; }
        }
        System.out.println(cnt + " " + tot);
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
