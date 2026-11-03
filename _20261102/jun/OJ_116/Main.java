package _20261102.jun.OJ_116;

import java.io.*;
import java.util.*;

public class Main {

    static int N, S, T = 0;
    static ArrayList<Integer>[] graph;
    static int[] visited; // 각 마을이 소문을 처음 듣는 시각
    static int[] t; // 각 시각에 소문을 처음 듣는 마을 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        S = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N + 1]; // 마을 번호는 1부터 시작
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }
        visited = new int[N + 1];
        Arrays.fill(visited, -1); // T는 0 부터 시작. 미방문은 T: -1

        // 입력
        for (int i = 1; i < N; i++) { // 간선은 N - 1 개
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph[u].add(v);
            graph[v].add(u);
        }

        // 탐색
        t = new int[N]; // T의 최대 값은 마을이 일렬로 연결된 트리 일 때. == 간선 수 == (N - 1)
        bfs(S);

        // 출력
        System.out.println(T);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < T; i++) {
            sb.append(t[i] + " ");
        }
        sb.append(t[T]);
        System.out.println(sb);
    }

    static void bfs(int start) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        visited[start] = 0; // 마을이 소문을 처음 듣는 시각
        t[visited[start]]++; // 소문을 처음 듣는 마을의 수

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int i = 0; i < graph[cur].size(); i++) {
                int next = graph[cur].get(i);

                if (visited[next] != -1) {
                    continue;
                }

                visited[next] = visited[cur] + 1;
                queue.offer(next);

                t[visited[next]]++;
                if (visited[next] > T) {
                    T = visited[next];
                }
            }
        }
    }
}
