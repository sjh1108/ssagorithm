import java.io.*;
import java.util.*;

public class Main_14438_수열과_쿼리_17 {

    /*
     * BOJ_14438 : 수열과 쿼리 17 (Gold_1)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 길이 N의 수열이 주어진다.
     * - Q개의 쿼리를 처리한다.
     *   1) 1 i v : i번째 값을 v로 갱신한다.
     *   2) 2 l r : 구간 [l, r]의 최솟값을 출력한다.
     *
     * [핵심 아이디어]
     * - 값이 변경(Update)되면서 구간 최솟값(Query)을 반복해서 구해야 하므로 세그먼트 트리가 적합하다.
     * - 각 노드는 자신의 구간에 대한 최솟값을 저장하고,
     *   갱신 시 리프에서 루트로 올라가며 최소값을 재계산한다.
     *
     * [구현 메모]
     * - p : 리프 노드 시작 인덱스 (N 이상인 가장 작은 2의 거듭제곱).
     * - build():
     *   - tree[p .. p+n-1]에 초기 값 저장
     *   - 남는 리프(tree[p+n .. 2p-1])는 최소 연산에 영향이 없도록 INF(Integer.MAX_VALUE)로 채움
     *   - 내부 노드는 bottom-up으로 tree[i] = min(tree[2i], tree[2i+1]) 계산
     * - update(idx, value):
     *   - 리프 갱신 후 부모로 올라가며 min 재계산
     * - getMin(left, right):
     *   - 리프 인덱스로 변환 후,
     *   - left가 오른쪽 자식(홀수)이면 포함, right가 왼쪽 자식(짝수)이면 포함
     *   - 이후 부모로 이동하며 구간을 줄이는 방식으로 O(log N)에 처리
     * - 입력은 1-based이므로 내부 인덱스는 -1 보정해서 0-based로 처리한다.
     *
     * [시간 복잡도]
     * - 트리 구축(Build): O(N)
     * - 각 쿼리(갱신/질의): O(log N)
     * - 총: O(N + Q log N)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, p = 1;
    static int[] tree; // 세그먼트 트리 배열

    static void build() throws Exception {
        n = Integer.parseInt(br.readLine());
        while(p < n) p <<= 1;
        tree = new int[p*2];

        st = new StringTokenizer(br.readLine());
        // 리프에 초기 값 저장
        for(int i=p; i<p+n; i++) tree[i] = Integer.parseInt(st.nextToken());
        // 남는 리프는 INF로 채워 min 연산에 영향 없게 처리
        for(int i=p+n; i<p*2; i++) tree[i] = Integer.MAX_VALUE;

        // 내부 노드 bottom-up 구성
        for(int i=p-1; i>0; i--) tree[i] = Math.min(tree[i<<1], tree[i<<1 | 1]);
    }

    static void update(int idx, int value) {
        // 지정된 위치의 값을 새 값으로 변경
        idx += p;
        tree[idx] = value;

        // 부모 노드로 올라가며 최솟값 갱신
        while(idx > 1) {
            idx >>= 1;
            tree[idx] = Math.min(tree[idx<<1], tree[idx<<1 | 1]);
        }
    }

    static int getMin(int left, int right) {
        int min = Integer.MAX_VALUE;
        left += p; right += p;

        while(left <= right) {
            // left가 오른쪽 자식이면 현재 노드 포함 후 이동
            if(left % 2 == 1) min = Math.min(min, tree[left++]);
            // right가 왼쪽 자식이면 현재 노드 포함 후 이동
            if(right % 2 == 0) min = Math.min(min, tree[right--]);

            // 부모 노드로 이동
            left >>= 1; right >>= 1;
        }

        return min;
    }

    public static void main(String[] args) throws Exception {
        build();
        int q = Integer.parseInt(br.readLine());

        for(int i=0; i<q; i++) {
            st = new StringTokenizer(br.readLine());
            char cmd = st.nextToken().charAt(0);
            int v1 = Integer.parseInt(st.nextToken());
            int v2 = Integer.parseInt(st.nextToken());

            // 1 : 값 변경(update), 2 : 구간 최소값 반환(getMin) 및 출력
            if(cmd == '1') update(v1-1, v2);
            else sb.append(getMin(v1-1, v2-1)).append("\n");
        }

        System.out.println(sb);
    }

}