import java.io.*;
import java.util.*;

// BOJ 1354 - 무한 수열 2
// A(0) = 1, A(i) = A(i/P - X) + A(i/Q - Y) (i >= 1, 인덱스 <= 0이면 1)
// 바텀업 DP + 큰 인덱스는 재귀(DFS)로 처리
class Main {
    private static long N;
    private static int P, Q, X, Y;
    private static final int MAX = (int) 1e7; // DP 테이블 최대 크기
    private static long[] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Long.parseLong(st.nextToken());

        P = Integer.parseInt(st.nextToken());
        Q = Integer.parseInt(st.nextToken());

        X = Integer.parseInt(st.nextToken());
        Y = Integer.parseInt(st.nextToken());

        // 바텀업 DP: 0 ~ MAX까지 미리 계산
        dp = new long[MAX + 1];
        dp[0] = 1;
        for (int i = 1; i <= MAX; i++) {
            int nx = i / P - X; // 왼쪽 자식 인덱스
            int ny = i / Q - Y; // 오른쪽 자식 인덱스
            dp[i] = (nx <= 0 ? 1 : dp[nx]) + (ny <= 0 ? 1 : dp[ny]);
        }

        // N이 MAX 이하면 DP 테이블에서 바로 조회, 아니면 재귀 호출
        System.out.println(N <= MAX ? dp[(int) N] : dfs(N));
    }

    // N > MAX인 경우 재귀로 계산 (나누기 연산으로 인덱스가 빠르게 줄어듦)
    private static long dfs(long n) {
        if (n <= 0) return 1;          // 기저 조건: 인덱스 0 이하이면 1
        if (n <= MAX) return dp[(int) n]; // DP 테이블 범위 안이면 조회
        return dfs(n / P - X) + dfs(n / Q - Y);
    }
}