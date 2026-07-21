import java.io.*;
import java.util.*;

public class Main {

    /*
     * 문제 : https://jungol.co.kr/problem/1082
     *
     * 자료구조 및 알고리즘 : BFS
     * 불도 1분마다 4방향으로 퍼지고, 용사도 1분마다 상하좌우로 이동할 수 있는 상황
     * 각 지역마다 불이 도달하는 시간을 미리 계산해두고, 이후 용사가 불과 마주치지 않고 'D'까지 이동 가능한 지 파악해야 함
     * 주의 : 불이 풀리는 초기 위치는 한 곳이 아닐 수 있음
     */

    static int r, c;
    static char[][] map; // 맵 정보
    static int[][] fire; // 각 지역마다 불이 도달하는 시간을 기록. 동시에 용사의 방문 시각도 함께 기록
    static Queue<int[]> q = new ArrayDeque<>();
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        fire = new int[r][c];
        int sy = -1, sx = -1;

        for(int i=0; i<r; i++) {
            String line = br.readLine();
            for(int j=0; j<c; j++) {
                char ch = line.charAt(j);
                if(ch == '*') {
                    // 불의 초기 위치 기록
                    // 불의 초기 위치는 여러 곳일 수 있음. 미리 큐에 등록
                    q.add(new int[]{i, j, 0});
                    fire[i][j] = 0;
                } else if(ch == 'S') {
                    // 용사의 초기 위치 기록
                    sy = i; sx = j;
                }
                map[i][j] = ch;
            }

            Arrays.fill(fire[i], Integer.MAX_VALUE);
        }

        fireBFS();
        heroBFS(sy, sx);
    }

    static void fireBFS() {
        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int time = cur[2] + 1;

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i], x = cur[1] + dx[i];
                // 배열 범위를 벗어나거나 다음 위치가 바위('X'), 집('D')인 경우 무시
                if(!check(y, x) || map[y][x] == 'D') continue;

                // 이미 불이 도착한 위치도 무시
                if(fire[y][x] <= time) continue;
                fire[y][x] = time;
                q.add(new int[]{y, x, time});
            }
        }
    }

    static void heroBFS(int sy, int sx) {
        // 큐에 사용하는 배열의 형태는 동일하므로 큐 재사용
        q.clear();
        q.add(new int[]{sy, sx, 0});

        // 불 도달 시각 배열 재활용
        // 불 도달 시각과 용사의 도착 시각을 비교해 더 작은 값을 넣으면 불 대면 및 중복 이동 방지를 동시에 할 수 있음
        fire[sy][sx] = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            int time = cur[2] + 1;

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i], x = cur[1] + dx[i];
                if(!check(y, x) || fire[y][x] <= time) continue;

                // 목적지 도착
                if(map[y][x] == 'D') {
                    System.out.println(time);
                    return;
                }

                fire[y][x] = time;
                q.add(new int[]{y, x, time});
            }
        }

        // 목적지 도착할 수 없는 경우
        System.out.println("impossible");
    }

    static boolean check(int y, int x) {
        return y >= 0 && y < r && x >= 0 && x < c && map[y][x] != 'X';
    }

}