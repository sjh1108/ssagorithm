import java.util.*;
import java.io.*;

public class Main {
    static class Node {
        int col, row, count;

        Node(int col, int row, int count) {
            this.col = col;
            this.row = row;
            this.count = count;
        }
    }

    static char[][] map;
    static Queue<Node> que;
    static boolean[][] visit;
    static int[][] delta = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    static int col;
    static int row;

    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        col = Integer.parseInt(st.nextToken());
        row = Integer.parseInt(st.nextToken());

        map = new char[col][row];
        visit = new boolean[col][row];
        que = new ArrayDeque<Node>();

        for (int i = 0; i < col; i++) {
            String s = br.readLine();

            for (int j = 0; j < row; j++) {
                map[i][j] = s.charAt(j);

                if (map[i][j] == '*') {
                    visit[i][j] = true;
                    que.add(new Node(i, j, 0));
                }
            }
        }

        Node max_node = bfs();

        //System.out.println("max_node : " + max_node.count);

        if (max_node.count == 0) System.out.println("-1");
        else {
            System.out.println(max_node.count);
            System.out.println((max_node.col + 1) + " " + (max_node.row + 1));
        }
    }

    static Node bfs() {
        //if(que.size() == 0) return -1;
        Node max_node = new Node(0, 0, 0);

        while (!que.isEmpty()) {
            Node n = que.poll();

            for (int i = 0; i < 4; i++) {
                int dcol = n.col + delta[i][0];
                int drow = n.row + delta[i][1];

                if (!(dcol >= 0 && dcol < col && drow >= 0 && drow < row)) continue;
                if (map[dcol][drow] == '*' || map[dcol][drow] == '#') continue;
                //System.out.println("aa");
                if (visit[dcol][drow]) continue;

                visit[dcol][drow] = true;

                Node cur = new Node(dcol, drow, n.count + 1);
                que.add(cur);
                if (max_node.count < cur.count) max_node = cur;
            }
        }

        return max_node;
    }
}
