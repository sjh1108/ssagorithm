import java.util.*;
import java.io.*;

public class Main {

    static class Node {
        int col;
        int row;
        int count;

        Node(int col, int row, int count) {
            this.col = col;
            this.row = row;
            this.count = count;
        }
    }

    static Node S;
    static Node E;
    static int H;
    static int W;
    static char[][] map;
    static int[][] delta = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());
        map = new char[H][W];

        for (int i = 0; i < H; i++) {
            String s = br.readLine();

            for (int j = 0; j < W; j++) {
                map[i][j] = s.charAt(j);

                if (map[i][j] == 'S') {
                    S = new Node(i, j, 0);
                    map[i][j] = '.';
                }
                if (map[i][j] == 'E') {
                    E = new Node(i, j, 0);
                    map[i][j] = '.';
                }
            }
        }

        bw.write(Integer.toString(bfs()));
        bw.flush();
        bw.close();
    }

    static int bfs() {
        Queue<Node> q = new ArrayDeque<Node>();
        boolean[][] visit = new boolean[H][W];

        q.add(S);
        visit[S.col][S.row] = true;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            for (int i = 0; i < 4; i++) {
                Node n = new Node(cur.col, cur.row, cur.count + 1);

                while (map[n.col + delta[i][0]][n.row + delta[i][1]] == '.') {
                    n.col += delta[i][0];
                    n.row += delta[i][1];
                }

                if (map[n.col + delta[i][0]][n.row + delta[i][1]] == 'O') {
                    n.col += delta[i][0];
                    n.row += delta[i][1];
                }

                if (visit[n.col][n.row]) continue;
                visit[n.col][n.row] = true;

                if (n.col == E.col && n.row == E.row) return n.count;

                q.add(n);
            }
        }

        return -1;
    }
}
