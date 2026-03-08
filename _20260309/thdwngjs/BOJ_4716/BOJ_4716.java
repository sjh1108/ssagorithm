package _20260309.thdwngjs.BOJ_4716;

import java.io.*;
import java.util.*;

class Main {
    private static class Balloon implements Comparable<Balloon> {
        int k, a, b;

        Balloon(int k, int a, int b) {
            this.k = k;
            this.a = a;
            this.b = b;
        }

        @Override
        public int compareTo(Balloon o) {
            // 두 창고 거리 차이가 큰 팀부터 처리해야 손해를 줄일 수 있다.
            return Integer.compare(Math.abs(o.a - o.b), Math.abs(this.a - this.b));
        }
    }

    private static long sum;
    private static int N, A, B;
    
    private static BufferedReader br;
    private static Queue<Balloon> pq;
    private static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        sb = new StringBuilder();

        // 0 0 0 이 나올 때까지 테스트 케이스 반복
        init();
        while (!(N == 0 && A == 0 && B == 0)) {
            input();
            solve();
            init();
        }

        System.out.print(sb);
    }

    private static void init() throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());

        pq = new PriorityQueue<>();
        sum = 0;
    }

    private static void input() throws IOException {
        StringTokenizer st;

        while (N-- > 0) {
            st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            pq.add(new Balloon(K, a, b));
        }
    }

    private static void solve() {
        while (!pq.isEmpty()) {
            Balloon cur = pq.poll();

            // 더 가까운 창고에서 우선 배정하고, 부족분은 반대 창고에서 채운다.
            if (cur.a <= cur.b) {
                int fromA = Math.min(A, cur.k);
                int fromB = cur.k - fromA;
                sum += (long) fromA * cur.a + (long) fromB * cur.b;
                A -= fromA;
                B -= fromB;
            } else {
                int fromB = Math.min(B, cur.k);
                int fromA = cur.k - fromB;
                sum += (long) fromA * cur.a + (long) fromB * cur.b;
                A -= fromA;
                B -= fromB;
            }
        }

        sb.append(sum).append('\n');
    }
}
