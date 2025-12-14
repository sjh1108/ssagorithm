package week12_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


// 랜선자르기 실버2
public class Main_1654 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");

        int K = Integer.parseInt(st.nextToken()); // 이미 가지고 있는 랜선의 개수
        int N = Integer.parseInt(st.nextToken()); // 필요한 랜선의 개수

        int[] cables = new int[K];
        long max = 0;

        for (int i = 0; i < K; i++) {
            cables[i] = Integer.parseInt(br.readLine());
            if (max < cables[i]) {
                max = cables[i];
            }
        }

        // 이분 탐색 시작
        // 길이는 자연수이므로 min은 1부터 시작
        long min = 1;
        
        while (min <= max) {
            long mid = (min + max) / 2;
            long count = 0;

            // 현재 자르려는 길이(mid)로 만들어지는 랜선 개수 구하기
            for (int i = 0; i < cables.length; i++) {
                count += (cables[i] / mid);
            }

            // 개수가 목표값 N보다 작다면? -> 너무 길게 잘랐음 (길이를 줄여야 함)
            if (count < N) {
                max = mid - 1;
            } 
            // 개수가 목표값 N보다 크거나 같다면? -> 더 길게 자를 수 있는지 확인 (길이를 늘려봄)
            else {
                min = mid + 1;
            }
        }

        // 반복문이 끝나면 max가 만들 수 있는 최대 길이가 됨
        System.out.println(max);
    }
}