package _20261019.jun.OJ_101;

import java.io.*;
import java.util.*;

// OJ_101 복도 띠 타일
// 실버, 백트래킹
public class Main {

    static int L, output;
    static final int k = 5;
    static int[] c = new int[k + 1]; // i == 타일길이, c[i] == 타일개수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력
        L = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= k; i++) {
            c[i] = Integer.parseInt(st.nextToken());
        }

        dfs(0);

        System.out.println(output);
    }

    /*
     백트래킹
     두 방법은 왼쪽부터 읽은 타일 길이의 순서가 다르면 서로 다른 방법.
     */
    static void dfs(int sum) {
        // 복도를 빈틈 없이 채움
        if (sum == L) {
            output++;
            return;
        }

        for (int cur = 1; cur <= k; cur++) {
            // 타일 재고가 없거나, 복도 길이 초과.
            if (c[cur] == 0 || sum + cur > L) {
                continue;
            }

            c[cur]--; // 타일 1장 사용.
            dfs(sum + cur);
            c[cur]++;
        }
    }
}
