package day1020;

import java.io.*;
import java.util.*;

public class BOJ_1270_전쟁_땅따먹기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder out = new StringBuilder();

        int n = Integer.parseInt(br.readLine()); // 땅의 개수

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int T = Integer.parseInt(st.nextToken()); // i번째 땅의 병사 수

            long[] a = new long[T];
            long cand = 0;
            int cnt = 0;

            int read = 0; 

            for (int j = 0; j < T; j++) {

                while (!st.hasMoreTokens()) {
                    st = new StringTokenizer(br.readLine());
                }

                long v = Long.parseLong(st.nextToken());
                a[read++] = v;

                if (cnt == 0) {
                    cand = v;
                    cnt = 1;
                } else if (v == cand) {
                    cnt++;
                } else {
                    cnt--;
                }
            }

            // 후보 검증
            int freq = 0;
            for (long x : a) {
                if (x == cand) freq++;
            }

            if (freq * 2 > T)
                out.append(cand).append('\n');
            else
                out.append("SYJKGW").append('\n');
        }

        System.out.print(out.toString());
    }
}