import java.io.*;
import java.util.*;

class Solution {
    static class Node {
        int x;
        int y;
        int count = 0;

        Node(int x, int y, int count) {
            this.x = x;
            this.y = y;
            this.count = count;
        }
    }

    static int map[][];
    static boolean visit[][];
    static int n;
    static int m;
    static int delta[][] = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public int solution(int[][] maps) {
        int answer = 0;

        map = maps;

        n = map.length;
        m = map[0].length;

        return bfs(0, 0);
    }

    static int bfs(int x, int y) {
        visit = new boolean[n][m];
        visit[0][0] = true;

        ArrayDeque<Node> q = new ArrayDeque<>();
        q.add(new Node(0, 0, 1));

        while (!q.isEmpty()) {
            Node node_cur = q.poll();

            if (node_cur.x == n - 1 && node_cur.y == m - 1) {
                return node_cur.count;
            }

            for (int i = 0; i < 4; i++) {
                int dx = node_cur.x + delta[i][0];
                int dy = node_cur.y + delta[i][1];

                if (dx < 0 || dx >= n || dy < 0 || dy >= m) continue;
                if (map[dx][dy] == 0) continue;
                if (visit[dx][dy]) continue;

                visit[dx][dy] = true;
                q.add(new Node(dx, dy, node_cur.count + 1));
            }
        }

        return -1;
    }
}

