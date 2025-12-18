package week12_03;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;


// 연속합 실버2
public class Main_1912 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        
        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];
        int[] dp = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 초기 입력
        
        // 초기값 설정(DP사용을 위함)
        dp[0] = arr[0];
        int max = arr[0];

        // 점화식: dp[i] = max(현재값, 이전까지의 최대합 + 현재값)
        // 이전까지의 합이 양수라면 더해서 가져가고, 음수라면 버리고 새로 시작함.
        for (int i = 1; i < n; i++) {
            dp[i] = Math.max(arr[i], dp[i - 1] + arr[i]);
            max = Math.max(max, dp[i]);
        }

        System.out.println(max);
    }
}


// 몇 개를 묶었는가는 중요하지 않음
// 앞의 뭉치가 이득인가 손해인가가 중요
// 카데인 알고리즘