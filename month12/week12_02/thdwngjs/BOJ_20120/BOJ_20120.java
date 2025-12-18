package week12_02.thdwngjs.BOJ_20120;

import java.io.*;
import java.util.*;

// 백준 20120 - 호반우와 리듬게임
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 노트의 개수
        
        // dp[j]: 현재까지 진행했을 때, '현재 콤보가 j'인 상태에서의 최대 점수
        // N개의 노트가 있으므로 최대 콤보는 N까지 가능
        long[] dp = new long[N+1];
        
        // k_1, k_2: 이전 단계들의 최대 점수들을 추적하기 위한 변수 (메모이제이션 최적화)
        // k_1: 바로 직전 단계(i-1)에서 콤보가 1 이상이었던 경우 중 최대 점수
        // k_2: 전전 단계(i-2)에서의 k_1 값 (즉, i-1 단계에서 콤보가 0이 되어 유지된 점수와 비교용)
        long k_1 = 0, k_2 = 0;
        long tmp; // 현재 단계에서 dp 배열을 순회하며 찾은 최대값(다음 k_1이 됨)

        StringTokenizer st = new StringTokenizer(br.readLine());
        
        // 첫 번째 노트 처리
        // 1번 노트를 쳤을 때: 콤보 1, 점수 = 1 * t
        dp[1] = Integer.parseInt(st.nextToken()); 
        
        // 2번 노트부터 N번 노트까지 순회
        for(int t, i = 1; i < N; i++){
            t = Integer.parseInt(st.nextToken()); // 현재 노트의 점수
            tmp = Long.MIN_VALUE; // 이번 단계에서의 최대 점수 초기화
            
            // [핵심 로직] 콤보가 이어지는 경우 (Top-down 방식의 배열 업데이트를 위해 뒤에서부터 순회)
            // j: 이전 단계의 콤보 수 (i부터 1까지)
            for (int j = i; j >= 1; j--) {
                // 현재 dp[j]값(i-1 단계의 결과) 중 최대값을 tmp에 갱신
                tmp = dp[j] > tmp ? dp[j] : tmp;
                
                // 콤보 갱신: 이전 콤보 j에서 현재 노트를 쳐서 j+1 콤보가 됨
                // 점수 = 이전 점수 + (새 콤보 j+1) * 현재 노트 점수 t
                dp[j + 1] = dp[j] + (j + 1) * t;
            }
            
            // [콤보가 끊기는 경우 / 새로 시작하는 경우]
            // dp[0]은 이번 노트를 치지 않아서(Miss) 콤보가 0이 된 상태의 최대 점수를 의미한다고 볼 수 있음.
            // 혹은 다음 단계에서 콤보 1을 시작하기 위한 베이스 점수.
            // k_1(직전 단계 최대)과 k_2를 비교하여 더 큰 값을 가져옴.
            // (문제 특성상 점수가 음수일 수 있어 0과 비교하거나 이전 최대값을 유지하는 로직)
            dp[0] = k_1 > k_2 ? k_1 : k_2;
            
            // 현재 노트를 쳐서 콤보 1이 되는 경우 (이전 상태가 콤보 0이었음)
            // dp[0] (이전까지의 최대 유지 점수) + 1 * t
            dp[1] = dp[0] + t;
            
            // k 변수 업데이트
            k_2 = k_1;
            k_1 = tmp; // 이번 단계에서 계산된 '콤보 1 이상인 상태들의 최대값'
        }
        
        // 마지막 단계까지 계산 후, 전체 dp 배열과 k 변수들 중 최대값 찾기
        tmp = k_1 > k_2 ? k_1 : k_2;
        for (int i = 1; i <= N; i++)
            tmp = dp[i] > tmp ? dp[i] : tmp;

        // 결과가 음수라면 0 출력 (아무것도 안 치는 경우 0점 가능)
        System.out.println(tmp > 0 ? tmp : 0);
    }
}