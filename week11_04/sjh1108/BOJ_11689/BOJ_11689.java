package week11_04.sjh1108.BOJ_11689;

import java.io.*;

// 백준 11689 - GCD(n, k) = 1 (오일러 피 함수)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    
        // n의 범위가 10^12까지이므로 long 타입 사용
        // 문제: 1 <= k <= n 인 정수 k 중 n과 서로소인 것의 개수 구하기
        long n = Long.parseLong(br.readLine());
        
        // 오일러 피 함수(Euler's Totient Function) φ(n) 계산
        // 공식: φ(n) = n * (1 - 1/p1) * (1 - 1/p2) ... * (1 - 1/pk)
        // (여기서 p1, p2...는 n의 서로 다른 소인수)
        long p = n; // 결과값 초기화 (공식의 맨 앞 n에 해당)

        // 2부터 √n까지 반복하며 소인수 탐색
        // (어떤 수 n이 합성수라면 √n 이하의 소인수를 반드시 가짐)
        for(long i=2; i <= Math.sqrt(n); i++) {
            // i가 n의 약수(소인수)라면
            if(n % i == 0) {
                // 오일러 피 함수 공식 적용: p = p * (1 - 1/i)
                // 이를 정수 연산으로 변환하면: p = p * (i-1) / i
                p = p / i * (i - 1);
                
                // n에서 해당 소인수(i)를 모두 제거
                // (소인수분해의 과정: i로 나누어 떨어지는 동안 계속 나눔)
                while(n % i == 0)
                    n /= i;
            }
        }
        
        // 반복문이 끝난 뒤에 n이 1이 아니라면,
        // 남은 n은 처리되지 않은 마지막 소인수임 (√n보다 큰 소인수)
        // 예: n이 10일 때, 2 처리 후 n=5가 남음. 5는 소수이므로 처리 필요.
        if(n != 1) {
            p = p / n * (n - 1);
        }
        
        // 결과 출력 (n과 서로소인 수의 개수)
        System.out.println(p);
    }
}