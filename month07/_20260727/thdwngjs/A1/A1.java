package _20260727.thdwngjs.A1;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        long n = nextLong(in);
        long x = nextLong(in);
        int N = (int) n;
        long[] a = new long[N];
        for (int i = 0; i < N; i++) a[i] = nextLong(in);
        Arrays.sort(a);

        int i = 0, j = N - 1;
        long cnt = 0;
        while (i < j) {
            if (a[i] + a[j] <= x) {
                cnt += (j - i);
                i++;
            } else {
                j--;
            }
        }
        System.out.println(cnt);
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
