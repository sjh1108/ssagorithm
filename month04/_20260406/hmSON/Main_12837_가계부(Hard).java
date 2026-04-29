package gold_1;

import java.io.*;
import java.util.*;

public class Main_12837_가계부(hard) {

    /*
     * BOJ_12837 : 가계부(Hard) (Gold_1)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 길이 N의 배열이 처음에는 모두 0으로 주어진다.
     * - Q개의 쿼리를 처리한다.
     *   1) 1 p x : p번째 값에 x를 더한다.
     *   2) 2 p q : 구간 [p, q]의 합을 출력한다.
     * - 갱신과 구간 합 질의를 빠르게 처리해야 한다.
     *
     * [핵심 아이디어]
     * - 점 갱신(update)과 구간 합(query)이 반복되므로 세그먼트 트리가 적합하다.
     * - 이 문제의 갱신은 "값 변경"이 아니라 "기존 값에 더하기"라는 점이 특징이다.
     * - 따라서 리프 노드에서 tree[idx] = val 이 아니라 tree[idx] += val 형태로 처리해야 한다.
     *
     * [구현 메모]
     * - p : 리프 노드 시작 인덱스 (N 이상인 가장 작은 2의 거듭제곱)
     * - update(idx, val):
     *   - idx 위치의 값에 val을 누적
     *   - 이후 부모로 올라가며 구간합 갱신
     * - getSum(left, right):
     *   - 반복문 기반 세그먼트 트리 방식으로 구간합 계산
     *   - left가 오른쪽 자식이면 포함 후 left++
     *   - right가 왼쪽 자식이면 포함 후 right--
     * - 값 자체가 매우 크고, 누적 합은 int 범위를 초과할 수 있으므로
     *   tree와 sum은 long으로 관리해야 한다.
     *
     * [시간 복잡도]
     * - 점 갱신: O(log N)
     * - 구간 합 질의: O(log N)
     * - 총: O(Q log N)
     */

    static StringBuilder sb = new StringBuilder();
    static int n, q, p = 1;
    static long[] tree; // int 범위를 넘는 값으로 변경될 수 있음

    static void update(int idx, int val) {
        idx += p;
        tree[idx] += val;

        while(idx > 1) {
            idx >>= 1;
            tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
        }
    }

    static void getSum(int left, int right) {
        left += p; right += p;
        long sum = 0;

        while(left <= right) {
            if(left % 2 == 1) sum += tree[left++];
            if(right % 2 == 0) sum += tree[right--];
            left >>= 1; right >>= 1;
        }

        sb.append(sum).append("\n");
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        q = Integer.parseInt(st.nextToken());

        while(p < n) p <<= 1;
        tree = new long[p*2];

        for(int i=0; i<q; i++) {
            st = new StringTokenizer(br.readLine());
            char cmd = st.nextToken().charAt(0);
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());

            if(cmd == '1') update(v1 - 1, v2);
            if(cmd == '2') getSum(v1 - 1, v2 - 1);
        }

        System.out.println(sb);
    }

}