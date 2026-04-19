package _20260420.thdwngjs.BOJ_32133;

import java.io.*;
import java.util.*;

// BFS(트리 탐색) + 그룹 분할 풀이
// 매 라운드마다 참가자들을 R/S/P 선택에 따라 그룹으로 분할하고,
// K명 이하가 되는 그룹이 생기면 그 그룹을 이기는 수를 역추적하여 출력한다.
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 참가자 수
        int M = Integer.parseInt(st.nextToken()); // 최대 라운드 수
        int K = Integer.parseInt(st.nextToken()); // 목표: K명 이하로 줄이기

        // 각 참가자의 M라운드 동안의 선택 (R/S/P)
        char[][] cmd = new char[N][];
        for (int i = 0; i < N; i++) {
            cmd[i] = br.readLine().toCharArray();
        }

        // 이미 참가자가 K명 이하이면 0라운드로 해결
        if (N >= 1 && N <= K) {
            System.out.print("0\n\n");
            return;
        }

        // 트리 구조로 경로를 기록 (parent[i] = {부모 노드 인덱스, 해당 라운드에서의 선택})
        List<int[]> parent = new ArrayList<>();
        parent.add(new int[]{-1, 0}); // 루트 노드

        // 초기 그룹: 모든 참가자
        int[] rootMem = new int[N];
        for (int i = 0; i < N; i++) rootMem[i] = i;

        int[] activeNodes = {0};       // 현재 탐색 중인 노드들
        int[][] activeMems = {rootMem}; // 각 노드에 속한 참가자 인덱스들

        int answerDepth = -1;
        int answerNode = -1;

        // 라운드별 BFS 탐색
        for (int d = 1; d <= M; d++) {
            List<Integer> nextNodes = new ArrayList<>();
            List<int[]> nextMems = new ArrayList<>();

            boolean found = false;
            for (int g = 0; g < activeNodes.length && !found; g++) {
                int nodeIdx = activeNodes[g];
                int[] mem = activeMems[g];

                // 현재 그룹의 참가자들을 d번째 라운드 선택(R/S/P)에 따라 카운트
                int rCount = 0, sCount = 0, pCount = 0;
                for (int idx : mem) {
                    char c = cmd[idx][d - 1];
                    if (c == 'R') rCount++;
                    else if (c == 'S') sCount++;
                    else pCount++;
                }

                // 각 선택별 그룹으로 참가자를 분류
                int[] rMem = new int[rCount];
                int[] sMem = new int[sCount];
                int[] pMem = new int[pCount];
                int ri = 0, si = 0, pi = 0;
                for (int idx : mem) {
                    char c = cmd[idx][d - 1];
                    if (c == 'R') rMem[ri++] = idx;
                    else if (c == 'S') sMem[si++] = idx;
                    else pMem[pi++] = idx;
                }

                int[][] subs = {rMem, sMem, pMem};
                char[] chars = {'R', 'S', 'P'};

                for (int i = 0; i < 3; i++) {
                    int size = subs[i].length;
                    if (size == 0) continue;

                    // 새 노드를 트리에 추가
                    int newNodeIdx = parent.size();
                    parent.add(new int[]{nodeIdx, chars[i]});

                    if (size <= K) {
                        // K명 이하 → 이 그룹을 이기면 된다
                        answerDepth = d;
                        answerNode = newNodeIdx;
                        found = true;
                        break;
                    } else {
                        // 아직 K명 초과 → 다음 라운드에서 계속 분할
                        nextNodes.add(newNodeIdx);
                        nextMems.add(subs[i]);
                    }
                }
            }

            if (found) break;
            if (nextNodes.isEmpty()) break;

            // 다음 라운드 탐색 대상 갱신
            activeNodes = new int[nextNodes.size()];
            activeMems = new int[nextNodes.size()][];
            for (int i = 0; i < nextNodes.size(); i++) {
                activeNodes[i] = nextNodes.get(i);
                activeMems[i] = nextMems.get(i);
            }
        }

        if (answerNode == -1) {
            System.out.print("-1\n");
            return;
        }

        // 트리를 역추적하여 각 라운드에서 상대가 낸 수를 복원
        char[] path = new char[answerDepth];
        int cur = answerNode;
        int d = answerDepth;
        while (cur != 0) {
            int[] node = parent.get(cur);
            path[--d] = (char) node[1];
            cur = node[0];
        }

        // 상대의 수를 이기는 수로 변환하여 출력 (R→S, S→P, P→R)
        StringBuilder sb = new StringBuilder();
        sb.append(answerDepth).append('\n');
        for (char t : path) {
            if (t == 'R') sb.append('S');       // 가위를 이기려면 바위... 아니, R(바위)을 이기려면 P(보)?
            else if (t == 'S') sb.append('P');   // 코드 로직: R→S(가위 출력), S→P(보 출력), P→R(바위 출력)
            else sb.append('R');                 // 즉, 상대 그룹의 수를 이기는 수를 낸다
        }
        sb.append('\n');

        System.out.print(sb);
    }
}
