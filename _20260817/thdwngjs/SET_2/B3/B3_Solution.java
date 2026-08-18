// 문제: 고대 화폐의 금액 (SILVER)
// 접근: dp[x] = 금액 x 를 만들 수 있는가. 동전마다 작은 금액부터 정방향으로 훑으면
//       같은 동전을 몇 번이든 다시 쓸 수 있다. O(M*S).
import java.io.*;

public class B3_Solution {

    public static void main(String[] args) throws IOException {
        int m = readInt();
        int s = readInt();
        boolean[] dp = new boolean[s + 1];
        dp[0] = true;
        for (int i = 0; i < m; i++) {
            long v = readLong();
            if (v > s) continue;
            int c = (int) v;
            for (int x = c; x <= s; x++) {
                if (dp[x - c]) dp[x] = true;
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
