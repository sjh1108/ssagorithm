package _20261109.jun.OJ_122;

import java.io.*;
import java.util.*;

// OJ_122 정상까지 몇 번 갈아타나
// 그래프 탐색, bfs
public class Main {

    static int N, M;
    static ArrayList<Integer>[] graph;
    static int[] distance; // 각 정류장에서 정상까지 타야하는 케이블카의 최소 횟수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        graph = new ArrayList[N + 1]; // 정류장 번호는 1부터 N 까지.
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<Integer>();
        }

        // 입력 그대로 정방향 탐색 시 출발 점이 매번 달라져서 여러번 탐색 실행 -> 시간초과.
        // 도착 점은 정상으로 매번 동일. 정상을 출발점으로 삼으면 한번의 탐색으로 모든 정류장 탐색 가능.
        // 도착점인 정상을 탐색 시작점으로 지정하기 위해 노선을 역방향으로 입력.
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            graph[v].add(u);
        }

        // 정상 N을 시작점으로 삼고 각 정류장 까지의 노선 거리 탐색.
        bfs(N);

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(distance[i] + " ");
        }
        System.out.println(sb.toString().trim());
    }

    static void bfs(int start) {
        distance = new int[N + 1];
        Arrays.fill(distance, -1); // 갈 수 없으면 -1

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        distance[start] = 0; // 출발지점이 이미 정상일 땐 케이블카 사용 횟수 0

        // 정상 N에서 각 정류장 까지의 노선 거리 탐색
        while (!queue.isEmpty()) {
            int cur = queue.poll();

            for (int i = 0; i < graph[cur].size(); i++) {
                int next = graph[cur].get(i);

                if (distance[next] != -1) {
                    continue;
                }

                distance[next] = distance[cur] + 1;
                queue.offer(next);
            }
        }
    }
}
