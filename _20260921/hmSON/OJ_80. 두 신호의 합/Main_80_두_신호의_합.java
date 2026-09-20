import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 정렬, 투 포인터
    // 서로 다른 두 신호의 세기 합이 t가 되는 순서쌍의 수를 구해야 함
    // 각 신호의 순서를 세기 오름차순으로 정렬하고 투 포인터를 이용해 탐색 범위를 좁혀야 함
    // 단, 동일한 신호 세기가 여러 번 등장할 수 있음에 유의해야 함

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long t = Long.parseLong(st.nextToken());

        // 신호 세기를 입력받고 오름차순으로 정렬
        long[] arr = new long[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }
        Arrays.sort(arr);

        // 투 포인터 : 양쪽 끝에서부터 두 신호의 세기 합 확인
        // 합이 t보다 크면 우측 포인터 감소, 작으면 좌측 포인터 증가
        // 합이 t일 경우 ->
        // 1. 합을 이루는 두 신호의 세기가 다른가? -> 두 신호 크기의 개수를 각각 센 뒤 곱한 값이 순서쌍의 개수
        // 2. 합을 이루는 두 신호의 세기가 같은가? -> 해당 크기인 신호의 수를 x라 할 때, x(x-1)/2가 순서쌍의 개수

        int left = 0, right = n-1, total = 0; // 각각 양쪽 포인터, 순서쌍 개수
        // 서로 다른 두 신호의 순서쌍만 취급하므로 left == right는 취급하지 않음
        while(left < right) {
            long sum = arr[left] + arr[right]; // 두 신호의 세기 합
            // sum != t 인 경우 상황에 따라 포인터 이동
            if(sum > t) right--;
            else if(sum < t) left++;
            // sum == t 인 경우
            else {
                // 두 신호의 세기가 같은가?
                if(arr[left] == arr[right]) {
                    int len = right - left + 1;
                    total += len * (len - 1) / 2;
                    break; // 두 신호의 세기가 같으면 더 이상 투포인터는 필요 없음. 루프 종료
                }

                left++;
                right--;
                int leftCnt = 1, rightCnt = 1; // 각 신호와 동일한 세기를 가진 모든 신호의 수를 카운트

                while(arr[left] == arr[left-1]) {
                    leftCnt++;
                    left++;
                }

                while(arr[right] == arr[right+1]) {
                    rightCnt++;
                    right--;
                }

                total += leftCnt * rightCnt;
            }
        }

        System.out.println(total);
    }

}