package algostudy.swea.a14510;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution_14510_나무높이_이용호 {
    public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            int N = Integer.parseInt(br.readLine());
            StringTokenizer st = new StringTokenizer(br.readLine());
            int[] tree = new int[N];
            int maxH = 0;

            // 가장 높은 나무의 키찾기 + 배열에 키 저장
            for (int i = 0; i < N; i++) {
                tree[i] = Integer.parseInt(st.nextToken());
                if (tree[i] > maxH) {
                    maxH = tree[i];
                }
            }

            int odd_need = 0;
            int even_need = 0;

            // 각 나무의 높이 차이를 계산 -> 1씩 물주기 횟수와 2씩 물주기 횟수(몫)를 구함
            for (int i = 0; i < N; i++) {
                int diff = maxH - tree[i];
                even_need += diff / 2;
                if (diff % 2 == 1) {
                    odd_need++;
                }
            }

            int days = 0;
            
            // 그리디 방식으로 최소 날짜를 계산
            // 물주기 횟수 중 더 많은 횟수를 먼저 처리
            if (odd_need > even_need) {
                // 짝수 물주기 횟수만큼 물을 주기 -> 2 * even_needs 일
                days += 2 * even_need;
                odd_need -= even_need;
                even_need = 0;
                
                // 남은 홀수 물주기 횟수 처리
                // (홀수 날 물주고, 짝수 날 쉬는 것을 반복)
                days += (odd_need * 2) - 1;

            } else if (even_need > odd_need) {
                // 홀수 물주기 횟수만큼 -> 2 * odd_needs 일
                days += 2 * odd_need;
                even_need -= odd_need;
                odd_need = 0;

                // 남은 짝수 물주기 횟수 처리
                // (휴식, +2)2일, (+2, 휴식)2일, (+1, +2),(+1 +2)4일
                // 3일마다 2번의 짝수 물주기를 할 수 있으므로 남은 짝수 물주기 횟수를 3으로 나눈 몫과 나머지를 이용해 날짜를 계산
                days += (even_need / 3) * 4;
                int remain_even = even_need % 3;
                if (remain_even == 1) {//(휴식 , +2) 2일
                    days += 2;
                } else if (remain_even == 2) {//(+1, +2, +1)->3일, 
                    days += 3;
                }
            } else {
                // 횟수가 같으면 1과 2를 번갈아 물주기
                days = 2 * odd_need;
            }

            System.out.println("#" + tc + " " + days);
        }
    }
}