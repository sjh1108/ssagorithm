import java.io.*;
import java.util.*;

public class Main_29703_펭귄의_하루 {

    /*
     * BOJ_29703 : 펭귄의 하루 (Gold_3)
     * 자료구조 및 알고리즘 : BFS
     *
     * [문제 요약]
     * - R×C 격자에서 시작점 S에서 출발한다.
     * - 칸의 종류: E(안전), D(위험/이동 불가), F(물고기), H(집).
     * - 물고기를 얻기 전에는 H로 가도 의미가 없고, 물고기를 얻은 뒤에 H에 도달해야 종료 가능하다.
     * - 최소 시간(이동 횟수)을 출력하고, 불가능하면 -1을 출력한다.
     *
     * [핵심 아이디어]
     * - 최소 이동 횟수 문제이므로 BFS가 적합하다.
     * - 같은 (y, x)라도 "물고기 보유 여부"에 따라 방문 가능 상태가 달라진다.
     *   => 상태를 (y, x, time, fish)로 확장해야 한다.
     * - 방문 처리도 2차원이 아니라 visited[y][x][fish]처럼 분리해야
     *   물고기 획득 전/후의 경로를 올바르게 탐색할 수 있다.
     *
     * [구현 메모]
     * - 큐에는 {y, x, time, fish}를 저장한다.
     *   - fish: 0(미보유), 1(보유)
     * - 이동 불가 조건:
     *   - 격자 범위 밖
     *   - 위험 구역 'D'
     *   - 이미 같은 fish 상태로 방문한 칸
     * - 상태 전이:
     *   - fish==0 상태로 'F'에 들어가면 fish를 1로 바꾼 뒤 큐에 삽입
     *   - fish==1 상태에서 'H'에 도달하면 즉시 time+1 반환 (BFS이므로 최단)
     *
     * [시간 복잡도]
     * - 상태 수: R * C * 2
     * - 각 상태에서 4방향 확인 -> O(R * C)
     */

    static int r, c, sy, sx;
    static char[][] map;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                map[i][j] = ch;
                if(ch == 'S') { sy = i; sx = j; }
            }
        }
    }

    static int bfs() {
        Queue<int[]> q = new ArrayDeque<>();
        q.add(new int[]{sy, sx, 0, 0}); // y, x, 시간, 물고기 획득 여부

        // visited[y][x][fish] : 같은 좌표라도 물고기 보유 여부에 따라 방문 상태를 분리
        boolean[][][] visited = new boolean[r][c][2];
        visited[sy][sx][0] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int time = cur[2];

            for(int i=0; i<4; i++) {
                int fish = cur[3];
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];

                if(y < 0 || y >= r || x < 0 || x >= c || map[y][x] == 'D' || visited[y][x][fish]) continue;

                // 물고기를 가진 상태에서 집(H)에 도달하면 종료(최단)
                if(fish == 1 && map[y][x] == 'H') return time+1;

                // 물고기가 없는 상태에서 F에 도달하면 상태 전이(획득)
                if(fish == 0 && map[y][x] == 'F') fish = 1;

                visited[y][x][fish] = true;
                q.add(new int[]{y, x, time+1, fish});
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        System.out.println(bfs());
    }

}