import java.io.*;
import java.util.*;

class Main {
    private static long N;
    private static int P, Q, X, Y;
    private static final int MAX = (int) 1e7;
    private static long[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Long.parseLong(st.nextToken());

        P = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        dp = new long[MAX + 1];
        dp[0] = 1;
        for (int i = 1; i <= MAX; i++) {
            int nx = i / P - X;
            int ny = i / Q - Y;
            dp[i] = (nx <= 0 ? 1 : dp[nx]) + (ny <= 0 ? 1 : dp[ny]);
        }

        System.out.println(N <= MAX ? dp[(int) N] : dfs(N));
    }

    private static long dfs(long n) {
        if (n <= 0) return 1;
        if (n <= MAX) return dp[(int) n];
        return dfs(n / P - X) + dfs(n / Q - Y);
    }
}