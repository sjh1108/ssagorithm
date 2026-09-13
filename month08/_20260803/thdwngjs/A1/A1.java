package _20260803.thdwngjs.A1;

// 문제: 가장 강한 해류 구간 (GOLD)
// 접근: 세그먼트 트리 노드에 (구간합, 왼쪽 끝 최대 접두합, 오른쪽 끝 최대 접미합, 최대 연속합) 네 값을 담는다.
import java.io.*;

class Main {
    static final long NEG = Long.MIN_VALUE / 4;
    static int size;
    static long[] S, P, F, B;

    // 두 자식 노드를 합치는 규칙. 순서를 바꾸면 안 되는 비가환 연산이라 질의에서 좌·우를 따로 모은다.
    static void pull(int i) {
        int l = 2 * i, r = l + 1;
        S[i] = S[l] + S[r];
        P[i] = Math.max(P[l], S[l] + P[r]);
        F[i] = Math.max(F[r], S[r] + F[l]);
        B[i] = Math.max(Math.max(B[l], B[r]), F[l] + P[r]);
    }

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();

        int n = nextInt(in);
        int q = nextInt(in);
        size = 1;
        while (size < n) size <<= 1;
        S = new long[2 * size];
        P = new long[2 * size];
        F = new long[2 * size];
        B = new long[2 * size];
        // 남는 잎은 항등원 (합 0, 나머지는 -무한) 으로 두면 합칠 때 무시된다
        for (int i = 0; i < 2 * size; i++) { P[i] = NEG; F[i] = NEG; B[i] = NEG; }
        for (int i = 0; i < n; i++) {
            long v = nextInt(in);
            int j = size + i;
            S[j] = v; P[j] = v; F[j] = v; B[j] = v;
        }
        for (int i = size - 1; i >= 1; i--) pull(i);

        for (int k = 0; k < q; k++) {
            int t = nextInt(in);
            int x = nextInt(in);
            int y = nextInt(in);
            if (t == 1) {
                int j = size + x - 1;
                S[j] = y; P[j] = y; F[j] = y; B[j] = y;
                for (j >>= 1; j >= 1; j >>= 1) pull(j);
            } else {
                int lo = size + x - 1, hi = size + y;
                long Ls = 0, Lp = NEG, Lf = NEG, Lb = NEG;
                long Rs = 0, Rp = NEG, Rf = NEG, Rb = NEG;
                while (lo < hi) {
                    if ((lo & 1) == 1) {
                        long ns = S[lo], np = P[lo], nf = F[lo], nb = B[lo];
                        long s2 = Ls + ns;
                        long p2 = Math.max(Lp, Ls + np);
                        long f2 = Math.max(nf, ns + Lf);
                        long b2 = Math.max(Math.max(Lb, nb), Lf + np);
                        Ls = s2; Lp = p2; Lf = f2; Lb = b2;
                        lo++;
                    }
                    if ((hi & 1) == 1) {
                        hi--;
                        long ns = S[hi], np = P[hi], nf = F[hi], nb = B[hi];
                        long s2 = ns + Rs;
                        long p2 = Math.max(np, ns + Rp);
                        long f2 = Math.max(Rf, Rs + nf);
                        long b2 = Math.max(Math.max(nb, Rb), nf + Rp);
                        Rs = s2; Rp = p2; Rf = f2; Rb = b2;
                    }
                    lo >>= 1; hi >>= 1;
                }
                long ans = Math.max(Math.max(Lb, Rb), Lf + Rp);
                sb.append(ans).append('\n');
            }
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
