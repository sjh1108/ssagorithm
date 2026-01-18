import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main_6087_레이저_통신 {

    /*
     * BOJ_6087 : 레이저 통신 (Gold_3)
     * 자료구조 및 알고리즘 : 다익스트라
     *
     * [문제 요약]
     * - W x H 크기의 지도에 두 개의 'C'(통신소)가 있다.
     * - 레이저는 직진만 가능하며, 거울을 설치(/, \)하여 90도 회전할 수 있다.
     * - 두 'C'를 연결하기 위해 필요한 거울의 최소 개수를 구하라.
     *
     * [핵심 아이디어]
     * - 가중치(거울 개수)가 있는 최단 경로 문제이므로 다익스트라 알고리즘을 사용한다.
     * - 특정 지점 (y, x)에 도달했을 때, '어느 방향에서 왔는지'에 따라 다음 이동의 거울 설치 여부가 결정된다.
     * - 따라서 방문 배열을 3차원 dist[y][x][dir]로 선언하여 방향별 최소 거울 수를 관리해야 한다.
     * - 같은 방향으로 진행하면 비용 0, 방향을 90도 꺾으면 비용 1이 추가된다.
     *
     * [구현 메모]
     * - Laser 클래스 : 현재 위치(y, x), 진행 방향(dir), 누적 거울 수(mirrorCnt) 정보를 가짐.
     * - PriorityQueue : 거울 사용 횟수가 적은 순서대로 정렬하여 탐색.
     * - 초기화 로직 : 시작점에서는 어느 방향으로든 쏠 수 있으며, 첫 발사는 거울이 필요 없으므로 4방향 모두 비용 0으로 큐에 넣고 시작한다.
     *
     * [시간 복잡도]
     * - 노드 수 V = R * C * 4 (각 좌표별 4방향)
     * - 다익스트라 시간 복잡도 O(V log V) 내외로 해결 가능.
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int r, c;
    static char[][] map;
    static int[][][] dist; // [y][x][dir] : 해당 위치에 해당 방향으로 도달했을 때의 최소 거울 수
    static int[] start, end, dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    // 우선순위 큐에서 사용할 객체 (거울 개수 기준 오름차순 정렬)
    static class Laser implements Comparable<Laser> {
        int y, x, dir, mirrorCnt;

        public Laser(int y, int x, int dir, int mirrorCnt) {
            this.y = y;
            this.x = x;
            this.dir = dir;
            this.mirrorCnt = mirrorCnt;
        }

        @Override
        public int compareTo(Laser o) {
            return this.mirrorCnt - o.mirrorCnt;
        }
    }

    static void init() throws Exception {
        // BufferedReader br은 필드에 선언됨
        StringTokenizer st = new StringTokenizer(br.readLine());
        c = Integer.parseInt(st.nextToken()); // 가로 (열)
        r = Integer.parseInt(st.nextToken()); // 세로 (행)

        map = new char[r][c];
        dist = new int[r][c][4]; // 방향까지 고려한 거리 배열
        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                if(ch == 'C') {
                    if(start == null) start = new int[]{i, j};
                    else end = new int[]{i, j};
                }

                map[i][j] = ch;

                // 최솟값을 구하기 위해 MAX_VALUE 초기화
                Arrays.fill(dist[i][j], Integer.MAX_VALUE);
            }
        }
    }

    static int dijkstra() {
        PriorityQueue<Laser> q = new PriorityQueue<>();
        int sy = start[0], sx = start[1];

        // 시작점에서 4방향으로 첫 출발 (첫 출발은 거울 개수 0)
        for(int i=0; i<4; i++) {
            int y = sy + dy[i];
            int x = sx + dx[i];

            // 범위 벗어나거나 벽이면 패스
            if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] == '*') continue;

            // 바로 목적지라면 0 반환
            if(y == end[0] && x == end[1]) return 0;

            q.add(new Laser(y, x, i, 0));
            dist[y][x][i] = 0;
            dist[sy][sx][i] = 0; // 시작점 방문 처리
        }

        while(!q.isEmpty()) {
            Laser cur = q.poll();

            // 목적지 도달 시 거울 개수 반환
            if(cur.y == end[0] && cur.x == end[1]) return cur.mirrorCnt;

            // 이미 더 적은 거울로 해당 지점+방향에 도달한 적 있다면 스킵
            if(dist[cur.y][cur.x][cur.dir] < cur.mirrorCnt) continue;

            for(int i=0; i<4; i++) {
                // 왔던 길을 되돌아가는 방향(180도 회전)은 불필요하므로 제외
                if(i == (cur.dir + 2)%4) continue;

                int y = cur.y + dy[i];
                int x = cur.x + dx[i];
                if(y < 0 || x < 0 || y >= r || x >= c || map[y][x] == '*') continue;

                // 진행 방향과 같으면 거울 수 유지(0), 다르면 거울 추가(+1)
                int curCnt = cur.mirrorCnt + (i == cur.dir ? 0 : 1);

                // 더 적은 거울 수로 도달 가능할 때만 갱신
                if(dist[y][x][i] <= curCnt) continue;

                dist[y][x][i] = curCnt;
                q.add(new Laser(y, x, i, curCnt));
            }
        }

        return -1;
    }

    public static void main(String[] args) throws Exception {
        init();
        System.out.println(dijkstra());
    }

}