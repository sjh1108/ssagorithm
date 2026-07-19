// [#12] 작업 이익 최대화  —  GOLD
// 접근: 마감 오름차순 정렬 + 최소 힙(이익). 작업을 넣다가 선택 수가 마감 d를 넘으면
//       (라인이 1개라 시각 d까지 슬롯은 d개) 이익이 가장 작은 작업을 버린다.
//       남은 이익의 합이 정답. 합이 int를 넘을 수 있어 long 사용.
// 복잡도: O(N log N)
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine().trim());
        long[][] job = new long[N][2]; // [deadline, profit]
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            job[i][0] = Long.parseLong(st.nextToken());
            job[i][1] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(job, (a, b) -> Long.compare(a[0], b[0]));
        PriorityQueue<Long> pq = new PriorityQueue<>(); // min-heap of accepted profits
        long sum = 0;
        for (int i = 0; i < N; i++) {
            long d = job[i][0], p = job[i][1];
            pq.add(p);
            sum += p;
            // 라인이 한 개이므로 마감 d까지 슬롯은 d개. 넘치면 이익 최소 작업을 버린다.
            if (pq.size() > d) {
                sum -= pq.poll();
            }
        }
        System.out.println(sum);
    }
}