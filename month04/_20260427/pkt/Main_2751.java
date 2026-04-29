package feature04_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

// 수 정렬하기 2 실버5
public class Main_2751 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int N = Integer.parseInt(br.readLine());


        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        Arrays.sort(arr);

        for (int i = 0; i < N; i++) {
            sb.append(arr[i]).append('\n');
        }

        System.out.println(sb);
    }
}

// [풀이전략]
// 정렬에 대한 고민
// 기존 간단풀이법에서 최적화된 풀이법 고민.

// [구현 순서]
// 첫째 줄에 수의 개수 N(1 ≤ N ≤ 1,000,000)이 주어진다.
// 둘째 줄부터 N개의 줄에는 수가 주어진다. 이 수는 절댓값이 1,000,000보다 작거나 같은 정수이다.
// 수는 중복되지 않는다.
// 참고사안.


// [실수]

// [코드 최적화]
// Arrays.sort() -> O(N) 편하지만, 최악의 수에는 문제 가능함.
//int N = Integer.parseInt(br.readLine());
//
//
//int[] arr = new int[N];
//        for (int i = 0; i < N; i++) {
//arr[i] = Integer.parseInt(br.readLine());
//        }
//
//        Arrays.sort(arr);
//
//        for (int i = 0; i < N; i++) {
//        sb.append(arr[i]).append('\n');
//        }
//
//                System.out.println(sb);

// Collections.sort() — 최악에도 O(N log N)이 보장 대신 메모리를 더 먹음
// 카운팅 정렬 — 이 문제 최적해 - 이해가 필요함, 직관 딸림.
//BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//StringBuilder sb = new StringBuilder();
//
//final int OFFSET = 1_000_000; // 언더스코어.
//final int SIZE = 2_000_001; // -1,000,000 ~ 1,000,000
//
//int N = Integer.parseInt(br.readLine());
//boolean[] count = new boolean[SIZE]; // 인덱스 범위를 커버 치기 위해 이게 들억감.
//
//        for (int i = 0; i < N; i++) {
//int num = Integer.parseInt(br.readLine());
//count[num + OFFSET] = true;
//        }

// 병합정렬 - 정렬 알고리즘 하나 구현