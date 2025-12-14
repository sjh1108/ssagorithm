package week12_03.thdwngjs.BOJ_1301;

import java.io.*;
import java.util.*;

// 백준 1301 - 비즈 공예 (DP, 상태 압축)
class Main {
    private static int N;
    private static int[] biz;
    // dp[전전구슬][직전구슬][남은구슬상태]
    // 구슬 종류는 0~4, 초기 상태(없음)를 표현하기 위해 크기를 6으로 설정 (인덱스 5 사용)
    private static long[][][] dp; 
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        biz = new int[5]; // N은 최대 5이지만 편의상 5로 고정
        
        for (int i = 0; i < N; i++) {
            biz[i] = Integer.parseInt(br.readLine());
        }

        // 최대 상태 크기: 11^5 = 161,051. 넉넉하게 161052 잡음
        dp = new long[6][6][161052]; 
        
        // DP 배열 -1로 초기화
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        // 초기 호출: 전전(5), 직전(5) - 아무것도 없는 상태
        System.out.println(solve(5, 5));
    }

    private static long solve(int prev, int last) {
        // 현재 남은 구슬들의 상태를 하나의 정수(key)로 변환
        // 11진법 사용: c0 + c1*11 + c2*11^2 ...
        int currentKey = 0;
        int power = 1;
        int totalLeft = 0;
        
        for (int i = 0; i < 5; i++) {
            currentKey += biz[i] * power;
            totalLeft += biz[i];
            power *= 11;
        }

        // 모든 구슬을 다 사용했으면 경우의 수 1 반환
        if (totalLeft == 0) return 1;

        // 이미 계산한 적 있는 상태면 반환
        if (dp[prev][last][currentKey] != -1) {
            return dp[prev][last][currentKey];
        }

        long ans = 0;
        
        // 다음 구슬 선택 (0 ~ N-1)
        for (int i = 0; i < N; i++) {
            // 구슬이 남아있어야 하고
            // 조건: 연속 3개에 같은 색이 없어야 함 (즉, 직전과 다르고, 전전과 달라야 함)
            if (biz[i] > 0 && i != last && i != prev) {
                biz[i]--; // 구슬 사용
                ans += solve(last, i); // 재귀 호출 (현재가 last가 되고, i가 새로운 last가 됨)
                biz[i]++; // 원상 복구 (백트래킹)
            }
        }

        return dp[prev][last][currentKey] = ans;
    }
}