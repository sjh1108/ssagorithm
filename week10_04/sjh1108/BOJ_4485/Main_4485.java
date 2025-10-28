package week10_04.sjh1108.BOJ_4485;
import java.io.*;
import java.util.*;

// 백준 4485 - 녹색 옷 입은 애가 젤다지? (다익스트라 알고리즘)
class Main {
    // 동굴의 크기 N
    private static int N;

    // 상, 하, 좌, 우 이동을 위한 배열
    private static int[] dx = {-1, 0, 1, 0}; // x좌표 (열)
    private static int[] dy = {0, -1, 0, 1}; // y좌표 (행)

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 첫 번째 N 입력
        N = Integer.parseInt(br.readLine());

        StringTokenizer st;
        StringBuilder sb = new StringBuilder(); // 테스트 케이스 결과들을 모을 StringBuilder
        int idx = 0; // 테스트 케이스 번호
        
        // N이 0이 입력될 때까지 테스트 케이스 반복
        while(N > 0){
            // N x N 크기의 동굴 지도 (각 칸의 도둑루피 크기)
            int[][] map = new int[N][N];

            // 맵 정보 입력받기
            for(int i = 0; i < N; i++){
                st = new StringTokenizer(br.readLine());
                for(int j = 0; j < N; j++){
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            // 테스트 케이스 번호 증가 및 출력 형식 맞추기
            sb.append("Problem " + ++idx + ": ");
            // 다익스트라 알고리즘을 실행하여 (0, 0) -> (N-1, N-1)까지의
            // 최소 비용을 계산하고 결과에 추가
            sb.append(dijkstra(map)).append('\n');
            
            // 다음 테스트 케이스의 N 입력
            N = Integer.parseInt(br.readLine());
        }
        // 모든 테스트 케이스 결과 출력
        System.out.println(sb);
    }

    /**
     * (x, y) 좌표가 동굴 범위(0 <= x, y < N) 내에 있는지 확인하는 함수
     * @param x 열 좌표
     * @param y 행 좌표
     * @return 범위 내이면 true, 아니면 false
     */
    private static boolean isIn(int x, int y){
        return x >= 0 && x < N && y >= 0 && y < N;
    }

    /**
     * 다익스트라 알고리즘을 사용하여 (0, 0)에서 (N-1, N-1)까지 이동하는
     * 최소 비용(잃는 최소 루피)을 계산합니다.
     * @param map 동굴 지도 (각 칸의 비용)
     * @return 최소 비용
     */
    private static int dijkstra(int[][] map){
        // 각 칸(y, x)까지 도달하는 데 드는 최소 비용을 저장하는 배열
        // (다익스트라의 dist 배열 역할)
        int[][] arr = new int[N][N];
        // 모든 칸의 비용을 무한대(Integer.MAX_VALUE)로 초기화
        for(int[] a: arr) Arrays.fill(a, Integer.MAX_VALUE);

        // 우선순위 큐(Min-Heap) 사용
        // {x, y, cost} 형태의 배열을 저장하며, cost(비용) 기준으로 오름차순 정렬
        Queue<int[]> q = new PriorityQueue<>((o1, o2) -> Integer.compare(o1[2], o2[2]));

        // 시작점 (0, 0) 추가
        // (0, 0)까지 가는 비용 = (0, 0)의 루피 비용
        q.add(new int[]{0, 0, map[0][0]}); 
        // 시작점의 최소 비용 갱신 (이 코드는 없어도 동작하지만, 정석대로라면 갱신)
        // arr[0][0] = map[0][0]; 

        while(!q.isEmpty()){
            // 현재까지 최소 비용을 가진 위치를 큐에서 꺼냄
            int[] cur = q.poll();

            int x = cur[0]; // 현재 열
            int y = cur[1]; // 현재 행
            int c = cur[2]; // (0, 0)부터 (y, x)까지 오는 데 든 *누적 최소 비용*

            // [최적화]
            // 만약 현재 꺼낸 비용(c)이 이미 알려진 (y, x)까지의 최소 비용(arr[y][x])보다 크다면,
            // 이 경로는 더 볼 필요가 없으므로 스킵 (이 문제에서는 갱신 검사 로직 때문에 없어도 됨)
            // if (c > arr[y][x]) continue;

            // 목적지(N-1, N-1)에 도착했다면,
            // 다익스트라 특성상 처음 도착했을 때의 비용이 최소 비용이므로 반환
            if(x == N-1 && y == N-1) return c;

            // 현재 위치(y, x)에서 상하좌우 4방향 탐색
            for(int i = 0; i < 4; i++){
                int nx = x + dx[i]; // 다음 열
                int ny = y + dy[i]; // 다음 행

                // 다음 위치가 동굴 범위를 벗어나면 스킵
                if(!isIn(nx, ny)) continue;

                // [핵심] 비용 갱신
                // (현재까지의 누적 비용 c) + (다음 칸의 비용 map[ny][nx])
                // 이 값이, (기존에 알려진 다음 칸까지의 최소 비용 arr[ny][nx]) 보다 작다면
                if(c + map[ny][nx] < arr[ny][nx]) {
                    // 최소 비용 갱신
                    arr[ny][nx] = c + map[ny][nx];
                    // 갱신된 최소 비용으로 다음 위치를 우선순위 큐에 추가
                    q.add(new int[]{nx, ny, arr[ny][nx]});
                }
            }
        }

        // (문제 조건상 항상 도달 가능하므로 이 부분은 실행되지 않음)
        return -1;
    }
}