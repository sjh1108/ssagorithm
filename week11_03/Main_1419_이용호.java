package baek_week11_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1419_이용호 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int l = Integer.parseInt(br.readLine()); // 구간 시작
        int r = Integer.parseInt(br.readLine()); // 구간 끝
        int k = Integer.parseInt(br.readLine()); // 항의 개수 (2 ~ 5)

        int ans = 0;

        /*
         * 등차수열의 합 특징 (x + (x+d) + ... + x+(k-1)d)
         * == k*x + d * (0+1+...+(k-1))  == k*x + d * k*(k-1)/2
         *
         * 여기서 x와 d는 자연수
         * 즉, k*(k-1)/2는 고정된 "최소값" 역할을 하며
         * 그 이후 일정한 간격으로 등장하는 수만 만들 수 있다.
         *
         * k별로 나오는 가능한 값들을 분석해보면:
         *
         * k = 2 → 3, 4, 5, 6, ...           (모든 3 이상 포함)
         * k = 3 → 6, 9, 12, 15, ...         (3의 배수, 최소 6부터)
         * k = 4 → 14, 16, 18, 20, ...  (짝수, 최소 14부터) + 예외적으로 10이 존재
         * k = 5 → 15, 20, 25, 30, ...       (5의 배수, 최소 15부터)
         *
         * 이 성질을 이용해 수학적으로 빠르게 개수를 구한다.
         */

        if (k == 2) {
            // k=2 일 때 가능한 모든 수 = 3 이상 모든 정수
            // [max(l, 3) ~ r] 구간의 개수
            ans = Math.max(0, r - Math.max(l, 3) + 1);

        } else if (k == 3) {
            // k=3 → 6, 9, 12, ...  → 3의 배수 중 6 이상
            // r/3 = r 이하 3의 배수 개수
            // (max(l,6)-1)/3 = l 이하 3의 배수 개수
            ans = Math.max(0, r / 3 - (Math.max(l, 6) - 1) / 3);

        } else if (k == 4) {
            // k=4 → 짝수 중 14 이상: 14,16,18,... = 2의 배수
            ans = Math.max(0, r / 2 - (Math.max(l, 14) - 1) / 2);

            // 단, 10이 예외적으로 포함됨 (유일한 특수 케이스)
            if (l <= 10 && 10 <= r) ans += 1;

        } else {
            // k=5 → 15, 20, 25, ... → 5의 배수, 15 이상
            ans = Math.max(0, r / 5 - (Math.max(l, 15) - 1) / 5);
        }

        System.out.println(ans);
    }
}
