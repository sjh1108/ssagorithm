package _20260727.thdwngjs.A2;
import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int N = (int) nextLong(in);
        long K = nextLong(in);
        long[] a = new long[N];
        for (int i = 0; i < N; i++) a[i] = nextLong(in);
        Arrays.sort(a);

        long lo = 2 * a[0], hi = 2 * a[N - 1];
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (countLE(a, N, mid) >= K) hi = mid;
            else lo = mid + 1;
        }
        System.out.println(lo);
    }

    // 합이 s 이하인 (i<j) 쌍의 개수 — A3와 동일한 투 포인터 기법
    private static long countLE(long[] a, int n, long s) {
        int i = 0, j = n - 1;
        long cnt = 0;
        while (i < j) {
            if (a[i] + a[j] <= s) {
                cnt += (j - i);
                i++;
            } else {
                j--;
            }
        }
        return cnt;
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
