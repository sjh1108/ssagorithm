import java.io.*;
import java.util.*;

class Main {

    static class Point {
        int y, x;

        public Point(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static int H, W;    // h와 w (2 ≤ h, w ≤ 100)
    static char[][] map;
    static boolean[] keys;
    static int dy[] = {1, 0, -1, 0};
    static int dx[] = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int tc = 1; tc <= T; tc++) 
        {
            st = new StringTokenizer(br.readLine());

            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new char[H + 2][W + 2];
            keys = new boolean[26];

            for(int i = 0; i < H + 2; i++)
                Arrays.fill(map[i], '.');

            // 빌딩 입력
            for(int i = 1; i <= H; i++) {
                String line = br.readLine();
                for(int j = 1; j <= W; j++)
                    map[i][j] = line.charAt(j - 1);
            }

            // 주어진 열쇠
            String line = br.readLine();
            for(int s = 0; s < line.length(); s++) {
                if(line.charAt(s) == '0')   break;
                keys[line.charAt(s) - 'a'] = true;
            }

            sb.append(bfs()).append("\n");
        }

        System.out.println(sb);
    }

    static boolean isIn(int y, int x) {
        return y >= 0 && y < H + 2 && x >= 0 && x < W + 2;
    }

    static int bfs() {
        int ret = 0;
        Queue<Point> q = new ArrayDeque<>();
        boolean[][] visited = new boolean[H + 2][W + 2];
        ArrayList<Point>[] doorList = new ArrayList[26];
        for(int i = 0; i < 26; i++)
            doorList[i] = new ArrayList<>();

        visited[0][0] = true;
        q.add(new Point(0, 0));

        while(!q.isEmpty()) {
            Point cur = q.poll();

            for(int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];

                if(!isIn(ny, nx))   continue;
                if(visited[ny][nx]) continue;
                if(map[ny][nx] == '*')  continue;
                if(map[ny][nx] >= 'A' && map[ny][nx] <= 'Z') {
                    int idx = map[ny][nx] - 'A';
                    // 키가 없는 경우
                    if(!keys[idx]) {
                        doorList[idx].add(new Point(ny, nx));
                        continue;
                    }
                }
                if(map[ny][nx] >= 'a' && map[ny][nx] <= 'z')    {
                    int idx = map[ny][nx] - 'a';
                    keys[map[ny][nx] - 'a'] = true;

                    for(Point p : doorList[idx]) {
                        visited[p.y][p.x] = true;
                        q.add(p);
                    }
                }
                if(map[ny][nx] == '$')  ret++;

                visited[ny][nx] = true;
                q.add(new Point(ny, nx));
            }

        }

        return ret;
    }
}