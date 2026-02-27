import java.io.*;
import java.util.*;

public class Main_2668_숫자고르기 {

    /*
     * BOJ_2668 : 숫자고르기 (Gold_5)
     * 자료구조 및 알고리즘 : DFS
     *
     * [문제 요약]
     * - 1~N의 각 숫자 i는 정확히 하나의 숫자 target[i]를 지목한다. (함수 그래프 형태)
     * - 어떤 집합을 골랐을 때, "집합의 원소들"과 "그들이 지목하는 원소들"이 같아야 한다.
     * - 조건을 만족하는 숫자들을 모두 찾아 개수와 오름차순 목록을 출력한다.
     *
     * [핵심 아이디어]
     * - 각 정점의 out-degree가 1인 그래프(함수 그래프)에서 조건을 만족하는 원소는
     *   결국 "사이클에 속한 정점"들이다.
     * - 따라서 DFS로 지목 관계를 따라가며,
     *   DFS 스택 위에서 다시 만나는 정점(visited 상태)을 발견하면 사이클이므로
     *   그 사이클에 포함된 모든 정점을 정답에 추가한다.
     *
     * [구현 메모]
     * - state 배열로 DFS 상태를 3가지로 관리한다.
     *   - 'r' (ready)    : 아직 방문하지 않음
     *   - 'v' (visited)  : 현재 DFS 경로(재귀 스택) 위에 있음
     *   - 'f' (finished) : DFS 처리가 끝남 (스택에서 빠짐)
     * - dfs(cur):
     *   1) cur을 'v'로 바꾸고 next = target[cur]로 진행
     *   2) next가 'r'이면 계속 DFS
     *   3) next가 'v'이면 "현재 DFS 스택 내부에서 만든 사이클"이므로
     *      next부터 cur까지 target을 따라가며 사이클 정점들을 모두 group에 넣는다.
     * - 사이클 수집 로직:
     *   - 먼저 cur을 넣고,
     *   - i=next부터 시작해서 i가 cur이 될 때까지 target을 따라가며 추가한다.
     * - 모든 DFS가 끝난 후 group을 정렬하여 오름차순 출력한다.
     *
     * [시간 복잡도]
     * - 각 정점은 DFS에서 한 번만 'r' -> 'v' -> 'f'로 변한다.
     * - 전체 O(N)
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;

    static int n, cnt;
    static int[] target; // r: ready, v: visited, f: finished
    static char[] state;
    static List<Integer> group = new ArrayList<>();

    static void init() throws Exception {
        n = Integer.parseInt(br.readLine());
        target = new int[n];
        state = new char[n];
        Arrays.fill(state, 'r');

        for(int i=0; i<n; i++) {
            target[i] = Integer.parseInt(br.readLine()) - 1;
        }
    }

    static void dfs(int cur) {
        state[cur] = 'v';          // 현재 DFS 경로에 포함(재귀 스택)
        int next = target[cur];

        if(state[next] == 'r') {
            dfs(next);
        } else {
            // next가 현재 DFS 스택 위('v')라면 사이클 발생
            if(state[next] == 'v') {
                cnt++;
                group.add(cur);

                // next부터 cur까지 target을 따라가며 사이클 구성원 수집
                for(int i=next; i!=cur; i=target[i]) {
                    cnt++;
                    group.add(i);
                }
            }
        }

        state[cur] = 'f'; // DFS 종료
    }

    public static void main(String[] args) throws Exception {
        init();
        cnt = 0;

        for(int i=0; i<n; i++) {
            if(state[i] != 'r') continue; // 이미 처리된 정점은 스킵
            dfs(i);
        }

        sb.append(cnt).append("\n");
        Collections.sort(group);
        for(int x : group) {
            sb.append(x+1).append("\n");
        }

        System.out.println(sb);
    }

}