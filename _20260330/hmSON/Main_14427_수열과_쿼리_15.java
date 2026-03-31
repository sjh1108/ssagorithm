import java.io.*;
import java.util.*;

public class Main_14427_수열과_쿼리_15 {

    /*
     * BOJ_14427 : 수열과 쿼리 15 (Gold_3)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 길이 N의 수열이 주어진다.
     * - 쿼리는 2가지이다.
     *   1) 1 i v : i번째 값을 v로 변경
     *   2) 2     : 현재 수열에서 최솟값을 가지는 인덱스 출력
     * - 최솟값이 여러 개라면 가장 작은 인덱스를 출력해야 한다.
     *
     * [핵심 아이디어]
     * - 구간 최솟값을 빠르게 갱신/조회해야 하므로 세그먼트 트리가 적합하다.
     * - 이 문제는 최솟값 "자체"보다, 그 최솟값이 위치한 "가장 작은 인덱스"를 찾는 과정이 핵심이다.
     * - 루트(tree[1])에는 전체 구간의 최솟값이 들어 있으므로,
     *   루트에서 시작해서 "왼쪽 자식에도 같은 최솟값이 있으면 왼쪽으로", 아니면 오른쪽으로 내려가면
     *   최솟값이 존재하는 가장 왼쪽 리프까지 도달할 수 있다.
     *
     * [구현 메모]
     * - p : 리프 시작 인덱스 (N 이상인 가장 작은 2의 거듭제곱)
     * - 사용하지 않는 리프 구간은 Integer.MAX_VALUE로 채워
     *   최소 연산에 영향이 없도록 처리했다.
     * - update(idx, val):
     *   - 리프 값을 갱신한 뒤 부모로 올라가며 구간 최솟값을 다시 계산한다.
     * - find():
     *   - 루트에서 시작
     *   - 왼쪽 자식 값이 현재 노드 값과 같으면 왼쪽으로 이동
     *   - 아니면 오른쪽으로 이동
     *   - 이렇게 하면 동일한 최솟값이 여러 개일 때 가장 작은 인덱스를 찾을 수 있다.
     *
     * [시간 복잡도]
     * - 트리 구축: O(N)
     * - 갱신: O(log N)
     * - 최솟값 인덱스 찾기: O(log N)
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
        for(int i=0; i<n; i++) tree[p+i] = Integer.parseInt(st.nextToken());
        for(int i=n; i<p; i++) tree[p+i] = Integer.MAX_VALUE;
        for(int i=p-1; i>0; i--) tree[i] = Math.min(tree[i << 1], tree[i << 1 | 1]);
    }

    static void update(int idx, int val) {
        idx += p;
        tree[idx] = val;

        while(idx > 1) {
            idx >>= 1;
            tree[idx] = Math.min(tree[idx << 1], tree[idx << 1 | 1]);
        }
    }

    static void find() {
        int idx = 1;

        // 루트의 최솟값을 따라 내려가면서,
        // 같은 최솟값이 왼쪽에 있으면 왼쪽으로 이동하여 "가장 작은 인덱스"를 찾는다.
        while(idx < p) {
            if(tree[idx << 1] == tree[idx]) idx <<= 1;
            else idx = idx << 1 | 1;
        }

        sb.append(idx - p + 1).append("\n");
    }

    public static void main(String[] args) throws Exception {
        init();
        int q = Integer.parseInt(br.readLine());
        while(q-- > 0) {
            st = new StringTokenizer(br.readLine());
            char cmd = st.nextToken().charAt(0);

            if(cmd == '1') {
                int idx = Integer.parseInt(st.nextToken()) - 1;
                int val = Integer.parseInt(st.nextToken());
                update(idx, val);
            } else {
                find();
            }
        }

        System.out.println(sb);
    }

}