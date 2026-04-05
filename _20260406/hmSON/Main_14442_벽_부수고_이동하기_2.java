package gold_3;

import java.io.*;
import java.util.*;

public class Main_14442_벽_부수고_이동하기_2 {

    /*
     * BOJ_14442 : 벽 부수고 이동하기 2 (Gold_3)
     * 자료구조 및 알고리즘 : BFS, 비트마스킹
     *
     * [문제 요약]
     * - R×C 격자에서 (0,0)에서 (R-1,C-1)까지 최단 거리로 이동해야 한다.
     * - 빈 칸은 그대로 이동 가능하고, 벽은 최대 K개까지 부술 수 있다.
     * - 목적지까지 이동하는 최단 거리를 출력하고, 불가능하면 -1을 출력한다.
     *
     * [핵심 아이디어]
     * - 최단 거리 문제이므로 기본적으로 BFS를 사용한다.
     * - 다만 같은 칸이라도 "지금까지 몇 개의 벽을 부쉈는지"에 따라 이후 가능한 경로가 달라진다.
     * - 따라서 상태를 (y, x, breakCnt)로 확장해서 방문 처리를 해야 한다.
     * - 보통은 visited[y][x][k+1] 형태의 3차원 배열을 쓰지만,
     *   이 코드는 visited[y][x]의 각 비트를 "벽을 b개 부순 상태로 방문했는지"로 관리해
     *   2차원 int 배열 + 비트마스킹으로 압축했다.
     *
     * [구현 메모]
     * - map[y][x] = true 이면 벽(1), false 이면 빈 칸(0)
     * - 큐 원소:
     *   {y, x, 현재까지 이동 거리, 지금까지 부순 벽 개수}
     * - 다음 칸이 벽이면 breakCnt를 1 증가시켜 이동 여부를 판단한다.
     * - visited[y][x]의 b번째 비트가 켜져 있으면,
     *   해당 칸에 "벽을 b개 부순 상태"로 이미 방문한 적이 있다는 뜻이다.
     * - 목적지는 BFS 특성상 처음 도달한 순간이 최단 거리이므로 즉시 반환한다.
     * - 시작점과 도착점이 같은 1×1 격자는 별도로 1을 출력한다.
     *
     * [시간 복잡도]
     * - 상태 수는 최대 R * C * (K+1)
     * - 각 상태에서 4방향 확인
     * - 총: O(R * C * K)
     */

    static int r, c, k;
    static boolean[][] map;
    static int[][] visited; // 벽 부순 횟수별 방문 처리는 비트마스킹으로 대체
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        map = new boolean[r][c];
        visited = new int[r][c];
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                map[i][j] = line.charAt(j) == '1';
            }
        }
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{0, 0, 1, 0});
        visited[0][0] |= 1; // 벽을 0개 부순 상태로 시작점 방문

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];
                if(y < 0 || x < 0 || y >= r || x >= c) continue;
                if(y == r-1 && x == c-1) return cur[2] + 1;

                int breakCnt = cur[3] + (map[y][x] ? 1 : 0);
                if(breakCnt > k || (visited[y][x] & (1 << breakCnt)) != 0) continue;

                visited[y][x] |= (1 << breakCnt);
                q.add(new int[]{y, x, cur[2] + 1, breakCnt});
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        System.out.println(r == 1 && c == 1 ? 1 : bfs());
    }

}