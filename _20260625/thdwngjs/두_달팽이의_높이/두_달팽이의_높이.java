package _20260625.thdwngjs.두_달팽이의_높이;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        long n = Long.parseLong(st.nextToken()); // A의 레벨
        long m = Long.parseLong(st.nextToken()); // B의 레벨
        int k = Integer.parseInt(st.nextToken());

        int maxDay = 0;
        int[] arr = new int[k];
        if (k > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < k; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
                if (arr[i] > maxDay) maxDay = arr[i];
            }
        }
        boolean[] commit = new boolean[maxDay + 2];
        for (int i = 0; i < k; i++) commit[arr[i]] = true;

        long A = n, d = 0;
        if (reachable(m, A, d)) { System.out.println(0); return; }
        while (true) {
            d++;
            boolean isCommit = (d <= maxDay) && commit[(int) d];
            A = isCommit ? A + 1 : Math.max(1, A - 1);
            if (reachable(m, A, d)) { System.out.println(d); return; }
        }
    }
    // B(시작 m)가 정확히 d일째에 레벨 T가 될 수 있는가
    static boolean reachable(long m, long T, long d) {
        long diff = Math.abs(m - T);
        if (d >= diff && ((d - diff) % 2 == 0)) return true; // 패리티 경로
        if (d >= m + T - 2) return true;                      // 바닥 대기 활용
        return false;
    }
}