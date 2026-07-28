package _20260727.thdwngjs.B1;

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
        PriorityQueue<Long> pq = new PriorityQueue<>();
        for (long x : w) pq.add(x);
        long cost = 0;
        while (pq.size() > 1) {
            long a = pq.poll();
            long b = pq.poll();
            long s = a + b;
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
