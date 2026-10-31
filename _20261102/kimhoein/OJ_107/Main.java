import java.util.*;
import java.io.*;

public class Main {

    static class Node {
        int col;
        int row;
        int id;

        Node(int col, int row, int id) {
            this.col = col;
            this.row = row;
            this.id = id;
        }
    }

    static char[][] arr;
    static int[][] delta = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // 상하좌우

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int h = Integer.parseInt(st.nextToken());
        int w = Integer.parseInt(st.nextToken());
        int t = Integer.parseInt(st.nextToken());

        arr = new char[h][w];

        Node one = null;
        Node two = null;

        for (int i = 0; i < h; i++) {
            String s = br.readLine();

            for (int j = 0; j < w; j++) {
                arr[i][j] = s.charAt(j);

                if (arr[i][j] == '1') {
                    one = new Node(i, j, 1);
                } else if (arr[i][j] == '2') {
                    two = new Node(i, j, 2);
                }
            }
        }

        String s = br.readLine();

        for (int i = 0; i < t; i++) {
            char c = s.charAt(i);
            int dir = 0;
            Node n = new Node(0, 0, 0);

            if (c == 'U') {
                dir = 0;
            } else if (c == 'D') {
                dir = 1;
            } else if (c == 'L') {
                dir = 2;
            } else if (c == 'R') {
                dir = 3;
            }

            int pos1 = one.col * delta[dir][0] + one.row * delta[dir][1];
            int pos2 = two.col * delta[dir][0] + two.row * delta[dir][1];

            if (pos1 > pos2) {
                move(one, dir);
                move(two, dir);
            } else {
                move(two, dir);
                move(one, dir);
            }
        }

        System.out.println((one.col + 1) + " " + (one.row + 1));
        System.out.println((two.col + 1) + " " + (two.row + 1));

        bw.flush();
        bw.close();
    }

    static void move(Node n, int dir) {
        while (arr[n.col + delta[dir][0]][n.row + delta[dir][1]] == '.') {
            arr[n.col][n.row] = '.';
            arr[n.col + delta[dir][0]][n.row + delta[dir][1]] = (char) n.id;

            n.col += delta[dir][0];
            n.row += delta[dir][1];
        }
    }
}
