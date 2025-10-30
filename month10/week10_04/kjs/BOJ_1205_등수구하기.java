package Test;

import java.io.*;
import java.util.*;

public class BOJ_1205_등수구하기 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int s = Integer.parseInt(st.nextToken());
        int p = Integer.parseInt(st.nextToken());

        int[] a = new int[n];
        if (n > 0) {
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) a[i] = Integer.parseInt(st.nextToken());
        }
        if (n == 0) {
        	// 비어있을 때
            System.out.println(1);
            return;
        }

        if (n == p && s <= a[n - 1]) {
        	// 랭킹 차 있음, 점수 갱신 불가
            System.out.println(-1);
            return;
        }

        int rank = 1;
        for (int i = 0; i < n; i++) {
            if (a[i] > s) rank++;
            else break;
        }

        System.out.println(rank);
    }
}

