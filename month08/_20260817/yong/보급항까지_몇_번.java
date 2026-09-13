/*
    각 섬이 Node, 항로가 Edge -> 그래프

    각 섬에서 보급항을 찾으면 BFS를 N번 반복해야함
    -> 모든 보급항을 큐에 먼저 넣고 BFS 한번 진행

    BFS는 가까운 노드부터 탐색하므로
    어떤 섬에 처음 도착했을 때의 거리가 가장 가까운 보급항까지의 최소 거리

    result를 방문 배열로도 사용
    -1 : 미방문
    0 : 보급항
    1 이상 : 보급항까지 최소 이동 횟수
*/

import java.io.*;
import java.util.*;

public class 보급항까지_몇_번 {

    static int N, M, P;
    static ArrayDeque<Integer>[] graph;
    static int result[];
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());

        // 최적 결과 초기화
        result = new int[N + 1]; // 1base
        Arrays.fill(result, -1);

        Queue<Integer> q = new ArrayDeque<>();

        // 보급항 입력
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < P; i++){
            int islandNum = Integer.parseInt(st.nextToken());
            result[islandNum] = 0;
            q.offer(islandNum);
        }
        // 섬 항로 초기화
        graph = new ArrayDeque[N + 1]; // 1base

        for(int i = 0; i <= N; i++){
            graph[i] = new ArrayDeque<>();
        }

        // 항로 입력
        for(int i = 0; i < M; i++){ 
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a].offer(b);
            graph[b].offer(a);
        }
        
        // 보급항 시뮬레이션
        simulate(q);
        
        for(int i = 1; i <= N; i++){
            System.out.print(result[i] + " ");
        }
    }

    // 해당 섬과 가장 가까운 보급항까지의 최소 이동횟수 구하는 함수
    public static void simulate(Queue<Integer> q){
        while(!q.isEmpty()){
            int now = q.poll();

            // 현재 섬과 연결된 다른섬들 확인
            for(int next : graph[now]){
                if(result[next] != -1) continue;
                // 현재 섬까지 이동 횟수 + 1
                result[next] = result[now] + 1;
                q.offer(next);
            }
        }
    }

}
