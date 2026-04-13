import java.io.*;
import java.util.*;

public class Main_6087_레이저_통신 {

    /*
     * BOJ_6087 : 레이저 통신 (Gold_3)
     * 자료구조 및 알고리즘 : 다익스트라
     *
     * [문제 요약]
     * - 격자에서 두 개의 C 지점을 레이저로 연결해야 한다.
     * - 레이저는 상/하/좌/우로 진행하며, 방향을 꺾을 때마다 거울 1개가 필요하다.
     * - 벽('*')은 통과할 수 없고, 필요한 거울 개수의 최솟값을 구해야 한다.
     *
     * [핵심 아이디어]
     * - 단순 BFS처럼 보여도, 이동 비용이 "한 칸 이동"이 아니라 "방향 전환 여부"에 따라 0 또는 1로 달라진다.
     * - 따라서 상태를 (y, x)만으로 두면 안 되고, "어느 방향으로 이 칸에 도달했는지"까지 포함해야 한다.
     * - dist[y][x][dir] = (y, x)에 dir 방향으로 도착했을 때 필요한 최소 반사 횟수
     * - 시작점에서 4방향으로 출발한 뒤, 직진은 비용 0, 회전은 비용 1로 두고 다익스트라를 수행하면 된다.
     *
     * [구현 메모]
     * - C는 2개가 주어지므로, 첫 번째 C를 시작점(sy, sx), 두 번째 C를 도착점(ey, ex)으로 저장한다.
     * - dist는 3차원 배열로 관리한다.
     *   - 같은 칸이라도 들어온 방향이 다르면 이후의 반사 횟수가 달라질 수 있기 때문
     * - 시작점에서는 인접한 4방향 칸을 초기 상태로 넣고, 이때 반사 횟수는 0이다.
     * - 다음 방향 탐색 시, 바로 반대 방향으로 꺾는 경우는 의미가 없으므로 제외한다.
     * - 새 비용 계산:
     *   - 같은 방향이면 +0
     *   - 다른 방향이면 +1
     *
     * [시간 복잡도]
     * - 상태 수는 R * C * 4
     * - 각 상태에서 최대 4방향 확인
     * - 다익스트라 기준 O(R * C * log(R * C)) 수준
     */

    static int r, c, sy = -1, sx, ey, ex;
    static char[][] map;
    static int[][][] dist;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    static class Laser implements Comparable<gold_3.Main_6087.Laser> {
        int y, x, dir, reflectCnt;

        public Laser(int y, int x, int dir, int reflectCnt) {
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.reflectCnt = reflectCnt;
        }

        @Override
        public int compareTo(gold_3.Main_6087.Laser o) {
            return this.reflectCnt - o.reflectCnt;
        }
    }

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        c = Integer.parseInt(st.nextToken());
        r = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        dist = new int[r][c][4];
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                if(ch == 'C') {
                    if(sy == -1) { sy = i; sx = j; }
                    else { ey = i; ex = j; }
                }

                map[i][j] = ch;
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
            }
        }
    }

    static int dijkstra() {
        PriorityQueue<gold_3.Main_6087.Laser> q = new PriorityQueue<>();

        // 시작점에서 4방향으로 첫 이동을 시도
        // 첫 방향 선택은 거울을 설치한 것이 아니므로 반사 횟수 0으로 시작
        for(int i=0; i<4; i++) {
            int y = sy + dy[i];
            int x = sx + dx[i];
            if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] == '*') continue;
            if(y == ey && x == ex) return 0;

            q.add(new gold_3.Main_6087.Laser(y, x, i, 0));
            dist[y][x][i] = 0;
            dist[sy][sx][i] = 0;
        }

        while(!q.isEmpty()) {
            gold_3.Main_6087.Laser cur = q.poll();

            if(cur.y == ey && cur.x == ex) return cur.reflectCnt;
            if(dist[cur.y][cur.x][cur.dir] < cur.reflectCnt) continue;

            for(int i=0; i<4; i++) {
                // 바로 뒤로 되돌아가는 방향은 고려하지 않음
                if((cur.dir+2)%4 == i) continue;

                int y = cur.y + dy[i];
                int x = cur.x + dx[i];
                if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] == '*') continue;

                // 직진이면 거울 추가 없음, 방향 전환이면 거울 1개 추가
                int ref = cur.reflectCnt + ((i == cur.dir) ? 0 : 1);
                if(ref >= dist[y][x][i]) continue;

                q.add(new gold_3.Main_6087.Laser(y, x, i, ref));
                dist[y][x][i] = ref;
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        System.out.println(dijkstra());
    }

}