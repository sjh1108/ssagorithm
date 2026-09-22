import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 누적합, 투 포인터, 슬라이딩 윈도우, 이분 탐색, 매개변수 탐색
    // 모든 구간의 소움 총량을 오름차순으로 나열했을 때, k번째 값을 구해야 함
    // 측정 횟수는 최대 100,000으로 주어지므로 모든 구간합을 나열 및 순회하는 것은 비효율적
    // 따라서 요구사항을 재구성할 필요가 있음
    // -> 소음 총량이 x 이하인 구간의 수가 k개 이상이라고 할 때, 이를 충족하는 x의 최소값을 구하여라.

    // 임의의 제한 총량 x를 던져 이를 충족하는 구간의 수가 k 이상인지 확인
    // 구간 수 확인 방식은 81번 문제와 동일하게 적용(슬라이딩 윈도우)
    // 구간의 수가 k 이상이면 high를 내려 더 작은 수를 검증
    // 구간의 수가 k 미만이면 low를 올려 더 큰 수를 검증

    static int n;
    static long k;
    static int[] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Long.parseLong(st.nextToken());

        arr = new int[n];
        st = new StringTokenizer(br.readLine());

        // low는 1부터 시작, high는 전체 구간의 합으로 시작
        long low = 1, high = 0;
        for(int i=0; i<n; i++) {
            int v = Integer.parseInt(st.nextToken());
            arr[i] = v;
            high += v;
        }

        // 매개변수 탐색
        // 구간의 수가 k 이상이 되게 하는 제한 총량 x의 최소값 찾아내기
        while(low <= high) {
            long mid = low + (high - low) / 2;

            // 위 조건을 충족하면 true, 미충족하면 false 반환
            if(getCombiCnt(mid)) high = mid - 1;
            else low = mid + 1;
        }

        System.out.println(low);
    }

    // 소음 총량이 limit 이하인 구간의 수가 k개 이상인지 검증하는 메서드
    // 슬라이딩 윈도우 로직을 이용해 구간 합의 크기에 따라 포인터를 이동시키면서 조건 충족 구간 수 카운트
    static boolean getCombiCnt(long limit) {
        long sum = 0, cnt = 0; // 구간 합 및 구간 수 모두 int 범위를 초과할 수 있음
        int left = 0, right = 0;
        while(right < n && cnt < k) { // cnt가 이미 k를 초과했다면 굳이 더 카운트할 필요 없음
            sum += arr[right++];
            while(sum > limit) sum -= arr[left++]; // 구간 합이 limit을 초과한 경우 left를 올려 구간 축소

            cnt += right - left;
        }

        return cnt >= k;
    }

}