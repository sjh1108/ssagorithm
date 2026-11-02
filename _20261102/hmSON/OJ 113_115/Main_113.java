import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : DFS(스택), Flood Fill
     *
     * 구역 전체를 순회하면서 미방문 상태인 육지를 마주치면 격자 탐색을 수행하여 각 육지의 넓이를 구해야 함
     * 육지의 중복 방문을 방지하기 위해 방문한 곳은 바다로 변경
     * 격자 탐색은 BFS, DFS 어떤 방식으로든 가능. 해당 코드에서는 스택 DFS로 풀이
     * 대각선 방향도 인접한 땅으로 취급하므로 8방향 탐색 요구됨
     */

    static int r, c;
    static boolean[][] isLand; // 육지면 true, 바다면 false
    static int[] stack, dy = {-1, -1, 0, 1, 1, 1, 0, -1}, dx = {0, 1, 1, 1, 0, -1, -1, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        isLand = new boolean[r][c];
        for(int i=0; i<r; i++) {
            char[] line = br.readLine().toCharArray();
            for(int j=0; j<c; j++) {
                isLand[i][j] = line[j] == '#';
            }
        }

        int cnt = 0, max = 0; // cnt: 섬의 개수, max: 가장 큰 섬의 넓이
        stack = new int[r*c]; // DFS 스택(2차원 좌표를 1차원 형태로 관리)
        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                if(!isLand[i][j]) continue; // 바다 또는 이미 방문한 육지
                isLand[i][j] = false;
                int areaCnt = dfs(i, j);

                cnt++;
                if(areaCnt > max) max = areaCnt;
            }
        }

        System.out.println(cnt + " " + max);
    }

    // 스택 DFS 메서드
    // 지나가는 칸마다 카운트만 수행
    static int dfs(int y, int x) {
        int top = 1, areaCnt = 0;
        stack[0] = y * c + x;

        while(top > 0) {
            int cur = stack[--top];
            int cy = cur / c;
            int cx = cur % c;
            areaCnt++;

            for(int i=0; i<8; i++) {
                int ny = cy + dy[i];
                int nx = cx + dx[i];
                if(!isIn(ny, nx) || !isLand[ny][nx]) continue;

                isLand[ny][nx] = false;
                stack[top++] = ny * c + nx;
            }
        }

        // 밟은 육지 칸 수 = 섬의 넓이
        return areaCnt;
    }

    // 배열 범위 초과 방지
    static boolean isIn(int y, int x) {
        return y >= 0 && y < r && x >= 0 && x < c;
    }

}