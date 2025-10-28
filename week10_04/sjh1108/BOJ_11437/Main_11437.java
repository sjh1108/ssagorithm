package week10_04.sjh1108.BOJ_11437;

import java.io.*;
import java.util.*;

class Main {
    
    // 각 노드의 깊이를 저장할 배열
    private static int[] depths;
    // 각 노드의 직계 부모 노드를 저장할 배열
    private static int[] parents;
    // 트리를 표현하기 위한 인접 리스트 (양방향 연결 정보)
    private static List<List<Integer>> list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 노드의 개수 N
        int N = Integer.parseInt(br.readLine());

        // 배열들 초기화 (1번 노드부터 사용하므로 N+1 크기)
        depths = new int[N+1];
        parents = new int[N+1];
        
        // 인접 리스트 초기화 (0번 ~ N번 리스트 생성)
        list = new ArrayList<>();
        for(int i = 0; i <= N; i++){
            list.add(new ArrayList<>());
        }

        StringTokenizer st;
        // N-1개의 간선(트리) 정보 입력받기
        for(int i = 0; i < N-1; i++){
            st = new StringTokenizer(br.readLine());
            
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            
            // 양방향으로 연결 정보 추가
            list.get(a).add(b);
            list.get(b).add(a);
        }
        
        // DFS를 실행하여 각 노드의 깊이(depths)와 부모(parents) 정보를 계산
        // 루트 노드를 1번, 깊이를 1, 루트의 부모는 0(없는 노드)으로 설정하고 시작
        initTree(1, 1, 0);
        
        // LCA를 찾을 쿼리의 개수 M
        int M = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder(); // 출력을 모을 StringBuilder
        
        // M개의 쿼리 처리
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            // LCA를 찾을 두 노드 a, b
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // LCA 함수를 호출하여 최소 공통 조상을 찾음
            int parent = LCA(a, b);
            sb.append(parent + "\n"); // 결과 저장
        }

        System.out.println(sb); // 모든 쿼리 결과 한 번에 출력
    }

    /**
     * DFS(깊이 우선 탐색)를 이용해 트리의 각 노드에 대한 깊이와 부모 노드 정보를 설정합니다.
     * @param cur 현재 탐색 중인 노드
     * @param depth 현재 노드의 깊이
     * @param parent 현재 노드의 부모 노드
     */
    private static void initTree(int cur, int depth, int parent){
        depths[cur] = depth; // 현재 노드의 깊이 기록
        parents[cur] = parent; // 현재 노드의 부모 기록

        // 현재 노드(cur)에 연결된 모든 다음 노드(nxt)를 순회
        for(int nxt: list.get(cur)){
            // 다음 노드(nxt)가 부모 노드(parent)가 아니라면 (즉, 자식 노드라면)
            if(nxt != parent){
                // 자식 노드로 재귀 호출 (깊이는 +1, 부모는 'cur'로 설정)
                initTree(nxt, depth+1, cur);
            }
        }
    }

    /**
     * 두 노드 a와 b의 최소 공통 조상(LCA)을 찾습니다. (이 코드는 기본적인 방식입니다)
     * @param a 노드 a
     * @param b 노드 b
     * @return 두 노드의 LCA
     */
    private static int LCA(int a, int b){
        // 1. 두 노드의 깊이를 가져옵니다.
        int aDepth = depths[a];
        int bDepth = depths[b];

        // 2. [깊이 맞추기] 두 노드의 깊이가 같아질 때까지 더 깊은 노드를 부모 쪽으로 한 칸씩 올립니다.
        
        // a가 더 깊다면, a를 부모로 이동시키고 깊이를 1 줄임 (b와 깊이가 같아질 때까지)
        while(aDepth > bDepth){
            a = parents[a];
            aDepth--;
        }
        
        // b가 더 깊다면, b를 부모로 이동시키고 깊이를 1 줄임 (a와 깊이가 같아질 때까지)
        while(bDepth > aDepth){
            b = parents[b];
            bDepth--;
        }

        // 3. [LCA 찾기] 이제 두 노드는 같은 깊이에 있습니다.
        //    두 노드가 같아질 때(LCA에서 만날 때)까지, 두 노드를 동시에 부모 쪽으로 한 칸씩 올립니다.
        while(a != b){
            a = parents[a];
            b = parents[b];
        }

        // 반복문이 종료되면 a와 b는 같은 노드(LCA)를 가리킵니다.
        return a;
    }
}