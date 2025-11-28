import java.io.*;
import java.util.*;

class Main {

    static class Point {
        int y, x;
        public Point(int y, int x) { this.y = y; this.x = x; }
    }

    static int N, M;
    static int[][] map;
    static int[][] groupMap;
    static ArrayList<Integer> groupSize;
    static int[] dy = {1, 0, -1, 0};
    static int[] dx = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new int[N][M];
        groupMap = new int[N][M];
        groupSize = new ArrayList<>();
        groupSize.add(0);

        for(int i = 0; i < N; i++) {
            String line = br.readLine();
            for(int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        int groupId = 1;
        for(int i = 0; i < N; i++)
            for(int j = 0; j < M; j++)
                if(map[i][j] == 0 && groupMap[i][j] == 0)
                    bfs(i, j, groupId++);


        for(int i = 0; i < N; i++) {
            for(int j = 0; j < M; j++) {
                if(map[i][j] == 1) {
                    HashSet<Integer> addedGroups = new HashSet<>(); // 중복 그룹 체크용
                    int moveCount = 1; // 자기 자신 포함

                    for(int d = 0; d < 4; d++) {
                        int ny = i + dy[d];
                        int nx = j + dx[d];

                        if(!isIn(ny, nx)) continue;
                        if(map[ny][nx] == 0) {
                            int neighborGroup = groupMap[ny][nx];

                            if(addedGroups.contains(neighborGroup)) continue;

                            moveCount += groupSize.get(neighborGroup);
                            addedGroups.add(neighborGroup);
                        }
                    }
                    sb.append(moveCount % 10);
                } 
                else    sb.append(0);
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    static boolean isIn(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    static void bfs(int y, int x, int id) {
        Queue<Point> q = new ArrayDeque<>();
        q.add(new Point(y, x));
        groupMap[y][x] = id;
        int cnt = 1;

        while(!q.isEmpty()) {
            Point cur = q.poll();

            for(int d = 0; d < 4; d++) {
                int ny = cur.y + dy[d];
                int nx = cur.x + dx[d];

                if(!isIn(ny, nx)) continue;
                if(map[ny][nx] != 0) continue;
                if(groupMap[ny][nx] != 0)   continue;

                groupMap[ny][nx] = id;
                q.add(new Point(ny, nx));
                cnt++;
            }
        }

        groupSize.add(cnt);
    }
}