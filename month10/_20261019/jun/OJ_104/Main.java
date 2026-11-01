package _20261019.jun.OJ_104;

import java.io.*;
import java.util.*;

// OJ_104 인쇄소의 최대 지연
// 실버, 그리디
public class Main {

    // 주문 [p, d]
    static class Order {
        int p;
        long d;

        public Order(int p, long d) {
            this.p = p;
            this.d = d;
        }
    }

    public static void main(String[] args) throws IOException {
        int N, p;
        Order[] orders;
        long d, output = 0L;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        orders = new Order[N];

        // 입력
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            p = Integer.parseInt(st.nextToken());
            d = Long.parseLong(st.nextToken());
            orders[i] = new Order(p, d);
        }

        // d 오름차순 정렬 -> if(d == d) p 오르차순 정렬
        Arrays.sort(orders, Comparator.comparingLong((Order order) -> order.d)
                .thenComparingInt((Order order) -> order.p));

        long C = 0L; // 끝나는 시각
        for (int i = 0; i < N; i++) {
            C += orders[i].p;
            long cur = C - orders[i].d; // C[i] - d[i]

            // 가장 오래 기다리게 하는 주문의 지연
            if (cur > output) {
                output = cur;
            }
        }

        System.out.println(output);
    }
}
