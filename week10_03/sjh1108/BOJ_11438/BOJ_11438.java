package week10_03.sjh1108.BOJ_11438;

import java.io.*;
import java.util.*;

// BOJ 11437 (LCA 1)과 달리, 이 문제는 N, M이 최대 100,000 이므로
// O(logN)으로 쿼리를 처리하는 '이진 리프팅(Binary Lifting)' 방식이 필요합니다.
class Main {
    // 트리의 최대 높이(N)에 대한 log2 값. 이진 리프팅(binary lifting)에 사용됩니다.
    // N=100,000 이면 약 16~17
    private static int K_MAX;
    
    // 각 노드의 깊이를 저장할 배열
    private static int[] depths;
    // `parents[i][k]` : i번 노드의 2^k 번째 조상 노드를 저장
    private static int[][] parents;
    // 트리를 표현하기 위한 인접 리스트 (양방향 연결 정보)
    private static List<List<Integer>> list;


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine()); // 노드의 개수 N

        // K_MAX 계산 (log2(N)의 내림값)
        K_MAX = (int) Math.floor(Math.log(N) / Math.log(2));

        // 배열들 초기화 (1번 노드부터 사용하므로 N+1 크기)
        depths = new int[N+1];
        // `parents[노드번호][k]` (k는 0 ~ K_MAX)
        parents = new int[N+1][K_MAX+1];
        
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
        
        // 1. DFS를 실행하여 각 노드의 깊이(depths)와 *직계 부모*(parents[i][0])를 계산
        initTree(1, 1, 0);
        
        // 2. DP(동적 계획법)를 이용해 `parents` 배열(희소 테이블)의 나머지(k=1 ~ K_MAX)를 채움
        setParent(N);
        
        int M = Integer.parseInt(br.readLine()); // LCA를 찾을 쿼리의 수 M
        StringBuilder sb = new StringBuilder();
        
        // M개의 쿼리 처리
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 3. 이진 리프팅을 이용해 O(logN)만에 LCA 쿼리 실행
            int parent = LCA(a, b);
            sb.append(parent + "\n");
        }

        System.out.println(sb);
    }

    /**
     * DFS를 이용해 트리의 각 노드에 대한 깊이와 *직계 부모*(2^0 조상) 정보를 설정합니다.
     * @param cur 현재 탐색 중인 노드
     * @param depth 현재 노드의 깊이
     * @param parent 현재 노드의 부모 노드
     */
    private static void initTree(int cur, int depth, int parent){
        depths[cur] = depth; // 현재 노드의 깊이 기록
        parents[cur][0] = parent; // 현재 노드의 1번째(2^0) 조상 = 직계 부모

        // 현재 노드(cur)에 연결된 모든 다음 노드(nxt)를 순회
        for(int nxt: list.get(cur)){
            // 다음 노드(nxt)가 부모 노드(parent)가 아니라면 (즉, 자식 노드라면)
            if(nxt != parent){
                // 자식 노드로 재귀 호출
                initTree(nxt, depth+1, cur);
            }
        }
    }

    /**
     * DP(동적 계획법)를 이용해 `parents` 배열(희소 테이블)을 완성합니다.
     * 점화식: `parents[i][k] = parents[parents[i][k-1]][k-1]`
     * (i의 2^k 번째 조상 = (i의 2^(k-1) 번째 조상)의 2^(k-1) 번째 조상)
     * @param N 노드의 총 개수
     */
    private static void setParent(int N){
        // k=1 (2^1 조상) 부터 K_MAX (2^K_MAX 조상) 까지
        for(int k = 1; k <= K_MAX; k++){
            // 모든 노드 i에 대해
            for(int i = 1; i <= N; i++){
                // i의 2^k 번째 조상을 계산하여 저장
                parents[i][k] = parents[parents[i][k-1]][k-1];
            }
        }
    }

    /**
     * 두 노드 a와 b의 최소 공통 조상(LCA)을 이진 리프팅(O(logN))으로 찾습니다.
     * @param a 노드 a
     * @param b 노드 b
     * @return 두 노드의 LCA
     */
    private static int LCA(int a, int b){
        // 1. [깊이 맞추기] a를 항상 더 깊은 노드로 만듭니다. (b가 얕거나 같음)
        if(depths[a] < depths[b]){
            int tmp = a;
            a = b;
            b = tmp;
        }

        // 2. [이진 리프팅 - 레벨링] a를 b와 같은 깊이로 올립니다.
        //    큰 점프(k)부터 확인 (K_MAX -> 0)
        for(int k = K_MAX; k >= 0; k--){
            // a를 2^k 만큼 올렸을 때(depths[a] - (1<<k)) 
            // b의 깊이보다 여전히 크거나 같다면 점프합니다. (1<<k 는 2^k)
            if(depths[a] - (1<<k) >= depths[b]){
                a = parents[a][k]; // a를 2^k번째 조상으로 이동
            }
        }

        // 3. 만약 깊이를 맞췄는데 두 노드가 같다면 (b가 a의 조상이었다면) b가 LCA
        if(a == b) return a;

        // 4. [이진 리프팅 - 동시 점프]
        //    a와 b를 *동시에* 점프시키며 LCA 바로 *아래* 노드까지 올립니다.
        //    큰 점프(k)부터 확인 (K_MAX -> 0)
        for(int k = K_MAX; k >= 0; k--){
            // a의 2^k 조상과 b의 2^k 조상이 *다르다면* // (LCA보다 아래에 있다는 의미이므로 점프)
            if(parents[a][k] != parents[b][k]){
                a = parents[a][k]; // 둘 다 2^k 만큼 점프
                b = parents[b][k];
            }
            // 만약 parents[a][k] == parents[b][k] 라면,
            // 2^k 만큼 점프하면 LCA이거나 LCA를 *지나쳐* 버리므로 점프하지 않습니다.
        }

        // 5. 반복문이 끝나면 a와 b는 LCA의 바로 아래 자식들입니다.
        //    따라서 이들의 직계 부모(parents[a][0])가 LCA입니다.
        return parents[a][0];
    }
}