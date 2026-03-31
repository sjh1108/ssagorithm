package Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class BOJ_2993_세부분_S5 {
    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine().trim();
        char[] arr = s.toCharArray();
        int n = arr.length;

        char[] best = new char[n];
        boolean isFirst = true;

        for (int i = 1; i <= n - 2; i++) {
            for (int j = i + 1; j <= n - 1; j++) {

                char[] candi = new char[n];
                int idx = 0;

                // 첫 부분 뒤집기
                for (int p = i - 1; p >= 0; p--) {
                	candi[idx++] = arr[p];
                }

                // 두 번째 부분 뒤집기
                for (int p = j - 1; p >= i; p--) {
                	candi[idx++] = arr[p];
                }

                // 세 번째 부분 뒤집기
                for (int p = n - 1; p >= j; p--) {
                	candi[idx++] = arr[p];
                }

                if (isFirst) {
                    // 첫 번째 후보는 그냥 저장
                    for (int k = 0; k < n; k++) {
                        best[k] = candi[k];
                    }
                    isFirst = false;
                } else {
                    // 사전순 비교
                    boolean Update = false;

                    for (int k = 0; k < n; k++) {
                        if (candi[k] < best[k]) {
                        	Update = true;
                            break;
                        } else if (candi[k] > best[k]) {
                            break;
                        }
                    }

                    if (Update) {
                        for (int k = 0; k < n; k++) {
                            best[k] = candi[k];
                        }
                    }
                }
            }
        }

        System.out.println(new String(best));
    }
}
