import java.util.*;

class Solution {

    // 자료구조 및 알고리즘 : BFS, Flood Fill
    // 2차원 배열 상에서 각 색상이 자연수로 표현된다. 0은 빈 칸이다.
    // 상하좌우 4방향으로 인접하면 하나의 영역으로 취급한다.
    // 그림이 2차원 배열로 주어지면, 그림 내에 존재하는 영역 수와, 가장 넓은 영역의 크기를 반환해야 한다.

    // 배열 전체를 돌면서 해당 좌표가 0이 아닌 영역(색칠된 영역)인지 확인한다.
    // 0이 아닌 영역이 존재하면 즉시 그 자리를 시작점으로 하여 영역을 확인한다.
    // 영역 확인을 위해 BFS를 활용한다. 방문한 위치는 0으로 변경하여 중복 탐지를 방지한다.
    // BFS 수행 중 방문하는 모든 좌표에 대해 카운트한다. BFS 종료 후의 카운트가 해당 영역의 크기이다.

    static int areaCnt, max, r, c;
    // BFS 호출시 사용할 int[] 타입 큐
    // 메서드 호출 횟수 많아지면, 매 회차마다 객체 재생성하는 것보다 하나를 재사용하는 것이 나음
    static Queue<int[]> q = new ArrayDeque<>();
    static int[][] arr;
    static int[] dy = {-1, 0, 1, 0}, dx = {0, 1, 0, -1};

    public int[] solution(int m, int n, int[][] picture) {
        r = m; c = n;
        areaCnt = 0; max = 0;
        arr = picture;

        for(int i=0; i<r; i++) {
            for(int j=0; j<c; j++) {
                if(arr[i][j] == 0) continue;
                // 0이 아닌 영역(색칠된 영역)이 존재하면 같은 색깔의 영역 전체를 탐색한다.
                int area = bfs(i, j, arr[i][j]);

                // 하나의 영역을 확인했으므로 영역 수 카운트
                areaCnt++;

                // 최대 크기 영역 갱신
                if(area > max) max = area;
            }
        }

        return new int[]{areaCnt, max};
    }

    static int bfs(int sy, int sx, int color) {
        // 현재 영역의 크기. 1은 시작 위치
        int area = 1;
        // 이론상 해당 문제에서는 q.clear()가 필요 없으나 객체 재사용을 명시하는 의미에서 사용했음. 빼도 됨.
        q.clear();
        // 시작 위치를 큐에 추가하고, 방문 처리를 위해 색을 지움
        q.add(new int[]{sy, sx});
        arr[sy][sx] = 0;

        while(!q.isEmpty()) {
            int[] cur = q.poll();

            for(int i=0; i<4; i++) {
                int y = cur[0] + dy[i];
                int x = cur[1] + dx[i];

                // 배열 범위 초과 방지 및 동일 색상 외 영역 방문 방지
                if(y < 0 || x < 0 || y >= r || x >= c || arr[y][x] != color) continue;

                // 같은 영역에 해당하는 모든 칸에 대해 카운트
                area++;
                q.add(new int[]{y, x});
                // 방문한 모든 칸의 색을 지움
                arr[y][x] = 0;
            }
        }

        // 반환 : 현재 영역 크기
        return area;
    }

}