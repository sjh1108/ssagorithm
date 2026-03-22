package _20260323.thdwngjs.BOJ_13206;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        long MOD = 1_000_000_007L;

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());

            int[] maxExp = new int[1001];

            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(st.nextToken());
                for (int p = 2; p * p <= num; p++) {
                    int exp = 0;
                    while (num % p == 0) {
                        exp++;
                        num /= p;
                    }
                    if (exp > maxExp[p]) maxExp[p] = exp;
                }
                if (num > 1) {
                    if (1 > maxExp[num]) maxExp[num] = 1;
                }
            }

            long result = 1;
            for (int p = 2; p <= 1000; p++) {
                for (int e = 0; e < maxExp[p]; e++) {
                    result = result * p % MOD;
                }
            }
            sb.append(result).append('\n');
        }
        System.out.print(sb);
    }
}
