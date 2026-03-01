package _20260303.sjh1108.BOJ_2342;

import java.io.*;
import java.util.*;

class Main{
    private static List<Integer> list;
    private static int[][][] dp;
    // atp[from][to]: 발 위치 이동 비용
    private static int[][] atp = {
        {1, 2, 2, 2, 2},
        {0, 1, 3, 4, 3},
        {0, 3, 1, 3, 4},
        {0, 4, 3, 1, 3},
        {0, 3, 4, 3, 1}
    };

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        list = new ArrayList<>();
        dp = new int[100_000][5][5];
        while(st.hasMoreTokens()){
            String s = st.nextToken();
            if(s.equals("0")) break;

            list.add(Integer.parseInt(s));
        }

        System.out.println(solve(0, 0, 0));
    }

    private static int solve(int idx, int left, int right){
        // 모든 입력 처리 완료
        if(idx == list.size()) return 0;

        // 이미 계산된 상태면 재사용
        if(dp[idx][left][right] != 0) return dp[idx][left][right];

        int n = list.get(idx);
        // 다음 발판을 왼발/오른발로 밟는 경우의 최소 비용
        dp[idx][left][right] = Math.min(solve(idx+1, n, right) + atp[left][n], solve(idx+1, left, n) + atp[right][n]);

        return dp[idx][left][right];
    }
}
