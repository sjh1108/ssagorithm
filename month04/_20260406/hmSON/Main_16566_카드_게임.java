package Rank5.gold_1;

import java.io.*;
import java.util.*;

public class Main_16566_카드_게임 {

    /*
     * BOJ_16566 : 카드 게임 (Gold_1)
     * 자료구조 및 알고리즘 : 이분 탐색, Union Find, 정렬
     *
     * [문제 요약]
     * - 민수는 카드 cardCnt장을 가지고 있고, 철수는 카드를 t번 낸다.
     * - 매 턴마다 민수는 "철수가 낸 카드보다 큰 카드 중 가장 작은 카드"를 내야 한다.
     * - 한 번 사용한 카드는 다시 사용할 수 없다.
     * - 각 턴마다 민수가 내는 카드를 출력한다.
     *
     * [핵심 아이디어]
     * - 먼저 카드들을 정렬해 두면,
     *   "op보다 큰 카드 중 가장 작은 카드"는 이분 탐색으로 찾을 수 있다.
     * - 하지만 사용한 카드는 다시 쓰면 안 되므로,
     *   단순 이분 탐색만으로는 이미 사용된 카드인지 빠르게 처리하기 어렵다.
     * - 그래서 Union Find를 이용해
     *   "현재 인덱스에서 실제로 사용할 수 있는 다음 카드 인덱스"를 관리한다.
     * - 어떤 카드를 사용하면, 그 인덱스를 바로 다음 인덱스와 연결해 두어
     *   이후 find를 호출했을 때 다음 사용 가능한 카드로 건너뛸 수 있게 만든다.
     *
     * [구현 메모]
     * - card[] : 민수가 가진 카드 값들 (오름차순 정렬)
     * - head[i] :
     *   - i번 카드 인덱스가 아직 사용 가능하면 head[i] == i
     *   - 사용 후에는 다음 후보 인덱스를 가리키도록 갱신
     * - 이분 탐색:
     *   - left를 "card[left] > op 를 처음 만족하는 위치"로 찾는다.
     * - myCard = find(left):
     *   - left 위치가 이미 사용된 경우, Union Find를 통해 다음 사용 가능한 카드 인덱스를 찾는다.
     * - 카드 사용 후:
     *   - head[myCard] = find(myCard + 1)
     *   - 즉, 현재 카드는 다음 후보와 연결하여 재사용되지 않도록 처리한다.
     * - head 배열 크기를 cardCnt+1로 두어,
     *   마지막 카드 다음 위치(cardCnt)를 처리할 수 있게 했다.
     *
     * [시간 복잡도]
     * - 카드 정렬: O(M log M)   (M = cardCnt)
     * - 각 턴:
     *   - 이분 탐색 O(log M)
     *   - Union Find 거의 O(1)
     * - 총: O(M log M + T log M)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int cardCnt, t;
    static int[] card, head;

    static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        int max = Integer.parseInt(st.nextToken());
        cardCnt = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());

        card = new int[cardCnt];
        head = new int[cardCnt+1];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<cardCnt; i++) {
            card[i] = Integer.parseInt(st.nextToken());
            head[i] = i;
        }

        Arrays.sort(card);
    }

    static int find(int x) {
        if(x == head[x]) return x;
        return head[x] = find(head[x]);
    }

    public static void main(String[] args) throws Exception {
        init();

        st = new StringTokenizer(br.readLine());
        for(int i=0; i<t; i++) {
            int op = Integer.parseInt(st.nextToken());

            // op보다 큰 카드 중 가장 왼쪽 인덱스를 이분 탐색으로 찾는다.
            int left = 0, right = cardCnt-1;
            while(left <= right) {
                int mid = (left + right) / 2;
                if(card[mid] > op) right = mid - 1;
                else left = mid + 1;
            }

            // 이미 사용된 위치일 수 있으므로 Union Find로 실제 사용 가능한 카드 인덱스를 찾는다.
            int myCard = find(left);
            sb.append(card[myCard]).append("\n");

            // 현재 카드를 사용 처리하고, 다음 사용 가능한 카드로 연결한다.
            head[myCard] = find(myCard + 1);
        }

        System.out.println(sb);
    }

}