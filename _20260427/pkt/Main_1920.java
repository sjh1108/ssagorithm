package feature04_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_1920 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] arr1 = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr1[i] = Integer.parseInt(st.nextToken());
        }

        int M = Integer.parseInt(br.readLine());
        int[] arr2 = new int[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            arr2[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr1);

        for (int num : arr2) {
            if (bin(arr1, num)) System.out.println(1);
            else System.out.println(0);
        }
    }

    private static boolean bin(int[] arr, int t) {
        int l = 0;
        int r = arr.length - 1;

        while (l <= r) {
            int m = (l + r) / 2;
            if(arr[m] == t) return true;
            else if ((arr[m] < t)) l = m + 1;
            else r = m - 1;
        }

        return false;
    }
}

// [풀이전략]
// 이진 탐색

// [구현 순서]

// [실수]

// [코드 최적화]
