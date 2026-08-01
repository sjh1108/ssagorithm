import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;



public class Main {
  public static void main(String[] args) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    int n = Integer.parseInt(br.readLine());

    // 최소 비용 우선순위 큐 사용 가자!
    PriorityQueue<Long> pq = new PriorityQueue<>();
    StringTokenizer st = new StringTokenizer(br.readLine());
    for(int i = 0; i < n; i++) {
      // Long으로 하고는 int 실수 할뻔함 ㄷㄷ
      pq.offer(Long.parseLong(st.nextToken()));
    }

    long res = 0;
    // 주괴를 두개씩 꺼내기에 1보다 커야 함. 
    while(pq.size() > 1) {
      long a = pq.poll();
      long b = pq.poll();
      long sum = a + b;
      res += sum;
      pq.offer(sum);
    }
    System.out.println(res);
  }
}
