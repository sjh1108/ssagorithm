import java.io.*;
import java.util.*;

public class Main_23309_철도_공사 {

    /*
     * BOJ_23309 : 철도 공사 (Gold_4)
     * 자료구조 및 알고리즘 : 구현, 연결리스트
     *
     * [문제 요약]
     * - 원형으로 연결된 역(노드)들이 있고, 각 역은 고유한 번호(ID)를 가진다.
     * - Q개의 명령을 처리한다.
     *   - Bx i j : i의 x방향(앞/뒤) 인접 역을 출력하고, 그 사이에 j를 삽입한다.
     *   - Cx i   : i의 x방향(앞/뒤) 인접 역을 출력하고, 그 역을 삭제한다.
     *   (x는 N 또는 P로 주어지며, 각각 next/prev 방향을 의미)
     * - 각 명령마다 출력이 필요하며, 모든 명령을 빠르게 처리해야 한다.
     *
     * [핵심 아이디어]
     * - 삽입/삭제가 빈번한 원형 구조이므로 "이중 연결리스트"가 가장 자연스럽다.
     * - 다만 노드 객체를 만들고 포인터를 갱신하는 방식(객체 생성/참조)은 상수 시간이 커질 수 있어,
     *   배열 2개(prev, next)로 연결리스트를 구현하면 매우 빠르게 처리할 수 있다.
     * - 각 역 ID를 인덱스로 사용하여
     *   prev[id] = 이전 역, next[id] = 다음 역 형태로 링크를 유지한다.
     *
     * [구현 메모]
     * - prev/next 배열 크기를 충분히 크게(1_000_000) 잡고, -1로 초기화한다.
     * - 초기 N개의 역을 입력받아 선형으로 연결한 뒤,
     *   맨 앞과 맨 뒤를 서로 연결하여 원형을 완성한다.
     * - add(dir, cur, newId):
     *   - dir에 따라 cur의 next 또는 prev를 target으로 잡고,
     *   - 문제 요구대로 target을 먼저 출력한 뒤,
     *   - (cur <-> target) 사이에 newId를 끼워 넣는다.
     * - remove(dir, cur):
     *   - dir에 따라 삭제 대상 target을 잡고 출력한 뒤,
     *   - cur이 target의 다음/이전을 직접 연결하도록 링크를 갱신한다.
     *   - 삭제된 노드의 링크는 -1로 끊어둔다(선택 사항이지만 디버깅/안전용).
     *
     * [시간 복잡도]
     * - 각 명령(삽입/삭제): 링크 갱신만 수행 -> O(1)
     * - 총: O(Q)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, cmds; // 초기 역 수, 명령어 수
    static int[] prev, next; // 각 역의 이전 역과 다음 역의 번호 관리(이중 연결리스트의 배열 구현)

    static void init() throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        cmds = Integer.parseInt(st.nextToken());

        // 역 ID를 인덱스로 쓰는 원형 이중 연결리스트 (배열 구현)
        prev = new int[1_000_000];
        next = new int[1_000_000];
        Arrays.fill(prev, -1);
        Arrays.fill(next, -1);

        st = new StringTokenizer(br.readLine());
        int prevId = -1, firstId = -1;
        for(int i=0; i<n; i++) {
            int id = Integer.parseInt(st.nextToken()) - 1;

            if(prevId == -1) firstId = id;
            else {
                // 이전 노드 <-> 현재 노드 연결
                prev[id] = prevId;
                next[prevId] = id;
            }
            prevId = id;
        }

        // 원형 연결 완성: 마지막 <-> 첫 번째
        prev[firstId] = prevId;
        next[prevId] = firstId;
    }

    static void add(char dir, int cur, int newId) {
        // dir에 따라 삽입 기준이 되는 이웃(target) 선택
        int target = dir == 'N' ? next[cur] : prev[cur];
        sb.append(target+1).append("\n"); // 문제 요구: 이웃 역 번호 출력

        // (cur <-> target) 사이에 newId 삽입
        if(dir == 'N') {
            next[newId] = target;
            prev[newId] = cur;
            next[cur] = newId;
            prev[target] = newId;
        } else {
            prev[newId] = target;
            next[newId] = cur;
            prev[cur] = newId;
            next[target] = newId;
        }
    }

    static void remove(char dir, int cur) {
        // dir에 따라 삭제 대상(target) 선택
        int target = dir == 'N' ? next[cur] : prev[cur];
        sb.append(target+1).append("\n"); // 문제 요구: 삭제될 이웃 역 번호 출력

        // target을 원형 리스트에서 제거하고 cur과 target의 반대편 이웃을 직접 연결
        if(dir == 'N') {
            next[cur] = next[target];
            prev[next[target]] = cur;
            next[target] = -1;
            prev[target] = -1;
        } else {
            prev[cur] = prev[target];
            next[prev[target]] = cur;
            next[target] = -1;
            prev[target] = -1;
        }
    }

    public static void main(String[] args) throws Exception {
        init();

        for(int i=0; i<cmds; i++) {
            st = new StringTokenizer(br.readLine());
            String cmd = st.nextToken();
            // 명령어의 해야 할 일과 방향을 분리
            char todo = cmd.charAt(0), direction = cmd.charAt(1);
            int target = Integer.parseInt(st.nextToken()) - 1;

            // 'B': 역 추가, 'C': 역 폐쇠
            if(todo == 'B') {
                int newId = Integer.parseInt(st.nextToken()) - 1;
                add(direction, target, newId);
            } else {
                remove(direction, target);
            }
        }

        System.out.println(sb);
    }

}