package OJ_98;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 회문 -> 완전 탐색
public class Main {

    static int N;
    static int[] c;
    static int output = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(bufferedReader.readLine());
        c =  new int[N];

        StringTokenizer stringTokenizer = new StringTokenizer(bufferedReader.readLine());
        for (int i = 0; i < N; i++) {
            c[i] =  Integer.parseInt(stringTokenizer.nextToken());
        }

        // 길이가 2 이상 대칭 구간 탐색.
        for (int i = 0; i < N; i++) {
            // 전체 길이 짝수 -> 중심
            count(i, i + 1);

            // 전체 길이 홀수 -> 중심 양 옆
            count(i - 1, i + 1);
        }

        System.out.println(output);
    }

    // 중심에 따른 대칭 구간 탐색.
    static void count(int l, int r) {
        while (l >= 0 && r < N) {
            if (c[l] != c[r]) {
                break;
            }

            output++;

            l--;
            r++;
        }
    }
}
