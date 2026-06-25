package feature05_01;

import java.util.ArrayDeque;
import java.util.Queue;

// 거리두기 확인하기 Lv.2
class Solution_81302 {
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};

    public int[] solution(String[][] places) {
        int[] answer = new int[places.length];
        for (int i = 0; i < places.length; i++) {
            answer[i] = isSafe(places[i]) ? 1 : 0;
        }
        return answer;
    }

    public boolean isSafe(String[] place) {
        char[][] room = new char[5][5];
        for (int i = 0; i < 5; i++) {
            room[i] = place[i].toCharArray();
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (room[i][j] == 'P' && !bfs(room, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean bfs(char[][] room, int sr, int sc) {
        boolean[][] visited = new boolean[5][5];
        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{sr, sc, 0});
        visited[sr][sc] = true;

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            int r = cur[0];
            int c = cur[1];
            int dist = cur[2];

            if (dist == 2) continue;

            for (int d = 0; d < 4; d++) {
                int nr = r + DR[d];
                int nc = c + DC[d];
                if (nr < 0 || nr >= 5 || nc < 0 || nc >= 5) continue;
                if (visited[nr][nc] || room[nr][nc] == 'X') continue;
                if (room[nr][nc] == 'P') return false;
                visited[nr][nc] = true;
                q.offer(new int[]{nr, nc, dist + 1});
            }
        }
        return true;
    }
}