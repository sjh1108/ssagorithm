import java.io.*;
import java.util.*;

public class Main_22988_재활용_캠페인 {

    /*
     * BOJ_22988 : 재활용 캠페인 (Gold_2)
     * 자료구조 및 알고리즘 : 그리디, 정렬, 투 포인터
     *
     * [문제 요약]
     * - 용량이 V인 에센스 통들이 주어진다.
     * - 이미 V만큼 가득 찬 통은 바로 정답에 포함된다.
     * - 가득 차지 않은 통들은 규칙에 따라 합쳐서 새 가득 찬 통으로 교환할 수 있다.
     * - 만들 수 있는 가득 찬 통의 최대 개수를 구한다.
     *
     * [핵심 아이디어]
     * - 먼저 이미 가득 찬 통(V)은 따로 세어두는 것이 가장 간단하다.
     * - 나머지 통들에 대해서는,
     *   두 통을 합쳤을 때 조건을 만족하면 2개로 1개를 만들 수 있으므로 이 경우를 우선적으로 최대한 많이 만드는 것이 유리하다.
     * - 두 통 조합이 가능한지의 기준은
     *   a + b + V/2 >= V
     *   이고, 이를 정리하면
     *   a + b >= ceil(V/2)
     *   형태가 된다.
     * - 따라서 정렬 후 작은 값과 큰 값을 두 포인터로 묶어서
     *   합이 need 이상이면 2개 조합 1개를 만들고,
     *   아니면 작은 값을 버리는 식으로 최대 pairCnt를 구할 수 있다.
     * - 두 통으로 못 묶고 남은 것들은 3개를 사용하면 1개를 만들 수 있으므로
     *   remain / 3 을 더해주면 된다.
     *
     * [구현 메모]
     * - need = (V + 1) / 2 :
     *   - ceil(V/2)을 정수 연산으로 표현한 값
     * - 정렬 후 뒤에서부터 V와 같은 값들을 세어 cnt에 저장
     * - 남은 구간 [0, end-1]에 대해 두 포인터 진행
     *   - arr[left] + arr[right] >= need 이면 2개 조합 성공
     *   - 아니면 arr[left]는 어떤 큰 값과도 조건을 만족시키기 어렵기 때문에 left 증가
     * - remain = end - pairCnt * 2 :
     *   - 2개 조합에 사용되지 않고 남은 통 개수
     * - 최종 답:
     *   - 이미 가득 찬 통 개수 cnt
     *   - 2개 조합으로 만든 개수 pairCnt
     *   - 3개씩 묶어서 만든 개수 remain / 3
     *
     * [시간 복잡도]
     * - 정렬: O(N log N)
     * - 두 포인터 탐색: O(N)
     * - 총: O(N log N)
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        long v = Long.parseLong(st.nextToken());
        long need = (v + 1) / 2;

        long[] arr = new long[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(arr);

        // 이미 가득 찬 통(V)은 바로 정답에 포함
        int cnt = 0, end = n;
        while(end > 0 && arr[end-1] == v) {
            cnt++; end--;
        }

        // 남은 통들 중 2개 조합으로 만들 수 있는 최대 개수 계산
        int left = 0, right = end - 1, pairCnt = 0;
        while(left < right) {
            if(arr[left] + arr[right] >= need) {
                pairCnt++;
                left++; right--;
            } else {
                left++;
            }
        }

        // 2개 조합에 쓰이지 않고 남은 통들은 3개당 1개로 처리 가능
        int remain = end - pairCnt * 2;
        int res = cnt + pairCnt + (remain / 3);
        System.out.println(res);
    }

}