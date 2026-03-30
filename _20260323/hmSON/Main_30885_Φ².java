package gold_4;

import java.io.*;
import java.util.*;

public class Main_30885_Φ² {

    /*
     * BOJ_30885 : Φ² (Gold_4)
     * 자료구조 및 알고리즘 : 구현, 시뮬레이션, 연결 리스트
     *
     * [문제 요약]
     * - 일렬로 배치된 n개의 미생물이 있고, 각 미생물은 (번호 idx, 양 amount)를 가진다.
     * - 왼쪽/오른쪽 인접 미생물 중 자신보다 양이 작거나 같은 미생물은 잡아먹을 수 있으며,
     *   잡아먹으면 그 미생물은 제거되고 자신의 양에 먹은 양만큼이 더해진다.
     * - 이 과정을 반복해 미생물이 1개만 남을 때, 남은 미생물의 (amount, idx)를 출력한다.
     *
     * [핵심 아이디어]
     * - 인접한 원소를 삭제/연결하며 반복적으로 시뮬레이션해야 하므로,
     *   배열에서 삭제를 반복하면 이동 비용이 커질 수 있다.
     * - prev/next 링크를 직접 갱신할 수 있는 이중 연결 리스트로 구현하면
     *   인접 삭제 처리를 O(1)로 수행할 수 있다.
     * - head/tail 더미 노드를 두어 양끝 경계 처리를 단순화한다.
     *
     * [구현 메모]
     * - Node: (idx, amount, prev, next)
     * - head(-1), tail(-1)을 더미 노드로 두고 실제 노드는 head.next ~ tail.prev로 관리한다.
     * - while(n > 1) 동안 전체를 순회하며,
     *   - 현재 cur이 왼쪽/오른쪽을 먹을 수 있으면 해당 노드를 리스트에서 제거하고(n--),
     *     먹은 amount를 add에 누적한다.
     *   - 양쪽 먹기가 모두 가능할 수도 있으므로 한 번의 방문에서 좌/우를 각각 판정한다.
     *   - 마지막에 cur.amount += add로 먹은 양을 반영한다.
     * - 삭제는 링크 갱신만으로 처리:
     *   - (삭제 대상).prev.next = (삭제 대상).next
     *   - (삭제 대상).next.prev = (삭제 대상).prev
     *
     * [시간 복잡도]
     * - 각 순회는 O(n)이고, n이 줄어들 때까지 반복되므로 최악의 경우 O(n^2)까지 가능하다.
     * - 다만 삭제 자체는 링크 갱신으로 O(1)이며, 문제 의도는 연결 리스트 기반 시뮬레이션이다.
     */

    static class Node {
        int idx;
        long amount;
        gold_4.Main_30885.Node prev, next;

        public Node(int idx, long amount) {
            this.idx = idx;
            this.amount = amount;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        gold_4.Main_30885.Node head = new gold_4.Main_30885.Node(-1, -1), tail = new gold_4.Main_30885.Node(-1, -1);
        gold_4.Main_30885.Node prev = head;

        // 이중 연결 리스트 구성 (head <-> 1 <-> 2 <-> ... <-> n <-> tail)
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++) {
            int amount = Integer.parseInt(st.nextToken());
            gold_4.Main_30885.Node node = new gold_4.Main_30885.Node(i, amount);
            node.prev = prev;
            prev.next = node;
            prev = node;
        }
        tail.prev = prev;
        prev.next = tail;

        // 미생물이 1개 남을 때까지 반복
        while(n > 1) {
            gold_4.Main_30885.Node cur = head.next;
            while(cur != tail) {
                // 왼쪽 미생물을 먹고 커진 뒤 원래 본인보다 컸던 오른쪽 미생물을 먹는 문제 방지
                long add = 0;

                // 왼쪽 미생물을 먹을 수 있으면 제거하고 양을 누적
                if(cur.prev.idx != -1 && cur.amount >= cur.prev.amount) {
                    n--;
                    add += cur.prev.amount;
                    cur.prev.prev.next = cur;
                    cur.prev = cur.prev.prev;
                }

                // 오른쪽 미생물을 먹을 수 있으면 제거하고 양을 누적
                if(cur.next.idx != -1 && cur.amount >= cur.next.amount) {
                    n--;
                    add += cur.next.amount;
                    cur.next.next.prev = cur;
                    cur.next = cur.next.next;
                }

                // 이번 방문에서 먹은 양 반영 후 다음 노드로 이동
                cur.amount += add;
                cur = cur.next;
            }
        }

        // 마지막 남은 미생물은 head.next
        System.out.println(head.next.amount + "\n" + head.next.idx);
    }

}