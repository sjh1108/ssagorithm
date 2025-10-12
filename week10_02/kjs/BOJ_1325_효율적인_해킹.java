package algorithm.d1012;

import java.io.*;
import java.util.*;

/**
 * BOJ 1325 효율적인 해킹
 * 역그래프를 만들고, 각 정점에서 BFS로 도달 가능한 노드 수를 센다.
 */
public class BOJ_1325_효율적인_해킹 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 컴퓨터 수 (정점)
        int M = Integer.parseInt(st.nextToken()); // 신뢰 관계 수 (간선)

        ArrayList<Integer>[] revGraph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) revGraph[i] = new ArrayList<>();

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int A = Integer.parseInt(st.nextToken());
            int B = Integer.parseInt(st.nextToken());
            revGraph[B].add(A);
        }

        int[] count = new int[N + 1]; 

        int[] visited = new int[N + 1];
        int visitToken = 1;

        ArrayDeque<Integer> queue = new ArrayDeque<>();

        for (int start = 1; start <= N; start++) {
            int cnt = 0;
            queue.clear();
            queue.add(start);
            visited[start] = visitToken;

            while (!queue.isEmpty()) {
                int cur = queue.poll();
                cnt++;
                for (int nxt : revGraph[cur]) {
                    if (visited[nxt] != visitToken) {
                        visited[nxt] = visitToken;
                        queue.add(nxt);
                    }
                }
            }

            count[start] = cnt;
            visitToken++;
        }

        int max = 0;
        for (int i = 1; i <= N; i++) if (count[i] > max) max = count[i];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            if (count[i] == max) {
                sb.append(i).append(' ');
            }
        }
        System.out.println(sb.toString().trim());
    }
}
