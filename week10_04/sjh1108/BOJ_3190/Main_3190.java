package week10_04.sjh1108.BOJ_3190;
import java.io.*;
import java.util.*;

class Main {

    // 보드의 크기 N
    private static int N;
    // 뱀의 현재 방향 (0:동, 1:남, 2:서, 3:북)
    private static int d = 0;

    // 방향 벡터 (동, 남, 서, 북)
    private static int[] dx = {1, 0, -1, 0};
    private static int[] dy = {0, 1, 0, -1};
    
    // 맵 정보 (0: 빈칸, 1: 사과)
    private static int[][] map;

    // 시간(초)을 key로, 방향('L' 또는 'D')을 value로 갖는 방향 전환 정보
    private static Map<Integer, String> moveInfo;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        N = Integer.parseInt(br.readLine());
        int k = Integer.parseInt(br.readLine());

        map = new int[N][N];
        moveInfo = new HashMap<>();

        StringTokenizer st;
        // K개의 사과 위치 정보 입력
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(br.readLine());
            
            int a = Integer.parseInt(st.nextToken()) - 1; // 1-based index -> 0-based
            int b = Integer.parseInt(st.nextToken()) - 1; // 1-based index -> 0-based
            map[a][b] = 1; // 1은 사과
        }
        
        // L개의 방향 전환 정보 입력
        int l = Integer.parseInt(br.readLine());
        for (int i = 0; i < l; i++) {
            st = new StringTokenizer(br.readLine());
            int time = Integer.parseInt(st.nextToken());
            String direction = st.nextToken();
            moveInfo.put(time, direction);
        }
        
        // 시뮬레이션 실행
        int resultTime = simulate();
        
        // 결과 출력
        System.out.println(resultTime);
    }

    /**
     * "뱀" 게임 시뮬레이션을 실행하여 게임이 종료되는 시간을 반환합니다.
     * @return 게임이 종료된 시간(초)
     */
    private static int simulate() {
        // 뱀의 몸통 위치를 저장하는 큐 (좌표를 y*N + x 형태로 저장)
        Queue<Integer> q = new LinkedList<>();
        q.add(0); // 뱀은 (0, 0)에서 시작 (0*N + 0 = 0)
        
        int time = 0; // 경과 시간
        int cx = 0; // 뱀 머리의 현재 x 좌표
        int cy = 0; // 뱀 머리의 현재 y 좌표
        
        while (true) {
            // 1. 시간 증가
            time++;
            
            // 2. 다음 머리 위치 계산
            int nx = cx + dx[d];
            int ny = cy + dy[d];
            
            // 3. [종료 조건] 벽에 부딪혔는지 확인
            if (nx < 0 || ny < 0 || nx >= N || ny >= N) {
                break;
            }
            
            // 4. [종료 조건] 자기 몸통에 부딪혔는지 확인
            // 큐에 (ny*N + nx) 값이 포함되어 있다면 몸통에 부딪힌 것
            if (q.contains(ny * N + nx)) {
                break;
            }

            // 5. [이동 처리] 
            
            // 5-1. 사과가 있는지 확인
            if (map[ny][nx] == 1) {
                // 사과가 있으면: 꼬리(q.poll())를 제거하지 않음
                map[ny][nx] = 0; // 사과 먹음
                q.add(ny * N + nx); // 머리만 다음 칸으로 이동
            } else {
                // 사과가 없으면: 꼬리(q.poll())를 제거
                q.add(ny * N + nx); // 머리를 다음 칸으로 이동
                q.poll(); // 꼬리 칸을 빔 (큐에서 제거)
            }
            
            // 6. [방향 전환]
            // 현재 시간(time)에 해당하는 방향 전환 정보가 있는지 확인
            if (moveInfo.containsKey(time)) {
                String data = moveInfo.get(time);
                if (data.equals("D")) {
                    // 오른쪽 90도
                    d = (d + 1) % 4; 
                } else {
                    // 왼쪽 90도
                    d = (d - 1);
                    if (d == -1) d = 3;
                }
            }
            
            // 7. 뱀 머리 위치 갱신
            cx = nx;
            cy = ny;
        }
        
        return time; // 루프가 종료된 시간(부딪힌 시간) 반환
    }
}