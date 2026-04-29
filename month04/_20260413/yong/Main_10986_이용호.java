package baek.d20260413;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10986_이용호 {
/*
 * 부분 구간합이 M으로 나눠지는 구간의 개수
 * 수는 N 개가 주어진다. 1 ~ N까지 누적합을 구해두면 구간합을 구할 수 있음
 * N = 1,000,000
 *  
 * 풀이법
 * 누적합을 S[x] 라고 하자
 * 구간 l 부터 r 까지 합은 S[r] - S[l-1]로 표현할 수 있다.
 * 수식으로 표현하면 (S[r] - S[l-1]) % M = 0 이 되어야 한다.
 * 위 식을 바꾸면 S[r] % M = S[l-1] % M 이고 이는 나머지가 같은 두 누적합을 고르면 두 사이 구간합은 M의 배수임을 의미하게 된다.
 * 
 */
	public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long[] count = new long[M]; // 각 나머지가 나온 횟수
        long sum = 0;
        long res = 0;

        // 누적합이 0인 상태를 미리 1개 넣어둠
        count[0] = 1;

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            sum += Integer.parseInt(st.nextToken());
            int mod = (int)(sum % M);
            // 이전에 있던 같은 나머지들과 각각 짝을 하나씩 만들 수 있으므로
            // 같은 나머지가 나온 횟수만큼 증가
            res += count[mod];
            
            // 현재 나머지 개수 증가
            count[mod]++;
        }

        System.out.println(res);
    }

}
