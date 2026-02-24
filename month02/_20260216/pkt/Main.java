import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        // n이 1000까지이므로 크기 1001 배열 선언
        int[] dp = new int[1001];

        // 초기값 설정
        dp[1] = 1;
        if (n >= 2) {
            dp[2] = 3;
        }

        // 각각 2x1 1x2 2x2 타일을 붙이는 경우의 수 합산
        // 각 경우의 수 독립적으로 합산들어가면 됨.
        for (int i = 3; i <= n; i++) {
            dp[i] = (dp[i - 1] + 2 * dp[i - 2]) % 10007;
        }

        System.out.println(dp[n]);
    }
}