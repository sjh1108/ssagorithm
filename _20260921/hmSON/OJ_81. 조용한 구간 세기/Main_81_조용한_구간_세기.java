import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 누적합, 투 포인터, 슬라이딩 윈도우
    // 소음 총량의 합이 X 이하인 구간의 수를 카운트해야 함
    // 최대 200,000 크기의 측정 횟수가 주어지므로 모든 구간의 누적합을 기록 및 관리하면 비효율적
    // 슬라이딩 윈도우를 이용해 현재 구간의 소음 총량을 확인하여 작거나 같으면 확장, 크면 축소하는 방식으로 구간 수 카운트

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long limit = Long.parseLong(st.nextToken());

        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        long sum = 0, cnt = 0; // 구간 합과 구간 수 모두 int 범위 초과할 수 있음
        int left = 0, right = 0; // 슬라이딩 윈도우 구현을 위한 투 포인터
        while(right < n) {
            sum += arr[right++]; // 다음 구간의 소음량을 추가
            while(sum > limit) sum -= arr[left++]; // 현재 구간의 총량이 limit 이하가 될 때까지 left를 증가시켜 구간 축소

            // 현재 구간의 길이를 카운트
            // 의미 : 구간의 끝이 (right-1)이면서 소음량의 구간합이 limit 이하인 구간의 수
            cnt += right - left;
        }

        System.out.println(cnt);
    }

}