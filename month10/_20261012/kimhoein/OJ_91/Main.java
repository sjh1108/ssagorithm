import java.util.*;
import java.io.*;

public class Main {
    static class Node {
        int safe, count;
        int col, row;

        Node(int col, int row, int safe, int count) {
            this.safe = safe;
            this.count = count;
            this.col = col;
            this.row = row;
        }
    }

    static char[][] origin_map;
    static int[][] map;
    static int H, W;
    static Queue<Node> que = new ArrayDeque<Node>();
    static Node S, E;
    static boolean[][] visit;
    static Node max;
    static int[][] delta = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        visit = new boolean[H][W];
        origin_map = new char[H][W];
        map = new int[H][W];
        max = new Node(0, 0, -1, 0);

        for (int i = 0; i < H; i++) {
            String s = br.readLine();

            for (int j = 0; j < W; j++) {
                origin_map[i][j] = s.charAt(j);

                if (origin_map[i][j] == 'S') S = new Node(i, j, 0, 0);
                if (origin_map[i][j] == 'E') E = new Node(i, j, 0, 0);
                if (origin_map[i][j] == '*') {
                    visit[i][j] = true;
                    que.add(new Node(i, j, 0, 0));
                }
            }
        }

        make_map_bfs();
        bfs();

        if (max.safe == -1) {
            System.out.println(-1);
        } else {
            System.out.println(max.safe + " " + max.count);
        }
    }

    static void make_map_bfs() {
        while (!que.isEmpty()) {
            Node node = que.poll();

            for (int i = 0; i < 4; i++) {
                int dcol = delta[i][0] + node.col;
                int drow = delta[i][1] + node.row;

                if (!(dcol >= 0 && dcol < H && drow >= 0 && drow < W)) continue;
                if (origin_map[dcol][drow] == '#') continue;
                if (visit[dcol][drow]) continue;
                visit[dcol][drow] = true;

                Node cur_node = new Node(dcol, drow, node.safe + 1, 0);
                map[dcol][drow] = cur_node.safe;

                que.add(cur_node);
            }
        }
    }

    static void bfs() {
        Queue<Node> queue = new ArrayDeque<Node>();
        S.safe = map[S.col][S.row];

        queue.add(S);
        int[][] visit_count = new int[H][W];

        for (int[] row : visit_count) {
            Arrays.fill(row, -1);
        }

        visit_count[S.col][S.row] = S.safe;

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            if (E.col == node.col && E.row == node.row) {
                if (max.safe < node.safe) max = node;
            }

            for (int i = 0; i < 4; i++) {
                int dcol = delta[i][0] + node.col;
                int drow = delta[i][1] + node.row;

                if (!(dcol >= 0 && dcol < H && drow >= 0 && drow < W)) continue;
                if (origin_map[dcol][drow] == '#') continue;

                int nextSafe = Math.min(map[dcol][drow], node.safe);
                if (visit_count[dcol][drow] >= nextSafe) continue;

                visit_count[dcol][drow] = nextSafe;
                Node n = new Node(dcol, drow, nextSafe, node.count + 1);

                queue.add(n);
            }
        }
    }
}
