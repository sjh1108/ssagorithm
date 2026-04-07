package _20260406.yong;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11054_이용호 {
/*
 * LIS(최장 증가 부분 수열)
 * 수열 A의 크기 N
 * 바이토닉 수열 = 0부터 i 까지 LIS + N부터 i 까지 LIS
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] nums = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            nums[i] = Integer.parseInt(st.nextToken());
        }

        int[] dp1 = new int[N];
        int[] dp2 = new int[N];

        // nums i번째 보다 작은 수중 가장 긴 수열
        // 왼쪽 -> 오른쪽
        for(int i = 0; i < N; i++){
            dp1[i] = 1;
            for(int j = 0; j < i; j++){
                if(nums[i] > nums[j]){
                    dp1[i] = Math.max(dp1[j] + 1, dp1[i]);
                }
            }
        }

        // 오른쪽 -> 왼쪽
        for(int i = N-1; i >= 0; i --){
            dp2[i] = 1;
            for(int j = N-1; j > i; j--){
                if(nums[i] > nums[j]){
                    dp2[i] = Math.max(dp2[i], dp2[j] + 1);
                }
            }
        }
        int res = 0;
        for(int i = 0; i < N; i++){
            res = Math.max(res, dp1[i] + dp2[i] - 1); // dp1, dp2는 각각 자신을 포함한 최장길이이므로 하나를 빼줘야함
        }
        System.out.println(res);
    }

}
