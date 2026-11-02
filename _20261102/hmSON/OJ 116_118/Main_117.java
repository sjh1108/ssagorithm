import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 멀티 소스 BFS, 누적합
     *
     * 중계탑의 출력 K와 전파가 닿는 거리가 1:1로 비례
     * 따라서 중계탑이 있는 마을이 시작점인 멀티 소스 BFS를 통해 가장 가까운 중계탑과의 거리가 0, 1, ...인 마을의 수를 카운트
     *
     * 각 출력 후보 K마다 전파가 닿지 않는 마을의 수를 구하는 것이 목적
     * 따라서 반대로 거리가 K 이하인 마을의 수를 미리 구해두면 거리가 K 초과인 마을의 수도 추가 순회 없이 단순 조회로 구할 수 있음
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int tower = Integer.parseInt(st.nextToken()); // 중계탑이 있는 마을 수
        int query = Integer.parseInt(st.nextToken()); // 쿼리 수

        boolean[] visited = new boolean[n];
        Queue<int[]> q = new ArrayDeque<>();
        st = new StringTokenizer(br.readLine());

        for(int i=0; i<tower; i++) {
            int ownTower = Integer.parseInt(st.nextToken()) - 1; // 중계탑이 있는 마을 번호
            q.offer(new int[]{ownTower, 0}); // 해당 마을들은 중계탑과의 거리가 0
            visited[ownTower] = true;
        }

        // 오솔길의 방향이 주어지지 않으므로 무방향 인접 리스트
        List<Integer>[] graph = new List[n];
        for(int i=0; i<n; i++) {
            graph[i] = new ArrayList<>();
        }

        for(int i=0; i<n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;
            graph[v1].add(v2);
            graph[v2].add(v1);
        }

        // 가장 가까운 중계탑과의 거리가 K에 해당하는 마을 수
        // 최악의 경우(경사 트리) 최대 거리는 n-1
        // 쿼리 입력으로 n이 주어질 수 있어 배열 크기는 n+1
        int[] cntByDist = new int[n+1];
        cntByDist[0] = tower; // 중계탑이 있는 마을들은 거리 0

        // BFS
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int nextDist = cur[1] + 1;

            for(int next : graph[cur[0]]) {
                if(visited[next]) continue; // 이미 방문한 마을

                visited[next] = true;
                cntByDist[nextDist]++;
                q.offer(new int[]{next, nextDist});
            }
        }

        // 누적합 전처리 : 가장 가까운 중계탑과의 거리가 K 이하인 마을의 수
        for(int i=1; i<=n; i++) {
            cntByDist[i] += cntByDist[i-1];
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<query; i++) {
            int power = Integer.parseInt(br.readLine()); // 출력 후보
            sb.append(n - cntByDist[power]).append("\n"); // 사각지대 미을 수 = 전체 마을 수 - 거리가 K 이하인 마을 수
        }

        System.out.println(sb);
    }

}