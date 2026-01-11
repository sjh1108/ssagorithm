package week01_02.thdwngjs.BOJ_14950;

import java.io.*;
import java.util.*;

// 백준 14950 - 정복자 (MST - Prim)
class Main {

    private static int N, M;
    private static List<List<int[]>> road;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken()); // 도시의 개수
        M = Integer.parseInt(st.nextToken()); // 도로의 개수
        int t = Integer.parseInt(st.nextToken()); // 정복할 때마다 증가하는 비용

        road = new ArrayList<>();
        for(int i = 0; i <= N; i++) road.add(new ArrayList<>());

        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken()); // 비용

            road.get(a).add(new int[]{b, c});
            road.get(b).add(new int[]{a, c});
        }
        
        // 프림 알고리즘을 위한 우선순위 큐 (비용 기준 오름차순)
        Queue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        pq.add(new int[] {1, 0}); // 1번 도시에서 시작

        // 비용의 합이 int 범위를 넘을 수 있으므로 long 사용 (최대 비용 약 50억)
        long sum = 0;
        
        boolean[] visited = new boolean[N+1];

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int to = cur[0];
            int cost = cur[1];

            if(visited[to]) continue;
            visited[to] = true;

            sum += cost;

            for(int[] con : road.get(to)){
                int nxt = con[0];

                if(visited[nxt]) continue;

                pq.add(con);
            }
        }

        // [추가 비용 계산]
        // 정복할 때마다 도로 비용이 t만큼 증가합니다.
        // 1번째 정복(도로 연결): +0 * t
        // 2번째 정복(도로 연결): +1 * t
        // ...
        // (N-1)번째 정복: +(N-2) * t
        // 총 추가 비용 = t * (0 + 1 + ... + N-2) = t * (N-1)(N-2)/2
        sum += (long)(N-1) * (N-2) / 2 * t;
        
        System.out.println(sum);
    }
}