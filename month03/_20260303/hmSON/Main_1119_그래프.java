import java.io.*;
import java.util.*;

public class Main_1119_그래프 {

    /*
     * BOJ_1119 : 그래프 (Gold_1)
     * 자료구조 및 알고리즘 : 유니온 파인드
     *
     * [문제 요약]
     * - N개의 정점(도시)과 인접행렬 형태의 간선 정보가 주어진다. (Y면 도로 존재, N이면 없음)
     * - 목표는 모든 도시를 연결 그래프로 만드는 것(단일 연결 요소)이며,
     *   이때 "불필요한 도로(사이클을 만드는 도로)"를 제거/재배치해서 다른 곳에 사용할 수 있다고 본다.
     * - 가능한 경우, 필요한 최소 작업 횟수(연결 요소를 합치는 데 필요한 간선 수)를 출력하고,
     *   불가능하면 -1을 출력한다.
     *
     * [핵심 아이디어]
     * - 현재 그래프의 연결 요소 개수를 disjoint라고 하면,
     *   모든 정점을 하나로 연결하려면 최소 (disjoint - 1)개의 간선이 추가로 필요하다.
     * - 이미 존재하는 간선 중, 같은 연결 요소 내부를 잇는 간선은 "사이클 간선"이므로
     *   제거해도 연결성에 영향이 없고, 그 수(removed)는 '여분 간선'처럼 다른 연결에 활용 가능하다고 본다.
     * - 따라서 조건은 다음 두 가지를 동시에 만족해야 한다.
     *   1) 고립 정점이 없어야 한다. (어떤 도시든 최소 1개의 간선을 가져야 재배치 과정으로도 연결 가능)
     *   2) removed(여분 간선 수) >= (disjoint - 1) 이어야 연결 요소들을 모두 합칠 수 있다.
     * - 위 조건을 만족하면 정답은 (disjoint - 1) (연결 요소를 1개로 만들기 위한 최소 간선 수).
     *
     * [구현 메모]
     * - 입력이 인접행렬이므로 i행을 읽고 j=i+1..N-1만 확인하여 간선을 한 번만 처리한다.
     * - union(i, j)가 성공하면 서로 다른 집합을 합친 것이므로 disjoint--.
     * - union(i, j)가 실패하면 이미 같은 집합(사이클)이라 removed++ (여분 간선 증가).
     * - visited[]는 "해당 정점이 한 번이라도 간선에 등장했는지" 체크:
     *   - 모든 정점이 visited=true여야(고립 정점 없음) 연결 가능성을 논할 수 있다.
     * - 특수 케이스:
     *   - N==1이면 이미 연결되어 있으므로 0.
     *
     * [시간 복잡도]
     * - 인접행렬의 상삼각만 순회: O(N^2)
     * - 유니온 파인드 연산: 거의 상수(역아커만)
     * - 총: O(N^2)
     */

    static int[] head;

    static int find(int x) {
        if(x == head[x]) return x;
        return head[x] = find(head[x]);
    }

    static boolean union(int a, int b) {
        int ha = find(a), hb = find(b);
        if(ha == hb) return false;

        if(ha < hb) head[hb] = ha;
        else head[ha] = hb;
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int v = Integer.parseInt(br.readLine());
        if(v == 1) {
            System.out.println(0);
            return;
        }

        head = new int[v];
        boolean[] visited = new boolean[v];
        for(int i=0; i<v; i++) {
            head[i] = i;
        }

        int removed = 0, disjoint = v;
        boolean flag = true;
        for(int i=0; i<v; i++) {
            String line = br.readLine();
            for(int j=i+1; j<v; j++) {
                if(line.charAt(j) == 'N') continue;

                // 간선이 존재하면 두 정점은 "고립이 아님"
                visited[i] = true;
                visited[j] = true;

                // 다른 컴포넌트 연결이면 disjoint 감소, 이미 연결된 곳이면 사이클 간선(여분) 증가
                if(union(i, j)) disjoint--;
                else removed++;
            }

            // i가 한 번도 간선에 등장하지 않았다면 고립 정점 존재 -> 불가능
            if(!visited[i]) {
                flag = false;
            }
        }

        // 고립 정점이 없고, 여분 간선 수가 컴포넌트 연결에 필요한 수(disjoint-1) 이상이면 가능
        if(flag && removed >= disjoint - 1) System.out.println(disjoint - 1);
        else System.out.println(-1);
    }

}