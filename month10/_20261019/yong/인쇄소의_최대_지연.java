import java.util.*;
import java.io.*;

/*
처리하는데 pi 시간, 찾으러 오는 시각 di, 최대 지연을 최소로
마감 시간 순대로 처리 -> 그리디

*/
public class Main {
  // 주문 클래스
  static class order{
    long p;
    long d;
    order(long p, long d){
      this.p = p;
      this.d = d;
    }

  }
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int N = Integer.parseInt(br.readLine());
    order[] orders = new order[N];
    long time = 0;
    long maxDelay = 0;

    for(int i = 0; i < N; i++){
      StringTokenizer st = new StringTokenizer(br.readLine());
      long pi = Long.parseLong(st.nextToken());
      long di = Long.parseLong(st.nextToken());
      orders[i] = new order(pi, di);
      // System.out.println(orders[i].p);
    }
    // 정렬
    Arrays.sort(orders,(a, b) -> Long.compare(a.d, b.d));

    for(order o : orders){
      time += o.p;
      // 최대 지연 갱신
      maxDelay = Math.max(maxDelay, time - o.d);
    }
    System.out.println(maxDelay);
  }
}
