package _20260720.thdwngjs.A3;

import java.io.*;
import java.util.*;

class A3 {
    static class Order {
        int oid, seq, price, qty;
        boolean buy, canceled;
        Order(int oid, int seq, boolean buy, int price, int qty) {
            this.oid = oid; this.seq = seq; this.buy = buy; this.price = price; this.qty = qty;
        }
    }

    // 한 종목의 호가창: 지정가/시장가 체결 + 취소(지연 삭제).
    static class OrderBook {
        PriorityQueue<Order> bids = new PriorityQueue<>(
                (a, b) -> a.price != b.price ? Integer.compare(b.price, a.price) : Integer.compare(a.seq, b.seq));
        PriorityQueue<Order> asks = new PriorityQueue<>(
                (a, b) -> a.price != b.price ? Integer.compare(a.price, b.price) : Integer.compare(a.seq, b.seq));
        HashMap<Integer, Order> byId = new HashMap<>();

        void cleanAsk() { while (!asks.isEmpty() && (asks.peek().qty == 0 || asks.peek().canceled)) asks.poll(); }
        void cleanBid() { while (!bids.isEmpty() && (bids.peek().qty == 0 || bids.peek().canceled)) bids.poll(); }

        void cancel(int oid) {
            Order o = byId.get(oid);
            if (o != null && !o.canceled && o.qty > 0) { o.canceled = true; o.qty = 0; }
        }

        void addLimit(Order o, List<int[]> trades) {
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

        // 시장가: 가격 조건 없이 최우선 호가부터 소진. 남은 수량은 버린다.
        void market(boolean buy, int qty, List<int[]> trades) {
            if (buy) {
                while (qty > 0) {
                    cleanAsk();
                    if (asks.isEmpty()) break;
                    Order best = asks.peek();
                    int t = Math.min(qty, best.qty);
                    trades.add(new int[]{best.price, t});
                    qty -= t; best.qty -= t;
                    if (best.qty == 0) asks.poll();
                }
            } else {
                while (qty > 0) {
                    cleanBid();
                    if (bids.isEmpty()) break;
                    Order best = bids.peek();
                    int t = Math.min(qty, best.qty);
                    trades.add(new int[]{best.price, t});
                    qty -= t; best.qty -= t;
                    if (best.qty == 0) bids.poll();
                }
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

    // 여러 종목의 호가창을 합성해 관리.
    static class Exchange {
        HashMap<String, OrderBook> books = new HashMap<>();
        HashMap<Integer, String> idToSymbol = new HashMap<>();
        HashMap<String, List<int[]>> trades = new HashMap<>();

        OrderBook book(String sym) {
            OrderBook b = books.get(sym);
            if (b == null) {
                b = new OrderBook();
                books.put(sym, b);
                trades.put(sym, new ArrayList<>());
            }
            return b;
        }
        void limit(String sym, Order o) {
            OrderBook b = book(sym);
            idToSymbol.put(o.oid, sym);
            b.addLimit(o, trades.get(sym));
        }
        void market(String sym, boolean buy, int qty) {
            OrderBook b = book(sym);
            b.market(buy, qty, trades.get(sym));
        }
        void cancel(int oid) {
            String sym = idToSymbol.get(oid);
            if (sym != null) books.get(sym).cancel(oid);
        }
    }

    public static void main(String[] args) throws IOException {
        String[] tok = readAll();
        int p = 0;
        int n = Integer.parseInt(tok[p++]);
        Exchange ex = new Exchange();
        int seq = 0;
        for (int i = 0; i < n; i++) {
            String cmd = tok[p++];
            if (cmd.equals("LIMIT")) {
                String sym = tok[p++];
                int oid = Integer.parseInt(tok[p++]);
                boolean buy = tok[p++].equals("BUY");
                int price = Integer.parseInt(tok[p++]);
                int qty = Integer.parseInt(tok[p++]);
                seq++;
                ex.limit(sym, new Order(oid, seq, buy, price, qty));
            } else if (cmd.equals("MARKET")) {
                String sym = tok[p++];
                boolean buy = tok[p++].equals("BUY");
                int qty = Integer.parseInt(tok[p++]);
                ex.market(sym, buy, qty);
            } else { // CANCEL
                int oid = Integer.parseInt(tok[p++]);
                ex.cancel(oid);
            }
        }

        List<String> syms = new ArrayList<>(ex.books.keySet());
        Collections.sort(syms);
        StringBuilder sb = new StringBuilder();
        sb.append(syms.size()).append('\n');
        for (String sym : syms) {
            OrderBook b = ex.books.get(sym);
            List<int[]> tr = ex.trades.get(sym);
            List<Order> bids = b.restingBids();
            List<Order> asks = b.restingAsks();
            long v = 0;
            for (int[] t : tr) v += (long) t[0] * t[1];
            sb.append(sym).append(' ').append(tr.size()).append(' ')
              .append(bids.size()).append(' ').append(asks.size()).append(' ').append(v).append('\n');
            for (int[] t : tr) sb.append(t[0]).append(' ').append(t[1]).append('\n');
            for (Order o : bids) sb.append(o.price).append(' ').append(o.qty).append('\n');
            for (Order o : asks) sb.append(o.price).append(' ').append(o.qty).append('\n');
        }
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