package week11_01.sjh1108.BOJ_1707;

import java.util.*;
import java.io.*;

// 백준 1707 - 이분 그래프
class Main{
    private static int N, M; // N: 정점의 개수, M: 간선의 개수
    
    // 각 정점의 색상(그룹)을 저장할 배열
    // 0: 미방문, 1: 1번 그룹(색상), -1: 2번 그룹(색상)
    private static int[] colors; 
    
    // 그래프를 표현할 인접 리스트
    private static List<List<Integer>> graph;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int T = Integer.parseInt(br.readLine()); // 테스트 케이스의 수

        StringBuilder sb = new StringBuilder(); // 결과를 모을 StringBuilder
        StringTokenizer st;
        
        // 각 테스트 케이스에 대해 반복
        while(T-- > 0){
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken()); // 정점 수
            M = Integer.parseInt(st.nextToken()); // 간선 수

            // 그래프(인접 리스트) 초기화 (0번 ~ N번)
            graph = new ArrayList<>();
            for(int i = 0; i <= N; i++){
                graph.add(new ArrayList<>());
            }

            // M개의 간선 정보 입력받기
            while(M-- > 0){
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                // 양방향 그래프(무방향 그래프)이므로 양쪽에 추가
                graph.get(a).add(b);
                graph.get(b).add(a);
            }

            boolean flag = true; // 이분 그래프 판별 플래그 (true: YES, false: NO)
            colors = new int[N + 1]; // 색상 배열 초기화 (0은 미방문)

            // [핵심] 비연결 그래프(컴포넌트가 여러 개인 경우)를 처리하기 위해
            // 모든 정점을 1번부터 N번까지 확인
            for(int i = 1; i <= N; i++){
                // 아직 방문(색칠)하지 않은 정점이 있다면 (새로운 컴포넌트의 시작점)
                if(colors[i] == 0){
                    // 해당 정점부터 BFS를 시작 (색상은 1로 시작)
                    // bfs가 false를 반환하면(이분 그래프가 아니면) flag를 false로 설정
                    flag = bfs(i, 1);
                }
                
                // BFS 결과, 이분 그래프가 아니라고 판명되면(flag == false)
                // 더 이상 탐색할 필요가 없으므로 즉시 중단
                if(!flag) break;
            }

            // 최종 판별 결과에 따라 YES/NO 추가
            if(flag){
                sb.append("YES\n");
            }else{
                sb.append("NO\n");
            }
        } // 테스트 케이스 루프 종료

        System.out.println(sb); // 모든 테스트 케이스 결과 한 번에 출력
    }

    /**
     * BFS를 사용하여 이분 그래프인지 판별합니다.
     * (인접한 노드끼리 서로 다른 색(1, -1)을 칠할 수 있는지 확인)
     * @param start BFS를 시작할 정점
     * @param color 시작 정점에 칠할 색상 (1 또는 -1)
     * @return 해당 컴포넌트가 이분 그래프이면 true, 아니면 false
     */
    static boolean bfs(int start, int color){
        Queue<Integer> q = new LinkedList<>();
        q.add(start); // 큐에 시작 정점 추가

        colors[start] = color; // 시작 정점 색칠
        
        while(!q.isEmpty()){
            int cur = q.poll(); // 큐에서 현재 정점을 꺼냄

            // 현재 정점(cur)에 연결된 모든 인접 정점(next)을 확인
            for(int next : graph.get(cur)){
                
                // [충돌 감지] 인접한 정점(next)이 현재 정점(cur)과 색상이 같은 경우
                // (예: cur=1인데 next=1)
                if(colors[next] == colors[cur]){
                    return false; // 이분 그래프가 아님
                }

                // 인접한 정점(next)이 아직 방문(색칠)하지 않았다면 (colors[next] == 0)
                if(colors[next] == 0){
                    // 현재 정점(cur)과 *반대*되는 색상으로 칠함
                    // (cur=1이면 next=-1, cur=-1이면 next=1)
                    colors[next] = -colors[cur];
                    
                    // 큐에 추가하여 다음 탐색 대상으로
                    q.add(next);
                }
                // (만약 colors[next] == -colors[cur] 라면, 
                //  즉 이미 방문했고 색상도 올바르게 반대라면 아무것도 안 함)
            }
        }

        // 큐가 비워질 때까지 (즉, 연결된 컴포넌트 탐색이 끝날 때까지) 충돌이 없었다면
        return true; // 이분 그래프 조건을 만족함
    }
}