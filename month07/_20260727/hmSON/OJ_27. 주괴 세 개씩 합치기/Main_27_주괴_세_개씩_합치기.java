import java.io.*;
import java.util.*;

public class Main_27_주괴_세_개씩_합치기 {

    /*
     * 자료구조 및 알고리즘 : 그리디, 우선순위 큐
     *
     * 정확히 3개의 주괴를 골라 제련해야 하나의 새 주괴를 만들 수 있음
     * 주어진 주괴만으로 위 조건을 충족하면서 모든 주괴를 하나로 합치는 것이 불가능할 경우를 대비해 "가짜 주괴"를 추가할 수 있음
     * 동시에 모든 주괴를 하나로 합치는 데 필요한 비용을 최소화해야 함
     *
     * 3개의 주괴를 제련하여 1개의 새 주괴가 만들어짐 -> 매 제련마다 2개의 주괴를 소모
     * 따라서 하나의 주괴만 남기려면 1 <- 3 <- 5 <- 7 <- ... 이므로 홀수의 주괴를 가지고 있어야 함
     * 주괴의 수 N이 짝수일 경우 위 조건을 충족하기 위해 무게가 0인 가짜 주괴 1개 추가
     *
     * 그 외에는 #26번 문제와 동일하게 주괴가 하나만 남을 때까지
     * 가장 가벼운 주괴 3개를 더해서 비용 합계에 추가하고 새 주괴 1개를 등록하는 과정 반복
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        PriorityQueue<Long> pq = new PriorityQueue<>();
        // 현재 주괴 수가 짝수 -> 주괴 하나만 남길 수 없으므로 가짜 주괴를 미리 확보
        if(n % 2 == 0) pq.add(0L);

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            pq.add(Long.parseLong(st.nextToken()));
        }

        // sum : 제련 비용 총합
        long sum = 0L;
        while(pq.size() > 1) {
            long val = pq.poll() + pq.poll() + pq.poll();
            sum += val;
            pq.add(val);
        }

        System.out.println(sum);
    }

}