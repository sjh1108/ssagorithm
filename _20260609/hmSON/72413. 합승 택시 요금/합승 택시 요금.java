import java.util.*;

class Solution {

    /**
     * 자료구조 및 알고리즘 : 플로이드 워셜
     * 정석적인 풀이는 다익스트라. 시작점, 도착점 A, 도착점 B를 시작점으로 하는 다익스트라 3번으로 풀이 가능
     * 해당 코드는 플로이드 워셜을 사용
     * dist[s][x] + dist[x][a] + dist[x][b]가 최소인 x를 찾는 것이 목표
     */

    static final int INF = 1_000_000_000;

    public int solution(int n, int s, int a, int b, int[][] fares) {
        // 거리 배열 초기화
        int[][] dist = new int[n][n];
        for(int i=0; i<n; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        for(int[] fare : fares) {
            int v1 = fare[0] - 1, v2 = fare[1] - 1, cost = fare[2];
            dist[v1][v2] = cost;
            dist[v2][v1] = cost;
        }

        // 플로이드 워셜
        // k를 경유지로 두는 경우의 거리 비교 및 최소값 갱신
        for(int k=0; k<n; k++) {
            for(int i=0; i<n; i++) {
                if(dist[i][k] == INF) continue;
                for(int j=0; j<n; j++) {
                    if(dist[k][j] == INF) continue;
                    dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int min = INF;
        for(int i=0; i<n; i++) {
            // 하나라도 이동 불가능한 경로가 있으면 무시
            // 택시 동승 경로 + 각자 이동 경로의 합의 최소값을 구함
            if(dist[s-1][i] == INF || dist[i][a-1] == INF || dist[i][b-1] == INF) continue;
            int total = dist[s-1][i] + dist[i][a-1] + dist[i][b-1];
            if(total < min) min = total;
        }

        return min;
    }

}