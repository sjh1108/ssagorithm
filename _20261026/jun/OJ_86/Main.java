package _20261026.jun.OJ_86;

import java.io.*;
import java.util.*;

// OJ_86 천문 시계의 톱니
// 실버, 분할 정복, 수학
public class Main {

    public static void main(String[] args) throws IOException {
        int Q;
        long[] a, b;
        int[] m;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        Q = Integer.parseInt(st.nextToken());
        a = new long[Q];
        b = new long[Q];
        m = new int[Q];

        // 입력
        for (int i = 0; i < Q; i++) {
            st = new StringTokenizer(br.readLine());
            a[i] = Long.parseLong(st.nextToken());
            b[i] = Long.parseLong(st.nextToken());
            m[i] = Integer.parseInt(st.nextToken());
        }

        // (Q <= 50000) 출력
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            sb.append(powMod(a[i], b[i], m[i])).append("\n");
        }

        System.out.print(sb);
    }

    // a^b mod m 분할 정복
    static long powMod(long a, long b, int m) {
        if (b == 0) return 1 % m;
        if (b == 1) return a % m;

        long half = powMod(a, b / 2, m);

        // 지수가 짝수. (A × A) % M = ((A % M) × (A % M)) % M
        long result = (half * half) % m;
        // 지수가 홀수. (A × A × a) % M = (((A % M) × (A % M)) % M × (a % M)) % M
        if (b % 2 != 0) {
            result =  (result * (a % m)) % m;
        }

        return result;
    }
}
