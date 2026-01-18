import java.io.*;
import java.util.*;

// 백준 1854 - K번째 최단경로 찾기 (다익스트라 응용)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int n = Integer.parseInt(st.nextToken()); // 노드 개수
        int m = Integer.parseInt(st.nextToken()); // 간선 개수
        int k = Integer.parseInt(st.nextToken()); // K번째 최단 경로를 구해야 함

        // 인접 리스트 초기화
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) graph.add(new ArrayList<>());

        // 도로 정보 입력 (u -> v, 가중치 w)
        while (m-- > 0) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()) - 1; // 1-based -> 0-based
            int v = Integer.parseInt(st.nextToken()) - 1;
            int w = Integer.parseInt(st.nextToken());
            graph.get(u).add(new int[]{v, w});
        }

        // [핵심] 각 노드에 도달하는 최단 경로들을 저장할 리스트
        // dist.get(i)는 i번 노드까지 가는 경로들의 비용 리스트 (최대 K개 유지)
        // 일반적으로는 PriorityQueue(Max Heap)를 사용하지만, 여기서는 List + Sort를 사용
        List<List<Integer>> dist = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            dist.add(new ArrayList<>());
        }

        // 다익스트라를 위한 우선순위 큐 (현재까지의 경로 비용 기준 오름차순, Min-Heap)
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o[1]));
        
        // 시작점(0번 노드) 초기화
        pq.add(new int[]{0, 0});
        dist.get(0).add(0);

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int u = cur[0];      // 현재 노드
            int cost = cur[1];   // 현재까지의 비용

            // 현재 노드(u)와 연결된 다음 노드(v)들을 탐색
            for (int[] next : graph.get(u)) {
                int v = next[0];
                int w = next[1];
                int newCost = cost + w; // 다음 노드까지의 새로운 총 비용
                
                // v번 노드에 저장된 경로 리스트 가져오기
                List<Integer> vDist = dist.get(v);

                // Case 1: 아직 K개의 경로를 다 채우지 못한 경우
                if (vDist.size() < k) {
                    vDist.add(newCost); // 무조건 추가
                    Collections.sort(vDist); // 오름차순 정렬 (가장 큰 값이 마지막에 오도록 유지)
                    pq.add(new int[]{v, newCost}); // 큐에 추가하여 탐색 계속
                } 
                // Case 2: 이미 K개의 경로를 찾았지만, 더 짧은 경로를 발견한 경우
                else {
                    // 현재 저장된 K개의 경로 중 가장 긴 경로(K번째 최단 경로) 확인
                    int maxDist = vDist.get(vDist.size() - 1);
                    
                    // 새로 찾은 경로(newCost)가 기존의 K번째 경로(maxDist)보다 짧다면 교체
                    if (newCost < maxDist) {
                        vDist.remove(vDist.size() - 1); // 기존의 가장 긴 경로 삭제
                        vDist.add(newCost); // 새 경로 추가
                        Collections.sort(vDist); // 다시 정렬하여 순서 유지
                        pq.add(new int[]{v, newCost}); // 큐에 추가하여 갱신된 정보로 탐색
                    }
                }
            }
        }

        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for(List<Integer> d : dist) {
            // K번째 경로가 존재하면 출력, 없으면 -1 출력
            if (d.size() == k)  sb.append(d.get(k - 1)).append('\n');
            else                sb.append(-1).append('\n');
        }
        System.out.println(sb);
    }
}