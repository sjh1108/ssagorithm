package _20261102.jun.OJ_110;

import java.io.*;
import java.util.*;

// OJ_110 숲길 관리 구역
// 실버, DFS, 그래프, 트리
public class Main {

    static int N;
    static ArrayList<Integer>[] graph;
    static boolean[] visited;
    static int[] c, parent;
    static long[] sumC;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N + 1]; // 쉼터는 1번 부터 시작.
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        visited = new boolean[N + 1];
        c = new int[N + 1];
        parent= new int[N + 1];
        sumC= new long[N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            c[i] = Integer.parseInt(st.nextToken());
        }

        int u, v;
        for (int i = 1; i < N; i++) { // 간선은 N - 1 개
            st = new StringTokenizer(br.readLine());
            u = Integer.parseInt(st.nextToken());
            v = Integer.parseInt(st.nextToken());
            graph[u].add(v);
            graph[v].add(u);
        }

        dfs(1); // 1번 쉼터 == root 부터 탐색 시작

        // 출력 (N <= 5,000)
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(parent[i] + " " + sumC[i]).append("\n");
        }
        System.out.print(sb);
    }

    static void dfs(int cur) {
        visited[cur] = true;
        sumC[cur] = (long)c[cur]; // 현재 구역 비용 초기화 (= 현재 방문 쉼터 비용)

        for (int i = 0; i < graph[cur].size(); i++) {
            int next = graph[cur].get(i);

            if (visited[next]) {
                continue;
            }

            parent[next] = cur; // root 부터 시작. 트리. dfs 탐색. 경로에서 자신 바로 앞 정점이 부모.

            dfs(next);

            sumC[cur] += sumC[next]; // 현재 구역 비용 = 현재 쉼터 비용 + (자식 구역들 비용 합)
        }
    }
}
