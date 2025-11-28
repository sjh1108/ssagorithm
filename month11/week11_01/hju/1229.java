import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 육각수의 규칙을 찾고
        // 육각수의 합으로 N을 이루는 최소값을 찾는 문제입니다.
        // 육각수의 규칙은 그림을 보면 쉽게 찾을 수 있습니다.
        // N의 제한이 1,000,000 으로 주어졌으니 이보다 작은 육각수까지만 계산하면 됩니다.
        // 707번째 육각수인 998,991 이 1,000,000 보다 작은 가장 큰 육각수입니다.

        int[] hexa = new int[709];
        int[] dp = new int[N + 1];
        hexa[1] = 1;
        dp[1] = 1;

        // 모든 육각수를 구해놓았으니 dp로 N의 최소 조합을 구하면 됩니다.
        // 육각수에 해당하는 수는 1개로 표현할 수 있겠죠?

        for(int i = 2; i <= 708; i++) {
            hexa[i] = hexa[i - 1] + (i * 4 - 3);
            if(hexa[i] > N) break;
            dp[hexa[i]] = 1;
        }
        
        for(int i = 2; i <= N; i++) {

            if(dp[i] == 1)  continue;

            // 초기값은 가장 큰 경우(1로만 이루어질 수 있는 경우)로 초기화 해줍시다.
            dp[i] = i;

            for(int j = 1; hexa[j] < i; j++) 
                dp[i] = Math.min(dp[i], dp[i - hexa[j]] + 1);
        }

        System.out.println(dp[N]);

    }
}