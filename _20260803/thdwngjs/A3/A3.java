package _20260803.thdwngjs.A3;

// 문제: 해류 관측소 구간 조회 (SILVER)
// 접근: 합과 "양수 개수" 두 가지 누적합 배열을 미리 만들어 질의를 O(1)에 답한다.
import java.io.*;
import java.util.*;

class Main{
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();

        int n = nextInt(in);
        int q = nextInt(in);

        // 합은 최대 1.2e5 * 1e9 = 1.2e14 라서 long 이 필요하다
        long[] ps = new long[n + 1];
        int[] pc = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            int v = nextInt(in);
            ps[i] = ps[i - 1] + v;
            pc[i] = pc[i - 1] + (v > 0 ? 1 : 0);
        }

        for (int k = 0; k < q; k++) {
            int t = nextInt(in), l = nextInt(in), r = nextInt(in);
            long ans = (t == 1) ? (ps[r] - ps[l - 1]) : (pc[r] - pc[l - 1]);
            sb.append(ans).append('\n');
        }
        System.out.print(sb);
    }

    private static int nextInt(DataInputStream in) throws IOException {
        int ret = 0, b;
        boolean neg = false;
        do { b = in.read(); } while (b < '-');
        if (b == '-') { neg = true; b = in.read(); }
        while (b >= '0') { ret = ret * 10 + (b - '0'); b = in.read(); }
        return neg ? -ret : ret;
    }
}
