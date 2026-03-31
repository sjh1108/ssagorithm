package _20260323.thdwngjs.BOJ_13206;

import java.io.*;
import java.util.*;

/**
 * BOJ 13206 - Professor KCM
 * 알고리즘: 소인수분해 + LCM (최소공배수)
 *
 * N개의 수의 LCM을 구하는 문제.
 * LCM = 각 소인수의 최대 지수의 곱
 *
 * 예) LCM(12, 18) = LCM(2^2 * 3, 2 * 3^2) = 2^2 * 3^2 = 36
 *
 * 각 수를 소인수분해하여 각 소인수별 최대 지수를 기록한 뒤,
 * 모든 소인수를 최대 지수만큼 곱하면 LCM이 된다.
 */
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
        long MOD = 1_000_000_007L;

        StringBuilder sb = new StringBuilder();
        while (T-- > 0) {
            int N = Integer.parseInt(br.readLine().trim());
            StringTokenizer st = new StringTokenizer(br.readLine());

            // maxExp[p] = 모든 수에서 소인수 p의 최대 지수
            int[] maxExp = new int[1001];

            for (int i = 0; i < N; i++) {
                int num = Integer.parseInt(st.nextToken());

                // 소인수분해: √num 이하의 소수로 나누기
                for (int p = 2; p * p <= num; p++) {
                    int exp = 0;
                    while (num % p == 0) {
                        exp++;
                        num /= p;
                    }
                    // 해당 소인수의 최대 지수 갱신
                    if (exp > maxExp[p]) maxExp[p] = exp;
                }
                // 남은 수가 1보다 크면 그 자체가 소인수 (지수 1)
                if (num > 1) {
                    if (1 > maxExp[num]) maxExp[num] = 1;
                }
            }

            // LCM 계산: 각 소인수를 최대 지수만큼 곱함
            long result = 1;
            for (int p = 2; p <= 1000; p++) {
                for (int e = 0; e < maxExp[p]; e++) {
                    result = result * p % MOD;
                }
            }
            sb.append(result).append('\n');
        }
        System.out.print(sb);
    }
}
