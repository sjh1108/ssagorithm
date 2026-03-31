import java.io.*;
import java.util.*;

public class Main_2268_수들의_합_7 {

    /*
     * BOJ_2268 : 수들의 합 7 (Gold_1)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 길이 N의 수열이 있으며 초기 값은 전부 0이다.
     * - M개의 쿼리를 처리한다.
     *   1) 0 a b : 구간 [a, b]의 합을 출력한다. (a > b일 수 있음)
     *   2) 1 a b : a번째 값을 b로 변경한다.
     * - 여러 번의 구간 합(Query)과 값 변경(Update)을 빠르게 처리해야 한다.
     *
     * [핵심 아이디어]
     * - 값이 수시로 변경되면서 구간 합을 자주 물어보는 형태이므로 세그먼트 트리가 적합하다.
     * - 재귀 대신 반복문 기반(Iterative) 세그먼트 트리를 사용해 구현을 단순화한다.
     * - 리프 시작 인덱스 p를 N 이상인 가장 작은 2의 거듭제곱으로 잡고,
     *   tree[p + i]에 원소를 두며 부모 노드에 구간합을 저장한다.
     *
     * [구현 메모]
     * - getSum(left, right):
     *   - 리프 인덱스로 변환: left += p, right += p
     *   - left가 오른쪽 자식(홀수)이면 포함 후 left++
     *   - right가 왼쪽 자식(짝수)이면 포함 후 right--
     *   - 이후 부모로 이동(left >>= 1, right >>= 1)하며 범위를 좁힌다.
     * - modify(idx, value):
     *   - 리프(tree[p+idx]) 값을 갱신하고,
     *   - idx를 부모로 올려가며(tree[idx<<1] + tree[idx<<1|1])로 구간합을 재계산한다.
     * - 입력은 1-based이므로 내부 처리는 0-based로 맞추기 위해 -1 보정한다.
     * - sum 쿼리에서 a > b일 수 있으므로 swap 후 처리한다.
     * - 합의 범위가 커질 수 있으므로 tree 및 sum은 long을 사용한다.
     *
     * [시간 복잡도]
     * - 각 쿼리(합/갱신): O(log N)
     * - 총: O(M log N)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, m, p = 1;
    static long[] tree;

    static long getSum(int left, int right) {
        long sum = 0L;
        // 리프 노드 인덱스로 변환
        left += p; right += p;

        while(left <= right) {
            // left가 오른쪽 자식이면 현재 노드 포함 후 다음으로 이동
            if(left % 2 == 1) sum += tree[left++];
            // right가 왼쪽 자식이면 현재 노드 포함 후 이전으로 이동
            if(right % 2 == 0) sum += tree[right--];

            // 부모 노드로 이동
            left >>= 1; right >>= 1;
        }

        return sum;
    }

    static void modify(int idx, int value) {
        // 리프 갱신
        idx += p;
        tree[idx] = value;

        // 루트까지 올라가며 구간합 갱신
        while(idx > 1) {
            idx >>= 1;
            tree[idx] = tree[idx<<1] + tree[idx<<1 | 1];
        }
    }

    public static void main(String[] args) throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 리프 시작점(p): N 이상인 가장 작은 2의 거듭제곱
        while(p < n) p <<= 1;
        tree = new long[p*2]; // 초기값은 전부 0

        for(int i=0; i<m; i++) {
            st = new StringTokenizer(br.readLine());
            char mode = st.nextToken().charAt(0);
            int input1 = Integer.parseInt(st.nextToken());
            int input2 = Integer.parseInt(st.nextToken());

            if(mode == '0') {
                // 구간 순서가 뒤집혀 들어올 수 있음
                if(input1 > input2) { int temp = input1; input1 = input2; input2 = temp; }
                // 구간합 결과 출력
                sb.append(getSum(input1-1, input2-1)).append("\n");
            }
            else {
                // input1(idx)번째 값을 input2(value)로 변경
                modify(input1-1, input2);
            }
        }

        System.out.println(sb);
    }

}