package _20260504.thdwngjs.BOJ_26076;

import java.io.*;
import java.util.*;

// (1,1) → (N,M) 이동을 막는 최소 추가 장애물 수 = 격자를 가로지르는 "장애물 벽"의 최소 길이.
// 쌍대 그래프 관점: (상단 행 ∪ 우측 열) 에서 (하단 행 ∪ 좌측 열) 까지 8방향으로 이어지는
// 칸들의 가중치 합 최소값을 구하면 된다. (이미 장애물=0, 빈칸=1 로 두고 0-1 BFS)
class Main {
    private static final int INF = Integer.MAX_VALUE / 4;

    private static int N, M;

    // cost[i][j]: 해당 칸을 "벽"에 포함시키는 비용 (이미 장애물이면 0, 빈칸이면 1, 시작/도착이면 -1=사용 불가)
    private static int[][] cost;
    // dist[i][j]: 시작 경계에서 (i,j)까지 누적된 최소 벽 비용
    private static int[][] dist;

    // 8방향 이동 (대각선 포함이어야 벽이 끊김 없이 이어짐)
    private static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

    // 0-1 BFS 용 덱 (가중치 0이면 앞쪽, 1이면 뒤쪽에 push)
    private static Deque<int[]> dq;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 격자 입력 → 비용 격자로 변환
        cost = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                int v = Integer.parseInt(st.nextToken());
                if ((i == 0 && j == 0) || (i == N - 1 && j == M - 1)) {
                    // 시작점/도착점에는 장애물을 둘 수 없으므로 벽 통과 불가 표시
                    cost[i][j] = -1;
                } else {
                    // 1(이미 장애물)이면 추가 비용 0, 0(빈칸)이면 새로 놓아야 하므로 비용 1
                    cost[i][j] = (v == 1) ? 0 : 1;
                }
            }
        }

        dist = new int[N][M];
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist[i], INF);
        }

        // 시작 경계: 상단 행(0,*) 과 우측 열(*, M-1) 의 모든 칸을 출발점으로 등록
        // (이 두 경계 중 한 곳에서 시작해 반대편 경계까지 이어지면 (1,1)~(N,M) 경로가 차단됨)
        dq = new ArrayDeque<>();
        for (int i = 0; i < M; i++){
            addQueue(0, i, cost[0][i]);
        }

        for(int i = 0; i < N; i++){
            addQueue(i, M - 1, cost[i][M - 1]);
        }

        int ans = INF;

        // 0-1 BFS
        while (!dq.isEmpty()) {
            int[] cur = dq.pollFirst();
            int x = cur[0], y = cur[1];
            int d = dist[x][y];

            // 도착 경계: 하단 행(N-1,*) 또는 좌측 열(*,0) 에 닿는 순간 후보 답 갱신
            if (x == N - 1 || y == 0) {
                if (d < ans) ans = d;
            }

            for (int k = 0; k < 8; k++) {
                int nx = x + dx[k];
                int ny = y + dy[k];
                if (isOut(nx, ny)) continue;
                if (cost[nx][ny] < 0) continue; // 시작/도착 칸은 벽으로 사용 불가

                int c = cost[nx][ny];
                int nd = d + c;
                if (nd < dist[nx][ny]) {
                    dist[nx][ny] = nd;
                    // 가중치가 0이면 앞에, 1이면 뒤에 넣어 덱이 단조 증가 상태 유지
                    if (c == 0) dq.addFirst(new int[]{nx, ny});
                    else        dq.addLast(new int[]{nx, ny});
                }
            }
        }

        System.out.println(ans);
    }

    // 시작 경계 칸을 dist 갱신과 함께 덱에 삽입
    private static void addQueue(int x, int y, int costVal) {
        if (costVal < 0) return; // 시작/도착 칸은 제외
        if (costVal < dist[x][y]) {
            dist[x][y] = costVal;
            if (costVal == 0) dq.addFirst(new int[]{x, y});
            else              dq.addLast(new int[]{x, y});
        }
    }

    private static boolean isOut(int x, int y){
        return x < 0 || x >= N || y < 0 || y >= M;
    }
}