import java.util.*;

class Solution {

    /*
    * 자료구조 및 알고리즘 : BFS
    * 1은 이동할 수 있는 칸, 0은 벽이며 상하좌우 4방향으로만 이동 가능
    * 시작지점은 (0, 0), 도착지점은 (r-1, c-1)로 고정
    * 2차원 배열로 게임 맵이 주어지면 시작지점부터 도착지점까지 지나가야 하는 칸의 최소 개수를 반환. 시작지점과 도착지점 포함.
    * 단, 도착 지점으로 이동할 방법이 없다면 -1 반환
    * */

    // 4방향 탐색(붃, 동, 남, 서)
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public int solution(int[][] maps) {
        int r = maps.length, c = maps[0].length;

        Queue<int[]> q = new ArrayDeque<>();
        // 각각 y, x, cnt(밟은 칸의 수, 시작 지점 포함이므로 초기 값 1)
        q.add(new int[]{0, 0, 1});
        // 이미 밟은 지역을 0으로 변경해 방문 처리
        maps[0][0] = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];
                // 배열 범위 초과 및 중복 방문 방지
                if(y < 0 || x < 0 || y >= r || x >= c || maps[y][x] == 0) continue;
                // 탐색 지점이 도착 지점이면 총 밟은 칸 수 반환
                if(y == r - 1 && x == c - 1) return cur[2] + 1;

                maps[y][x] = 0;
                q.add(new int[]{y, x, cur[2] + 1});
            }
        }

        // 도착 지점까지 이동할 방법 없음. -1 반환.
        return -1;
    }

}