# 다종목 거래소와 시장가 주문 — 풀이 (의도와 목적)

## 문제 핵심
A2까지의 단일 호가창이 이제 **여러 종목**으로 늘고, **시장가 주문(`MARKET`)**이 추가됩니다. 종목이 다르면 절대 체결되지 않습니다. 명령은 `LIMIT 종목 …`, `MARKET 종목 방향 수량`, `CANCEL id` 세 가지이고, 마지막에 **종목별로** 체결 내역과 대기 잔량을 출력합니다.

## 어떻게 접근했는가 — 상속이 아니라 합성
"기능이 늘었으니 `OrderBook`을 상속해 확장할까?"가 첫 유혹이지만, 실제로 필요한 건 **종목 수만큼의 독립된 호가창**입니다. 이건 상속(is-a)이 아니라 **합성(has-a)** 문제입니다.

> 거래소(`Exchange`)는 여러 개의 `OrderBook`을 **가진다**. 각 종목 로직은 A2의 호가창을 그대로 재사용하고, 거래소는 "명령을 올바른 종목으로 라우팅"하는 얇은 층만 담당한다.

이 관점 덕분에 A2에서 검증된 매칭·취소 로직을 한 줄도 고치지 않고 종목별로 복제해 쓸 수 있습니다. 새로 짤 것은 시장가 체결과 라우팅뿐입니다.

## 왜 이렇게 설계·구현했는가
- `Exchange`가 `Map<종목, OrderBook>`을 들고, 처음 보는 종목이면 호가창을 지연 생성합니다.
- **취소 라우팅**을 위해 `id → 종목` 맵을 둡니다. `CANCEL id`만으로 어느 종목인지 알아야 하기 때문입니다.
- **시장가**는 지정가에서 "가격 조건"만 뺀 형태입니다. 반대편 최우선 호가부터 무조건 소진하고, **채우지 못한 잔량은 버립니다(호가창에 등록하지 않음)**.

```java
void market(boolean buy, int qty, List<int[]> trades) {   // OrderBook 내부
    if (buy) {
        while (qty > 0) {
            cleanAsk();
            if (asks.isEmpty()) break;                 // 반대편이 없으면 멈춤
            Order best = asks.peek();
            int t = Math.min(qty, best.qty);
            trades.add(new int[]{best.price, t});      // 체결가 = 대기 주문 가격
            qty -= t; best.qty -= t;
            if (best.qty == 0) asks.poll();
        }                                              // 남은 qty는 그냥 사라짐
    } else { /* 매도: 최고 매수부터 대칭 */ }
}
```

거래소 층은 이렇게 얇습니다.

```java
void limit(String sym, Order o) { book(sym); idToSymbol.put(o.oid, sym); book(sym).addLimit(o, trades.get(sym)); }
void market(String sym, boolean buy, int qty) { book(sym).market(buy, qty, trades.get(sym)); }
void cancel(int oid) { String s = idToSymbol.get(oid); if (s != null) books.get(s).cancel(oid); }
```

## 놓치기 쉬운 포인트
- **종목별 격리**: 체결·잔량·총액을 종목마다 따로 집계해야 합니다. 한 곳에 뭉치면 서로 다른 종목이 섞입니다.
- **시장가는 등록 금지**: 남은 수량을 호가창에 넣으면 오답입니다. 지정가와 딱 이 지점이 다릅니다.
- **출력은 종목 사전순**: 종목 키를 정렬해 블록 단위로 출력합니다.
- 취소가 어느 종목인지 모르면 처리할 수 없으므로 `id → 종목` 맵이 필수입니다.

## 복잡도
모든 주문/명령을 통틀어 각 지정가 주문은 힙에 한 번 push/pop 됩니다. 시장가·취소도 로그 또는 상수. 전체 **O(N log N)**.

> 전체 코드는 첨부한 `A3_Main.java` 참고.