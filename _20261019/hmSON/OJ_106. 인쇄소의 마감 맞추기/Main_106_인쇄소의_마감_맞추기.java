import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 정렬, 그리디, 우선순위 큐
     *
     * 하나의 인쇄기에 n개의 주문이 들어오고, 각 주문의 처리 시간과 마감 시각이 주어지는 선점 스케줄링 문제
     * 받을 수 있는 최대 주문 수와, 그 만큼 주문을 받을 때 처리 시간 합의 최소값을 구해야 함
     *
     * 마감 시각이 빠른 주문부터 하나씩 받아서 처리 시간 합계를 계산하고
     * 만약 현재 받은 주문까지의 처리 시간 합계가 마감 시간을 초과한 경우 처리 시간이 긴 주문부터 하나씩 제외
     * 가능한 모든 주문들을 받으면서 단일 처리 시간이 큰 주문들만 배제할 수 있음
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        long[][] orders = new long[n][2]; // { 주문 처리 시간, 마감 시각 }
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            orders[i][0] = Long.parseLong(st.nextToken());
            orders[i][1] = Long.parseLong(st.nextToken());
        }

        // 마감 시각 오름차순. 마감 시각이 빠른 주문부터 조회
        Arrays.sort(orders, (a, b) -> Long.compare(a[1], b[1]));
        // 주문 처리 시간 내림차순. 처리 시간 합계가 마감 시각을 넘어갈 경우 단일 처리 시간이 큰 주문부터 제외하기 위함
        Queue<Long> pq = new PriorityQueue<>(Comparator.reverseOrder());

        // 처리 시간 합계와 마감 시각
        long totalTime = 0, closeTime;
        for(long[] order : orders) {
            pq.add(order[0]);
            totalTime += order[0];
            closeTime = order[1];

            // 현재 받은 주문까지의 처리 시간 합계가 마감 시각을 넘어갔다면
            if(totalTime > closeTime) {
                // 단일 처리 시간이 제일 큰 주문 하나를 제외
                totalTime -= pq.poll();
            }
        }

        System.out.println(pq.size() + " " + totalTime);
    }

}