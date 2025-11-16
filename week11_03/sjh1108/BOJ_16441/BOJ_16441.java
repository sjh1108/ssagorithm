package week11_03.sjh1108.BOJ_16441;

import java.io.*;
import java.util.*;

// 백준 16441 - 아기 돼지와 늑대 (BFS)
class Main {
    private static int N, M; // N: 맵의 행(세로), M: 맵의 열(가로)

    private static char[][] map; // 맵 정보 ('.', '#', 'W', '+')
    private static boolean[][] visited; // 늑대가 도달할 수 있는 칸(true)

    // 상, 좌, 하, 우 (x가 행, y가 열)
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};
    
    // 늑대('W')의 시작 위치들을 저장하는 리스트
    private static List<int[]> W;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken()); // 행
        M = Integer.parseInt(st.nextToken()); // 열

        map = new char[N][M];
        visited = new boolean[N][M];

        W = new ArrayList<>(); // 늑대 위치 리스트 초기화
        for(int i = 0; i < N; i++){
            map[i] = br.readLine().toCharArray(); // 맵 한 줄씩 읽기

            for(int j = 0; j < M; j++){
                if(map[i][j] == 'W'){
                    W.add(new int[]{i, j}); // 늑대 위치(i, j) 저장
                } else if(map[i][j] == '#'){
                    visited[i][j] = true; // 벽('#')은 어차피 못 가므로 미리 방문 처리
                }
            }
        }

        // 큐를 생성하고, 모든 늑대의 위치(W)를 한 번에 큐에 추가 (Multi-source BFS)
        Queue<int[]> q = new ArrayDeque<>(W);

        while(!q.isEmpty()){ // BFS 실행
            int[] cur = q.poll(); // 현재 늑대 위치 {x(행), y(열)}

            int x = cur[0], y = cur[1];
            
            // [최적화] 큐에 중복으로 들어간 경우(빙판에서 같은 곳 도착)나,
            // 이미 다른 늑대가 먼저 방문한 경우 스킵
            if(visited[x][y]) continue;
            
            // 현재 위치 방문 처리
            visited[x][y] = true;

            // 4방향(상좌하우) 탐색
            for(int d = 0; d < 4; d++){
                int nx = x + dx[d]; // 다음 행
                int ny = y + dy[d]; // 다음 열

                // 맵 밖이거나, 이미 방문한 곳(벽, 늑대/빙판/초원)이면 스킵
                if(!isIn(nx, ny) || visited[nx][ny]) continue;
                
                // [핵심 로직]
                // 1. 다음 칸이 빙판('+')인 경우
                if(map[nx][ny] == '+'){
                    // 미끄러지기 시작
                    while(true){
                        nx += dx[d]; // 같은 방향(d)으로 계속 이동
                        ny += dy[d];

                        // 맵 밖으로 나가거나 벽('#')을 만나면
                        if(!isIn(nx, ny) || map[nx][ny] == '#'){
                            nx -= dx[d]; // 한 칸 뒤로 물러나서 (벽/맵 밖의 *직전* 칸)
                            ny -= dy[d];
                            break; // 미끄러짐 중단
                        }
                        // 미끄러지다가 초원('.')을 만나면
                        else if(map[nx][ny] == '.'){
                            break; // 그 초원 위에서 미끄러짐 중단
                        }
                        // (빙판('+')이나 늑대('W') 위라면 계속 미끄러짐)
                    }
                }
                
                // 2. (nx, ny)는 최종 도착 지점
                //    (그냥 초원('.')이거나, 빙판을 타고 미끄러짐이 멈춘 지점)
                //    이 위치를 큐에 추가하여 다음 탐색 대상으로
                q.add(new int[]{nx, ny});
            }
        } // BFS 종료

        // 늑대가 도달 가능한 모든 칸이 visited = true 로 표시됨

        StringBuilder sb = new StringBuilder(); // 출력용
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                // 만약 해당 칸이 초원('.')이었는데, 늑대가 방문하지 못했다면(visited == false)
                if(map[i][j] =='.' && !visited[i][j]){
                    map[i][j] = 'P'; // 'P' (안전한 목초지)로 변경
                }
                sb.append(map[i][j]); // 맵 상태 추가
            }
            sb.append('\n');
        }

        System.out.println(sb); // 최종 맵 출력
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