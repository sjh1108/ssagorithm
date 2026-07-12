// [#15] 생산 라인 작업 배정  —  GOLD (서브태스크)
// 접근: 마감 오름차순 정렬 + 최소 힙(이익). 라인이 M개이므로 시각 d까지 슬롯은 M*d개.
//       힙 크기가 M*d를 넘으면 이익 최소 작업을 버린다(마감 순 처리라 모든 t의 실행
//       가능성이 보장됨). 남은 이익의 합이 정답. 합이 최대 1e14라 long 필수.
// 복잡도: O(N log N)
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        long M = Long.parseLong(st.nextToken());

        long[][] jobs = new long[N][2]; // [deadline, profit]
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            jobs[i][0] = Long.parseLong(st.nextToken());
            jobs[i][1] = Long.parseLong(st.nextToken());
        }

        // process by deadline ascending
        Arrays.sort(jobs, (a, b) -> Long.compare(a[0], b[0]));

        PriorityQueue<Long> pq = new PriorityQueue<>(); // min-heap of accepted profits
        long sum = 0;
        for (int i = 0; i < N; i++) {
            long d = jobs[i][0], p = jobs[i][1];
            pq.add(p);
            sum += p;
            // by time d there are M*d job slots; keep at most that many of the
            // already-considered jobs (all have deadline <= d), dropping the least profitable
            if (pq.size() > M * d) {
                sum -= pq.poll();
            }
        }
        System.out.println(sum);
    }
}