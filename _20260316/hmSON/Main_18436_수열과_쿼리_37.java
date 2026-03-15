import java.io.*;
import java.util.*;

public class Main_18436_수열과_쿼리_37 {

    /*
     * BOJ_18436 : 수열과 쿼리 37 (Gold_1)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 길이 N의 수열이 주어진다.
     * - 다음 3가지 쿼리를 처리한다.
     *   1) 1 i x : i번째 값을 x로 변경한다.
     *   2) 2 l r : 구간 [l, r]의 짝수 개수를 출력한다.
     *   3) 3 l r : 구간 [l, r]의 홀수 개수를 출력한다.
     *
     * [핵심 아이디어]
     * - 값이 변경되면서 구간의 짝/홀 개수를 빠르게 질의해야 하므로 세그먼트 트리가 적합하다.
     * - 각 원소는 실제 값이 아니라 (값 % 2)만 알면 짝/홀 판정이 가능하다.
     *   - 홀수면 1, 짝수면 0으로 저장하면
     *   - 구간 합 = 홀수 개수
     * - 짝수 개수는 (구간 길이 - 홀수 개수)로 바로 계산할 수 있다.
     *
     * [구현 메모]
     * - 구간합을 관리하는 세그먼트 트리 구현.
     * - p: 리프 시작 인덱스 (N 이상인 가장 작은 2의 거듭제곱)
     * - tree의 리프에는 각 값의 (value % 2)를 저장한다.
     * - 내부 노드는 자식 합으로 관리 -> 해당 구간의 "홀수 개수"를 의미한다.
     * - getSum(l, r)은 구간 [l, r]의 홀수 개수 반환.
     * - cmd == 2(짝수 개수):
     *   (r-l+1) - getSum(l, r)
     *
     * [시간 복잡도]
     * - 트리 구축: O(N)
     * - 각 쿼리(갱신/질의): O(log N)
     * - 총: O(N + Q log N)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, p = 1;
    static int[] tree;

    static void init() throws Exception {
        n = Integer.parseInt(br.readLine());

        while(p < n) p <<= 1;
        tree = new int[p*2];

        st = new StringTokenizer(br.readLine());
        // 리프에 홀수 여부(홀수 : 1 / 짝수 : 0) 저장
        for(int i=p; i<p+n; i++) {
            tree[i] = Integer.parseInt(st.nextToken()) % 2;
        }

        // 내부 노드: 홀수 개수 구간합
        for(int i=p-1; i>0; i--) {
            tree[i] = tree[i << 1] + tree[i << 1 | 1];
        }
    }

    static void update(int idx, int value) {
        idx += p;
        tree[idx] = value; // 홀수면 1, 짝수면 0

        // 상위 노드로 올라가면서 업데이트
        while(idx > 1) {
            idx >>= 1;
            tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
        }
    }

    // 구간 [left, right]의 홀수 개수 반환
    static int getSum(int left, int right) {
        int cnt = 0;
        left += p; right += p;

        while(left <= right) {
            if(left % 2 == 1) cnt += tree[left++];
            if(right % 2 == 0) cnt += tree[right--];
            left >>= 1; right >>= 1;
        }

        return cnt;
    }

    public static void main(String[] args) throws Exception {
        init();

        int cmds = Integer.parseInt(br.readLine());
        for(int i=0; i<cmds; i++) {
            st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            int val1 = Integer.parseInt(st.nextToken());
            int val2 = Integer.parseInt(st.nextToken());

            if(cmd == 1) {
                // 기존 값과 새 값의 나머지가 같다면 변화 없음
                if(tree[p + val1 - 1] == val2 % 2) continue;
                update(val1 - 1, val2 % 2);
            }
            // 짝수 개수 = 구간 길이 - 홀수 개수
            else if(cmd == 2) sb.append((val2 - val1 + 1 - getSum(val1 - 1, val2 - 1))).append("\n");
                // 홀수 개수
            else sb.append(getSum(val1 - 1, val2 - 1)).append("\n");
        }

        System.out.println(sb);
    }

}