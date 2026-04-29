package baek.d20260413;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11401_이용호 {
	/*
	 * 자연수 N, 정수 K가 주어졌을 때 이항 계수 nCk를 1,000,000,007로 나눈 나머지를 구하는 문제
	 *
	 * 이항 계수 공식
	 * nCk = n! / (k! * (n-k)!)
	 *
	 * 하지만 모듈러 연산에서는 나눗셈을 직접 할 수 없으므로
	 * 분모의 역원을 곱하는 방식으로 바꿔야 한다.
	 *
	 * B = k! * (n-k)! 라고 하면
	 * nCk = n! / B
	 *     = n! * B^(-1)   (mod MOD)
	 *
	 * 여기서 B^(-1)은 B의 모듈러 역원이다.
	 * 모듈러에서 역원이란
	 * a * x ≡ 1 (mod p)
	 * 를 만족하는 x를 말한다.
	 *
	 * 문제의 MOD = 1,000,000,007은 소수이므로
	 * 페르마의 소정리를 사용할 수 있다.
	 *
	 * a^(p-1) ≡ 1 (mod p)
	 * => a^(p-2) ≡ a^(-1) (mod p)
	 *
	 * 따라서
	 * B^(-1) = B^(MOD-2)
	 *
	 * 최종적으로
	 * nCk = n! * (k! * (n-k)!)^(MOD-2) % MOD
	 */
	static final long MOD = 1000000007L;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		long factN = factorial(N);
		long factK = factorial(K);
		// N - K 팩토리얼
		long factNMK = factorial(N - K);
		
		// B구하기 B = k! * (n-k)!
		long deno = (factK * factNMK) % MOD;
		long inverse = pow(deno, MOD - 2);
		
		long res = (factN * inverse) % MOD;
		System.out.println(res);
	}
	/* 
	 * 최종적으로 필요한 값은 n! % MOD 이다.
	 * 모듈러 곱셈 성질:
	 * (a * b) % MOD = ((a % MOD) * (b % MOD)) % MOD
	 * 따라서 매번 % MOD를 하면서 곱해도
     * 마지막에 한 번만 % MOD 한 결과와 동일하다.
     * 또한 수가 너무 커지는 것도 막을 수 있다.
     */
	static long factorial(int n) {
	    long result = 1;
	    for (int i = 2; i <= n; i++) {
	        result = (result * i) % MOD;
	    }
	    return result;
	}
	/* Math.pow는 double을 반환하고, 모듈러 거듭제곱이 아니므로 사용할 수 없다.
	* 분모의 역원은 페르마의 소정리에 따라 denominator^(MOD-2) % MOD 로 구해야 하므로
	* 직접 구현한 분할 정복 거듭제곱 함수를 사용한다.
	* 
	* 분할 정복을 이용한 빠른 거듭제곱
	* a^exp % MOD 를 계산한다.
	*
	* exp가 짝수이면
	* a^exp = (a^(exp/2))^2
	*
	* exp가 홀수이면
	* a^exp = (a^(exp/2))^2 * a
	*
	* 지수를 절반씩 줄여가므로 O(log exp)에 계산 가능하다.
	*/
	static long pow(long a, long exp) {
        if (exp == 0) return 1;

        long half = pow(a, exp / 2);
        long result = (half * half) % MOD;
        if (exp % 2 == 1) {
            result = (result * a) % MOD;
        }
        return result;
    }

}
