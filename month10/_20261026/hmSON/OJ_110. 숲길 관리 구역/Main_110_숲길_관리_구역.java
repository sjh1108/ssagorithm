import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 트리, DFS
     *
     * 트리의 각 쉼터마다 자신이 루트인 서브트리의 비용 합계를 구하는 트리 DFS 문제
     * DFS는 스택으로 구현
     * 진입 시점에는 상하위 관계를 기록
     * 탈출 시점에는 현재 노드의 총 관리비를 상위 노드의 관리비에 합산
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // DFS 과정에서 하위 트리의 관리비를 합산하기 위해 미리 long 타입으로 생성
        long[] cost = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            cost[i] = Long.parseLong(st.nextToken());
        }

        // 길의 방향이 주어지지 않으므로 무방향 인접 리스트로 구성
        List<Integer>[] graph = new ArrayList[n];
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

        int[] stack = new int[n], parent = new int[n]; // DFS 스택과 상위 쉼터 번호 배열
        stack[0] = 0;
        int top = 1;
        Arrays.fill(parent, -1);

        boolean[] visited = new boolean[n]; // 쉼터 방문 처리 배열

        while(top > 0) {
            int cur = stack[top-1]; // 일단 꺼내지 않고 peek 확인만

            // 방문 여부에 따라 진입 시점과 탈출 시점으로 분기
            if(!visited[cur]) {
                // 진입 시점 : 하위 쉼터들을 스택에 올리고 자신은 그대로 대기
                visited[cur] = true;
                for(int next : graph[cur]) {
                    if(visited[next]) continue; // 이미 방문한 쉼터는 상위 쉼터임
                    stack[top++] = next;
                    parent[next] = cur; // 상위 쉼터 기록
                }
            } else {
                // 탈출 시점 : 하위 쉼터의 값이 모두 확정된 상태
                int p = parent[cur];
                if(p != -1) cost[p] += cost[cur]; // 현재 쉼터가 루트인 서브트리의 총 관리비를 상위 쉼터 관리비에 합산
                top--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<n; i++) {
            sb.append(parent[i]+1).append(" ").append(cost[i]).append("\n");
        }

        System.out.println(sb);
    }

}