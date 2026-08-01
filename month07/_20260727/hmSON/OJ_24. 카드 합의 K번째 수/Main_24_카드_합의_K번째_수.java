import java.io.*;
import java.util.*;

public class Main_24_카드_합의_K번째_수 {

    /*
     * 자료구조 및 알고리즘 : 정렬, 이분 탐색, 매개변수 탐색, 투 포인터
     *
     * 카드 N장에서 서로 다른 두 장의 카드 조합을 전부 오름차순으로 나열했을 때, K번째로 작은 값을 구하여라
     * #23번 문제에서는 최대값 X가 주어졌을 때 이에 충족하는 조합의 수를 구하는 문제였음
     * 이 방식을 그대로 이용해 임의의 최대값 X를 던져서 조건을 충족하는 조합의 수가 K개 또는 그 이상인지 확인할 것임
     * 요약 : 두 수의 합이 X 이하인 조합의 수가 K개 이상이라고 할 때, 이를 충족하는 X의 최소값을 구하여라
     *
     * 임의의 최대값 X를 던졌을 때
     * 구성 가능한 조합이 K개 미만이면 K번째 값은 최대값 X보다 크다는 점을 알 수 있음
     * 구성 가능한 조합이 K개 또는 그 이상이면 K번째 값은 최대값 X 이하라는 점을 알 수 있음
     *
     * 임의의 최대값 X를 던졌을 때 조건을 충족하는 조합의 수는 #23번 문제의 방식을 그대로 사용
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        long[] arr = new long[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(arr);
        // 서로 다른 두 카드의 조합으로 만들 수 있는 최소값과 최대값을 탐색 범위의 양 끝으로 지정
        long min = arr[0] + arr[1], max = arr[n-2] + arr[n-1];
        while(min <= max) {
            // 두 끝의 중앙값을 임의의 최대값 X로 지정
            long mid = min + (max-min)/2;

            long cnt = 0;
            // 여기부터는 #23번 문제의 풀이와 동일
            // 투포인터를 이용해 left 카드와 right 카드의 합이 최대값 X 이하려면 right와 left의 차만큼 카운트
            int left = 0, right = n-1;
            while(left < right) {
                long sum = arr[left] + arr[right];
                if(sum > mid) right--;
                else {
                    cnt += right - left;
                    left++;
                }
            }

            // cnt : 조건을 충족하는 조합의 수
            // 조합의 수가 K 이상이면 max 범위를 축소, K 미만이면 min 범위를 축소
            if(cnt >= k) max = mid - 1;
            else min = mid + 1;
        }

        System.out.println(min);
    }

}