package week12_01.thdwngjs.BOJ_9370;

import java.io.*;
import java.util.*;

// 백준 9370 - 미확인 도착지 (다익스트라 응용)
class Main {
    private static final int INF = 1 << 30; // 무한대 값 설정
    private static int N, M, t; // N: 교차로(노드) 수, M: 도로(간선) 수, t: 목적지 후보 수
    private static int s, g, h; // s: 예술가 출발지, g-h: 예술가가 반드시 지나간 도로

    private static int[] dist; // 최단 거리 배열
    private static List<List<int[]>> road; // 인접 리스트

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 수

        StringBuilder sb = new StringBuilder();
        while(T-- > 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            t = Integer.parseInt(st.nextToken());

            dist = new int[N+2];
            Arrays.fill(dist, INF); // 거리 배열 무한대로 초기화
            
            st = new StringTokenizer(br.readLine());
            s = Integer.parseInt(st.nextToken());
            g = Integer.parseInt(st.nextToken());
            h = Integer.parseInt(st.nextToken());

            road = new ArrayList<>();
            for(int i = 0; i < N + 2; i++) road.add(new ArrayList<>());

            // [핵심 로직: 가중치 변형]
            // 1. 모든 도로의 가중치를 2배로 늘린다. (짝수로 만듦)
            // 2. 반드시 지나야 하는 도로(g-h)의 가중치만 1을 뺀다. (홀수로 만듦)
            // 결과:
            // - g-h 도로를 포함하는 경로의 총합 = (짝수들) + (홀수 1개) = 홀수
            // - g-h 도로를 포함하지 않는 경로의 총합 = (짝수들) = 짝수
            // 따라서, 목적지까지의 최단 거리가 '홀수'라면 해당 최단 경로는 g-h를 반드시 포함했다는 뜻이 됨.
            // (단, 2*d - 1 은 여전히 다른 경로보다 상대적 크기 관계를 유지해야 하므로
            //  원래 최단 경로가 g-h를 포함한다면 변형된 가중치에서도 최단 경로가 됨)
            for(int i = 0; i < M; i++){
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                int d = Integer.parseInt(st.nextToken()) * 2; // 모든 가중치 2배

                // 반드시 지나야 하는 g-h 구간인 경우 1 감소 (홀수로 만듦)
                if((a == g && b == h) || (a == h && b == g)) --d;

                road.get(a).add(new int[]{b, d});
                road.get(b).add(new int[]{a, d});
            }

            // 다익스트라 실행 (출발지 s부터 모든 노드까지 최단 거리 계산)
            dijkstra();
            
            // 목적지 후보들 입력 받기
            List<Integer> list = new ArrayList<>();
            for(int i = 0; i < t; i++){
                list.add(Integer.parseInt(br.readLine()));
            }

            // 후보들 중 유효한 목적지(홀수 거리) 찾기
            Queue<Integer> ans = checkList(list);
            
            // 정답 출력 (오름차순 정렬되어 있음)
            while(!ans.isEmpty()){
                sb.append(ans.poll() + " ");
            }
            sb.append('\n');
        } // testcase end
        
        System.out.print(sb);
    }

    // 다익스트라 알고리즘
    private static void dijkstra(){
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparing(o -> o[1]));
        pq.add(new int[]{s, 0}); // 시작점
        dist[s] = 0;

        while(!pq.isEmpty()){
            int[] cur = pq.poll();
            int to = cur[0]; // 현재 노드
            int cost = cur[1]; // 현재까지의 비용

            // 이미 더 짧은 경로로 방문한 적이 있다면 스킵
            if(cost > dist[to]) continue;

            for(int[] nxt: road.get(to)){
                int next = nxt[0]; // 다음 노드
                int weight = nxt[1]; // 가중치
                
                // 최단 거리 갱신
                if(dist[next] > cost + weight){
                    dist[next] = cost + weight;
                    pq.add(new int[]{next, dist[next]});
                }
            }
        }
    }

    // 목적지 후보들 중 유효한 것 판별
    private static Queue<Integer> checkList(List<Integer> list){
        Queue<Integer> pq = new PriorityQueue<>(); // 결과를 오름차순으로 저장하기 위해 우선순위 큐 사용

        for(int p: list){
            // [판별 로직]
            // 최단 거리가 홀수(dist[p] % 2 == 1)라면, 
            // 변형된 가중치(2*d - 1)인 g-h 도로를 이용했다는 뜻이므로 유효한 목적지임.
            // (만약 도달할 수 없는 경우 INF는 짝수이므로 걸러짐)
            if(dist[p] % 2 == 1) pq.add(p);
        }

        return pq;
    }
}