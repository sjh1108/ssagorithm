/**
 * BOJ 21278 - 호석이 두 마리 치킨 (골드 4)
 *
 * [풀이] 플로이드-워셜 + 완전 탐색
 * N개의 건물 중 치킨집을 정확히 2개 선택했을 때, 모든 건물에서 가장 가까운
 * 치킨집까지 왕복 거리의 합이 최소가 되는 (a, b)와 그 합을 구한다.
 *
 * 1) 간선 가중치는 모두 1이므로 플로이드-워셜로 모든 쌍 최단 거리 map[i][j] 계산.
 * 2) 치킨집 후보 쌍 (i, j) (i < j)을 완전 탐색하며, 각 건물 k에 대해
 *    min(map[i][k], map[j][k])를 누적한 sum이 최소인 쌍을 선택.
 * 3) 왕복이므로 출력 시 2배.
 */
package _20260406.thdwngjs.BOJ_21278;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken()); // 건물 수
        int M = Integer.parseInt(st.nextToken()); // 도로 수

        int INF = 1_000_000;
        int[][] map = new int[N + 1][N + 1]; // 최단 거리 테이블 (1-indexed)
        for(int i = 1; i <= N; i++){
            Arrays.fill(map[i], INF);

            map[i][i] = 0; // 자기 자신까지는 0
        }

        // 양방향 간선, 가중치 1
        while(M-- > 0){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            map[a][b] = map[b][a] = 1;
        }

        // 플로이드-워셜: 경유지 k를 거쳐 i → j로 가는 경로로 최단 거리 갱신
        for(int k = 1; k <= N; k++){
            for(int i = 1; i <= N; i++){
                if(k == i) continue;
                for(int j = 1; j <= N; j++){
                    if(i == j || k == j) continue;

                    map[i][j] = Math.min(map[i][j], map[i][k] + map[k][j]);
                }
            }
        }

        // 치킨집 2개 (i, j)를 모두 시도하여 "모든 건물 → 가까운 치킨집" 거리 합 최소화
        int min = Integer.MAX_VALUE;
        int a = -1, b = -1;
        for(int i = 1; i < N; i++){
            for(int j = i + 1; j <= N; j++){
                int sum = 0;

                // 각 건물 k에 대해 두 치킨집 중 더 가까운 쪽까지의 거리 누적
                for(int k = 1; k <= N; k++){
                    sum += Math.min(i == k ? 0 : map[i][k], j == k ? 0 : map[j][k]);
                }

                if(sum < min){
                    min = sum;
                    a = i;
                    b = j;
                }
            }
        }

        // 왕복이므로 편도 합의 2배가 정답
        System.out.println(a + " " + b + " " + min * 2);
    }
}