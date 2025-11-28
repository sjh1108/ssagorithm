package week11_01.sjh1108.BOJ_14442;
import java.io.*;
import java.util.*;

// 백준 14442 - 벽 부수고 이동하기 2 (BFS)
class Main {
    // 2^20, "무한대"를 나타내는 큰 값 (최대 1000*1000 = 1,000,000 이므로 2^20은 충분히 큼)
    private static final int INF = 1 << 20; 
    private static int N, M, K; // N: 행, M: 열, K: 부술 수 있는 벽의 최대 개수

    private static char[][] map; // 맵 (0: 이동 가능, 1: 벽)

    // 상, 우, 하, 좌 (행 이동)
    private static int[] dx = {-1, 0, 1, 0};
    // 상, 우, 하, 좌 (열 이동)
    private static int[] dy = {0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행
        M = Integer.parseInt(st.nextToken()); // 열
        K = Integer.parseInt(st.nextToken()); // 부술 수 있는 벽의 개수

        map = new char[N][M]; // 맵 초기화

        for(int i = 0; i < N; i++){
            map[i] = br.readLine().toCharArray(); // 맵 정보 입력받기
        }

        // [핵심] 3차원 방문/거리 배열
        // dist[x][y][k] = (x, y) 위치에 *벽을 k번 부수고* 도달했을 때의 "최단 거리"
        int[][][] dist = new int[N][M][K+1];
        for(int[][] a : dist){
            for(int[] b: a){
                Arrays.fill(b, INF); // 모든 값을 "무한대"로 초기화 (방문하지 않음)
            }
        }
        
        // BFS를 위한 큐
        Queue<int[]> q = new ArrayDeque<>();
        // 큐에 시작점 추가: {x, y, cnt, cost}
        // {행, 열, 부순 벽의 수, 현재까지의 거리}
        q.add(new int[]{0, 0, 0, 1}); // (0, 0)에서 시작, 벽 0번 부숨, 시작점 거리 1

        while(!q.isEmpty()){
            int[] cur = q.poll(); // 큐에서 현재 위치 정보 꺼냄

            int x = cur[0]; // 현재 행
            int y = cur[1]; // 현재 열
            int cnt = cur[2]; // *지금까지* 부순 벽의 수
            int cost = cur[3]; // *지금까지* 이동한 거리

            // [최적화] 이미 더 짧은 경로로 (x, y)에 'cnt'번 부수고 도착한 적이 있다면 스킵
            // (큐에서 꺼낼 때 방문 처리를 하므로, 큐에 중복 상태가 들어갈 수 있음)
            if(dist[x][y][cnt] != INF) continue;

            // 방문 처리: (x, y)에 'cnt'번 부수고 'cost' 거리로 처음 도달했음을 기록
            dist[x][y][cnt] = cost;
            
            // 4방향(상우하좌) 탐색
            for(int d = 0; d < 4; d++){
                int nx = x + dx[d]; // 다음 행
                int ny = y + dy[d]; // 다음 열

                // 맵의 범위를 벗어나면 스킵
                if(!isIn(nx, ny)) continue;

                // Case 1: 다음 칸이 길('0')인 경우
                if(map[nx][ny] == '0'){
                    // (nx, ny)에 'cnt'번 부순 상태로 (cost+1)의 거리로 이동
                    // (다음 위치의 dist가 INF인지 확인 후 큐에 넣으면 더 최적화되지만,
                    //  큐에서 꺼낼 때 확인하는 방식도 동일하게 동작)
                    q.add(new int[]{nx, ny, cnt, cost+1});
                    continue; // 다음 방향 탐색
                } else{ // Case 2: 다음 칸이 벽('1')인 경우
                    // *아직* 벽을 부술 기회(K)가 남아있다면 (cnt < K)
                    if(cnt == K) continue; // 기회 없으면 스킵
                    
                    // (nx, ny)에 *벽을 부수고* (cnt+1)번 부순 상태로 (cost+1)의 거리로 이동
                    q.add(new int[]{nx, ny, cnt+1, cost+1});
                }
            }
        } // BFS 종료

        // BFS가 끝난 후, 목적지(N-1, M-1)에 도달한 모든 경우를 확인
        int min = INF;
        // 벽을 0번 부순 경우 ~ K번 부순 경우까지 모두 확인
        for(int cnt = 0; cnt <= K; cnt++){
            // (N-1, M-1)에 cnt번 부수고 도착한 거리와, 현재까지의 최소값(min)을 비교
            min = Math.min(dist[N-1][M-1][cnt], min);
        }

        // 최종 최소 거리가 INF면 도달 못한 것(-1), 아니면 최소 거리 출력
        System.out.println(min == INF ? -1 : min);
    }

    /**
     * (x, y) 좌표가 맵 범위(0 <= x < N, 0 <= y < M) 내에 있는지 확인
     * @param x 행 좌표
     * @param y 열 좌표
     * @return 범위 내이면 true, 아니면 false
     */
    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < M;
    }
}