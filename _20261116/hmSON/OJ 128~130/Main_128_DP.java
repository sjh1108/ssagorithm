import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : LIS(최장 증가 부분 수열), 정렬, DP
     *
     * dp[i] : i번째 인형을 가장 바깥에 둘 때 포갤 수 있는 최대 개수
     * 너비 기준으로 정렬해두면, i번째 인형 안에 들어갈 수 있는 후보는 항상 앞쪽에만 존재
     * -> 앞선 인형 중 너비와 높이가 모두 작은 것 뒤에 이어 붙이며 최댓값 갱신
     *
     * 두 조건을 모두 직접 비교하므로 너비가 같은 인형의 정렬 순서는 신경 쓰지 않아도 됨
     *
     * 시간 복잡도 : O(N^2)
     * N이 최대 3,000이라 약 450만 회 비교로 충분
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[][] dolls = new int[n][2]; // { 너비, 높이 }
        StringTokenizer st;
        for(int i=0; i<n; i++) {
            st = new StringTokenizer(br.readLine());
            dolls[i][0] = Integer.parseInt(st.nextToken());
            dolls[i][1] = Integer.parseInt(st.nextToken());
        }

        // 너비 오름차순
        Arrays.sort(dolls, (a, b) -> Integer.compare(a[0], b[0]));

        int[] dp = new int[n];
        int max = 0;
        for(int i=0; i<n; i++) {
            dp[i] = 1; // 자기 혼자만 있는 경우
            for(int j=0; j<i; j++) {
                // 너비와 높이가 모두 엄격히 작은 인형만 안에 넣을 수 있음
                if(dolls[i][0] > dolls[j][0] && dolls[i][1] > dolls[j][1]) dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            if(dp[i] > max) max = dp[i];
        }
        System.out.println(max);
    }

}