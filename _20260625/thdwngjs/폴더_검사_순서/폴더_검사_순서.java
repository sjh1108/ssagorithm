package _20260625.thdwngjs.폴더_검사_순서;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long limit = Long.parseLong(st.nextToken());
        long diff  = Long.parseLong(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] w = new int[k];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) w[i] = Integer.parseInt(st.nextToken());

        List<long[]> carts = new ArrayList<>();   // [sum, min, max, count]
        for (int i = 0; i < k; i++) {
            int x = w[i];
            boolean placed = false;
            for (long[] c : carts) {              // 생성된 순서대로 확인
                long ns = c[0] + x, nmin = Math.min(c[1], x), nmax = Math.max(c[2], x);
                if (ns <= limit && nmax - nmin <= diff) {
                    c[0] = ns; c[1] = nmin; c[2] = nmax; c[3]++;
                    placed = true;
                    break;                        // 실을 수 있으면 반드시 싣는다
                }
            }
            if (!placed) carts.add(new long[]{x, x, x, 1});  // 새 카트
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < carts.size(); i++) {
            if (i > 0) sb.append(' ');
            sb.append(carts.get(i)[3]);
        }
        System.out.println(sb.toString());
    }
}