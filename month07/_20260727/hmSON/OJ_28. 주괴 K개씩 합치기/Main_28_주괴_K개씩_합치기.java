import java.io.*;
import java.util.*;

public class Main_28_주괴_K개씩_합치기 {

    /*
     * 자료구조 및 알고리즘 : 그리디, 우선순위 큐
     *
     * 정확히 K개의 주괴를 골라 제련해야 하나의 새 주괴를 만들 수 있음
     * 주어진 주괴만으로 위 조건을 충족하면서 모든 주괴를 하나로 합치는 것이 불가능할 경우를 대비해 "가짜 주괴"를 추가할 수 있음
     * 동시에 모든 주괴를 하나로 합치는 데 필요한 비용을 최소화해야 함
     *
     * K개의 주괴를 제련하여 1개의 새 주괴가 만들어짐 -> 매 제련마다 (K-1)개의 주괴를 소모
     * 따라서 하나의 주괴만 남기려면 1 <- 1+(K-1) <- 1+2(K-1) <- 1+3(K-1) <- ...
     * N = 1 + X(K - 1) -----> N % (K-1) = 1을 충족해야 함
     * 주괴의 수 N이 이를 충족하지 않을 경우, 이를 충족시킬 때까지 가짜 주괴를 추가해야 함
     * [ 단, 위 조건식은 K > 2일 때에만 성립. K == 2인 경우 N이 어떤 자연수여도 전부 하나의 주괴로 합칠 수 있음 ]
     *
     * 그 외에는 #26, #27번 문제와 동일하게 주괴가 하나만 남을 때까지
     * 가장 가벼운 주괴 K개를 더해서 비용 합계에 추가하고 새 주괴 1개를 등록하는 과정 반복
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        PriorityQueue<Long> pq = new PriorityQueue<>();
        // 가짜 주괴 없이 모든 주괴를 하나로 합칠 수 없는 경우, 부족한 수만큼 가짜 주괴 확보
        if(k > 2 && n % (k-1) != 1) {
            int diff = (k - n % (k-1)) % (k-1);
            for(int i=0; i<diff; i++) pq.add(0L);
        }

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            pq.add(Long.parseLong(st.nextToken()));
        }

        // sum : 제련 비용 총합
        long sum = 0L;
        while(pq.size() > 1) {
            long val = 0;
            for(int i=0; i<k; i++) val += pq.poll();
            sum += val;
            pq.add(val);
        }

        System.out.println(sum);
    }

}