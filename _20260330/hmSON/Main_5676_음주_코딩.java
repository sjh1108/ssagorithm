import java.io.*;
import java.util.*;

public class Main_5676_음주_코딩 {

    /*
     * BOJ_5676 : 음주 코딩 (Gold_1)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 길이 N의 수열이 주어진다.
     * - 두 가지 명령을 처리해야 한다.
     *   1) C i v : i번째 수를 v로 변경
     *   2) P l r : 구간 [l, r]의 곱의 부호를 출력
     * - 실제 곱의 크기는 중요하지 않고, 결과가 양수(+), 음수(-), 0 중 무엇인지만 알면 된다.
     *
     * [핵심 아이디어]
     * - 구간 곱 자체를 그대로 관리하면 값이 매우 커질 수 있지만,
     *   이 문제는 부호만 필요하므로 각 원소를 -1, 0, 1로 압축해서 관리하면 된다.
     * - 각 수 x에 대해
     *   - x > 0 -> 1
     *   - x == 0 -> 0
     *   - x < 0 -> -1
     * - 그러면 구간 곱의 부호도 이 값들의 곱으로 그대로 표현된다.
     * - 값 변경과 구간 곱 질의가 반복되므로 세그먼트 트리로 처리한다.
     *
     * [구현 메모]
     * - tree의 리프에는 실제 값이 아니라 Integer.compare(num, 0) 결과를 저장한다.
     * - 사용하지 않는 리프 구간은 곱의 항등원인 1로 채운다.
     * - 내부 노드는 왼쪽 자식 * 오른쪽 자식으로 구간 부호를 저장한다.
     * - getMul(left, right):
     *   - 구간 곱의 부호를 계산하며,
     *   - 결과가 0이 되는 순간 더 곱해도 0이므로 조기 종료(res != 0 조건)한다.
     * - 입력이 여러 테스트케이스 형태로 EOF까지 주어지므로,
     *   init()이 false를 반환할 때까지 반복한다.
     *
     * [시간 복잡도]
     * - 트리 구축: O(N)
     * - 갱신: O(log N)
     * - 구간 질의: O(log N)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, p, cmdCnt;
    static int[] tree;

    static boolean init() throws Exception {
        String line = br.readLine();
        if(line == null || line.trim().isEmpty()) return false;

        st = new StringTokenizer(line);
        n = Integer.parseInt(st.nextToken());
        cmdCnt = Integer.parseInt(st.nextToken());
        p = 1;

        // 세그먼트 트리의 리프 노드 크기 설정
        while(p < n) p <<= 1;
        tree = new int[p*2];

        // 세그먼트 트리의 리프 노드에 주어진 값이 양수, 음수, 0 중 어떤 것인지 입력
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            int num = Integer.parseInt(st.nextToken());
            tree[p+i] = Integer.compare(num, 0);
        }

        // 나머지 리프 노드는 곱의 항등원인 1 입력
        for(int i=n; i<p; i++) tree[p+i] = 1;

        // 상위 노드로 올라가면서 구간곱 계산
        for(int i=p-1; i>0; i--) tree[i] = tree[i << 1] * tree[i << 1 | 1];

        return true;
    }

    // 특정 인덱스의 값을 변경
    static void update(int idx, int val) {
        idx += p;
        tree[idx] = Integer.compare(val, 0);

        while(idx > 1) {
            idx >>= 1;
            tree[idx] = tree[idx << 1] * tree[idx << 1 | 1];
        }
    }

    // 구간곱 결과 출력
    static void getMul(int left, int right) {
        left += p; right += p;
        int res = 1;

        // 구간 곱의 부호만 구하면 되므로, 0이 되는 순간 더 계산할 필요가 없다.
        while(left <= right && res != 0) {
            if(left % 2 == 1) res *= tree[left++];
            if(right % 2 == 0) res *= tree[right--];
            left >>= 1; right >>= 1;
        }

        sb.append(res == 0 ? 0 : res > 0 ? "+" : "-");
    }

    public static void main(String[] args) throws Exception {
        while(init()) {
            for(int i=0; i<cmdCnt; i++) {
                st = new StringTokenizer(br.readLine());
                char cmd = st.nextToken().charAt(0);
                int v1 = Integer.parseInt(st.nextToken());
                int v2 = Integer.parseInt(st.nextToken());

                if(cmd == 'C') update(v1 - 1, v2);
                else getMul(v1 - 1, v2 - 1);
            }

            sb.append("\n");
        }

        System.out.println(sb);
    }

}