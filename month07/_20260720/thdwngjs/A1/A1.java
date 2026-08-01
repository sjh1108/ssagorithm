package _20260720.thdwngjs.A1;
import java.io.*;
import java.util.*;

class A1 {
    // 하나의 지정가 주문. idx = 도착 순서(시간 우선순위).
    static class Order {
        int idx, price, qty;
        boolean buy;
        Order(int idx, boolean buy, int price, int qty) {
            this.idx = idx; this.buy = buy; this.price = price; this.qty = qty;
        }
    }

    // 양방향 호가창: 매수는 최고가 우선, 매도는 최저가 우선, 동가는 도착 빠른 순.
    static class OrderBook {
        // 매수: price 큰 것, 같으면 idx 작은 것이 top
        PriorityQueue<Order> bids = new PriorityQueue<>(
                (a, b) -> a.price != b.price ? Integer.compare(b.price, a.price) : Integer.compare(a.idx, b.idx));
        // 매도: price 작은 것, 같으면 idx 작은 것이 top
        PriorityQueue<Order> asks = new PriorityQueue<>(
                (a, b) -> a.price != b.price ? Integer.compare(a.price, b.price) : Integer.compare(a.idx, b.idx));

        Order bestAsk() {
            while (!asks.isEmpty() && asks.peek().qty == 0) asks.poll();
            return asks.isEmpty() ? null : asks.peek();
        }
        Order bestBid() {
            while (!bids.isEmpty() && bids.peek().qty == 0) bids.poll();
            return bids.isEmpty() ? null : bids.peek();
        }

        // 주문을 넣고 발생한 체결을 trades에 append
        void add(Order o, List<int[]> trades) {
            if (o.buy) {
                while (o.qty > 0) {
                    Order best = bestAsk();
                    if (best == null || best.price > o.price) break;
                    int t = Math.min(o.qty, best.qty);
                    trades.add(new int[]{best.price, t});
                    o.qty -= t; best.qty -= t;
                    if (best.qty == 0) asks.poll();
                }
                if (o.qty > 0) bids.add(o);
            } else {
                while (o.qty > 0) {
                    Order best = bestBid();
                    if (best == null || best.price < o.price) break;
                    int t = Math.min(o.qty, best.qty);
                    trades.add(new int[]{best.price, t});
                    o.qty -= t; best.qty -= t;
                    if (best.qty == 0) bids.poll();
                }
                if (o.qty > 0) asks.add(o);
            }
        }

        List<Order> restingBids() {
            List<Order> b = new ArrayList<>();
            for (Order o : bids) if (o.qty > 0) b.add(o);
            b.sort((x, y) -> x.price != y.price ? Integer.compare(y.price, x.price) : Integer.compare(x.idx, y.idx));
            return b;
        }
        List<Order> restingAsks() {
            List<Order> a = new ArrayList<>();
            for (Order o : asks) if (o.qty > 0) a.add(o);
            a.sort((x, y) -> x.price != y.price ? Integer.compare(x.price, y.price) : Integer.compare(x.idx, y.idx));
            return a;
        }
    }

    public static void main(String[] args) throws IOException {
        String[] tok = readAll();
        int p = 0;
        int n = Integer.parseInt(tok[p++]);
        OrderBook book = new OrderBook();
        List<int[]> trades = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            boolean buy = tok[p++].equals("BUY");
            int price = Integer.parseInt(tok[p++]);
            int qty = Integer.parseInt(tok[p++]);
            book.add(new Order(i, buy, price, qty), trades);
        }

        StringBuilder sb = new StringBuilder();
        sb.append(trades.size()).append('\n');
        long totalValue = 0;
        for (int[] tr : trades) {
            sb.append(tr[0]).append(' ').append(tr[1]).append('\n');
            totalValue += (long) tr[0] * tr[1];
        }
        List<Order> bids = book.restingBids();
        List<Order> asks = book.restingAsks();
        sb.append(bids.size()).append(' ').append(asks.size()).append('\n');
        for (Order o : bids) sb.append(o.price).append(' ').append(o.qty).append('\n');
        for (Order o : asks) sb.append(o.price).append(' ').append(o.qty).append('\n');
        sb.append(totalValue).append('\n');
        System.out.print(sb);
    }

    static String[] readAll() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder all = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) all.append(line).append(' ');
        StringTokenizer stk = new StringTokenizer(all.toString());
        ArrayList<String> list = new ArrayList<>();
        while (stk.hasMoreTokens()) list.add(stk.nextToken());
        return list.toArray(new String[0]);
    }
}