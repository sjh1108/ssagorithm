import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DFS(스택), Flood Fill
     *
     * 물 구역을 바다와 호수로 구분하는 작업이 선행되어야 해안선의 길이 및 호수의 개수를 구할 수 있음
     * 따라서 2번의 순회가 요구됨
     * 1차: 해도 가장자리만 순회하여 해도 외부와 이어지는 물 칸 전체를 '바다(O)'로 지명
     * 2차: 해도 전체를 순회하며 2가지 일을 수행
     * - 육지 칸(#): 4방향으로 인접한 칸 중 바다가 몇 칸인지 세기. 해도 외부도 포함
     * - 물 칸(.): 미방문 상태인 물 칸은 육지 내부이므로 '호수'로 지명
     */

    static int r, c;
    static char[][] map; // #(육지), .(물-미방문), O(바다), L(호수)
    static int[] stack, dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        map = new char[r][c];
        for(int i=0; i<r; i++) {
            char[] line = br.readLine().toCharArray();
            for(int j=0; j<c; j++) {
                map[i][j] = line[j];
            }
        }

        stack = new int[r*c]; // DFS 스택(2차원 좌표를 1차원 형태로 관리)

        // 해도 외부로 이어지는 물 구역이 '바다'임을 표기해야 함
        // 해도 가장자리 물 구역을 시작점으로 하는 DFS 수행
        for(int i=0; i<r; i++) {
            if(map[i][0] == '.') dfs(i, 0, 'O');
            if(map[i][c-1] == '.') dfs(i, c-1, 'O');
        }
        for(int i=1; i<c-1; i++) {
            if(map[0][i] == '.') dfs(0, i, 'O');
            if(map[r-1][i] == '.') dfs(r-1, i, 'O');
        }

        int coastLine = 0, lakeCnt = 0; // coastLine: 해안선의 길이, lakeCnt: 호수의 개수
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                // 1. 육지 칸: 해안선 길이 측정
                if(map[i][j] == '#') coastLine += getCoastCnt(i, j);
                // 2. 물 칸: 호수의 개수 카운트 및 방문 처리를 위한 DFS 수행
                else if(map[i][j] == '.') {
                    lakeCnt++;
                    dfs(i, j, 'L');
                }
            }
        }

        System.out.println(coastLine + "\n" + lakeCnt);
    }

    // 스택 DFS 메서드
    // 지나가는 칸마다 지정한 문자(kind)로 변경
    static void dfs(int y, int x, char kind) {
        int top = 1;
        stack[0] = y * c + x;
        map[y][x] = kind;

        while(top > 0) {
            int cur = stack[--top];
            int cy = cur / c;
            int cx = cur % c;

            for(int i=0; i<4; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                if(!isIn(ny, nx) || map[ny][nx] != '.') continue;

                map[ny][nx] = kind;
                stack[top++] = ny * c + nx;
            }
        }
    }

    // 해안선 길이 측정 메서드
    // 육지 칸의 인접 4방향의 바다 칸 개수가 해안선의 길이
    // 해도 외부 또한 바다 칸으로 취급
    static int getCoastCnt(int y, int x) {
        int cnt = 0;

        for(int i=0; i<4; i++) {
            int ny = y + dy[i];
            int nx = x + dx[i];
            if(!isIn(ny, nx) || map[ny][nx] == 'O') cnt++;
        }

        return cnt;
    }

    // 배열 범위 초과 방지
    static boolean isIn(int y, int x) {
        return y >= 0 && y < r && x >= 0 && x < c;
    }

}