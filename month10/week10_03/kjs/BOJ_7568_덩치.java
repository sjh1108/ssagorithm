package day1020;


import java.io.*;
import java.util.*;

public class BOJ_7568_덩치 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        int[][] body = new int[N][2]; // [i][0]=weight, [i][1]=height
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            body[i][0] = Integer.parseInt(st.nextToken()); // weight
            body[i][1] = Integer.parseInt(st.nextToken()); // height
        }

        int[] rank = new int[N];
        Arrays.fill(rank, 1); // 기본 등수 1

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                if (body[j][0] > body[i][0] && body[j][1] > body[i][1]) {
                    rank[i]++; // 자신보다 '덩치가 더 큰' 사람 수만큼 등수 증가
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            if (i > 0) sb.append(' ');
            sb.append(rank[i]);
        }
        System.out.println(sb.toString());
    }
}
