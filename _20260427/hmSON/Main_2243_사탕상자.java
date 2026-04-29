import java.io.*;
import java.util.*;

public class Main_2243_사탕상자 {

    /*
     * BOJ_2243 : 사탕상자 (Platinum_5)
     * 자료구조 및 알고리즘 : 세그먼트 트리, 이분 탐색
     *
     * [문제 요약]
     * - 맛 번호가 1 ~ 1,000,000인 사탕들을 관리한다.
     * - 명령은 2가지이다.
     *   1) 1 B : 현재 들어 있는 사탕들 중 "우선순위가 B번째인 사탕"의 맛 번호를 출력하고, 그 사탕 1개를 꺼낸다.
     *   2) 2 B C : 맛 B인 사탕의 개수를 C만큼 증가(또는 감소)시킨다.
     * - 맛 종류 수가 매우 크고, 개수 변경과 k번째 원소 찾기가 반복되므로 빠른 자료구조가 필요하다.
     *
     * [핵심 아이디어]
     * - 각 맛 번호를 인덱스로 보고, 해당 맛의 사탕 개수를 세그먼트 트리의 리프에 저장한다.
     * - 내부 노드는 구간합을 저장하므로,
     *   특정 구간에 사탕이 총 몇 개 있는지 빠르게 알 수 있다.
     * - "B번째 사탕 찾기"는 루트부터 내려가며 처리할 수 있다.
     *   - 왼쪽 자식의 구간합이 B 이상이면 왼쪽으로 내려간다.
     *   - 아니면 B에서 왼쪽 자식 개수를 빼고 오른쪽으로 내려간다.
     * - 이렇게 하면 k번째 원소 찾기를 O(log MAX)에 처리할 수 있다.
     *
     * [구현 메모]
     * - 맛의 종류가 1,000,000개이므로, 이를 모두 담을 수 있는 리프 개수로 2^20 = 1,048,576(P)을 사용했다.
     * - tree[idx]:
     *   - 리프: 해당 맛 번호의 사탕 개수
     *   - 내부 노드: 자식 구간의 사탕 개수 합
     * - update(idx, value):
     *   - 맛 idx의 개수를 value만큼 증감시키고, 부모 구간합을 갱신한다.
     * - find(k):
     *   - 현재 사탕들 중 k번째 사탕의 맛 번호를 찾는다.
     *   - 내려가면서 지나가는 노드들의 개수를 미리 1씩 감소시켜,
     *     "찾은 사탕을 꺼내는 효과"까지 한 번에 처리한다.
     *
     * [시간 복잡도]
     * - 개수 갱신: O(log MAX)
     * - k번째 사탕 찾기: O(log MAX)
     * - 총: O(N log MAX)
     */

    static StringBuilder sb = new StringBuilder();

    static final int P = 1_048_576;
    static int n;
    static int[] tree;

    static void find(int k) {
        int idx = 1;

        // 루트부터 내려가며 k번째 사탕이 속한 구간을 찾는다.
        while(idx < P) {
            tree[idx]--; // 이 경로의 사탕 1개를 꺼내는 효과를 미리 반영

            // 왼쪽 구간에 k번째 사탕이 포함되면 왼쪽으로 이동
            if(k <= tree[idx << 1]) idx <<= 1;
            else {
                // 아니면 왼쪽 구간 개수만큼 건너뛰고 오른쪽으로 이동
                k -= tree[idx << 1];
                idx = idx << 1 | 1;
            }
        }

        // 리프에서도 실제 사탕 1개 제거
        tree[idx]--;
        sb.append(idx - P + 1).append("\n");
    }

    static void update(int idx, int value) {
        idx += P;
        tree[idx] += value;

        while(idx > 1) {
            idx >>= 1;
            tree[idx] = tree[idx << 1] + tree[idx << 1 | 1];
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        n = Integer.parseInt(br.readLine());
        tree = new int[P*2];

        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int cmd = Integer.parseInt(st.nextToken());
            if(cmd == 1) {
                int k = Integer.parseInt(st.nextToken());
                find(k);
            } else {
                int taste = Integer.parseInt(st.nextToken()) - 1;
                int cnt = Integer.parseInt(st.nextToken());
                update(taste, cnt);
            }
        }

        System.out.println(sb);
    }

}