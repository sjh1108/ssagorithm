package week04.sjh1108.BOJ_7570;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_7570_송주헌 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        int max = 0;
        // 오름차순 부분 수열을 위한 DP 배열
        int[] arr = new int[N+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            int tmp = Integer.parseInt(st.nextToken());

            arr[tmp] = arr[tmp-1] + 1;
            // LIS 길이 구하기
            max = Math.max(arr[tmp], max);
        }

        // N - LIS 길이 = 최소로 제거해야 하는 학생 수
        System.out.println(N - max);
    }
}
