import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 그리디, 정렬, 우선순위 큐
    // 한 시간마다 작업할 수 있는 라인 수가 주어짐
    // 각 작업마다 마감 기한 시각과 작업 수행시의 이익이 주어짐
    // 실현 가능한 최대 이익을 구해야 함

    // 모든 작업에 대해 마감 기한 오름차순으로 정렬
    // 마감 기한이 빠른 작업부터 우선순위 큐에 등록
    // 해당 작업을 수행하려면 우선순위 큐에 등록된 총 작업 수가 현재 작업의 마감 기한(jobs[i][0]) * 라인 수(line)보다 작거나 같아야 함
    // 만약 위 조건이 충족되지 않으면 현재까지 등록된 모든 작업 중 이익이 가장 작은 작업만 제외

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        // 주어지는 값은 int 범위 내임
        // 이후 마감 기한 * 라인 수 연산 결과가 int 범위를 초과할 수 있어 미리 long 타입 등록
        long line = Long.parseLong(st.nextToken());

        // 각 작업의 마감 기한과 이익
        long[][] jobs = new long[n][2];
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            jobs[i][0] = Long.parseLong(st.nextToken());
            jobs[i][1] = Long.parseLong(st.nextToken());
        }

        // 마감 기한 오름차순으로 작업 정렬
        Arrays.sort(jobs, Comparator.comparingLong(o -> o[0]));

        // 작업 등록용 우선순위 큐
        // 각 작업의 이익값만 넣어 오름차순으로 등록
        // 이후 작업을 제외해야 할 때 순서 무관 제일 이익값이 작은 작업만 제외
        PriorityQueue<Long> pq = new PriorityQueue<>();
        long sum = 0;

        for(long[] job : jobs) {
            pq.add(job[1]);
            sum += job[1];
            if(pq.size() > line * job[0]) sum -= pq.poll();
        }

        System.out.println(sum);
    }

}