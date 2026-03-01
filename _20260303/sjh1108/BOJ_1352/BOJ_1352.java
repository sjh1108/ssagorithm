package _20260303.sjh1108.BOJ_1352;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    private static int num;
    private static boolean check = false;
    
    // alpha: 현재 분할(증가하는 수열) 저장, ans: 정답 분할 저장
    private static int[] alpha = new int[27];
    private static int[] ans = new int[27];
    // 최종 출력 문자열 버퍼
    private static char[] res;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        num = Integer.parseInt(br.readLine());

        // num 길이만큼의 결과를 구성(최소 101 길이 확보)
        res = new char[Math.max(101, num + 1)];

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < 18; i++) {
            // 길이 i의 증가 수열 분할을 백트래킹으로 탐색
            back(i, 1, 0);

            if (check) {
                // ans[j]는 j번째 문자(A, B, ...)의 등장 횟수
                for (int j = 1; j <= i; j++) {
                    res[ans[j]] = (char) ('A' + j - 1);
                }

                // 각 문자 등장 횟수를 위치에 배치
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
        // pos가 len+1이면 길이 len의 분할 완성
        if (len + 1 == pos) {
            if (sum == num) {
                check = true;
                System.arraycopy(alpha, 0, ans, 0, alpha.length);
            }

        } else {
            // 이전 값보다 큰 수를 선택해 증가 수열 유지
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
