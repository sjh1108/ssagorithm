import java.util.*;
import java.io.*;

/*
bfs임
bfs 돌리면서 최소 산소 값 저장하고 도착하면 max 값 측정하도록하고
만약 max 보다 크면 update 진행
*/

public class Main {
    static class Node {
        int H, W, ox;

        Node(int H, int W, int ox) {
            this.H = H;
            this.W = W;
            this.ox = ox;
        }
    }

    static int H, W;
    static int[][] map;
    static int[][] visit;
    static int max = 0;
    static int[][] delta = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());

        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        map = new int[H][W];
        visit = new int[H][W];

        for (int i = 0; i < H; i++) {
            st = new StringTokenizer(br.readLine());

            for (int j = 0; j < W; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        bfs();

        bw.write(Integer.toString(max));
        bw.flush();
        bw.close();
    }

    static void bfs() {
        Node node = new Node(0, 0, map[0][0]);

        Queue<Node> que = new ArrayDeque<Node>();
        visit[0][0] = map[0][0];
        que.add(node);

        while (!que.isEmpty()) {
            Node n = que.poll();

            if (n.H == H - 1 && n.W == W - 1) {
                max = Math.max(n.ox, max);
                continue;
            }

            for (int i = 0; i < 4; i++) {
                int dh = delta[i][0] + n.H;
                int dw = delta[i][1] + n.W;

                if (!(dh >= 0 && dh < H && dw >= 0 && dw < W)) continue;
                if (visit[dh][dw] >= n.ox) continue;

                visit[dh][dw] = n.ox;

                Node cur_node = new Node(dh, dw, Math.min(n.ox, map[dh][dw]));
                que.add(cur_node);
            }
        }
    }
}
