import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DFS(스택), 트리 DP
     *
     * 길로 이어진 두 쉼터에 동시에 부스를 열 수 없다는 제약 하에 방문객 합을 최대화하는 문제
     * 각 쉼터마다 해당 부스를 "여는 경우"와 "열지 않는 경우"를 제일 아래부터 시작하여 루트까지 들고 올라가야 함
     * open[v]   : 쉼터 v에 부스를 열었을 때 v가 루트인 서브트리에서 얻을 수 있는 최대값
     * closed[v] : 쉼터 v에 부스를 열지 않았을 때 v가 루트인 서브트리에서 얻을 수 있는 최대값
     *
     * 쉼터가 최대 100,000개이고 한쪽으로 치우친 트리가 주어질 수 있음
     * 재귀 호출은 스택 오버플로 위험이 있으므로 명시적 스택으로 구현
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // open DP 배열은 각 쉼터의 방문객 수로 초기화
        // 이후 하위 쉼터의 closed값을 누적시키면 점화식이 완성됨
        long[] open = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            open[i] = Long.parseLong(st.nextToken());
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
        stack[0] = 0; // 루트 지정
        int top = 1;
        Arrays.fill(parent, -1); // 루트는 상위 쉼터가 없음을 -1로 표시

        boolean[] visited = new boolean[n];
        long[] closed = new long[n];

        while(top > 0) {
            int cur = stack[top-1]; // 일단 꺼내지 않고 peek 확인만

            // 방문 여부에 따라 진입 시점과 탈출 시점으로 분기
            if(!visited[cur]) {
                // 진입 시점 : 하위 쉼터들을 스택에 올리고 자신은 그대로 대기
                visited[cur] = true;
                for(int next : graph[cur]) {
                    if(visited[next]) continue; // 이미 방문한 쉼터는 상위 쉼터임
                    parent[next] = cur;
                    stack[top++] = next; // 상위 쉼터 기록
                }
            } else {
                // 탈출 시점 : 하위 쉼터의 값이 모두 확정된 상태
                int p = parent[cur];
                top--;
                if(p == -1) continue; // 루트는 올려보낼 부모가 없음

                open[p] += closed[cur]; // 상위 쉼터가 열리면 자신은 반드시 부스를 닫아야 함
                closed[p] += Math.max(open[cur], closed[cur]); // 상위 쉼터가 닫히면 자신은 자유 선택이므로 방문객 수가 더 높은 쪽으로
            }
        }

        // 루트의 두 경우 중 더 큰 값이 전체 최댓값
        System.out.println(Math.max(open[0], closed[0]));
    }

}