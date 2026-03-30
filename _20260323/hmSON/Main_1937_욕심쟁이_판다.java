import java.io.*;
import java.util.*;

public class Main_1937_욕심쟁이_판다 {

    /*
     * BOJ_1937 : 욕심쟁이 판다 (Gold_3)
     * 자료구조 및 알고리즘 : DP, DFS
     *
     * [문제 요약]
     * - N×N 격자에 대나무 양이 주어진다.
     * - 판다는 현재 칸보다 대나무가 "더 많은" 칸으로만 상/하/좌/우 이동할 수 있다.
     * - 어디서 시작하든 생존할 수 있는 최대 날짜(이동 포함 방문 칸 수의 최댓값)를 출력한다.
     *
     * [핵심 아이디어]
     * - 각 칸에서 시작했을 때의 최대 이동 길이는, 더 큰 값으로 이동하는 방향으로만 뻗어나가므로
     *   사이클이 생기지 않는다(항상 값이 증가).
     * - 따라서 "DFS로 탐색 + 메모이제이션"을 적용하면,
     *   dp[y][x] = (y,x)에서 시작했을 때의 최대 이동 길이를 한 번만 계산하고 재사용할 수 있다.
     * - 모든 칸에 대해 dp를 계산하고 그 중 최대값을 답으로 한다.
     *
     * [구현 메모]
     * - dist[y][x]를 dp 역할로 사용한다.
     *   - 0이면 아직 계산되지 않은 상태
     *   - 0이 아니면 이미 해당 칸의 정답이 저장되어 있으므로 바로 반환
     * - dfs(y, x):
     *   - 기본값은 1(현재 칸에서 더 못 가면 1일)
     *   - 4방향 중 map[ny][nx] > map[y][x]인 칸으로만 이동 가능
     *   - dfs(ny, nx) + 1 중 최댓값으로 갱신
     * - 전체 답:
     *   - 모든 (i, j)에 대해 dfs(i, j) 수행하며 최대값을 갱신
     *
     * [시간 복잡도]
     * - 각 칸의 dfs는 한 번만 계산되고, 계산 시 4방향만 확인한다.
     * - 총 O(N^2)
     */

    static int n;
    static int[][] map, dist;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        map = new int[n][n];
        dist = new int[n][n];
        for(int i=0; i<n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
    }

    static int dfs(int y, int x) {
        // 이미 계산된 칸이면 저장된 결과 재사용
        if(dist[y][x] != 0) return dist[y][x];

        int maxDist = 1; // 최소 1일(현재 칸)
        for(int i=0; i<4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(ny < 0 || ny >= n || nx < 0 || nx >= n || map[ny][nx] <= map[y][x]) continue;

            int bestDist = dfs(ny, nx) + 1;
            if(bestDist > maxDist) maxDist = bestDist;
        }

        // 메모이제이션 저장
        dist[y][x] = maxDist;
        return maxDist;
    }

    public static void main(String[] args) throws Exception {
        init();

        int max = 1;
        for(int i=0; i<n; i++) {
            for(int j=0; j<n; j++) {
                int d = dfs(i, j);
                if(d > max) max = d;
            }
        }

        System.out.println(max);
    }

}