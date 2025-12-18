package week12_01.thdwngjs.BOJ_1301;

import java.io.*;
import java.util.*;

// 백준 1301 - 비즈 공예
// 분류: 다이나믹 프로그래밍, 비트마스킹(또는 진법 변환을 통한 상태 압축)
class Main {
    private static int N; // 비즈의 종류 수
    private static int[] biz; // 각 비즈의 남은 개수
    
    // DP 테이블
    // dp[prev][last][state]
    // prev: 전전 비즈의 종류 (0~4), 초기값은 5 (아무것도 없는 상태)
    // last: 직전 비즈의 종류 (0~4), 초기값은 5 (아무것도 없는 상태)
    // state: 남은 비즈 개수들을 11진수로 인코딩한 고유 값
    private static long[][][] dp; 
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        biz = new int[5]; // 비즈 종류는 최대 5개
        for (int i = 0; i < N; i++) {
            biz[i] = Integer.parseInt(br.readLine());
        }

        // DP 테이블 초기화
        // prev, last는 0~4 인덱스를 사용하고, 초기 상태를 위해 5번 인덱스까지 할당 (총 6x6)
        // state는 각 비즈가 최대 10개이므로 11진법을 사용하여 상태를 표현.
        // 가능한 최대값 계산: 5종류 모두 10개일 때 
        // -> 10*11^0 + 10*11^1 + 10*11^2 + 10*11^3 + 10*11^4 ≈ 161,050
        // 따라서 161052 크기면 충분함.
        dp = new long[6][6][161052]; 
        
        // DP 배열을 -1로 초기화 (미방문 상태 표시)
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        // 초기 호출: 전전(5), 직전(5) 상태에서 시작 (아무것도 놓지 않은 상태)
        System.out.println(solve(5, 5));
    }

    /**
     * 재귀 함수 (Top-down DP + Backtracking)
     * @param prev 전전 비즈의 색깔 인덱스
     * @param last 직전 비즈의 색깔 인덱스
     * @return 현재 상태에서 가능한 목걸이의 수
     */
    private static long solve(int prev, int last) {
        // [상태 키 생성]
        // 현재 남은 비즈의 개수(biz[]) 배열을 하나의 정수(Key)로 변환
        // 각 비즈 개수는 0~10이므로 11진법을 사용하여 겹치지 않는 고유 키 생성
        int currentKey = 0;
        int power = 1;
        int totalLeft = 0; // 남은 비즈의 총 개수
        
        for (int i = 0; i < 5; i++) {
            currentKey += biz[i] * power;
            totalLeft += biz[i];
            power *= 11;
        }

        // 기저 사례: 모든 비즈를 다 사용했다면(totalLeft == 0), 유효한 배치 1개를 완성한 것임
        if (totalLeft == 0) return 1;

        // 메모이제이션: 이미 계산한 상태(prev, last, 남은 개수 조합)라면 저장된 값 반환
        if (dp[prev][last][currentKey] != -1) {
            return dp[prev][last][currentKey];
        }

        long ans = 0;
        
        // 다음 비즈 선택 (0번부터 N-1번 비즈까지 시도)
        for (int i = 0; i < N; i++) {
            // [조건 검사]
            // 1. 해당 비즈가 남아있어야 함 (biz[i] > 0)
            // 2. 직전 비즈(last)와 색이 달라야 함 (i != last)
            // 3. 전전 비즈(prev)와 색이 달라야 함 (i != prev)
            if (biz[i] > 0 && i != last && i != prev) {
                biz[i]--; // 비즈 사용 (개수 감소)
                
                // 재귀 호출
                // 현재 놓는 비즈(i)가 다음 호출의 'last'가 되고,
                // 현재 호출의 'last'가 다음 호출의 'prev'가 됨.
                ans += solve(last, i); 
                
                biz[i]++; // 백트래킹 (비즈 개수 복구)
            }
        }

        // 결과 저장(Memoization) 및 반환
        return dp[prev][last][currentKey] = ans;
    }
}