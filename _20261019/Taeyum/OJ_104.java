package _20261019.Taeyum;

// 주문 N 건 하나씩 처리
// i번 주문 처리 p(i) 시간, 마감 d(i) 시간
// 인쇄소 : 오픈 0시
// 지연은 끝난 시각 - 마감 (그 반대 마감 - 시각은 얼마나 있든 상관없음)
// 마감이 늦은걸 먼저 하면 지연 발생 -> 지연을 최소로 해야함 -> 마감 기준으로 오름차순 정렬

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class OJ_104 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());

        Order[] orders = new Order[N];

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            orders[i] = new Order(Long.parseLong(st.nextToken()), Long.parseLong(st.nextToken()));
        }

        Arrays.sort(orders);
        long time = 0 , ans = 0;

        for (Order order : orders) {
            time += order.p;
            ans = Math.max(ans, time - order.d);
        }

        System.out.println(ans);

    }

    public static class Order implements Comparable<Order>{
        long p, d;

        public Order(long p, long d) {
            this.p = p;
            this.d = d;
        }

        @Override
        public int compareTo(Order o) {
            return Long.compare(this.d, o.d);
        }
    }
}
