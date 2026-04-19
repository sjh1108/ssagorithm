package _20260420.thdwngjs.BOJ_32133;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        char[][] cmd = new char[N][];
        for (int i = 0; i < N; i++) {
            cmd[i] = br.readLine().toCharArray();
        }

        if (N >= 1 && N <= K) {
            System.out.print("0\n\n");
            return;
        }

        List<int[]> parent = new ArrayList<>();
        parent.add(new int[]{-1, 0});

        int[] rootMem = new int[N];
        for (int i = 0; i < N; i++) rootMem[i] = i;

        int[] activeNodes = {0};
        int[][] activeMems = {rootMem};

        int answerDepth = -1;
        int answerNode = -1;

        for (int d = 1; d <= M; d++) {
            List<Integer> nextNodes = new ArrayList<>();
            List<int[]> nextMems = new ArrayList<>();

            boolean found = false;
            for (int g = 0; g < activeNodes.length && !found; g++) {
                int nodeIdx = activeNodes[g];
                int[] mem = activeMems[g];

                int rCount = 0, sCount = 0, pCount = 0;
                for (int idx : mem) {
                    char c = cmd[idx][d - 1];
                    if (c == 'R') rCount++;
                    else if (c == 'S') sCount++;
                    else pCount++;
                }

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

                    int newNodeIdx = parent.size();
                    parent.add(new int[]{nodeIdx, chars[i]});

                    if (size <= K) {
                        answerDepth = d;
                        answerNode = newNodeIdx;
                        found = true;
                        break;
                    } else {
                        nextNodes.add(newNodeIdx);
                        nextMems.add(subs[i]);
                    }
                }
            }

            if (found) break;
            if (nextNodes.isEmpty()) break;

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

        char[] path = new char[answerDepth];
        int cur = answerNode;
        int d = answerDepth;
        while (cur != 0) {
            int[] node = parent.get(cur);
            path[--d] = (char) node[1];
            cur = node[0];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(answerDepth).append('\n');
        for (char t : path) {
            if (t == 'R') sb.append('S');
            else if (t == 'S') sb.append('P');
            else sb.append('R');
        }
        sb.append('\n');

        System.out.print(sb);
    }
}
