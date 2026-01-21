package Test;

import java.io.*;
import java.util.*;

public class BOJ_1755_숫자놀이_S4 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        StringTokenizer st = new StringTokenizer(br.readLine());
        int M = Integer.parseInt(st.nextToken());
        int N = Integer.parseInt(st.nextToken());

        // 영어 사전순으로 정렬된 0~9
        int[] dic = {8, 5, 4, 9, 1, 7, 6, 3, 2, 0};

        int cnt = 0;

        for (int i = 0; i < 10; i++) {
            int d = dic[i];

            if (d >= M && d <= N) {
                sb.append(d).append(' ');
                if (++cnt % 10 == 0) sb.append('\n');
            }

            for (int k = 0; k < 10; k++) {
                int ones = dic[k];
                int num = d * 10 + ones;

                if (num < 10 || num < M || num > N) continue;

                sb.append(num).append(' ');
                if (++cnt % 10 == 0) sb.append('\n');
            }
        }

        System.out.print(sb.toString());
    }
}
