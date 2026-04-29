/**
 * BOJ 14442 - 벽 부수고 이동하기 2 (골드 3)
 *
 * [풀이] BFS + 상태 확장
 * (0,0)에서 (N-1,M-1)까지 이동하며, 벽('1')을 최대 K개까지 부술 수 있을 때 최단 경로.
 * 같은 칸이라도 "지금까지 부순 벽의 개수"가 다르면 별도 상태로 취급해야 한다.
 *
 * visited[x][y] = 해당 칸에 도달했을 때 사용한 최소 벽 부수기 횟수.
 *   → 더 적은 횟수로 도달할 수 있을 때만 갱신/재방문하여 불필요한 상태 폭발 방지.
 * BFS 특성상 가장 먼저 목적지에 도달한 경로가 최단 거리이므로 즉시 출력하고 종료.
 */
import java.io.*;
import java.util.*;

class Main {
    private static int N, M;

    // 상하좌우 이동 델타
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken()); // 부술 수 있는 벽의 최대 개수

        char[][] map = new char[N][M];

        for(int i = 0; i < N; i++){
            map[i] = br.readLine().toCharArray();
        }

        // visited[x][y]: (x,y)에 도달하기까지 사용한 최소 벽 부수기 횟수
        int[][] visited = new int[N][M];
        for(int[] row : visited) Arrays.fill(row, Integer.MAX_VALUE);
        visited[0][0] = 0;

        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0, 1, 0}); // x, y, dist(=이동 칸 수), cnt(=부순 벽 수)

        while(!q.isEmpty()){
            int[] cur = q.poll();

            int x = cur[0], y = cur[1], dist = cur[2], cnt = cur[3];

            // BFS는 거리 오름차순으로 확장되므로 최초 도달 시점이 최단 거리
            if(x == N-1 && y == M-1){
                System.out.println(dist);
                return;
            }

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];
                if(!isIn(nx, ny)) continue;

                if(map[nx][ny] == '0'){
                    // 빈 칸: 더 적은 벽 부수기로 도달 가능할 때만 진행
                    if(visited[nx][ny] > cnt){
                        visited[nx][ny] = cnt;
                        q.add(new int[]{nx, ny, dist + 1, cnt});
                    }
                } else {
                    // 벽: 아직 부술 여유(K)가 있고, 더 적은 부수기로 도달 가능할 때만 진행
                    if(cnt < K && visited[nx][ny] > cnt + 1){
                        visited[nx][ny] = cnt + 1;
                        q.add(new int[]{nx, ny, dist + 1, cnt + 1});
                    }
                }
            }
        }

        // 큐가 비도록 도달 실패 → 경로 없음
        System.out.println(-1);
    }

    // 맵 범위 체크
    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}
