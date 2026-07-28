import java.io.*;
import java.util.*;

public class Main_25_두_상자의_카드_합의_K번째_수 {

    /*
     * 자료구조 및 알고리즘 : 정렬, 이분 탐색, 매개변수 탐색, 투 포인터
     *
     * 두 상자의 카드를 한 장씩 뽑아 만들 수 있는 모든 조합을 오름차순으로 나열했을 때, K번째로 작은 값을 구하여라
     * #24번 문제에서는 하나의 카드 뭉치에서 서로 다른 2장을 선택하는 상황이었음
     *
     * K번째로 작은 값을 구하기 위해 임의의 최대값 X를 던져 조건을 충족하는 조합의 수를 구하고 범위를 좁혀나가는 방식은 그대로 사용
     * 임의의 최대값 X를 던졌을 때
     * 구성 가능한 조합이 K개 미만이면 K번째 값은 최대값 X보다 크다는 점을 알 수 있음
     * 구성 가능한 조합이 K개 또는 그 이상이면 K번째 값은 최대값 X 이하라는 점을 알 수 있음
     *
     * #23, #24와 달리 두 상자에서 카드를 한 장씩 뽑는 형태이므로, 기존의 방식을 응용
     * 두 상자를 각각 정렬하고 left 포인터는 상자 1에, right 포인터는 상자 2에 붙인 뒤
     * 상자 1의 최소값과 상자 2의 최대값을 비교하는 방식으로 조합 수 카운트를 위한 연산을 최소화
     */

    static int n1, n2; // 상자 1과 2의 카드 수
    static long[] arr1, arr2; // 상자 1과 2

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n1 = Integer.parseInt(st.nextToken());
        n2 = Integer.parseInt(st.nextToken());
        // 만들 수 있는 조합의 최대 수는 N1 * N2이므로 int 범위 초과할 수 있음
        long k = Long.parseLong(st.nextToken());

        arr1 = new long[n1];
        arr2 = new long[n2];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n1; i++) {
            arr1[i] = Long.parseLong(st.nextToken());
        }
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n2; i++) {
            arr2[i] = Long.parseLong(st.nextToken());
        }

        Arrays.sort(arr1);
        Arrays.sort(arr2);
        // 만들 수 있는 조합의 최소값은 각 상자의 첫번째 카드 합, 최대값은 각 상자의 마지막 카드 합임
        long min = arr1[0] + arr2[0], max = arr1[n1-1] + arr2[n2-1];
        while(min <= max) {
            // 두 끝의 중앙값을 임의의 최대값 X로 지정
            long mid = min + (max-min)/2;

            // cnt : 조건을 충족하는 조합의 수
            // 조합의 수가 K 이상이면 max 범위를 축소, K 미만이면 min 범위를 축소
            long cnt = getCombiCnt(mid);
            if(cnt >= k) max = mid - 1;
            else min = mid + 1;
        }

        System.out.println(min);
    }

    // 임의의 최대값 X가 주어졌을 때 조건을 충족하는 조합 수를 카운트하는 메서드
    static long getCombiCnt(long x) {
        // left는 상자 1의 첫번째 카드부터 시작, right는 상자 2의 마지막 카드부터 시작
        int left = 0, right = n2-1;
        long cnt = 0;

        while(left < n1 && right >= 0) {
            long sum = arr1[left] + arr2[right];
            if(sum > x) right--;
            else {
                cnt += right+1;
                left++;
            }
        }

        return cnt;
    }

}