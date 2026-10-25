import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DFS(스택), 트리 DP
     *
     * 모든 쉼터가 최소 하나의 감시탑 범위 안에 들어가게 하는 감시탑 설치 최소 비용을 구해야 함
     * 감시탑은 자기 자신과 인접한 쉼터를 감시하며, 이는 하위 노드와 상위 노드 모두 포함됨
     *
     * 각 쉼터의 상태를 3가지 상태로 관리
     * - built[v]          : 쉼터 v에 감시탑을 설치한 경우의 최소 비용
     * - underSurveil[v]   : 쉼터 v에 감시탑을 설치하지 않았지만 다른 쉼터로부터 감시받는 경우
     * - withoutSurveil[v] : 쉼터 v에 감시탑을 설치하지 않았고 감시 범위에도 들어가지 않은 경우
     *
     * 각 상태별 전이
     * - built          : 하위 쉼터는 3가지 상태 자유롭게 선택 가능하므로 비용이 적은 쪽을 선택
     * - underSurveil   : 자식 중 최소 하나가 감시탑 설치 의무를 져야 함
     *                    기본적으로 min(built, UnderSurveil)을 누적한 뒤
     *                    어떤 하위 쉼터도 감시탑을 설치하지 않았다면 비용차가 가장 적은 쉼터 하나를 골라 강제로 설치시킴
     * - withoutSurveil : 자신은 감시 범위에서 벗어나고 하위 쉼터들은 감시탑 설치 없이 감시 범위 내에 포함되어야 함
     *                    각 하위 쉼터의 underSurveil을 누적한 값
     *                    하위 쉼터 중 리프가 있으면 그 리프는 감시탑을 설치할 하위 쉼터가 없으므로 불가능(INF)
     */

    static final long INF = (long)1e15;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // built DP 배열은 각 쉼터의 설치 비용으로 초기화
        // 이후 하위 쉼터의 값들을 누적시키면 점화식이 완성됨
        long[] built = new long[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            built[i] = Long.parseLong(st.nextToken());
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
        long[] underSurveil = new long[n], withoutSurveil = new long[n];
        // 하위 쉼터를 한 번도 처리하지 않은 쉼터는 리프이므로 underSurveil을 불가능(INF) 상태로 초기화
        Arrays.fill(underSurveil, INF);

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
                long minDiff = INF, underSurveilSum = 0;

                // 무방향 그래프이므로 상위 쉼터 하나를 제외한 나머지가 하위 쉼터
                for(int next : graph[cur]) {
                    if(parent[cur] == next) continue;

                    // 이 하위 쉼터에 감시탑을 강제로 설치시킬 때 추가되는 비용
                    // 음수라면 어차피 설치하는 쪽이 이득이므로 설치 의무가 이미 충족된 것으로 확인
                    long curDiff = built[next] - underSurveil[next];
                    if(curDiff < minDiff) minDiff = curDiff;

                    if(withoutSurveil[next] == 0) {
                        // 하위 쉼터가 리프인 경우
                        // cur이 설치하지 않으면 이 리프는 스스로 설치할 수밖에 없음
                        underSurveilSum += built[next];
                        // 리프 자식이 있으면 cur은 감시받지 못한 상태로 남을 수 없음
                        withoutSurveil[cur] = INF;
                    } else {
                        // cur이 설치하면 하위 쉼터는 세 상태 중 자유롭게 선택
                        built[cur] += min3(built[next], underSurveil[next], withoutSurveil[next]);
                        // cur이 하위 쉼터에게 감시받으려면 최소 한 곳은 자발적으로 감시탑을 설치해야 함
                        underSurveilSum += Math.min(built[next], underSurveil[next]);
                        // cur이 감시받지 못한 상태라면 하위 쉼터들은 스스로 해결해야 함
                        withoutSurveil[cur] += underSurveil[next];
                        if(withoutSurveil[cur] > INF) withoutSurveil[cur] = INF; // 불가능 상태 유지
                    }
                }

                // cur이 감시탑 미설치 상태인데 하위 쉼터 중 아무도 감시탑을 설치하지 않은 경우
                // -> 비용차가 가장 작은 한 곳에 강제로 설치
                // minDiff가 음수면 이미 설치된 자식이 있다는 뜻이므로 추가 비용 없음
                underSurveilSum += (minDiff <= 0 ? 0 : minDiff);
                underSurveil[cur] = underSurveilSum;

                top--;
            }
        }

        // 루트는 부모가 없으므로 withoutSurveil 상태를 선택할 수 없음
        System.out.println(Math.min(built[0], underSurveil[0]));
    }

    // 세 값 중 최솟값 반환
    static long min3(long a, long b, long c) {
        return Math.min(Math.min(a, b), c);
    }

}