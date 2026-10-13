import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : BFS, 이분 탐색, 매개변수 탐색
     * 안전도 = 각 칸별 가장 가까운 가스 분출구까지의 거리(가스 분출구 자신의 안전도는 0)
     * 안전 등급 = 로봇이 S->E 이동시 밟는 모든 칸의 안전도 중 최소값
     * 이동 가능한 최대 안전 등급과, 그 안전 등급을 달성하는 경로 중 최소 이동 횟수를 구해야 함
     *
     * 1. 모든 열린 칸의 안전도를 미리 구해야 함 -> 가스 분출구들을 시작점으로 하는 멀티 소스 BFS
     * 2. 안전 등급 탐색 범위 초기 설정 -> low = 0(가스 분출구 통과), high = (S, E 칸의 안전도 중 작은 값)
     * 3. 검증을 위한 임의의 제한 안전 등급 지정 -> 이분 탐색, 매개변수 탐색
     * 4. 주어진 안전 등급을 준수하여 S->E 이동이 가능한 지, 가능하다면 최단 경로의 길이는 몇인지 검증 -> BFS
     * 5. 검증 결과에 따라 탐색 범위 재조정 및 3,4 과정 반복
     */

    static final int INF = 250_001;
    static int r, c, sy, sx, ey, ex; // 맵 크기, 출발점과 도착점 좌표
    static char[][] map;
    static int[][] dist; // 각 칸의 안전도 기록 배열
    static boolean[][] visited; // 안전 등급 검증시 방문 처리 배열
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};
    static Queue<int[]> q = new ArrayDeque<>(); // { y, x, 이동 거리 }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        dist = new int[r][c];
        visited = new boolean[r][c];
        for(int i=0; i<r; i++) {
            Arrays.fill(dist[i], INF);

            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                map[i][j] = ch;

                if(ch == '*') {
                    q.add(new int[]{i, j, 0});
                    dist[i][j] = 0;
                } else if(ch == 'S') {
                    sy = i; sx = j;
                } else if(ch == 'E') {
                    ey = i; ex = j;
                }
            }
        }

        // 모든 칸의 안전도를 미리 기록
        gasBFS();

        // 출발점과 도착점은 반드시 밟아야 하므로, 두 칸의 안전도 중 더 작은 값을 high로 초기화
        int res = -1, low = 0, high = Math.min(dist[sy][sx], dist[ey][ex]);
        while(low <= high) {
            int mid = low + (high - low)/2;

            // 최단 경로의 길이 반환, E 도달 불가시 -1 반환
            int curDist = pathBFS(mid);
            if(curDist == -1) {
                high = mid - 1;
            } else {
                res = curDist; // 최단 경로의 길이 기록, 마지막에 기록된 값이 최대 안전 등급 기준 최단 경로
                low = mid + 1;
            }
        }

        System.out.print(high);
        if(high != -1) System.out.println(" " + res); // high == -1이면 어떤 경로로도 S->E 이동 불가
    }

    // 가스 확산 BFS
    // 모든 열린 칸의 안전도 기록
    static void gasBFS() {
        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];
                if(!isIn(y, x) || dist[y][x] != INF) continue;

                dist[y][x] = cur[2] + 1;
                q.add(new int[]{y, x, cur[2] + 1});
            }
        }
    }

    // 안전등급 검증 BFS
    // 임의의 안전 등급을 주고, 해당 등급을 준수하며 S->E 이동이 가능한 지 확인
    // 이동 가능하면 최단 경로의 길이를 반환하고, 그렇지 않으면 -1 반환
    static int pathBFS(int min) {
        q.clear();
        for(int i=0; i<r; i++) Arrays.fill(visited[i], false);

        q.add(new int[]{sy, sx, 0});
        visited[sy][sx] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];
                if(!isIn(y, x) || visited[y][x] || dist[y][x] < min) continue;

                if(y == ey && x == ex) return cur[2] + 1;

                visited[y][x] = true;
                q.add(new int[]{y, x, cur[2] + 1});
            }
        }

        return -1;
    }

    static boolean isIn(int y, int x) {
        return y >= 0 && x >= 0 && y < r && x < c && map[y][x] != '#';
    }

}