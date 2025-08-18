import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.StringTokenizer;

public class 장훈이 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder output = new StringBuilder();
    static StringTokenizer tokens;

    static int T, N, B;
    static int[] heights;

    static int MIN;

    public static void main(String[] args) throws NumberFormatException, IOException {
        input = new BufferedReader(new StringReader(inStr));
        T = Integer.parseInt(input.readLine());
        for (int t = 1; t <= T; t++) {
            tokens = new StringTokenizer(input.readLine());
            N = Integer.parseInt(tokens.nextToken());
            B = Integer.parseInt(tokens.nextToken());
            heights = new int[N];
            tokens = new StringTokenizer(input.readLine());
            for (int n = 0; n < N; n++) {
                heights[n] = Integer.parseInt(tokens.nextToken());
            }

            MIN = Integer.MAX_VALUE;
            // 문제 풀기
            subset(0, 0);
            output.append("#").append(t).append(" ").append(MIN).append("\n");
        }
        System.out.println(output);
    }

    private static void subset(int nth, int sum) {
        // 3. 가지
        if (sum - B >= MIN) {
            return;
        }

        // 2. 기저
        if (nth == N) {
            if (sum >= B) {
                MIN = Math.min(sum - B, MIN);
            }
            return;
        }

        // 1. 재귀
        subset(nth + 1, sum); // 선택 안함
        subset(nth + 1, sum + heights[nth]); // 선택함
    }

    private static String inStr = "1\n" +
                                  "5 16\n" +
                                  "3 1 3 5 6";
}
