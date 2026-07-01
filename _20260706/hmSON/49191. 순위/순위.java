import java.util.Arrays;

class Solution {

    /**
     * 자료구조 및 알고리즘 : 플로이드 워셜
     * 정확하게 순위를 매길 수 있는가? -> 직간접적으로 다른 모든 플레이어와의 우열 관계가 드러나 있는가?
     * 본인에게 진 플레이어 B가 다른 플레이어 C에게 이겼다면 본인은 플레이어 C보다 순위 높음
     * 본인에게 이긴 플레이어 D가 다른 플레이어 E에게 졌다면 본인은 플레이어 E보다 순위 낮음
     */

    static final int MAX = 200;

    public int solution(int n, int[][] results) {
        // graph[a][b] -> A는 B보다 순위 높음. B는 A보다 순위 낮음.
        int[][] graph = new int[n][n];

        // 초기 값을 MAX로 채우고 본인에 대해서만 0으로 초기화
        for(int i=0; i<n; i++) {
            Arrays.fill(graph[i], MAX);
            graph[i][i] = 0;
        }

        // 플레이어 간 관계를 전부 1로 작성
        for(int[] match : results) {
            int winner = match[0] - 1, loser =  match[1] - 1;
            graph[winner][loser] = 1;
        }

        for(int k=0; k<n; k++) {
            for(int i=0; i<n; i++) {
                if(graph[i][k] == MAX) continue;
                for(int j=0; j<n; j++) {
                    if(graph[k][j] == MAX) continue;
                    // 해당 문제에서 경유 횟수는 필요 없음
                    // 어떻게든 관계 확인이 가능하다면 1로 처리
                    graph[i][j] = 1;
                }
            }
        }

        // 다른 플레이어 간 관계가 전부 드러나 있는 플레이어 수 카운트
        int cnt = 0;
        for(int i=0; i<n; i++) {
            // 다른 플레이어간의 관계는 전부 1 또는 MAX로 표현되어 있음
            // 따라서 배열의 항이 1인 경우만 sum에 더해서 총합이 n-1인지 확인
            // graph[i][i] 기준 가로/ 세로 방향으로 관계 확인
            // sum == n-1이면 해당 플레이어는 다른 모든 플레이어와의 관계가 드러나 있음
            int sum = 0;
            for(int j=0; j<n; j++) {
                if(i == j) continue;
                if(graph[i][j] < MAX) sum ++;
                if(graph[j][i] < MAX) sum ++;
            }

            if(sum == n-1) cnt++;
        }

        return cnt;
    }

}