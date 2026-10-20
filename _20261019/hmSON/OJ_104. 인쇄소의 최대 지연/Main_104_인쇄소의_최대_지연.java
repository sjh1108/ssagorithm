import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 정렬, 그리디
     *
     * 하나의 인쇄기에 n개의 주문이 들어오고, 각 주문의 처리 시간과 마감 시각이 주어지는 비선점 스케줄링 문제
     * 지연 : 각 주문의 처리 완료 시각 - 마감 시각 (처리가 먼저 끝난 경우 0)
     * 각 주문을 처리할 때마다 지연을 확인하고 최대 지연을 최소화해야 함
     *
     * 마감 시각이 제일 빠른 주문부터 처리하기 위해 마감 오름차순으로 정렬
     * 정렬된 주문을 순차 처리하고 각 주문의 처리 이후 (현재 시각 - 마감 시각)을 계산하고 최대 지연 갱신
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 마감 시각이 int 범위 초과할 수 있음
        long[][] orders = new long[n][2];
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            orders[i][0] = Long.parseLong(st.nextToken());
            orders[i][1] = Long.parseLong(st.nextToken());
        }

        // 마감 시각 오름차순으로 정렬(주문 처리 시간은 정렬할 필요 없음)
        Arrays.sort(orders, Comparator.comparingLong(o -> o[1]));

        // maxDelay : 최대 지연, current : 현재 시각
        long maxDelay = 0, current = 0;
        for(int i=0; i<n; i++) {
            current += orders[i][0];
            long delay = current - orders[i][1];
            if(delay > maxDelay) maxDelay = delay;
        }

        System.out.println(maxDelay);
    }

}