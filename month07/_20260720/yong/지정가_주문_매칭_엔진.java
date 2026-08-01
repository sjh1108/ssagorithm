import java.util.*;
import java.io.*;
/*
문제 요약
도착한 순서가 곧 시간 우선순위(FIFO) -> 큐
매수 : 매도 주문중 가격이 가장 낮은것과 체결 시도(매도 가격 <= 새 매수 가격)
매도 : 대기중인 매수 주문중 가장 가격이 높은것(새 매도 가격 <= 매수 가격)
체결 수량 : 두 주문의 남은 수량 중 작은것, 새 주문 수량이 남았다면 다음으로 좋은 대기 주문과 체결 시도
, 조건 만족하는 대기 주문 없거나 새 주문 수량이 0이 되면 주문처리 종료
새 주문 수량이 남았다면 호가창에 대기주문으로 등록
발생한 체결 내역과 최종 호가창 상태를 출력
*/

/*
문제 접근
주문 큐 BUY, SELL 나눠서 관리, SELL은 가격 오름차순, 주문 순 정렬 
*/
public class Main {
  public static class Order{
    char buyOrSell;
    int price;
    int seq;
    int cnt;

    Order(char buyOrSell, int price, int seq, int cnt){
      this.buyOrSell = buyOrSell;
      this.price = price;
      this.seq = seq;
      this.cnt = cnt;
    }
  }
  public static class Trade {
    int price;
    int cnt;

    Trade(int price, int cnt) {
      this.price = price;
      this.cnt = cnt;
    }
  }
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    
    PriorityQueue<Order> buyOrders = new PriorityQueue<>(
      (o1, o2) -> {
        if(o1.price == o2.price){ // 가격 같으면 주문 순서 오름 차순
          return o1.seq - o2.seq;
        }
        return o2.price - o1.price; // 가격 내림 차순
      }
    );
    PriorityQueue<Order> sellOrders = new PriorityQueue<>(
      (o1, o2) -> {
        if(o1.price == o2.price){ // 가격 같으면 주문 순서 오름 차순
          return o1.seq - o2.seq;
        }
        return o1.price - o2.price; // 가격 오름 차순
      }
    );

    // 체결 리스트
    List<Trade> trades = new ArrayList<>();
    // 총 체결 금액
    long totalAmount = 0;

    for (int i = 0; i < N; i++) {
    StringTokenizer st = new StringTokenizer(br.readLine());

    String BorS = st.nextToken();
    int price = Integer.parseInt(st.nextToken());
    int cnt = Integer.parseInt(st.nextToken());

    Order newOrder;

    if (BorS.equals("BUY")) {
        newOrder = new Order('B', price, i, cnt);

        // 가장 낮은 가격의 매도 주문부터 체결
        while (newOrder.cnt > 0 && !sellOrders.isEmpty()) {
            Order sellOrder = sellOrders.peek();

            // 새 매수 가격보다 매도 가격이 높으면 체결 불가능
            if (newOrder.price < sellOrder.price) {
                break;
            }

            int tradeCnt = Math.min(
                    newOrder.cnt,
                    sellOrder.cnt
            );

            // 기존에 대기 중이던 매도 주문 가격으로 체결
            int tradePrice = sellOrder.price;

            trades.add(new Trade(tradePrice, tradeCnt));
            totalAmount += (long) tradePrice * tradeCnt;

            newOrder.cnt -= tradeCnt;
            sellOrder.cnt -= tradeCnt;

            // 기존 매도 주문의 수량이 모두 소진되면 제거
            if (sellOrder.cnt == 0) {
                sellOrders.poll();
            }
        }

        // 새 매수 주문의 수량이 남았다면 호가창에 등록
        if (newOrder.cnt > 0) {
            buyOrders.offer(newOrder);
        }

    } else if (BorS.equals("SELL")) {
        newOrder = new Order('S', price, i, cnt);

        // 가장 높은 가격의 매수 주문부터 체결
        while (newOrder.cnt > 0 && !buyOrders.isEmpty()) {
            Order buyOrder = buyOrders.peek();

            // 새 매도 가격보다 매수 가격이 낮으면 체결 불가능
            if (newOrder.price > buyOrder.price) {
                break;
            }

            int tradeCnt = Math.min(
                    newOrder.cnt,
                    buyOrder.cnt
            );

            // 기존에 대기 중이던 매수 주문 가격으로 체결
            int tradePrice = buyOrder.price;

            trades.add(new Trade(tradePrice, tradeCnt));
            totalAmount += (long) tradePrice * tradeCnt;

            newOrder.cnt -= tradeCnt;
            buyOrder.cnt -= tradeCnt;

            // 기존 매수 주문의 수량이 모두 소진되면 제거
            if (buyOrder.cnt == 0) {
                buyOrders.poll();
            }
        }

        // 새 매도 주문의 수량이 남았다면 호가창에 등록
        if (newOrder.cnt > 0) {
            sellOrders.offer(newOrder);
        }
    }
}
    StringBuilder sb = new StringBuilder();

    // 체결 횟수
    sb.append(trades.size()).append('\n');

    // 체결 내역
    for (Trade trade : trades) {
      sb.append(trade.price)
        .append(' ')
        .append(trade.cnt)
        .append('\n');
    }

    // 남은 매수 주문 수, 매도 주문 수
    sb.append(buyOrders.size())
      .append(' ')
      .append(sellOrders.size())
      .append('\n');

    // 남은 매수 주문 출력
    while (!buyOrders.isEmpty()) {
      Order order = buyOrders.poll();

      sb.append(order.price)
        .append(' ')
        .append(order.cnt)
        .append('\n');
    }

    // 남은 매도 주문 출력
    while (!sellOrders.isEmpty()) {
      Order order = sellOrders.poll();

      sb.append(order.price)
        .append(' ')
        .append(order.cnt)
        .append('\n');
    }

    // 총 체결 금액
    sb.append(totalAmount).append('\n');

    System.out.print(sb);
  }
}
