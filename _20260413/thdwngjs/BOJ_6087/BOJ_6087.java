import java.io.*;
import java.util.*;

// 레이저 통신 - 두 'C' 사이를 최소 거울 개수로 연결하는 0-1 BFS 풀이
class Main {
    private static int W, H; // W: 너비, H: 높이

    // 상, 좌, 하, 우 방향 이동
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};

    private static int[] C;            // 두 'C' 좌표 저장 (C[0],C[1]: 시작, C[2],C[3]: 도착)
    private static char[][] map;       // 맵 정보
    private static int[][][] visited;  // visited[x][y][dir]: (x,y)에 dir 방향으로 도달할 때 최소 거울 수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        W = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());

        int temp = 0;
        C = new int[4];
        map = new char[H][W];
        visited = new int[H][W][4];
        for(int i = 0; i < H; i++){
            map[i] = br.readLine().toCharArray();

            for(int j = 0; j < W; j++){
                // 'C' 위치 두 개를 순서대로 저장
                if(map[i][j] == 'C'){
                    C[temp++] = i;
                    C[temp++] = j;
                }
                // 최소 거울 수를 구하기 위해 INF로 초기화
                Arrays.fill(visited[i][j], Integer.MAX_VALUE);
            }
        }

        System.out.println(bfs());
    }

    // 0-1 BFS: 같은 방향 이동(비용 0)은 덱 앞에, 방향 전환(비용 1)은 덱 뒤에 삽입
    private static int bfs(){
        Deque<int[]> dq = new ArrayDeque<>();

        // 시작점에서 4방향 모두 비용 0으로 출발
        for(int d = 0; d < 4; d++){
            visited[C[0]][C[1]][d] = 0;
            dq.addFirst(new int[]{C[0], C[1], d, 0}); // {x, y, 방향, 거울 수}
        }

        while(!dq.isEmpty()){
            int[] cur = dq.pollFirst();
            int x = cur[0], y = cur[1], dir = cur[2], cost = cur[3];

            // 도착점 'C'에 도달하면 정답 반환
            if(x == C[2] && y == C[3]) return cost;
            // 이미 더 적은 거울 수로 방문한 경우 스킵
            if(cost > visited[x][y][dir]) continue;

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                // 범위 밖이거나 벽('*')이면 스킵
                if(!isIn(nx, ny) || map[nx][ny] == '*') continue;

                // 방향이 바뀌면 거울 설치 (비용 +1), 같으면 비용 0
                int ncost = cost + (d != dir ? 1 : 0);
                if(ncost < visited[nx][ny][d]){
                    visited[nx][ny][d] = ncost;
                    if(d != dir){
                        dq.addLast(new int[]{nx, ny, d, ncost});  // 비용 1: 덱 뒤에 삽입
                    } else {
                        dq.addFirst(new int[]{nx, ny, d, ncost}); // 비용 0: 덱 앞에 삽입
                    }
                }
            }
        }
        return -1; // 도달 불가능한 경우
    }

    // 범위 체크
    private static boolean isIn(int x, int y){
        return x >= 0 && x < H && y >= 0 && y < W;
    }
}
