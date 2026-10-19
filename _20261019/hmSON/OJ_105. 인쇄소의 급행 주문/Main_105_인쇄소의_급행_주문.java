import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 정렬, 그리디, 우선순위 큐
     *
     * 하나의 인쇄기에 n개의 주문이 들어오고, 각 주문의 마감 일자와 보수가 주어지는 비선점 스케줄링 문제
     * 인쇄기는 마감 일자 및 보수와 관계 없이 하루에 한 주문만 처리할 수 있음
     * 받을 수 있는 최대 보수 합과 그 때 받는 주문 수를 구해야 함
     *
     * 마감 시각이 빠른 주문부터 하나씩 후보군에 추가해 보수 합을 계산하고
     * 만약 현재까지 받은 주문 수가 마감 일자를 초과한 경우 보수가 적은 주문부터 하나씩 제외
     * 가능한 모든 주문들을 받으면서 보수가 적은 주문들만 배제할 수 있음
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] orders = new int[n][2]; // { 마감 일자, 보수 }

        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            orders[i][0] = Integer.parseInt(st.nextToken());
            orders[i][1] = Integer.parseInt(st.nextToken());
        }

        // 마감 일자 오름차순. 마감 일자가 빠른 주문부터 조회
        Arrays.sort(orders, (a, b) -> Integer.compare(a[0], b[0]));
        // 보수 오름차순. 후보군으로 등록된 주문 수가 마감 일자를 넘어갈 경우 보수가 적은 주문부터 제외하기 위함
        Queue<Integer> pq = new PriorityQueue<>();

        // 총 보수와 마지막으로 받은 주문의 마감 일자
        long totalPay = 0;
        int day;

        for(int[] order : orders) {
            pq.add(order[1]);
            totalPay += order[1];
            day = order[0];

            // 현재까지 받은 주문 수가 마감 일자를 넘어갔다면
            while(pq.size() > day) {
                // 보수가 적은 주문부터 제외하여 주문 수를 마감 일자에 맞춤
                totalPay -= pq.poll();
            }
        }

        System.out.println(pq.size() + " " + totalPay);
    }

}