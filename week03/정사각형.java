
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.StringTokenizer;

public class 정사각형 {

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer tokens;

    static int T, N;
    static int[][] map;

    static int minStart;
    static int maxLen;

    static int[][] deltas = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
    static int[][] memo; // memo[r][c] : r, c에서 출발했을 때 가본 최대 길이

    public static void main(String[] args) throws IOException {
        input = new BufferedReader(new StringReader(inStr));
        T = Integer.parseInt(input.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(input.readLine());
            map = new int[N][N];
            for (int r = 0; r < N; r++) {
                tokens = new StringTokenizer(input.readLine());
                for (int c = 0; c < N; c++) {
                    map[r][c] = Integer.parseInt(tokens.nextToken());
                }
            }

            minStart = Integer.MAX_VALUE;
            maxLen = Integer.MIN_VALUE;
            memo = new int[N][N];
            for (int r = 0; r < N; r++) {
                for (int c = 0; c < N; c++) {
                    int len = dfs(new Point(r, c, map[r][c]));
                    if (len > maxLen) {
                        maxLen = len;
                        minStart = map[r][c];
                    } else if (len == maxLen) {
                        minStart = Math.min(minStart, map[r][c]);
                    }

                }
            }

            output.append("#").append(t).append(" ").append(minStart).append(" ").append(maxLen).append("\n");
        }
        System.out.println(output);
    }

    static int dfs(Point p) {
        int len = 1;

        for (int d = 0; d < deltas.length; d++) {
            int nr = p.r + deltas[d][0];
            int nc = p.c + deltas[d][1];
            if (isIn(nr, nc) && map[nr][nc] == p.h + 1) {
                // 지점에 갔더니.. 메모가 있더라???
                if (memo[nr][nc] != 0) {
                    len += memo[nr][nc];
                } else {
                    len += dfs(new Point(nr, nc, map[nr][nc]));
                }
            }
        }

        return memo[p.r][p.c] = len;
    }

    static boolean isIn(int r, int c) {
        return 0 <= r && r < N && 0 <= c && c < N;
    }

    static class Point {
        int r, c, h;

        public Point(int r, int c, int h) {
            super();
            this.r = r;
            this.c = c;
            this.h = h;
        }

    }

    private static String inStr = "2\r\n" +
                                  "2\r\n" +
                                  "1 2\r\n" +
                                  "3 4\r\n" +
                                  "3\r\n" +
                                  "9 3 4\r\n" +
                                  "6 1 5\r\n" +
                                  "7 8 2";

}
