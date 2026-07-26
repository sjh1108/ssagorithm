package _20260727.thdwngjs.A3;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        int N = (int) nextLong(in);
        int M = (int) nextLong(in);
        long K = nextLong(in);
        long[] A = new long[N];
        for (int i = 0; i < N; i++) A[i] = nextLong(in);
        long[] B = new long[M];
        for (int i = 0; i < M; i++) B[i] = nextLong(in);
        Arrays.sort(A);
        Arrays.sort(B);

        long lo = A[0] + B[0], hi = A[N - 1] + B[M - 1];
        while (lo < hi) {
            long mid = lo + (hi - lo) / 2;
            if (countLE(A, B, N, M, mid) >= K) hi = mid;
            else lo = mid + 1;
        }
        System.out.println(lo);
    }

    // A는 오름차순 순회, B는 그에 맞춰 단조 감소하는 포인터 — 호출마다 j를 M-1로 리셋
    private static long countLE(long[] a, long[] b, int n, int m, long s) {
        long cnt = 0;
        int j = m - 1;
        for (int i = 0; i < n; i++) {
            while (j >= 0 && a[i] + b[j] > s) j--;
            if (j < 0) break;
            cnt += (j + 1);
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
