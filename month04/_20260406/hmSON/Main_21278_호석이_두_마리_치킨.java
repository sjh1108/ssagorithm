package gold_4;

import java.io.*;
import java.util.*;

public class Main_21278_호석이_두_마리_치킨 {

    /*
     * BOJ_21278 : 호석이 두 마리 치킨 (Gold_5)
     * 자료구조 및 알고리즘 : 플로이드 워셜, 브루트포스
     *
     * [문제 요약]
     * - 건물 V개와 양방향 도로 E개가 주어진다.
     * - 두 건물을 치킨집으로 선택했을 때,
     *   모든 건물에서 "가까운 치킨집까지의 왕복 거리" 합이 최소가 되도록 해야 한다.
     * - 최적의 치킨집 2개 번호와 최소 왕복 거리 합을 출력한다.
     *
     * [핵심 아이디어]
     * - 먼저 모든 건물 쌍 사이의 최단 거리를 알아야,
     *   어떤 두 건물을 치킨집으로 골랐을 때 총 거리 합을 바로 계산할 수 있다.
     * - 간선 가중치가 모두 1이고, 모든 쌍 최단거리가 필요하므로 플로이드 워셜이 적합하다.
     * - 이후 건물 2개를 조합으로 모두 선택해 보면서,
     *   각 건물 k에 대해 min(dist[k][i], dist[k][j])를 더하면 된다.
     * - 문제에서 왕복 거리 기준이므로 마지막에 2를 곱해 합산한다.
     *
     * [구현 메모]
     * - dist[i][j]:
     *   - i번 건물에서 j번 건물까지의 최단 거리
     *   - 초기에는 자기 자신만 0, 나머지는 INF
     *   - 연결된 도로는 1로 설정
     * - 플로이드 워셜:
     *   - i를 경유지로 두고 dist[j][k]를 갱신
     * - 치킨집 2개 선택:
     *   - (i, j) 조합을 모두 확인
     *   - 모든 건물 k에 대해 가까운 치킨집까지의 거리 * 2(왕복)를 누적
     * - 더 작은 합을 찾으면 정답 후보를 갱신한다.
     *
     * [시간 복잡도]
     * - 플로이드 워셜: O(V^3)
     * - 치킨집 2개 조합 탐색: O(V^3)
     * - 총: O(V^3)
     */

    static final int INF = 10001;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());

        int[][] dist = new int[v][v];
        for(int i=0; i<v; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        for(int i=0; i<e; i++) {
            st = new StringTokenizer(br.readLine());
            int v1 = Integer.parseInt(st.nextToken()) - 1;
            int v2 = Integer.parseInt(st.nextToken()) - 1;

            dist[v1][v2] = 1;
            dist[v2][v1] = 1;
        }

        // 플로이드 워셜: 모든 건물 쌍 사이의 최단 거리 계산
        for(int i=0; i<v; i++) {
            for(int j=0; j<v; j++) {
                if(i == j) continue;
                for(int k=0; k<v; k++) {
                    if(i == k || j == k) continue;

                    if(dist[j][k] > dist[j][i] + dist[i][k]) {
                        dist[j][k] = dist[j][i] + dist[i][k];
                    }
                }
            }
        }

        // 최악의 경우(선형 그래프 and 치킨집 1, 2번)에도 총 합계가 10000을 넘지 않는다.
        int min = INF, ch1 = -1, ch2 = -1;
        // 치킨집이 될 건물 2개를 모든 조합으로 확인
        for(int i=0; i<v-1; i++) {
            for(int j=i+1; j<v; j++) {
                int sum = 0;
                for(int k=0; k<v; k++) sum += Math.min(dist[k][i], dist[k][j]) * 2;

                if(sum < min) {
                    ch1 = i+1; ch2 = j+1; min = sum;
                }
            }
        }

        System.out.println(ch1 + " " + ch2 + " " + min);
    }

}