package week11_04.sjh1108.BOJ_15824;
import java.io.*;
import java.util.*;

// 백준 15824 - 너 봄에는 캡사이신이 맛있단다
// (수학, 정렬, 조합론, 모듈러 연산)
class Main {
    private static final int MOD = 1_000_000_007;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 메뉴(스코빌 지수)의 개수

        long[] arr = new long[N]; // 스코빌 지수를 저장할 배열

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            arr[i] = Long.parseLong(st.nextToken());
        }

        // [핵심 1] 정렬
        // 배열을 오름차순으로 정렬합니다.
        // 정렬을 하면 특정 원소 arr[i]에 대해:
        // - 그 앞에 있는 i개의 원소들은 모두 arr[i]보다 작거나 같습니다.
        // - 그 뒤에 있는 (N-1-i)개의 원소들은 모두 arr[i]보다 크거나 같습니다.
        Arrays.sort(arr);

        // [핵심 2] 2의 거듭제곱 미리 계산 (Precomputing Powers of 2)
        // 부분집합의 개수를 구할 때 2^k 형태의 계산이 반복적으로 필요합니다.
        // 매번 계산하지 않고 모듈러 연산을 적용하여 미리 저장해둡니다.
        long[] num = new long[N];
        num[0] = 1; // 2^0 = 1
        for(int i = 1; i < N; i++){
            num[i] = (num[i - 1] * 2) % MOD; // 이전 값 * 2
        }

        long sum = 0; // 최종 정답(모든 주헌고통지수의 합)

        // 모든 원소를 순회하며, 해당 원소가 '최댓값'일 때와 '최솟값'일 때를 계산해 더합니다.
        for(int i = 0; i < N; i++){
            // 1. arr[i]가 부분집합의 '최댓값'이 되는 경우
            // arr[i]보다 작은 원소들(인덱스 0 ~ i-1, 총 i개)로 만들 수 있는 부분집합의 개수만큼
            // arr[i]는 최댓값으로 더해집니다.
            // 개수: 2^i  => 코드상 num[i]
            long max = arr[i] * num[i]; 
            
            // 모듈러 연산을 중간에 적용해주면 좋습니다 (오버플로우 방지)
            // max %= MOD; 

            // 2. arr[i]가 부분집합의 '최솟값'이 되는 경우
            // arr[i]보다 큰 원소들(인덱스 i+1 ~ N-1, 총 N-1-i개)로 만들 수 있는 부분집합의 개수만큼
            // arr[i]는 최솟값으로 빼집니다.
            // 개수: 2^(N-1-i) => 코드상 num[N-1-i]
            long min = arr[i] * num[N-1 - i] % MOD;

            // 3. 누적 합 계산
            // 주헌고통지수 = (부분집합의 최댓값 - 부분집합의 최솟값)
            // 따라서 총합에는 (max 기여분)은 더하고 (min 기여분)은 뺍니다.
            // (sum + max - min) 계산 시 음수가 될 수 있으므로 주의해야 하지만,
            // 일단 연산 후 마지막 출력에서 처리하는 방식을 사용했습니다.
            sum = (sum + max - min) % MOD;
        }

        // 자바에서 % 연산은 음수 결과를 낼 수 있습니다. (예: -5 % 3 = -2)
        // 따라서 결과에 MOD를 더한 뒤 다시 % MOD를 하여 양수로 변환해 줍니다.
        System.out.println((sum + MOD) % MOD);
    }
}