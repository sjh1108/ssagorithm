import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class 준환이 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer tokens;

    static int T, N;
    static int[] weights;
    static int A;
    static int total;
    static int[][] memo; // memo[l][b] : l 왼쪽 무게, b 상태 bit에서의 가지수

    public static void main(String[] args) throws NumberFormatException, IOException {
        input = new BufferedReader(new StringReader(inStr));
        T = Integer.parseInt(input.readLine());
        for (int t = 1; t <= T; t++) {
            N = Integer.parseInt(input.readLine());
            weights = new int[N];
            tokens = new StringTokenizer(input.readLine());
            total = 0;
            for (int n = 0; n < N; n++) {
                weights[n] = Integer.parseInt(tokens.nextToken());
                total += weights[n];
            }
            // 입력 완료
            //System.out.println(Arrays.toString(weights));
            A = 0;
            memo = new int[total + 1][1 << N];
            // 문제 풀이
            //permBasic(0, new boolean[N], 0, 0);
            // permBit(0, 0, 0, 0);
            A = permBitMemo(0, 0, 0, 0);
            output.append("#").append(t).append(" ").append(A).append("\n");
        }
        System.out.println(output);
    }

    private static int permBitMemo(int nth, int status, int sumL, int sumR) {
        // 3. 가지치기(옵션)
        if (sumL < sumR) {
            return 0;
        }

        if (memo[sumL][status] != 0) {
            return memo[sumL][status];
        }

        // 2. 기저 상황
        if (nth == N) { // 갈때까지 간 상황 - 추를 다 배치한 상황
            return 1;
        }

        // 1. 재귀 상황
        int cnt = 0;
        for (int n = 0; n < N; n++) {
            //if (!visited[n]) {
            if ((status & 1 << n) == 0) {
                //visited[n] = true;
                // 선택 -> 오른쪽? 왼쪽?
                cnt += permBitMemo(nth + 1, status | 1 << n, sumL + weights[n], sumR);
                cnt += permBitMemo(nth + 1, status | 1 << n, sumL, weights[n] + sumR);
                //visited[n] = false;
            }
        }

        return memo[sumL][status] = cnt;
    }

    private static void permBit(int nth, int status, int sumL, int sumR) {
        // 3. 가지치기(옵션)
        if (sumL < sumR) {
            return;
        }

        // 2. 기저 상황
        if (nth == N) { // 갈때까지 간 상황 - 추를 다 배치한 상황
            A++;
            return;
        }

        // 1. 재귀 상황
        for (int n = 0; n < N; n++) {
            //if (!visited[n]) {
            if ((status & 1 << n) == 0) {
                //visited[n] = true;
                // 선택 -> 오른쪽? 왼쪽?
                permBit(nth + 1, status | 1 << n, sumL + weights[n], sumR);
                permBit(nth + 1, status | 1 << n, sumL, weights[n] + sumR);
                //visited[n] = false;
            }
        }
    }

    private static void permBasic(int nth, boolean[] visited, int sumL, int sumR) {
        // 3. 가지치기(옵션)
        if (sumL < sumR) {
            return;
        }

        // 2. 기저 상황
        if (nth == N) { // 갈때까지 간 상황 - 추를 다 배치한 상황
            A++;
            return;
        }

        // 1. 재귀 상황
        for (int n = 0; n < N; n++) {
            if (!visited[n]) {
                visited[n] = true;
                // 선택 -> 오른쪽? 왼쪽?
                permBasic(nth + 1, visited, sumL + weights[n], sumR);
                permBasic(nth + 1, visited, sumL, weights[n] + sumR);
                visited[n] = false;
            }
        }
    }

    private static String inStr = "3\r\n" +
                                  "3\r\n" +
                                  "1 2 4\r\n" +
                                  "3\r\n" +
                                  "1 2 3\r\n" +
                                  "9\r\n" +
                                  "1 2 3 5 6 4 7 8 9";
}
