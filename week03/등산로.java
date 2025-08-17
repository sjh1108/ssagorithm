
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 등산로 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer tokens;

    static int T, N, K;
    static int[][] map;
    static int maxHeight;
    static int maxPathLength;

    static boolean[][] visited;
    static int[][] deltas = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new StringReader(inStr));
        T = Integer.parseInt(input.readLine());
        for (int t = 1; t <= T; t++) {
            tokens = new StringTokenizer(input.readLine());
            N = Integer.parseInt(tokens.nextToken());
            K = Integer.parseInt(tokens.nextToken());
            map = new int[N][N];

            maxHeight = 0;// 선언한 변수는 반드시 초기화 해주자.
            for (int r = 0; r < N; r++) {
                tokens = new StringTokenizer(input.readLine());
                for (int c = 0; c < N; c++) {
                    map[r][c] = Integer.parseInt(tokens.nextToken());
                    maxHeight = Math.max(maxHeight, map[r][c]);
                }
            }
            // 입력 완료
            //            for(int [] row: map) {
            //                System.out.println(Arrays.toString(row));
            //            }

            maxPathLength = 0;
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    if (map[r][c] == maxHeight) {
                        visited = new boolean[N][N];
                        dfs(new Point(r, c, map[r][c], 1, true));
                    }
                }
            }
            output.append("#").append(t).append(" ").append(maxPathLength).append("\n");
        }
        System.out.println(output);
    }

    static void dfs(Point p) {
        // 지점에서 현재 상태 처리
        visited[p.r][p.c] = true;
        maxPathLength = Math.max(maxPathLength, p.l);

        for (int d = 0; d < deltas.length; d++) {
            int nr = p.r + deltas[d][0];
            int nc = p.c + deltas[d][1];

            if (isIn(nr, nc) && !visited[nr][nc]) {
                // 내리막만 가능, 
                if (map[nr][nc] < p.h) {
                    dfs(new Point(nr, nc, map[nr][nc], p.l + 1, p.canDig));
                } else {
                    // 내리막이 아니라면 팔수 있나 보고..
                    if (p.canDig && K > map[nr][nc] - p.h) {
                        dfs(new Point(nr, nc, p.h - 1, p.l + 1, false));
                    }
                }
            }
        }
        visited[p.r][p.c] = false;
    }

    static boolean isIn(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < N;
    }

    static class Point {
        int r, c, h, l; // row, col, height, length
        boolean canDig;

        public Point(int r, int c, int h, int l, boolean canDig) {
            super();
            this.r = r;
            this.c = c;
            this.h = h;
            this.l = l;
            this.canDig = canDig;
        }

        @Override
        public String toString() {
            return "Point [r=" + r + ", c=" + c + ", h=" + h + ", l=" + l + ", canDig=" + canDig + "]";
        }

    }

    // REMOVE_START
    private static String inStr = "10\r\n" +
                                  "5 1\r\n" +
                                  "9 3 2 3 2\r\n" +
                                  "6 3 1 7 5\r\n" +
                                  "3 4 8 9 9\r\n" +
                                  "2 3 7 7 7\r\n" +
                                  "7 6 5 5 8\r\n" +
                                  "3 2\r\n" +
                                  "1 2 1\r\n" +
                                  "2 1 2\r\n" +
                                  "1 2 1\r\n" +
                                  "5 2\r\n" +
                                  "9 3 2 3 2\r\n" +
                                  "6 3 1 7 5\r\n" +
                                  "3 4 8 9 9\r\n" +
                                  "2 3 7 7 7\r\n" +
                                  "7 6 5 5 8\r\n" +
                                  "4 4\r\n" +
                                  "8 3 9 5\r\n" +
                                  "4 6 8 5\r\n" +
                                  "8 1 5 1\r\n" +
                                  "4 9 5 5\r\n" +
                                  "4 1\r\n" +
                                  "6 6 1 7\r\n" +
                                  "3 6 6 1\r\n" +
                                  "2 4 2 4\r\n" +
                                  "7 1 3 4\r\n" +
                                  "5 5\r\n" +
                                  "18 18 1 8 18\r\n" +
                                  "17 7 2 7 2\r\n" +
                                  "17 8 7 4 3\r\n" +
                                  "17 9 6 5 16\r\n" +
                                  "18 10 17 13 18\r\n" +
                                  "6 4\r\n" +
                                  "12 3 12 10 2 2\r\n" +
                                  "13 7 13 3 11 6\r\n" +
                                  "2 2 6 5 13 9\r\n" +
                                  "1 12 5 4 10 5\r\n" +
                                  "11 10 12 8 2 6\r\n" +
                                  "13 13 7 4 11 7\r\n" +
                                  "7 3\r\n" +
                                  "16 10 14 14 15 14 14\r\n" +
                                  "15 7 12 2 6 4 9\r\n" +
                                  "10 4 11 4 6 1 1\r\n" +
                                  "16 4 1 1 13 9 14\r\n" +
                                  "3 12 16 14 8 13 9\r\n" +
                                  "3 4 17 15 12 15 1\r\n" +
                                  "6 6 13 6 6 17 12\r\n" +
                                  "8 5\r\n" +
                                  "2 3 4 5 4 3 2 1\r\n" +
                                  "3 4 5 6 5 4 3 2\r\n" +
                                  "4 5 6 7 6 5 4 3\r\n" +
                                  "5 6 7 8 7 6 5 4\r\n" +
                                  "6 7 8 9 8 7 6 5\r\n" +
                                  "5 6 7 8 7 6 5 4\r\n" +
                                  "4 5 6 7 6 5 4 3\r\n" +
                                  "3 4 5 6 5 4 3 2\r\n" +
                                  "8 2\r\n" +
                                  "5 20 15 11 1 17 10 14\r\n" +
                                  "1 1 11 16 1 14 7 5\r\n" +
                                  "17 2 3 4 5 13 19 20\r\n" +
                                  "6 18 5 16 6 7 8 5\r\n" +
                                  "10 4 5 4 9 2 10 16\r\n" +
                                  "2 7 16 5 8 9 10 11\r\n" +
                                  "12 19 18 8 7 11 15 12\r\n" +
                                  "1 20 18 17 16 15 14 13\r\n" +
                                  "";
    // REMOVE_END

}
