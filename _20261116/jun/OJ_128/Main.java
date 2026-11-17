package _20261116.jun.OJ_128;

import java.io.*;
import java.util.*;

// OJ_128 나무 인형 포개기
// DP, 정렬
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[][] wh = new int[N][2];

        // 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            wh[i][0] = Integer.parseInt(st.nextToken());
            wh[i][1] = Integer.parseInt(st.nextToken());
        }

        int[] dp = new int[N]; // i번 인형이 가장 큰 인형일 때 포갤 수 있는 인형의 최대 개수
        Arrays.fill(dp, 1); // 기본 값 == 자기 자신 == 1개
        Arrays.sort(wh, (a, b) -> Integer.compare(a[0], b[0])); // 너비 기준 오름차순 정렬

        for (int i = 1; i < N; i++) {
            for (int j = 0; j < i; j++) {
                // 너비, 높이 비교 => 포개질 수 있는 조건 탐색
                if ((wh[i][0] > wh[j][0]) && (wh[i][1] > wh[j][1])) {
                    // 탐색 결과 중 최대 값만 저장 <= 이 부분 예시 1번으로는 안 잡혀서 고려를 못해서 찾는데 시간이 걸림.
                    if (dp[i] < dp[j] + 1) {
                        // 지금까지 포개진 인형 갯수 + 1(지금 인형)
                        dp[i] = dp[j] + 1;
                    }
                }
            }
        }

        // 최대 개수 비교
        int maxCount = 1;
        for (int i = 1; i < N; i++) {
            if (dp[i] > maxCount) {
                maxCount = dp[i];
            }
        }

        // 출력
        System.out.println(maxCount);
    }
}
