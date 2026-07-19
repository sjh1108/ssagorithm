package study;
import java.io.*;
import java.util.*;

public class 두_달팽이의_높이 {

    static boolean canMeet(long day, long aHeight, long bStart) {
        long dist = Math.abs(aHeight - bStart);

        // 최소 이동 거리보다 날짜가 부족하면 불가능
        if (day < dist) return false;

        // 남는 시간이 짝수면 왕복으로 시간 소모 가능
        if ((day - dist) % 2 == 0) return true;

        // 높이 1에서는 미끄러져도 1이므로 홀수 시간도 소모 가능
        // B가 1까지 내려갔다가 다시 aHeight까지 올라오는 최소 시간
        return day >= bStart + aHeight - 2;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);

        int n = fs.nextInt();
        int m = fs.nextInt();
        int k = fs.nextInt();

        int[] arr = new int[k];

        for (int i = 0; i < k; i++) {
            arr[i] = fs.nextInt();
        }

        long a = n;
        long bStart = m;

        // 0일차 체크
        if (a == bStart) {
            System.out.println(0);
            return;
        }

        int upIdx = 0;

        /*
         * 충분한 탐색 상한
         * 마지막 상승일 이후 A는 결국 1까지 내려감.
         * B도 결국 1에 도달 가능.
         */
        long limit = Math.max((long) arr[k - 1] + n + k, (long) m) + 5;

        for (long day = 1; day <= limit; day++) {

            // day일차에 A 이동
            if (upIdx < k && arr[upIdx] == day) {
                a++;
                upIdx++;
            } else {
                a = Math.max(1, a - 1);
            }

            // day일이 지난 뒤 B가 A 높이에 도달 가능한지 확인
            if (canMeet(day, a, bStart)) {
                System.out.println(day);
                return;
            }
        }
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) {
            this.in = in;
        }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        int nextInt() throws IOException {
            int c;

            do {
                c = read();
            } while (c <= ' ');

            int val = 0;

            while (c > ' ') {
                val = val * 10 + (c - '0');
                c = read();
            }

            return val;
        }
    }
}