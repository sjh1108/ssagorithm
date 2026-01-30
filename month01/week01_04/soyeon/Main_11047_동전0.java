package week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 문제 이해
 * - 동전 N종류
 * - 가치의 합을 K로 만들고자함
 * - 이때 필요한 동전 개수의 초솟값 구하기
 *
 * 입력
 * - 첫째줄에 N과 K  (1 ≤ N ≤ 10, 1 ≤ K ≤ 100,000,000)
 * - 둘째 줄부터 N개의 줄에 동전의 가치가 오름차순으로 주어짐
 * - (1 ≤ Ai ≤ 1,000,000, A1 = 1, i ≥ 2인 경우에 Ai는 Ai-1의 배수)
 *
 * 전략
 * - 동전의 값들이 배수이므로 그리디적으로 가도딜듯?
 */
public class Main_11047_동전0 {
    static int N, K;
    static int[] val;
    static int cnt;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        val = new int[N];

        for(int i = 0; i < N; i++){
            val[i] = Integer.parseInt(br.readLine());
        }// 입력끝

        a : for(int i = N-1; i >= 0; i--){
            while(K >= val[i]){
                if(K == 0){
                    break a;
                }
                K -= val[i];
                cnt++;

            }
        }

        System.out.println(cnt);
    }
}
