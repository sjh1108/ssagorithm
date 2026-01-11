package study;

import java.io.*;
import java.util.*;

public class Main_1949_김회인 {
    static int[] town;
    static List<Integer>[] list;
    static int[][] dp;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        int n = Integer.parseInt(br.readLine());
        
        StringTokenizer st = new StringTokenizer(br.readLine());
        town = new int[n + 1];
        dp = new int[n + 1][2];
        
        for (int i = 1; i <= n; i++) { // 1부터 n까지 입력받기
            town[i] = Integer.parseInt(st.nextToken());
            Arrays.fill(dp[i], -1);
        }

        list = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            list[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list[a].add(b);
            list[b].add(a);
        }

        // 결과 계산 및 출력
        int ans = Math.max(solve(1, -1, 1) + town[1], solve(1, -1, 0));
        bw.write(String.valueOf(ans)); // 정답 출력 추가
        bw.newLine();
        bw.flush();
    }

    static int solve(int pos, int prev, int flag) {
        if (dp[pos][flag] != -1) return dp[pos][flag];

        int result = 0; // 지역 변수로 계산 후 마지막에 dp에 저장

        for (int next : list[pos]) {
            if (next != prev) {
                if (flag == 1) {
                    // 내가 우수 마을이면 자식은 무조건 우수 마을이 아님
                    result += solve(next, pos, 0);
                } else {
                    // 내가 우수 마을이 아니면 자식은 우수 마을일 수도 있고 아닐 수도 있음
                    result += Math.max(solve(next, pos, 1) + town[next], solve(next, pos, 0));
                }
            }
        }

        return dp[pos][flag] = result;
    }
}