package _20261116.jun.OJ_125;

import java.io.*;
import java.util.*;

// OJ_125 룬 석판의 공명 구간
// 실버, 누적xor, 브루트포스
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());
        int[] a = new int[N];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            a[i] = Integer.parseInt(st.nextToken());
        }

        int count = 0; // 공명값이 정확히 K 인 구간의 개수

        // 조건(l ≤ r) => 구간 l == r 가능
        // 예시에선 구간 l == r 이면 공명 값은 해당 구간의 룬 값.
        // x ^ 0 == x
        for (int i = 0; i < N; i++) {
            int accXOR = 0; // 누적 XOR
            for (int j = i; j < N; j++) {
                accXOR ^= a[j];
                if (accXOR == K) count++;
            }
        }

        System.out.println(count);
    }
}
