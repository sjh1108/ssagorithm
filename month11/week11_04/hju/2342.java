import java.io.*;
import java.util.*;

class Main {

    static final int INF = 987654321;
    static int[][][] dp;

    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int answer = INF;
        dp = new int[100001][5][5];

        for(int i = 0; i <= 100000; i++) 
            for(int j = 0; j < 5; j++)
                Arrays.fill(dp[i][j], INF);

        dp[0][0][0] = 0;

        int i = 1;
        for(; i <= 100001; i++) {
            int t = Integer.parseInt(st.nextToken());
            if(t == 0)  break;

            // 왼발이 움직이는 경우
            for(int l = 0; l < 5; l++) 
                for(int r = 0; r < 5; r++) 
                    if(r != t)
                        dp[i][t][r] = Math.min(dp[i][t][r], dp[i - 1][l][r] + power(l, t));

            // 오른발이 움직이는 경우
            for(int l = 0; l < 5; l++) 
                for(int r = 0; r < 5; r++) 
                    if(l != t)
                        dp[i][l][t] = Math.min(dp[i][l][t], dp[i - 1][l][r] + power(r, t));
        }

        for(int l = 0; l < 5; l++) 
            for(int r = 0; r < 5; r++)
                if(dp[i - 1][l][r] != INF)
                    answer = Math.min(answer, dp[i - 1][l][r]);

        System.out.println(answer);
    }

    static int power(int from, int to) {    
        if(from == 0) return 2; 
        if(from == to) return 1; 
        if(Math.abs(from - to) == 2) return 4; 
        return 3;
    }
}