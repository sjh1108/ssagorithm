import java.util.*;
import java.io.*;

public class Main {

    static class Node {
        int id, col, row;

        Node(int id, int col, int row) {
            this.id = id;
            this.col = col;
            this.row = row;
        }
    }

    static class Nodes {
        Node one;
        Node two;
        int count = 0;

        Nodes(Node one, Node two, int count) {
            this.one = one;
            this.two = two;
            this.count = count;
        }
    }

    static int H, W;
    static char[][] map;
    static Node one, two, A, B;
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
                char c = s.charAt(j);
                map[i][j] = c;

                if (c == '1') {
                    one = new Node(1, i, j);
                    map[i][j] = '.';
                }
                if (c == '2') {
                    two = new Node(2, i, j);
                    map[i][j] = '.';
                }
                if (c == 'A') {
                    A = new Node(0, i, j);
                    map[i][j] = '.';
                }
                if (c == 'B') {
                    B = new Node(0, i, j);
                    map[i][j] = '.';
                }
            }
        }

        bw.write(Integer.toString(bfs()));
        bw.flush();
        bw.close();
    }

    static int bfs() {
        Nodes n = new Nodes(one, two, 0);

        Queue<Nodes> q = new ArrayDeque<Nodes>();
        boolean[][][][] visit = new boolean[H][W][H][W];

        q.add(n);
        visit[one.col][one.row][two.col][two.row] = true;

        while (!q.isEmpty()) {
            Nodes ns = q.poll();

            for (int i = 0; i < 4; i++) {
                Node nextOne = new Node(1, ns.one.col, ns.one.row);
                Node nextTwo = new Node(2, ns.two.col, ns.two.row);
                Nodes nst = new Nodes(nextOne, nextTwo, ns.count + 1);

                int pos1 = nst.one.col * delta[i][0] + nst.one.row * delta[i][1];
                int pos2 = nst.two.col * delta[i][0] + nst.two.row * delta[i][1];

                if (pos1 > pos2) {
                    move(nst.one, nst.two, i);
                    move(nst.two, nst.one, i);
                } else {
                    move(nst.two, nst.one, i);
                    move(nst.one, nst.two, i);
                }

                if (visit[nst.one.col][nst.one.row][nst.two.col][nst.two.row]) continue;
                visit[nst.one.col][nst.one.row][nst.two.col][nst.two.row] = true;

                if (nst.one.col == A.col && nst.one.row == A.row
                        && nst.two.col == B.col && nst.two.row == B.row) return nst.count;

                q.add(nst);
            }
        }

        return -1;
    }

    static void move(Node n, Node other, int dir) {
        while (map[n.col + delta[dir][0]][n.row + delta[dir][1]] == '.') {
            //map[n.col][n.row] = '.';
            //map[n.col+delta[dir][0]][n.row+delta[dir][1]] = (char) n.id;

            if (n.col + delta[dir][0] == other.col && n.row + delta[dir][1] == other.row) break;

            n.col += delta[dir][0];
            n.row += delta[dir][1];
        }
    }
}
