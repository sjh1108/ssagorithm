package week01_04.sjh1108.BOJ_1167;

import java.util.*;
import java.io.*;

// 백준 1167 - 트리의 지름 (DFS 2번 사용)
class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N; // 정점의 개수
    static int M; // 가장 먼 노드의 인덱스를 저장할 변수

    static class Node {
        int x, y; // x: 연결된 노드 번호, y: 거리(가중치)
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    static ArrayList<Node>[] list; // 인접 리스트
    static boolean[] visited; // 방문 체크 배열
    static int max; // 최대 거리(지름) 저장 변수
    
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        // 그래프 초기화 (1번 노드부터 N번 노드까지)
        list = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            list[i] = new ArrayList<>();
        }

        // 트리 정보 입력
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()); // 시작 정점
            while(true){
                int b = Integer.parseInt(st.nextToken()); // 연결된 정점
                if(b == -1) break; // -1이면 입력 종료
                int c = Integer.parseInt(st.nextToken()); // 거리
                list[a].add(new Node(b, c));
            }
        }

        // [트리의 지름 구하는 공식]
        // 1. 임의의 정점(여기서는 1번)에서 가장 먼 정점(M)을 찾는다.
        // 2. 그 정점(M)에서 가장 먼 정점까지의 거리가 트리의 지름이다.

        // 1단계: 임의의 점(1)에서 가장 먼 노드 M 찾기
        visited = new boolean[N + 1];
        dfs(1, 0);

        // 2단계: 찾은 노드 M에서 가장 먼 노드까지의 거리 찾기 (이것이 지름)
        visited = new boolean[N + 1];
        max = 0; // 거리 초기화 (M은 dfs 내에서 갱신되지만, 여기서는 시작점으로 쓰임)
        dfs(M, 0);
        
        // 결과 출력
        System.out.println(max);
    }

    /**
     * DFS 탐색을 통해 가장 먼 노드와 그 거리를 찾습니다.
     * @param x 현재 노드
     * @param len 현재까지의 누적 거리
     */
    static void dfs(int x, int len){
        // 현재까지의 거리가 최대 거리보다 크면 갱신
        if(len > max){
            max = len;
            M = x; // 가장 먼 노드 번호 저장
        }
        visited[x] = true;

        // 연결된 노드들 탐색
        for(int i = 0; i < list[x].size(); i++){
            Node node = list[x].get(i);
            if(!visited[node.x]){
                dfs(node.x, len + node.y);
                // visited[node.x] = true; // 주의: 이 줄은 불필요하거나 로직상 틀릴 수 있음 (재귀 호출 전에 true로 만드는 것이 일반적이나, 여기서는 재귀 안에서 처리됨. 백트래킹이 필요 없는 트리 탐색이므로 다시 false로 만들 필요 없음)
                // 위 코드에서 dfs 호출 전에 visited[node.x] = true를 하면 dfs 함수 첫 줄의 visited[x]=true와 중복되거나 로직이 꼬일 수 있음.
                // 현재 구조에서는 dfs 진입 시 visited[x]=true를 하므로, 호출 후 별도 처리가 필요 없음.
            }
        }
    }
}