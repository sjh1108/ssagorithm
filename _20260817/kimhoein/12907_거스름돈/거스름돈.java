import java.io.*;
import java.util.*;

class Solution {
    public int solution(int n, int[] money) {
        int answer = 0;

        int[] dp = new int[n + 1];

        dp[0] = 1;

        for (int i = 0; i < money.length; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                if (j - money[i] < 0) continue;

                dp[j] = (dp[j] + dp[j - money[i]]) % 1_000_000_007;
            }
        }

        answer = dp[n];

        return answer;
    }
}
