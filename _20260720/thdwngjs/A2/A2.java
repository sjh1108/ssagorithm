package _20260720.thdwngjs.A2;

import java.io.*;
import java.util.*;

class A2 {
    static class Order {
        int oid, seq, price, qty;
        boolean buy, canceled;
        Order(int oid, int seq, boolean buy, int price, int qty) {
            this.oid = oid; this.seq = seq; this.buy = buy; this.price = price; this.qty = qty;
        }
    }

    // 취소를 지원하는 호가창. 임의 원소 삭제는 '지연 삭제'로 처리.
    static class OrderBook {
        PriorityQueue<Order> bids = new PriorityQueue<>(
                (a, b) -> a.price != b.price ? Integer.compare(b.price, a.price) : Integer.compare(a.seq, b.seq));
        PriorityQueue<Order> asks = new PriorityQueue<>(
                (a, b) -> a.price != b.price ? Integer.compare(a.price, b.price) : Integer.compare(a.seq, b.seq));
        HashMap<Integer, Order> byId = new HashMap<>();

        void cleanAsk() {
            while (!asks.isEmpty() && (asks.peek().qty == 0 || asks.peek().canceled)) asks.poll();
        }
        void cleanBid() {
            while (!bids.isEmpty() && (bids.peek().qty == 0 || bids.peek().canceled)) bids.poll();
        }
        void cancel(int oid) {
            Order o = byId.get(oid);
            if (o != null && !o.canceled && o.qty > 0) {
                o.canceled = true;
                o.qty = 0; // 남은 수량 제거 (힙에서는 나중에 지연 삭제)
            }
        }
        void add(Order o, List<int[]> trades) {
            byId.put(o.oid, o);
            if (o.buy) {
                while (o.qty > 0) {
                    cleanAsk();
                    if (asks.isEmpty()) break;
                    Order best = asks.peek();
                    if (best.price > o.price) break;
                    int t = Math.min(o.qty, best.qty);
                    trades.add(new int[]{best.price, t});
                    o.qty -= t; best.qty -= t;
                    if (best.qty == 0) asks.poll();
                }
                if (o.qty > 0) bids.add(o);
            } else {
                while (o.qty > 0) {
                    cleanBid();
                    if (bids.isEmpty()) break;
                    Order best = bids.peek();
                    if (best.price < o.price) break;
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
            for (Order o : bids) if (o.qty > 0 && !o.canceled) b.add(o);
            b.sort((x, y) -> x.price != y.price ? Integer.compare(y.price, x.price) : Integer.compare(x.seq, y.seq));
            return b;
        }
        List<Order> restingAsks() {
            List<Order> a = new ArrayList<>();
            for (Order o : asks) if (o.qty > 0 && !o.canceled) a.add(o);
            a.sort((x, y) -> x.price != y.price ? Integer.compare(x.price, y.price) : Integer.compare(x.seq, y.seq));
            return a;
        }
    }

    public static void main(String[] args) throws IOException {
        String[] tok = readAll();
        int p = 0;
        int n = Integer.parseInt(tok[p++]);
        OrderBook book = new OrderBook();
        List<int[]> trades = new ArrayList<>();
        int seq = 0;
        for (int i = 0; i < n; i++) {
            String cmd = tok[p++];
            if (cmd.equals("LIMIT")) {
                int oid = Integer.parseInt(tok[p++]);
                boolean buy = tok[p++].equals("BUY");
                int price = Integer.parseInt(tok[p++]);
                int qty = Integer.parseInt(tok[p++]);
                seq++;
                book.add(new Order(oid, seq, buy, price, qty), trades);
            } else { // CANCEL
                int oid = Integer.parseInt(tok[p++]);
                book.cancel(oid);
            }
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