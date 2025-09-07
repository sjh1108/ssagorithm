package algorithm.d0907;

import java.io.*;
import java.util.*;

public class BOJ_11286_절대값힙 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            long aa = Math.abs((long) a);
            long bb = Math.abs((long) b);
            if (aa != bb) return Long.compare(aa, bb);
            return Integer.compare(a, b);
        });

        for (int i = 0; i < N; i++) {
            int x = Integer.parseInt(br.readLine());
            if (x == 0) {
                sb.append(pq.isEmpty() ? 0 : pq.poll()).append('\n');
            } else {
                pq.add(x);
            }
        }

        System.out.print(sb.toString());
    }
}
