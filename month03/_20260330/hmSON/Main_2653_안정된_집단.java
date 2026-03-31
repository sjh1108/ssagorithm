import java.io.*;
import java.util.*;

public class Main_2653_안정된_집단 {

    /*
     * BOJ_2653 : 안정된 집단 (Gold_4)
     * 자료구조 및 알고리즘 : Union Find
     *
     * [문제 요약]
     * - 사람들 사이의 관계가 주어지며, 서로 좋아하는 관계(0)와 싫어하는 관계(1)가 있다.
     * - 좋아하는 사람끼리는 반드시 같은 집단에 속해야 하고, 싫어하는 사람끼리는 같은 집단에 속하면 안 된다.
     * - 또한 어떤 사람도 집단에 혼자 남아서는 안 된다.
     * - 조건을 만족하는 집단 구성이 가능하면 집단 수와 각 집단의 구성원을 출력하고, 불가능하면 0을 출력한다.
     *
     * [핵심 아이디어]
     * - 좋아하는 관계는 같은 집합으로 묶어야 하므로 Union Find로 관리할 수 있다.
     * - 입력을 보면서
     *   1) 좋아하는 관계면 union으로 같은 집합으로 합친다.
     *   2) 싫어하는 관계인데 이미 같은 집합이라면 조건 위반이므로 즉시 불가능하다.
     * - 마지막에는 어떤 사람도 단 한 번도 좋아하는 관계로 묶이지 않았다면
     *   혼자 있는 집단이 된다는 뜻이므로 이 경우도 불가능하다.
     *
     * [구현 메모]
     * - head[i] : i번 사람의 대표 노드
     * - groupCnt : 현재 집단 수
     * - visited[i] :
     *   - i번 사람이 한 번이라도 좋아하는 관계(= union 대상)에 포함되었는지 체크
     *   - 끝까지 false면 혼자 남는 사람으로 판단하여 0 출력
     * - union(a, b, hate):
     *   - hate=false이면 실제 union 수행
     *   - hate=true이면 합치지 않고, 이미 같은 집합인지 여부만 검사
     * - 모든 입력 처리가 끝난 뒤 대표 노드별로 구성원을 모아 출력한다.
     *
     * [시간 복잡도]
     * - 사람 수를 N이라 할 때, 관계 입력은 하삼각 형태로 총 O(N^2)
     * - 각 union/find는 거의 상수 시간
     * - 총: O(N^2)
     */

    static int groupCnt;
    static int[] head;
    static boolean[] visited;

    static int find(int x) {
        if(x == head[x]) return x;
        return head[x] = find(head[x]);
    }

    static boolean union(int a, int b, boolean hate) {
        int ra = find(a), rb = find(b);

        // 서로 싫어하는 관계인데 이미 같은 집합이면 조건 위반
        if(ra == rb) return !hate;

        if(!hate) {
            if(ra < rb) head[rb] = ra;
            else head[ra] = rb;
            groupCnt--;
            // 좋아하는 관계로 한 번이라도 묶인 사람만 정상적인 집단 구성원으로 인정
            visited[a] = true;
            visited[b] = true;
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        head = new int[n];
        visited = new boolean[n];
        groupCnt = n;

        for(int i=0; i<n; i++) head[i] = i;

        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<i; j++) {
                boolean hate = st.nextToken().equals("1");
                boolean flag = union(i, j, hate);

                if(!flag) {
                    System.out.println(0);
                    return;
                }
            }
        }

        // 한 번도 좋아하는 관계로 연결되지 않은 사람은 고립된 집단이므로 불가능
        for(int i=0; i<n; i++) {
            if(visited[i]) continue;

            System.out.println(0);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(groupCnt).append("\n");

        List<Integer>[] groups = new ArrayList[n];
        for(int i=0; i<n; i++) {
            groups[i] = new ArrayList<>();
            groups[head[i]].add(i+1);
        }

        for(int i=0; i<n; i++) {
            if(groups[i].isEmpty()) continue;

            for(int member : groups[i]) sb.append(member).append(" ");
            sb.append("\n");
        }

        System.out.println(sb);
    }

}