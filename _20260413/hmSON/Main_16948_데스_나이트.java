import java.io.*;
import java.util.*;

public class Main_16948_데스_나이트 {

    /*
     * BOJ_16948 : 데스 나이트 (Silver_1)
     * 자료구조 및 알고리즘 : BFS
     *
     * [문제 요약]
     * - N×N 체스판에서 데스 나이트가 시작 위치에서 도착 위치까지 이동하려고 한다.
     * - 이동 방법은 일반 나이트와 다르고, 문제에서 주어진 6가지 방향으로만 이동할 수 있다.
     * - 도착 위치까지 가는 최소 이동 횟수를 구하고, 도달할 수 없으면 -1을 출력한다.
     *
     * [핵심 아이디어]
     * - 각 이동의 비용이 모두 1이므로 최단 거리 문제는 BFS로 해결할 수 있다.
     * - 현재 위치에서 이동 가능한 6방향만 인접 정점으로 보면,
     *   격자 그래프에서의 일반적인 BFS와 동일하다.
     * - BFS에서 처음 도착 지점에 도달하는 순간의 이동 횟수가 최단 거리이다.
     *
     * [구현 메모]
     * - dy, dx 배열에 데스 나이트의 6가지 이동 방향을 저장한다.
     * - visited[y][x]로 방문 여부를 관리해 중복 탐색을 막는다.
     * - 큐에는 {현재 y, 현재 x, 현재까지 이동 횟수}를 저장한다.
     * - 시작점과 도착점이 같으면 BFS를 수행할 필요 없이 바로 0을 출력한다.
     *
     * [시간 복잡도]
     * - 각 칸은 최대 한 번만 방문하므로 O(N^2)
     */

    static int n, sy, sx, ey, ex;
    static boolean[][] visited;
    static int[] dy = {-2, -2, 0, 0, 2, 2}, dx = {-1, 1, -2, 2, -1, 1};

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sy, sx, 0}); // y, x, 이동 횟수
        visited[sy][sx] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<6; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];

                // BFS에서 처음 도착 지점에 닿는 순간이 최단 거리
                if(y == ey && x == ex) return cur[2] + 1;
                if(y < 0 || x < 0 || y >= n || x >= n || visited[y][x]) continue;

                visited[y][x] = true;
                q.add(new int[]{y, x, cur[2] + 1});
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        visited = new boolean[n][n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        sy = Integer.parseInt(st.nextToken());
        sx = Integer.parseInt(st.nextToken());
        ey = Integer.parseInt(st.nextToken());
        ex = Integer.parseInt(st.nextToken());

        // 시작점 == 도착점일 가능성이 있을 것으로 예측되어 조건식을 추가
        System.out.println((sy == ey && sx == ex) ? 0 : bfs());
    }

}