package week10_03.sjh1108.BOJ_1503;
import java.io.*;
import java.util.*;

// Baekjoon Online Judge 1503번 - 세 수 고르기
class Main {
    public static void main(String[] args) throws IOException {
        // 빠른 입력을 위해 BufferedReader 사용
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 첫 줄에서 N과 M을 공백으로 구분하여 입력받음
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken()); // 목표값 N
        int M = Integer.parseInt(st.nextToken()); // 집합 S의 크기 M

        // 집합 S에 속한 숫자를 빠르게 확인하기 위한 boolean 배열
        // arr[x]가 true이면, 숫자 x는 집합 S에 포함되어 있음을 의미
        // 배열 크기를 1002로 하여 인덱스 1001까지 안전하게 접근 가능
        boolean[] arr = new boolean[1002];

        // M이 0보다 큰 경우에만 집합 S의 원소를 입력받음
        if (M > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                // S에 포함된 수를 입력받아 해당 인덱스를 true로 설정
                arr[Integer.parseInt(st.nextToken())] = true;
            }
        }

        // |N - xyz|의 최솟값을 저장할 변수, 비교를 위해 충분히 큰 값으로 초기화
        int min = 1_000_000_000;

        /*
         * --- 핵심 최적화 (가지치기, Pruning) ---
         * 우리가 찾는 세 수를 i, j, k 라고 하고, 중복 계산을 피하기 위해 i <= j <= k 관계를 가정합니다.
         *
         * 만약 가장 작은 두 수 i와 j가 모두 52 이상이라면?
         * -> i >= 52, j >= 52 이므로 k >= 52가 됩니다.
         * 이 경우 세 수의 곱(i*j*k)이 가질 수 있는 가장 작은 값은 52 * 52 * 52 = 140,608 입니다.
         *
         * 문제에서 N의 최댓값은 1,000 입니다.
         * 따라서 |N - (i*j*k)|의 차이는 최소 |1000 - 140608| = 139,608 이 됩니다.
         * 이 값은 1*1*1 과 같은 훨씬 간단한 조합으로 만들 수 있는 차이보다 터무니없이 큽니다.
         * 즉, i와 j가 52 이상인 경우는 절대로 최적의 해가 될 수 없습니다.
         *
         * 결론: 따라서 최적해를 만드는 세 수 중 가장 작은 두 수(i, j)는
         * 반드시 52보다 작아야 합니다. 이 원리를 이용해 탐색 범위를 대폭 줄여 시간 초과를 피합니다.
         */

        // 가장 작은 수 i (위의 원리에 따라 51까지만 탐색)
        for (int i = 1; i < 52; i++) {
            // 만약 i가 집합 S에 포함된 수라면 건너뜀
            if (arr[i]) continue;

            // 두 번째 수 j (중복을 피하기 위해 i부터 시작하며, 마찬가지로 51까지만 탐색)
            for (int j = i; j < 52; j++) {
                // 만약 j가 집합 S에 포함된 수라면 건너뜀
                if (arr[j]) continue;

                // 세 번째 수 k (중복을 피하기 위해 j부터 시작)
                // k의 범위를 1001까지 하는 이유:
                // 문제 조건상 집합 S에는 1000 이하의 수만 들어가므로, 1001은 절대 S에 있을 수 없는 '안전한' 숫자이기 때문.
                for (int k = j; k <= 1001; k++) {
                    // 만약 k가 집합 S에 포함된 수라면 건너뜀
                    if (arr[k]) continue;

                    // 세 수의 곱과 N의 차이(절대값)를 계산하여 최솟값을 갱신
                    min = Math.min(min, Math.abs(N - (i * j * k)));
                }
            }
        }

        // 최종적으로 찾은 최솟값 출력
        System.out.println(min);
    }
}

// 참조 : https://www.acmicpc.net/source/84154141