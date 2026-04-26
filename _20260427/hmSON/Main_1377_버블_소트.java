import java.io.*;
import java.util.*;

public class Main_1377_버블_소트 {

    /*
     * BOJ_1377 : 버블 소트 (Gold_2)
     * 자료구조 및 알고리즘 : 정렬
     *
     * [문제 요약]
     * - 수열에 대해 버블 소트를 수행할 때, "몇 번째 패스에서 정렬이 완료되는지"를 구하는 문제다.
     * - 실제로 버블 소트를 구현하면 O(N^2)이 걸리므로, 정렬 과정의 성질을 이용해 더 빠르게 계산해야 한다.
     *
     * [핵심 아이디어]
     * - 버블 소트에서 어떤 원소가 왼쪽으로 이동해야 하는 거리만큼,
     *   적어도 그 횟수만큼의 패스가 필요하다.
     * - 따라서 각 원소에 대해
     *   "원래 인덱스 - 정렬 후 인덱스" 를 구하면,
     *   가장 많이 왼쪽으로 이동해야 하는 원소가 필요한 패스 수를 결정한다.
     * - 여기에 마지막으로 정렬 완료를 확인하는 1번의 패스가 더 필요하므로
     *   정답은 max(왼쪽 이동 거리) + 1 이 된다.
     *
     * [구현 메모]
     * - 이 코드는 비교 정렬 대신, 값의 범위가 작다는 점을 이용해 counting sort 방식으로
     *   각 값의 "정렬 후 시작 인덱스"를 구한다.
     * - cnt[x]:
     *   - 처음에는 값 x의 등장 횟수를 저장
     *   - 이후 prefix sum을 적용하면 값 x가 정렬 배열에서 처음 시작하는 인덱스가 된다.
     * - arr[i]를 순서대로 보면서
     *   - sortedIdx = cnt[v]++ : 현재 원소 v가 정렬 후 들어갈 실제 인덱스
     *   - leftMove = i - sortedIdx : 왼쪽으로 이동해야 하는 거리
     * - 그 최대값에 1을 더한 것이 정답이다.
     *
     * [시간 복잡도]
     * - 입력 처리: O(N)
     * - counting / prefix sum: O(MAX)
     * - 최종 스캔: O(N)
     * - 총: O(N + MAX)
     */

    static final int MAX = 1_000_000;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n], cnt = new int[MAX+1];

        for(int i=0; i<n; i++) {
            int x = Integer.parseInt(br.readLine());
            arr[i] = x;
            cnt[x]++;
        }

        // cnt[i]를 "값 i가 정렬 후 시작하는 인덱스"로 변환
        int sum = 0;
        for(int i=0; i<=MAX; i++) {
            int c = cnt[i];
            cnt[i] = sum;
            sum += c;
        }

        int res = 1;
        for(int i=0; i<n; i++) {
            int v = arr[i];
            int sortedIdx = cnt[v]++;
            int leftMove = i - sortedIdx;

            // 가장 많이 왼쪽으로 이동해야 하는 거리 + 1 이 정답
            if(leftMove + 1 > res) res = leftMove + 1;
        }

        System.out.println(res);
    }

}