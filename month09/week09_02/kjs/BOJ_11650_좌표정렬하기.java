package algorithm.d0907;

import java.io.*;
import java.util.*;

public class BOJ_11650_좌표정렬하기 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[][] p = new int[N][2];

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            p[i][0] = Integer.parseInt(st.nextToken());
            p[i][1] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(p, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0])
                                              : Integer.compare(a[1], b[1]));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) sb.append(p[i][0]).append(' ').append(p[i][1]).append('\n');
        System.out.print(sb.toString());
    }
}
