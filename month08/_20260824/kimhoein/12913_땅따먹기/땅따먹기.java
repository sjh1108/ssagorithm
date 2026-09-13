import java.io.*;
import java.util.*;

class Solution {
    int solution(int[][] land) {
        int answer = 0;

        int[][] dp = new int[land.length][4];

        for (int i = 0; i < 4; i++)
        {
            dp[0][i] = land[0][i];
        }

        for (int i = 1; i < land.length; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                for (int a = 0; a < 4; a++)
                {
                    if (j == a) continue;

                    dp[i][j] = Math.max(dp[i][j], land[i][j] + dp[i - 1][a]);
                }
            }
        }

        for (int i = 0; i < 4; i++)
        {
            answer = Math.max(answer, dp[land.length - 1][i]);
        }

        return answer;
    }
}
