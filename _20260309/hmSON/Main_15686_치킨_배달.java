import java.io.*;
import java.util.*;

public class Main_15686_치킨_배달 {

    /*
     * BOJ_15686 : 치킨 배달 (Gold_5)
     * 자료구조 및 알고리즘 : 백트래킹(조합), 메모이제이션
     *
     * [문제 요약]
     * - N×N 도시에서 집(1)과 치킨집(2)의 위치가 주어진다.
     * - 치킨집 중 정확히 M개를 선택해 남길 때,
     *   각 집에서 가장 가까운 치킨집까지의 거리(치킨 거리)의 합이 최소가 되도록 하라.
     * - 최소 치킨 거리 합을 출력한다.
     *
     * [핵심 아이디어]
     * - 남길 치킨집 M개를 고르는 모든 경우의 수(조합)를 탐색해야 한다.
     * - 한 조합이 정해지면,
     *   모든 집에 대해 "선택된 치킨집 중 최소 거리"를 더한 값이 그 조합의 도시 치킨 거리다.
     * - 조합 탐색은 백트래킹으로 구현한다.
     *
     * [구현 메모]
     * - houseList / chickenList에 좌표를 저장한다.
     * - distMemo[houseIdx][chickenIdx]에 집-치킨집 맨해튼 거리를 미리 전처리해 둔다.
     *   -> 조합마다 거리 계산(절댓값 계산)을 반복하지 않고 O(1) 조회로 처리 가능.
     * - selected[]에는 현재 조합에서 선택된 치킨집 인덱스를 저장한다.
     * - backtracking(start, cnt):
     *   - cnt == selectCnt(M)이 되면 getDist()로 도시 치킨 거리 계산 후 최솟값 갱신.
     *   - i를 start부터 가능한 범위까지 진행하며 조합을 만든다.
     * - getDist():
     *   - 각 집마다 선택된 치킨집들 중 최소 거리를 찾고 누적한다.
     *   - 누적 합이 이미 minDist 이상이면 더 볼 필요가 없어 조기 종료(가지치기)한다.
     *
     * [시간 복잡도]
     * - H = 집 개수, C = 치킨집 개수, M = 선택 개수
     * - 거리 전처리: O(H * C)
     * - 조합 수: C(C, M)
     * - 각 조합 평가: O(H * M)
     * - 총: O(H*C + (C choose M) * H * M) (가지치기로 평균은 더 감소)
     */

    // 0: 빈 칸, 1: 집, 2: 치킨집
    static int n, selectCnt, minDist = Integer.MAX_VALUE;
    static List<int[]> houseList = new ArrayList<>(), chickenList = new ArrayList<>(); // 집과 치킨집의 좌표 리스트
    static int[] selected;
    static int[][] distMemo; // 집 - 치킨집간 거리 메모

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        selectCnt = Integer.parseInt(st.nextToken());
        selected = new int[selectCnt];

        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                char state = st.nextToken().charAt(0);
                if(state == '2') chickenList.add(new int[]{i, j});
                if(state == '1') houseList.add(new int[]{i, j});
            }
        }

        // 집-치킨집 거리 전처리 (맨해튼 거리)
        distMemo = new int[houseList.size()][chickenList.size()];
        for(int i=0; i<houseList.size(); i++) {
            int[] house = houseList.get(i);
            for(int j=0; j<chickenList.size(); j++) {
                int[] chicken = chickenList.get(j);
                distMemo[i][j] = Math.abs(chicken[0] - house[0]) + Math.abs(chicken[1] - house[1]);
            }
        }
    }

    // 치킨집 인덱스 조합 생성
    static void backtracking(int start, int cnt) {
        if(cnt == selectCnt) {
            int dist = getDist();
            if(dist < minDist) minDist = dist;
            return;
        }

        // 남은 자리(selectCnt - cnt)를 채울 수 있는 범위까지만 순회 (조합 범위 최적화)
        for(int i=start; i<= chickenList.size() - selectCnt + cnt; i++) {
            selected[cnt] = i;
            backtracking(i+1, cnt+1);
        }
    }

    // 현재 선택된 치킨집 조합에 대한 도시 치킨 거리 계산
    static int getDist() {
        int totalDist = 0;

        for(int i=0; i<houseList.size(); i++) {
            int chickenDist = 2 * n; // 충분히 큰 초기값(최대 맨해튼 거리는 2*(n-1))
            for(int j : selected) {
                int dist = distMemo[i][j]; // 미리 전처리해둔 값을 그대로 호출
                if(dist < chickenDist) chickenDist = dist;
            }

            totalDist += chickenDist;

            // 이미 최솟값 이상이면 더 계산해도 의미 없으므로 가지치기
            if(totalDist >= minDist) break;
        }

        return totalDist;
    }

    public static void main(String[] args) throws Exception {
        init();
        backtracking(0, 0);

        System.out.println(minDist);
    }

}