package week11_01.sjh1108.BOJ_1162;

import java.io.*;
import java.util.*;

// 백준 1262 - 도로 포장 (다익스트라 응용)
class Main {

    // 다익스트라 우선순위 큐(PQ)에 저장할 노드 정보
    static class Node implements Comparable<Node>{
        int to,cnt; // to: 목적지 노드, cnt: 'to'까지 오면서 *포장한 도로의 수*
        long cost; // cost: 'to'까지 오는 데 걸린 *누적 비용*

        public Node(int node, long weight, int cnt) {
            this.to = node; // 목적지
            this.cnt = cnt; // 포장 횟수
            this.cost = weight; // 누적 비용
        }

        // 비용(cost)을 기준으로 오름차순 정렬 (최소 힙)
        @Override
        public int compareTo(Node n){
            return Long.compare(this.cost, n.cost);
        }
    }

    private static int N, M, K; // N: 도시(노드) 수, M: 도로(간선) 수, K: 포장할 도로의 최대 개수
    private static List<List<Node>> graph; // 인접 리스트 (그래프 정보)

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 그래프 초기화 (0번 ~ N번 노드)
        graph = new ArrayList<>();
        for(int i = 0; i <= N; i++){
            graph.add(new ArrayList<>());
        }

        // M개의 간선(도로) 정보 입력
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());
            
            int s = Integer.parseInt(st.nextToken()); // 출발지
            int e = Integer.parseInt(st.nextToken()); // 도착지
            int c = Integer.parseInt(st.nextToken()); // 비용

            // 양방향 간선 추가
            // (Node의 cnt는 0으로 넣지만, 이 값은 그래프 저장 시에는 의미 없음)
            graph.get(s).add(new Node(e, c, 0));
            graph.get(e).add(new Node(s, c, 0));
        }

        // 다익스트라 알고리즘 실행 및 결과 출력
        System.out.println(dijkstra());
    }

    /**
     * K개의 도로를 포장할 수 있을 때, 1번에서 N번까지 가는 최소 비용을
     * 다익스트라 알고리즘으로 계산합니다.
     * @return 1번 -> N번 최소 비용
     */
    private static long dijkstra(){
        // [핵심] 2차원 최소 비용(거리) 배열
        // dist[i][j] = "i번 노드까지 j개의 도로를 포장하여 도착했을 때의 최소 비용"
        long[][] dist = new long[N+1][K+1]; 

        // 모든 비용을 무한대(Long.MAX_VALUE)로 초기화
        for(long[] d: dist){
            Arrays.fill(d, Long.MAX_VALUE);
        }

        // 우선순위 큐 (최소 힙)
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // 시작 설정: 1번 노드에 0개의 도로를 포장하고 도착하는 비용은 0
        dist[1][0] = 0;
        // 큐에 시작점 추가 (1번 노드, 비용 0, 포장 0개)
        pq.add(new Node(1, 0, 0));

        while(!pq.isEmpty()){
            // 현재 PQ에서 누적 비용(cost)이 가장 적은 노드(경로)를 꺼냄
            Node cur = pq.poll();
            
            // (cur.to: 현재 노드, cur.cnt: *지금까지* 포장한 도로 수, cur.cost: *지금까지* 누적 비용)

            // [최적화]
            // 만약 큐에서 꺼낸 비용(cur.cost)이, 이미 알려진
            // (cur.to에 cur.cnt번 포장해서 가는) 최소 비용(dist[cur.to][cur.cnt])보다 크다면,
            // 이 경로는 더 볼 필요가 없으므로 스킵
            if(dist[cur.to][cur.cnt] < cur.cost) continue;

            // 현재 노드(cur.to)와 연결된 모든 다음 노드(nxt)를 탐색
            for (Node nxt : graph.get(cur.to)) {
                // (nxt.to: 다음 노드, nxt.cost: (cur.to -> nxt.to) 간선의 비용)

                // [Case 1] (cur.to -> nxt.to) 도로를 *포장하는* 경우
                
                // (cur.cnt < K): 포장 기회(K)가 아직 남아있어야 함
                // (dist[...] > dist[...]):
                //    (cur.to -> nxt.to)를 포장(비용 0)해서 가는 것이 (이때 포장 횟수는 cur.cnt + 1 이 됨)
                //    기존에 (cur.cnt+1)번 포장해서 nxt.to에 갔던 최소 비용보다 저렴한지 확인
                if(cur.cnt < K && dist[nxt.to][cur.cnt + 1] > dist[cur.to][cur.cnt]){
                    
                    // (포장했으므로) 간선 비용(nxt.cost)을 더하지 않고,
                    // 'cur.to'까지의 비용(dist[cur.to][cur.cnt])으로
                    // 'nxt.to'까지 (cur.cnt+1)번 포장한 최소 비용을 갱신
                    dist[nxt.to][cur.cnt + 1] = dist[cur.to][cur.cnt];

                    // 갱신된 정보를 큐에 추가 (포장 횟수 +1)
                    pq.add(new Node(nxt.to, dist[nxt.to][cur.cnt + 1], cur.cnt + 1));
                }

                // [Case 2] (cur.to -> nxt.to) 도로를 *포장하지 않는* 경우 (일반 다익스트라)
                
                // (dist[...] > dist[...]):
                //    포장 횟수를 *그대로*(cur.cnt) 유지하고, 간선 비용(nxt.cost)을 내고 가는 것이
                //    기존에 (cur.cnt)번 포장해서 nxt.to에 갔던 최소 비용보다 저렴한지 확인
                if(dist[nxt.to][cur.cnt] > dist[cur.to][cur.cnt] + nxt.cost){
                    
                    // 최소 비용 갱신 (비용 + nxt.cost)
                    dist[nxt.to][cur.cnt] = dist[cur.to][cur.cnt] + nxt.cost;
                    
                    // 갱신된 정보를 큐에 추가 (포장 횟수 *유지*)
                    pq.add(new Node(nxt.to, dist[nxt.to][cur.cnt], cur.cnt));
                }
            }
        }

        // N번 노드(도착지)까지의 최종 최소 비용 계산
        long min = Long.MAX_VALUE;
        
        // N번 노드에 (0 ~ K)번 포장해서 도착한 모든 경우 (dist[N][0] ~ dist[N][K]) 중에서
        for(long d: dist[N]){
            // 가장 작은 값을 찾음
            min = Math.min(min, d);
        }
        
        // 최종 최소 비용 반환
        return min;
    }
}