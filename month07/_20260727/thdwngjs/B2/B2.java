package _20260727.thdwngjs.B2;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int N = (int) nextLong(in);
        long[] w = new long[N];
        for (int i = 0; i < N; i++) w[i] = nextLong(in);
        if (N <= 1) {
            System.out.println(0);
            return;
        }
        int K = 3;
        long r = (N - 1) % (K - 1);
        int pad = (r == 0) ? 0 : (int) (K - 1 - r); // 더미(무게 0) 주괴 개수, K=3이므로 0 또는 1

        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (long x : w) pq.add(x);
        for (int i = 0; i < pad; i++) pq.add(0L);

        long cost = 0;
        while (pq.size() > 1) {
            long s = pq.poll() + pq.poll() + pq.poll();
            cost += s;
            pq.add(s);
        }
        System.out.println(cost);
    }

    private static long nextLong(DataInputStream in) throws IOException {
        int b = in.read();
        while (b != -1 && b <= ' ') b = in.read();
        boolean neg = false;
        if (b == '-') { neg = true; b = in.read(); }
        long ret = 0;
        while (b >= '0' && b <= '9') {
            ret = ret * 10 + (b - '0');
            b = in.read();
        }
        return neg ? -ret : ret;
    }
}
