import java.io.*;
import java.util.*;

class Node {
    int x, y, dist;
    int wall;

    Node(int x, int y, int dist, int wall) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.wall = wall;
    }
}

public class Main {
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1};
    static int N, M;
    static int[][] map;

    static boolean[][][] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        visited = new boolean[N][M][2];

        // 맵 정보 입력 (공백 없이 들어오므로 한 줄씩 읽어서 split)
        for (int i = 0; i < N; i++) {
            String line = br.readLine();
            for (int j = 0; j < M; j++) {
                map[i][j] = line.charAt(j) - '0';
            }
        }

        // BFS 실행 및 결과 출력
        System.out.println(bfs());
    }

    public static int bfs() {
        Queue<Node> q = new LinkedList<>();

        // 시작 지점 (0,0)에서 거리 1 = 이동칸, 벽 안 부순 상태(0)로 시작
        q.offer(new Node(0, 0, 1, 0));
        visited[0][0][0] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            // 목표 지점에 도달하면 현재까지의 거리 반환
            if (cur.x == N - 1 && cur.y == M - 1) {
                return cur.dist;
            }

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + dx[i];
                int ny = cur.y + dy[i];

                // 맵 범위 밖이면 무시
                if (nx < 0 || ny < 0 || nx >= N || ny >= M) continue;

                // Case 1: 이동하려는 칸이 빈 칸(0)인 경우
                if (map[nx][ny] == 0) {
                    // 현재 상태(벽 부숨 여부)를 유지하며 방문한 적이 없는지 확인
                    if (!visited[nx][ny][cur.wall]) {
                        visited[nx][ny][cur.wall] = true;
                        q.offer(new Node(nx, ny, cur.dist + 1, cur.wall));
                    }
                }

                // Case 2: 이동하려는 칸이 벽(1)인 경우
                else if (map[nx][ny] == 1) {
                    /*
                     * 조건 A: 아직 벽을 부순 적이 없어야 함 (curr.wall == 0)
                     * 조건 B: 벽을 부수고 방문하는 상태(1)로 해당 칸에 간 적이 없어야 함
                     */
                    if (cur.wall == 0 && !visited[nx][ny][1]) {
                        visited[nx][ny][1] = true;
                        // 이제 벽을 부쉈으므로 wall 값을 1로 변경하여 큐에 삽입
                        q.offer(new Node(nx, ny, cur.dist + 1, 1));
                    }
                }
            }
        }

        // 큐가 빌 때까지 목표점에 도달하지 못하면 경로가 없는 것
        return -1;
    }
}