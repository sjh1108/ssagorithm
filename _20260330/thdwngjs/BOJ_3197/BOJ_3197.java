/**
 * BOJ 3197 - 백조의 호수 (플래티넘 5)
 *
 * [풀이] BFS (물 녹이기 + 백조 이동을 분리)
 * 매일 물에 인접한 얼음이 녹는다. 두 백조가 만날 수 있는 최소 일수를 구한다.
 *
 * 핵심 최적화:
 * - water 큐: 매일 물과 인접한 얼음을 녹임 (한 턴에 인접한 것만 처리)
 * - border 큐: 백조가 이동 가능한 영역의 경계(얼음과 맞닿은 물 칸)만 관리
 *   → 매 턴마다 전체 BFS를 다시 하지 않고, 이전에 막혔던 경계에서만 재탐색
 */
package _20260330.thdwngjs.BOJ_3197;

import java.io.*;
import java.util.*;

class Main {
    private static int R, C;
    private static int[] L; // 백조 두 마리의 좌표 (L[0],L[1]), (L[2],L[3])

    private static char[][] map;
    private static boolean[][] visited; // 백조 BFS 방문 배열

    private static Queue<int[]> water, border; // water: 물 녹이기용, border: 백조 경계 탐색용

    private static int[] dx = {0, -1, 0, 1};
    private static int[] dy = {1, 0, -1, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        L = new int[4];
        map = new char[R][];

        water = new ArrayDeque<>();
        border = new ArrayDeque<>();

        // 입력 처리: 백조 위치 저장, 물 칸을 water 큐에 추가
        int tmp = 0;
        for(int i = 0; i < R; i++){
            map[i] = br.readLine().toCharArray();

            for(int j = 0; j < C; j++){
                if(map[i][j] == 'L'){
                    L[tmp++] = i;
                    L[tmp++] = j;

                    map[i][j] = '.'; // 백조 위치도 물로 취급
                }

                if(map[i][j] == '.'){
                    water.add(new int[]{i, j});
                }
            }
        }

        // 첫 번째 백조 위치에서 BFS 시작
        visited = new boolean[R][C];
        border.add(new int[]{L[0], L[1]});
        visited[L[0]][L[1]] = true;

        int day = 0;
        while(true){
            if(checkMovable()) break; // 두 백조가 만날 수 있는지 확인
            melt();                    // 얼음 녹이기
            day++;
        }

        System.out.println(day);
    }

    /**
     * 백조 BFS: 경계(border)에서 출발하여 물 위를 탐색
     * - 물('.') → border에 추가하여 계속 탐색
     * - 얼음('X') → 다음 턴의 경계(q)에 추가 (녹으면 탐색 가능)
     */
    private static boolean checkMovable(){
        int endX = L[2], endY = L[3]; // 두 번째 백조 위치

        Queue<int[]> q = new ArrayDeque<>(); // 다음 턴의 경계

        while(!border.isEmpty()){
            int[] cur = border.poll();

            int x = cur[0], y = cur[1];
            if(x == endX && y == endY) return true; // 두 백조가 만남

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(!isIn(nx, ny) || visited[nx][ny]) continue;
                visited[nx][ny] = true;

                if(map[nx][ny] == '.'){
                    border.add(new int[]{nx, ny}); // 물이면 계속 탐색
                } else if(map[nx][ny] == 'X'){
                    q.add(new int[]{nx, ny});       // 얼음이면 다음 턴 경계로
                }
            }
        }

        border = q; // 다음 턴에 녹은 경계에서 재탐색
        return false;
    }

    /**
     * 물 녹이기: 현재 물에 인접한 얼음을 녹여서 물로 변환
     * 이번 턴에 추가된 물만큼만 처리 (size로 제한)
     */
    private static void melt(){
        int size = water.size();

        while(size-- > 0){
            int[] cur = water.poll();
            int x = cur[0], y = cur[1];

            for(int d = 0; d < 4; d++){
                int nx = x + dx[d];
                int ny = y + dy[d];

                if(!isIn(nx, ny)) continue;

                if(map[nx][ny] == 'X'){
                    map[nx][ny] = '.';
                    water.add(new int[]{nx, ny}); // 새로 녹은 칸을 다음 턴에 사용
                }
            }
        }
    }

    private static boolean isIn(int x, int y){
        return x >= 0 && x < R && y >= 0 && y < C;
    }
}
