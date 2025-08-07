package silver_1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_7562_손홍민 {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();
    static StringTokenizer st;
    // 체스판 크기, 목적지 좌표
    static int n, destY, destX;
    // 맵, 이미 방문한 곳은 true
    static boolean[][] visited;
    static Queue<int[]> q = new ArrayDeque<>();
    // 나이트 이동 반경 델타 배열
    static int[] dy = {-2, -2, -1, 1, 2, 2, 1, -1}, dx = {-1, 1, 2, 2, 1, -1, -2, -2};

    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            init(); bfs();
            // 탐색 도중에 종료하므로 큐에 데이터가 남아있을 수 있음
            q.clear();
        }
        System.out.println(sb);
    }

    public static void init() throws Exception {
        // 맵 정보 입력
        n = Integer.parseInt(br.readLine());
        visited = new boolean[n][n];

        // 나이트 시작 위치 입력
        st = new StringTokenizer(br.readLine());
        int y = Integer.parseInt(st.nextToken());
        int x = Integer.parseInt(st.nextToken());
        visited[y][x] = true;
        q.add(new int[]{y, x, 0});

        // 나이트 목적지 입력
        st = new StringTokenizer(br.readLine());
        destY = Integer.parseInt(st.nextToken());
        destX = Integer.parseInt(st.nextToken());
    }

    public static void bfs() throws Exception {
        while(!q.isEmpty()) {
            int[] p = q.poll();
            // 각각 현재 좌표와 이동 횟수
            int y = p[0], x = p[1], moveCnt = p[2];
            // 현재 위치가 목적지인 경우 이동 횟수 출력 후 탐색 종료
            if(y == destY && x == destX) {
                sb.append(moveCnt).append("\n");
                return;
            }
            // 이동 방향 별 탐색
            for(int i=0; i<8; i++) {
                int newY = y + dy[i];
                int newX = x + dx[i];
                // 배열 범위 제한 초과 방지
                if(!isIn(newY, newX) || visited[newY][newX]) continue;
                visited[newY][newX] = true;
                q.add(new int[]{newY, newX, moveCnt+1});
            }
        }
    }

    public static boolean isIn(int y, int x) {
        return y >= 0 && y < n && x >= 0 && x < n;
    }
}
