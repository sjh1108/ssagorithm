import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int C = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        int[] dp = new int[C + 101];
        
        Arrays.fill(dp, Integer.MAX_VALUE); 
        dp[0] = 0;

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int people = Integer.parseInt(st.nextToken());

            for(int j = people; j < dp.length; j++) 
                if (dp[j - people] != Integer.MAX_VALUE) 
                    dp[j] = Math.min(dp[j], dp[j - people] + cost);
        }

        int answer = Integer.MAX_VALUE;
        for (int i = C; i < dp.length; i++) 
            answer = Math.min(answer, dp[i]);
        
        System.out.println(answer);
    }
}