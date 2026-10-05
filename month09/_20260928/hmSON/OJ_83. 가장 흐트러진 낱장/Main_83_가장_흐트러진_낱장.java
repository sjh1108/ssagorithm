import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 브루트 포스
    // 주어는 n의 입력값이 최대 3,000이므로 시간복잡도 O(n^2)으로도 충분히 문제를 해결할 수 있음
    // 각 숫자가 큰 쪽이든 작은 쪽이든, 엇갈린 쌍에 포함된다면 카운트한다
    // 2중 반복문으로 엇갈린 쌍이면 양쪽 숫자의 엇갈린 쌍 카운트를 증가시킨다

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n], cnt = new int[n]; // cnt : 각 수의 엇갈린 쌍 개수
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<n-1; i++) {
            for(int j=i+1; j<n; j++) {
                if(arr[i] > arr[j]) {
                    cnt[i]++; cnt[j]++; // 양쪽 모두 엇갈린 쌍 개수 카운트
                }
            }
        }

        // 엇갈린 쌍이 없을 경우 자동으로 처음인 1이 출력되게끔 maxIdx = 0으로 초기화
        int maxCnt = 0, maxIdx = 0;
        for(int i=0; i<n; i++) {
            if(cnt[i] > maxCnt) {
                maxCnt = cnt[i];
                maxIdx = i;
            }
        }

        // maxCnt == 0(엇갈린 쌍이 없는 경우) : maxIdx는 1로 출력
        System.out.println(maxCnt + " " + (maxIdx + 1));
    }

}