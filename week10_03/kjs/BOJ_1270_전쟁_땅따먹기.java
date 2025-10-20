package day1020;

import java.io.*;
import java.util.*;

public class BOJ_1270_전쟁_땅따먹기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine()); 

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int T = Integer.parseInt(st.nextToken()); 

            long[] soldiers = new long[T];
            long cand = 0; 
            int cnt = 0;


            for (int j = 0; j < T; j++) {
                long v = Long.parseLong(st.nextToken());
                soldiers[j] = v;


                if (cnt == 0) {
                    cand = v;
                    cnt = 1;
                } else if (v == cand) {
                    cnt++;
                } else {
                    cnt--;
                }
            }

            int freq = 0;
            for (long s : soldiers) {
                if (s == cand) freq++;
            }

            if (freq * 2 > T) sb.append(cand).append('\n');
            else sb.append("SYJKGW\n");
        }

        System.out.print(sb);
    }
}
