package _20260323.thdwngjs.BOJ_1119;

import java.io.*;
import java.util.*;

/**
 * BOJ 1119 - 그래프
 * 알고리즘: 그래프 탐색 (DFS) + 연결 요소 분석
 *
 * 간선을 재배치(한 쪽 끝점을 옮기기)하여 그래프를 연결 그래프로 만들 때,
 * 최소 연산 횟수를 구하는 문제.
 *
 * 핵심 아이디어:
 * - 각 연결 요소의 노드 수와 간선 수를 구한다.
 * - 연결 요소를 하나로 합치려면 (연결 요소 수 - 1)개의 간선 재배치가 필요하다.
 * - 여분 간선(사이클에 포함된 간선) = edges - nodes + 1 → 이것이 재배치 가능한 간선 수
 * - 고립 노드(간선 없는 노드)가 있으면 불가능 (-1)
 */
class Main {
    private static int N;

    private static boolean[] visited;
    private static boolean[][] linked;
    // 각 연결 요소의 [노드 수, 간선 수]를 저장
    private static List<int[]> edgeList = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        // 노드가 1개면 이미 연결 그래프
        if(N == 1){
            System.out.println(0);
            return;
        }

        // 인접 행렬 입력 ('Y' = 연결, 'N' = 비연결)
        linked = new boolean[N][N];

        for(int i = 0; i < N; i++){
            char[] input = br.readLine().toCharArray();

            for(int j = 0; j <= i; j++){
                linked[i][j] = linked[j][i] = input[j] == 'Y';
            }
        }

        // 모든 연결 요소 탐색
        visited = new boolean[N];

        for(int i = 0; i < N; i++){
            if(!visited[i]) dfs(i);
        }

        boolean enable = true;
        int cnt = 0; // 재배치 가능한 여분 간선 수의 합

        for(int[] comp: edgeList){
            int nodes = comp[0];
            int edges = comp[1];

            // 고립 노드 (간선 0개)가 있으면 연결 불가능
            if(nodes == 1){
                enable = false;
                break;
            }

            // 여분 간선 수 = 간선 수 - (노드 수 - 1) = 트리에서 초과하는 간선
            cnt += edges - nodes + 1;
        }

        // 여분 간선이 (연결 요소 수 - 1) 이상이면 모두 연결 가능
        if(enable && cnt >= edgeList.size() - 1){
            System.out.println(edgeList.size() - 1);
        } else{
            System.out.println(-1);
        }
    }

    /**
     * DFS로 연결 요소를 탐색하며 노드 수와 간선 수를 세는 함수
     */
    private static void dfs(int start){
        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(start);
        visited[start] = true;

        int nodes = 0;
        int edges = 0;

        while(!stack.isEmpty()){
            int cur = stack.pop();
            nodes++;

            for(int i  = 0; i < N; i++){
                if(!linked[cur][i]) continue;

                // 양방향 간선이므로 양쪽에서 모두 카운트 (나중에 /2)
                edges++;

                if(!visited[i]){
                    visited[i] = true;
                    stack.push(i);
                }
            }
        }

        // edges는 양방향으로 세었으므로 /2
        edgeList.add(new int[]{nodes, edges / 2});
    }
}
