package _20260803.thdwngjs.A2;

// 문제: 해류 관측값 정정 (GOLD)
// 접근: 펜윅 트리(BIT)로 점 갱신 O(log N), 구간 합 O(log N).
import java.io.*;

class Solution {
    static int n;
    static long[] tree;

    // 값을 "바꾸는" 연산이므로 차이(delta)만 더해 준다
    static void add(int i, long delta) {
        for (; i <= n; i += i & -i) tree[i] += delta;
    }
    static long prefix(int i) {
        long s = 0;
        for (; i > 0; i -= i & -i) s += tree[i];
        return s;
    }

    public static void main(String[] args) throws IOException {
        DataInputStream in = new DataInputStream(new BufferedInputStream(System.in, 1 << 16));
        StringBuilder sb = new StringBuilder();

        n = nextInt(in);
        int q = nextInt(in);
        long[] a = new long[n + 1];
        tree = new long[n + 1];
        // O(N) 선형 초기화: 자기 구간의 합을 부모로 한 번만 올려 준다
        for (int i = 1; i <= n; i++) {
            int v = nextInt(in);
            a[i] = v;
            tree[i] += v;
            int j = i + (i & -i);
            if (j <= n) tree[j] += tree[i];
        }

        for (int k = 0; k < q; k++) {
            int t = nextInt(in), x = nextInt(in), y = nextInt(in);
            if (t == 1) {
                add(x, y - a[x]);
                a[x] = y;
            } else {
                sb.append(prefix(y) - prefix(x - 1)).append('\n');
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
