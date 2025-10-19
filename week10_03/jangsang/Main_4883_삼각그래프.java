package algo2025_10_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_4883_삼각그래프 {
    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer tokens;
    static int[][] graph;
    static int N;
    
    public static void main(String[] args) throws NumberFormatException, IOException {
        int cnt = 1; // 테케 1부터시작
        
        while (true) {
            N = Integer.parseInt(input.readLine());
            if (N == 0) break; // 0받았을때 입력종료
            
            graph = new int[N][3];
            for (int i = 0; i < N; i++) {
                tokens = new StringTokenizer(input.readLine());
                for (int j = 0; j < 3; j++) {
                    graph[i][j] = Integer.parseInt(tokens.nextToken());
                }
            }

            System.out.println(cnt + ". " + dp(N));
            cnt++; // 테케 출력해야해서 ++
        }
    }
    
    // 1시간 반동안 붙잡고있다가 안돼서 지선생님 호출함
    // 스터디떄 다시 설명 들어봐야할듯
    static int dp(int n) {
        int[][] dp = new int[n][3];
        // 첫 번째 줄 초기화
        dp[0][0] = Integer.MAX_VALUE;        // 왼쪽은 불가능
        dp[0][1] = graph[0][1];
        dp[0][2] = graph[0][1] + graph[0][2];
        
        for (int i = 1; i < n; i++) {
            dp[i][0] = graph[i][0] + Math.min(dp[i-1][0], dp[i-1][1]);
            dp[i][1] = graph[i][1] + Math.min(Math.min(dp[i-1][0], dp[i-1][1]), Math.min(dp[i-1][2], dp[i][0]));
            dp[i][2] = graph[i][2] + Math.min(Math.min(dp[i-1][1], dp[i-1][2]), dp[i][1]);
        }

        return dp[n-1][1];
    }
    
    // 처음에 작성한 코드임
//    static int dp(int n){
//		int sum = 0;
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < 3; j++) {
//				int x = graph[i][j];
//				if (x < graph[i][j]) {
//					sum = graph[i][j];
//				}
//			}
//		}
//		
//		return sum;
//	}
}
