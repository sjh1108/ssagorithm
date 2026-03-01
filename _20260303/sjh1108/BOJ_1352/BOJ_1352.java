package _20260303.sjh1108.BOJ_1352;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    private static int num;
    private static boolean check = false;
    
    private static int[] alpha = new int[27];
    private static int[] ans = new int[27];
    private static char[] res;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        num = Integer.parseInt(br.readLine());

        res = new char[Math.max(101, num + 1)];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 18; i++) {
            back(i, 1, 0);

            if (check) {
                for (int j = 1; j <= i; j++) {
                    res[ans[j]] = (char) ('A' + j - 1);
                }

                int cnt = 1;
                for (int j = 1; j <= i; j++) {
                    while (ans[j] != 1) {
                        if (res[cnt] == 0) {
                            res[cnt] = (char) ('A' + j - 1);
                            ans[j]--;
                            cnt++;
                        } else {
                            cnt++;
                        }
                    }
                }

                for (int j = 1; j <= num; j++) {
                    if (res[j] == 0) {
                        sb.append('0');
                    } else {
                        sb.append(res[j]);
                    }
                }

                System.out.print(sb.toString());
                return;
            }
        }

        sb.append("-1");
        System.out.print(sb.toString());
    }

    private static void back(int len, int pos, int sum) {
        if (len + 1 == pos) {
            if (sum == num) {
                check = true;
                System.arraycopy(alpha, 0, ans, 0, alpha.length);
            }

        } else {
            for (int i = alpha[pos - 1] + 1; i <= sum + 1; i++) {
                if (sum + i > num) {
                    break;
                }
                alpha[pos] = i;
                back(len, pos + 1, sum + i);
            }
        }
    }
}
